package com.toss.tossclone.dto;

import com.toss.tossclone.entity.Bank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter @Getter
@ToString
public class ReceiverAccountDto {
    private String bankName;

    @NotEmpty(message = "보낼 사람의 계좌번호는 필수 입니다.")
    private String receiverAccountCode;

    @NotEmpty(message = "보낼 사람의 계좌명은 필수 입니다.")
    private String receiverAccountName;

    @NotNull(message = "받는 계좌가 내 계좌인지 남의 계좌인지 알아야 합니다.")
    private Boolean mine;

    public static ReceiverAccountDto of(ReceiverAccountFormDto receiverAccountFormDto, String receiverAccountName) {
        ReceiverAccountDto receiverAccountDto = new ReceiverAccountDto();
        receiverAccountDto.setBankName(receiverAccountFormDto.getBankName());
        receiverAccountDto.setReceiverAccountCode(receiverAccountFormDto.getReceiverAccountCode());
        receiverAccountDto.setReceiverAccountName(receiverAccountName);
        receiverAccountDto.setMine(false);
        return receiverAccountDto;
    }
}
