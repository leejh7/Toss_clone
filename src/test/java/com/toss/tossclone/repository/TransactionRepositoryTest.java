package com.toss.tossclone.repository;

import com.toss.tossclone.constant.TossConstant;
import com.toss.tossclone.dao.RecentTransactionAccountDao;
import com.toss.tossclone.dto.MemberFormDto;
import com.toss.tossclone.entity.Account;
import com.toss.tossclone.entity.Member;
import com.toss.tossclone.entity.Transaction;
import com.toss.tossclone.exception.NotEnoughMoneyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.time.Month;
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

    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;

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

        Account senderAccount = Account.builder().name("돈 보내는 사람 계좌").member(sender).accountCode("1111-1111").balance(10000L).password("pw").build();
        Account receiverAccount = Account.builder().name("돈 받는 사람 계좌").member(receiver).accountCode("2222-2222").balance(10000L).password("pw").build();
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
        assertThat(accountRepository.findAccountByAccountCode("1111-1111").getBalance()).isEqualTo(5000L);
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
    @DisplayName("송금 거래 내역 조회")
    public void findTransactionBySenderAccountCode() throws Exception
    {
        // given
        this.createTransactionList();

        // when
        String targetAccountCode = "1111-1111";

        // then
        List<Transaction> transactionList = transactionRepository.findTransactionBySenderAccountCodeOrderByTransferTimeDesc(targetAccountCode);
        printTransactionList(transactionList, "[송금 내역]");
    }

    /**
     * <h3>테스트 내용</h3>
     * 1. 특정 계좌의 입금 거래 내역들을 확인<br>
     */
    @Test
    @DisplayName("입금 거래 내역 조회")
    public void findTransactionByReceiverAccountCode() throws Exception
    {
        // given
        this.createTransactionList();

        // when
        String targetAccountCode = "1111-1111";

        // then
        List<Transaction> transactionList = transactionRepository.findTransactionByReceiverAccountCodeOrderByTransferTimeDesc(targetAccountCode);
        printTransactionList(transactionList, "[송금 내역]");
    }

    @Test
    @DisplayName("전체 거래 내역 조회")
    public void findTransaction() throws Exception
    {
        // given
        this.createTransactionList();

        // when
        Account targetAccount = accountRepository.findAccountByAccountCode("1111-1111");

        List<Transaction> transactionList = transactionRepository.findTransactionByAccountCodeOrderByTransferTimeDesc(targetAccount.getAccountCode());
        printTransactionList(transactionList, "[전체 내역]");

        printDividerLine();

        for (Transaction transaction : transactionList) {
            System.out.println("보내는 사람: " + transaction.getSenderName() + " 거래 당시 남은 잔액: " + transaction.getSenderAccHisBal());
            System.out.println("받는 사람: " + transaction.getReceiverName());
        }
    }

    @Test
    @DisplayName("최근 보낸 계좌 조회")
    public void findRecentRecAcc() throws Exception
    {
        // given
        this.createTransactionList();

        Account senderAccount = accountRepository.findAccountByAccountCode("1111-1111");

        // when
        List<RecentTransactionAccountDao> results = transactionRepository.findRecentTransactionReceiverAccountByAccountCode(senderAccount.getAccountCode());

        printDividerLine();


        for (RecentTransactionAccountDao result : results) {
            System.out.println("result = " + result);
        }

    }

    /**
     * <h3>테스트 내용</h3>
     * 1. 특정 계좌의 일별 송금 내역들을 확인<br>
     */
    @Test
    @DisplayName("일별 송금 거래 내역 조회")
    public void findDailyTransaction() throws Exception
    {
        // given
        this.createTransactionList();
        Account targetAccount = accountRepository.findAccountByAccountCode("1111-1111");

        // when
        List<Long> dailyTransaction = transactionRepository.findDailyTransaction(targetAccount);

        // then
        for (Long amount : dailyTransaction) {
            System.out.println("amount = " + amount);
        }
    }
    
    @Test
    @DisplayName("무료 송금 횟수")
    public void useAllTransactionCount() throws Exception
    {
        // given
        this.createTransactionList();
        // when
        Member member1 = memberRepository.findByEmail("member1"+"@test.com");
        System.out.println("member1.getTransactionCount() = " + member1.getTransactionCount());

        Account member1Account = accountRepository.findAccountByAccountCode("1111-1111");
        Long oriBalance = member1Account.getBalance();
        Long amount = 10000L;
        Transaction exceptionTransaction = Transaction.createTransaction("member1", "member2", member1Account, accountRepository.findAccountByAccountCode("2222-2222"), amount, LocalDateTime.now(), "무료 수수료 끝남");
        Long newBalance = member1Account.getBalance();
        // then
        assertThat(newBalance).isEqualTo(oriBalance - amount - TossConstant.COMMISSION);
    }

    private void printTransactionList(List<Transaction> transactionList, String message) {
        System.out.println(message);
        for (Transaction transaction : transactionList) {
            System.out.println("받은 사람:" + transaction.getReceiverName() + " 금액: " + transaction.getAmount());
            System.out.println("보낸 사람 잔액: " + transaction.getSenderAccHisBal());
        }
    }

    private void createMemberList() {
        for(int i=1; i<=10; i++) {
            MemberFormDto memberFormDto = new MemberFormDto();
            memberFormDto.setName("member" + i);
            memberFormDto.setEmail("member" + i + "@test.com");
            memberFormDto.setPassword("password");
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberRepository.save(member);
        }
    }

    private void createAccountList() {
        for(int i=1; i<=10; i++) {
            Member member = memberRepository.findByEmail("member"+i+"@test.com");
            Account account = Account.builder().name("member"+i+"\'s Acc").member(member).accountCode((1111 * i) + "-" + (1111 * i)).balance(100000L).password("pw").build();
            accountRepository.save(account);
        }
    }

    private void createTransactionList() throws Exception {
        this.createMemberList();
        this.createAccountList();

        Account member1Account = accountRepository.findAccountByAccountCode("1111-1111");
        for(int i=2; i<=10; i++) {
            String accountCode = (1111 * i) + "-" + (1111 * i);
            Account account = accountRepository.findAccountByAccountCode(accountCode);
            Transaction sendTransaction = Transaction.createTransaction(
                    "member1", "member" + i, member1Account,
                    account, (long) ((Math.random() * 1000) + 1),
                    LocalDateTime.of(2022, Month.JULY, (int)(Math.random() * 3) + 1, 12, 0, 0), "[테스트용] 돈 보내기 메모" + i);
            Transaction receiveTransaction = Transaction.createTransaction(
                    "member" + i, "member1", account,
                    member1Account, 1000L,
                    LocalDateTime.of(2022, Month.JULY, (int)(Math.random() * 3) + 1, 12, 0, 0), "[테스트용] 돈 받기 메모" + i);
            transactionRepository.save(sendTransaction);
            transactionRepository.save(receiveTransaction);
        }

        Transaction transaction = Transaction.createTransaction("member2", "member3", accountRepository.findAccountByAccountCode("2222-2222"), accountRepository.findAccountByAccountCode("3333-3333"), 1000L, LocalDateTime.now(), "[테스트용] 출력 되면 안되는데...");
        transactionRepository.save(transaction);

        em.flush();
        em.clear();

        printDividerLine();
    }

    private void printDividerLine() {
        System.out.println("===============================================================");
    }

}