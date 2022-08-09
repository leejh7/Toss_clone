package com.toss.tossclone.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SenderAccountDto {
    private String accountCode;
    private String accountName;
    private Long balance;
}
