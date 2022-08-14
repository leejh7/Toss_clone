package com.toss.tossclone.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class TransactionFormDto {
    @NotEmpty(message = "보내는 이의 이름은 반드시 필요합니다.")
    private String senderName;

    @NotEmpty(message = "받는 이의 이름은 반드시 필요합니다.")
    private String receiverName;

    @NotEmpty(message = "보내는 이의 계좌 번호는 반드시 필요합니다.")
    private String senderAccountCode;

    @NotEmpty(message = "받는 이의 계좌 번호는 반드시 필요합니다.")
    private String receiverAccountCode;

    @DecimalMin(value = "0", inclusive = false, message = "1원 이상은 보내야합니다.")
    private Long amount;

}
