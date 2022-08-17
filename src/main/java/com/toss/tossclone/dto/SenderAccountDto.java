package com.toss.tossclone.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ReceierAccountDto와 동일
 */
@Getter @Setter
@ToString
public class SenderAccountDto {
    private String accountCode;
    private String accountName;
    private Long balance;
}
