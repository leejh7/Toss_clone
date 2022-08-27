package com.toss.tossclone.entity;

import com.toss.tossclone.constant.TossConstant;
import com.toss.tossclone.exception.NotEnoughTransactionCountException;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transactions")
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "transaction_key")
    private Long id;

    @Column(name = "sender_name", nullable = false)
    private String senderName;

    @Column(name = "receiver_name", nullable = false)
    private String receiverName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_account_key", referencedColumnName = "account_key", nullable = false)
    private Account senderAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_account_key", referencedColumnName = "account_key", nullable = false)
    private Account receiverAccount;

    @Column(nullable = false)
    private Long amount;

    @Column(name = "sender_account_history_balance", nullable = false)
    private Long senderAccHisBal;

    @Column(name = "receiver_account_history_balance", nullable = false)
    private Long receiverAccHisBal;

    @Column(name = "transfer_time", nullable = false)
    private LocalDateTime transferTime;

    @Lob
    private String memo;

    @Builder
    private Transaction(String senderName, String receiverName, Account senderAccount, Account receiverAccount, Long amount, Long senderAccHisBal, Long receiverAccHisBal, LocalDateTime transferTime, String memo) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
        this.senderAccHisBal = senderAccHisBal;
        this.receiverAccHisBal = receiverAccHisBal;
        this.transferTime = transferTime;
        this.memo = memo;
    }

    //==생성 메서드==//
    public static Transaction createTransaction(String senderName, String receiverName, Account senderAccount, Account receiverAccount,
                                                Long amount, LocalDateTime transferTime, String memo) {
        // TODO: TransactionFormDto 만들어서 파라미터 변경해주기

        // 보내는 사람의 계좌에서는 금액만큼 빼기
        Long restBalance = senderAccount.deductBalance(amount);
        // 받는 사람의 계좌에서는 금액만큼 더하기
        Long newBalance = receiverAccount.addBalance(amount);

        Transaction transaction = Transaction.builder()
                .senderName(senderName)
                .receiverName(receiverName)
                .senderAccount(senderAccount)
                .receiverAccount(receiverAccount)
                .amount(amount)
                .senderAccHisBal(restBalance)
                .receiverAccHisBal(newBalance)
                .transferTime(transferTime)
                .memo(memo)
                .build();

        try {
            senderAccount.getMember().countingTransactionCount();
        } catch (NotEnoughTransactionCountException e) {
            senderAccount.deductBalance(TossConstant.COMMISSION);
        }

        return transaction;
    }

    public void updateMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "senderName='" + senderName + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", amount=" + amount +
                ", transferTime=" + transferTime +
                ", memo='" + memo + '\'' +
                '}';
    }
}
