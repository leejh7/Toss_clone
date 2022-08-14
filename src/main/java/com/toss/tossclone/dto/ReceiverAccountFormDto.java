package com.toss.tossclone.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReceiverAccountFormDto {

    @NotBlank(message = "계좌번호 입력은 필수입니다.")
    private String receiverAccountCode;

    @NotBlank(message = "은행은 반드시 선택해야합니다.")
    private String bankName;
}
