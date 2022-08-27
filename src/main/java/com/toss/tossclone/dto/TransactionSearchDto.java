package com.toss.tossclone.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TransactionSearchDto {

    private String searchDateType; // 1m: 한달(기본), 1d: 하루, 1w: 일주일, 3m: 3달

    private String searchTransactionType;  // all: 전체(기본), deposit: 입금, withdraw: 출금

    private String searchBy;   // receiverName, senderName

    private String searchQuery = "";    // 조회할 사람
}
