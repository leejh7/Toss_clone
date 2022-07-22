package com.toss.tossclone.entity;

import lombok.*;

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
}
