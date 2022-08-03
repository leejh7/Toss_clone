package com.toss.tossclone.entity;

import com.toss.tossclone.exception.NotEnoughMoneyException;
import lombok.*;
import net.bytebuddy.asm.Advice;

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

    @Column(name = "transfer_time", nullable = false)
    private LocalDateTime transferTime;

    @Lob
    private String memo;

    @Builder
    private Transaction(String senderName, String receiverName, Account senderAccount, Account receiverAccount, Long amount, LocalDateTime transferTime, String memo) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
        this.transferTime = transferTime;
        this.memo = memo;
    }

    //==생성 메서드==//
    public static Transaction createTransaction(String senderName, String receiverName, Account senderAccount, Account receiverAccount, Long amount, LocalDateTime transferTime, String memo) {
        //TODO: TransactionFormDto 만들어서 파라미터 변경해주기

        Transaction transaction = Transaction.builder()
                .senderName(senderName)
                .receiverName(receiverName)
                .senderAccount(senderAccount)
                .receiverAccount(receiverAccount)
                .amount(amount)
                .transferTime(transferTime)
                .memo(memo)
                .build();

        // 보내는 사람의 계좌에서는 금액만큼 빼기
        senderAccount.deductBalance(amount);
        // 받는 사람의 계좌에서는 금액만큼 더하기
        receiverAccount.addBalance(amount);

        return transaction;
    }

    public void changeMemo(String memo) {
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
