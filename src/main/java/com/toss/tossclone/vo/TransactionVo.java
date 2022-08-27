package com.toss.tossclone.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDateTime;

@Getter @Setter
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

    @QueryProjection
    public TransactionVo(String senderName, String receiverName, String senderAccountCode, String receiverAccountCode, String memo, LocalDateTime transferTime, LocalDateTime transferDate, Long amount, Long senderAccHisBal, Long receiverAccHisBal) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.senderAccountCode = senderAccountCode;
        this.receiverAccountCode = receiverAccountCode;
        this.memo = memo;
        this.transferTime = transferTime;
        this.transferDate = transferDate;
        this.amount = amount;
        this.senderAccHisBal = senderAccHisBal;
        this.receiverAccHisBal = receiverAccHisBal;
    }
}
