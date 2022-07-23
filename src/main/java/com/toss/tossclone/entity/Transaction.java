package com.toss.tossclone.entity;

import lombok.*;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transactions")
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction {
    @Id @GeneratedValue
    @Column(name = "transaction_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @Column(name = "sender_account_id")
    private Account senderAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @Column(name = "receiver_account_id")
    private Account receiverAccount;

    @Column(nullable = false)
    private Long amount;

    @Column(name = "transfer_time", nullable = false)
    private LocalDateTime transferTime;

    @Lob
    private String memo;

    @Builder
    public Transaction(Account senderAccount, Account receiverAccount, Long amount, LocalDateTime transferTime, String memo) {
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
        this.transferTime = transferTime;
        this.memo = memo;
    }

    //==생성 메서드==//
    public static Transaction createTransaction(Account senderAccount, Account receiverAccount, Long amount, LocalDateTime transferTime, String memo) {
        // MEMO: 빌더 패턴을 사용하는게 맞는 것인가? 생성 메서드를 사용하게 되면 빌더 패턴의 이점을 못누리는데...
        Transaction transaction = Transaction.builder()
                .senderAccount(senderAccount)
                .receiverAccount(receiverAccount)
                .amount(amount)
                .transferTime(transferTime)
                .memo(memo)
                .build();

        senderAccount.deductBalance(amount);
        receiverAccount.addBalance(amount);

        return transaction;
    }

    public void changeMemo(String memo) {
        this.memo = memo;
    }
}
