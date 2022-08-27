package com.toss.tossclone.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 계좌 직접 입력하여 송금할 때 폼(form)에서 사용하는 DTO
 * ReceiverAccountDto와 다른 점은 계좌명(accountName)과 자기 계좌인지 확인하는(mine) 플래그가 없다.
 */
@Getter
@Setter
public class ReceiverAccountFormDto {

    @NotBlank(message = "계좌번호 입력은 필수입니다.")
    private String receiverAccountCode;

    @NotBlank(message = "은행은 반드시 선택해야합니다.")
    private String bankName;
}
