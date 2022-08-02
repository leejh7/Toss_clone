package com.toss.tossclone.repository;

import com.toss.tossclone.entity.Account;
import com.toss.tossclone.entity.Member;
import com.toss.tossclone.entity.Transaction;
import com.toss.tossclone.exception.NotEnoughMoneyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class TransactionRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    /**
     * <h3>테스트 내용</h3>
     * 1. 거래가 만들어질 때 sender name, receiver name이 제대로 저장되는지 확인<br>
     * 2. 거래가 만들어질 때 sender account, receiver account가 제대로 저장되는지 확인<br>
     * 3. sender account에 잔액이 부족하면 NotEnoughMoney 예외 발생하는지 확인<br>
     */
    @Test
    public void createTransactionTest() throws Exception
    {
        // given
        Member sender = Member.builder().name("돈 보내는 사람").build();
        Member receiver = Member.builder().name("돈 받는 사람").build();
        memberRepository.save(sender);
        memberRepository.save(receiver);

        Account senderAccount = Account.builder().member(sender).accountCode("1111-1111").balance(10000L).build();
        Account receiverAccount = Account.builder().member(receiver).accountCode("2222-2222").balance(10000L).build();
        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

        Transaction transaction = Transaction.createTransaction(sender.getName(), receiver.getName(), senderAccount, receiverAccount, 5000L, LocalDateTime.now(), "테스트용 거래");

        // when
        transactionRepository.save(transaction);

        // then
        assertThat(transaction.getSenderName()).isEqualTo(sender.getName());
        assertThat(transaction.getReceiverName()).isEqualTo(receiver.getName());
        assertThat(transaction.getSenderAccount()).isEqualTo(senderAccount);
        assertThat(transaction.getReceiverAccount()).isEqualTo(receiverAccount);
        Assertions.assertThrows(NotEnoughMoneyException.class, ()-> {
            Transaction exceptionTransaction = Transaction.createTransaction(sender.getName(), receiver.getName(), senderAccount, receiverAccount, 5001L, LocalDateTime.now(), "예외 발생용 거래");
            transactionRepository.save(exceptionTransaction);
        });
    }

    /**
     * <h3>테스트 내용</h3>
     * 1. 특정 계좌의 출금 거래 내역들을 확인<br>
     */
    @Test
    public void findTransactionBySenderAccount() throws Exception
    {
        // given
        this.createTransactionList();

        // when
        Account senderAccount = accountRepository.findAccountByAccountCode("1111-1111");

        // then
        List<Transaction> transactionList = transactionRepository.findTransactionBySenderAccount(senderAccount);
        printTransactionList(transactionList, "[송금 내역]");
    }

    /**
     * <h3>테스트 내용</h3>
     * 1. 특정 계좌의 입금 거래 내역들을 확인<br>
     */
    @Test
    public void findTransactionByReceiverAccount() throws Exception
    {
        // given
        this.createTransactionList();

        // when
        Account receiverAccount = accountRepository.findAccountByAccountCode("1111-1111");

        // then
        List<Transaction> transactionList = transactionRepository.findTransactionByReceiverAccount(receiverAccount);
        printTransactionList(transactionList, "[입금 내역]");
    }

    @Test
    public void findTransaction() throws Exception
    {
        // given
        this.createTransactionList();

        // when
        Account targetAccount = accountRepository.findAccountByAccountCode("1111-1111");

        // then
        List<Transaction> transactionList = transactionRepository.findTransactionBySenderAccountOrReceiverAccount(targetAccount, targetAccount);
        printTransactionList(transactionList, "[전체 내역]");
    }

    private void printTransactionList(List<Transaction> transactionList, String message) {
        System.out.println(message);
        for (Transaction transaction : transactionList) {
            System.out.println(transaction);
        }
    }

    private void createMemberList() {
        for(int i=1; i<=10; i++) {
            Member member = Member.builder().name("member" + i).build();
            memberRepository.save(member);
        }
    }

    private void createAccountList() {
        for(int i=1; i<=10; i++) {
            Member member = memberRepository.findMemberByName("member"+i);
            Account account = Account.builder().member(member).accountCode((1111 * i) + "-" + (1111 * i)).balance(100000L).build();
            accountRepository.save(account);
        }
    }

    private void createTransactionList() {
        this.createMemberList();
        this.createAccountList();

        Account member1Account = accountRepository.findAccountByAccountCode("1111-1111");
        for(int i=2; i<=10; i++) {
            String accountCode = (1111 * i) + "-" + (1111 * i);
            Account account = accountRepository.findAccountByAccountCode(accountCode);
            Transaction sendTransaction = Transaction.createTransaction(
                    "member1", "member" + i, member1Account,
                    account, 1000L, LocalDateTime.now(), "[테스트용] 돈 보내기 메모" + i);
            Transaction receiveTransaction = Transaction.createTransaction(
                    "member" + i, "member1", account, member1Account,
                    1000L, LocalDateTime.now(), "[테스트용] 돈 받기 메모" + i);
            transactionRepository.save(sendTransaction);
            transactionRepository.save(receiveTransaction);
        }

    }

}