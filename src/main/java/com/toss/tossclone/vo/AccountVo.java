package com.toss.tossclone.vo;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

@Data
public class AccountVo {
    private String accountName;
    private String bankName;
    private String accountCode;

    @NumberFormat(pattern = "###,###")
    private Long balance;
}
