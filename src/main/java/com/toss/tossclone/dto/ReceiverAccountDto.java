package com.toss.tossclone.dto;

import com.toss.tossclone.entity.Bank;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter @Getter
public class ReceiverAccountDto {
    private String bankName;

    @NotEmpty(message = "보낼 사람의 계좌번호는 필수 입니다.")
    private String receiverAccountCode;

    @NotEmpty(message = "보낼 사람의 계좌명은 필수 입니다.")
    private String receiverAccountName;
}
