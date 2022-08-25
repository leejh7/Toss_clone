package com.toss.tossclone.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDateTime;

@Data
public class TransactionVo {

    private String senderName;
    private String receiverName;
    private String senderAccountCode;
    private String receiverAccountCode;

    private String memo;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalDateTime transferTime;

    @DateTimeFormat(pattern = "MM-dd")
    private LocalDateTime transferDate;

    @NumberFormat(pattern = "###,###")
    private Long amount;

    @NumberFormat(pattern = "###,###")
    private Long senderAccHisBal;

    @NumberFormat(pattern = "###,###")
    private Long receiverAccHisBal;
}
