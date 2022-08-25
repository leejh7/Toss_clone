package com.toss.tossclone.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * ReceierAccountDto와 동일
 */
@Getter @Setter
@ToString
public class SenderAccountDto {

    //TODO: 적합한 validation 적용하기

    @NotEmpty(message = "계좌번호를 못받아 오면 안됩니다.")
    private String accountCode;
    @NotEmpty(message = "계좌명을 못받아 오면 안됩니다.")
    private String accountName;
    @NotNull(message = "잔액은 null이 될 수 없습니다.")
    private Long balance;
}
