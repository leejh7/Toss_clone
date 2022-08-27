package com.toss.tossclone.dao;

import lombok.Data;

/**
 * 최근 거래한 계좌를 DB에서 받아올 dao
 */
@Data
public class RecentTransactionAccountDao {
    private String bankName;
    private String receiverName;
    private String receiverAccountCode;
    private String receiverAccountName;

    public RecentTransactionAccountDao(String bankName, String receiverName, String receiverAccountCode, String receiverAccountName) {
        this.bankName = bankName;
        this.receiverName = receiverName;
        this.receiverAccountCode = receiverAccountCode;
        this.receiverAccountName = receiverAccountName;
    }
}
