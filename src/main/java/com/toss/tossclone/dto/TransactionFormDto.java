package com.toss.tossclone.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

/**
 * 송금 마지막 단계에서 필요한 폼(form)을 위한 Dto
 * Transaction(Entity)의 생성 메서드의 매개변수에 필요한 정보를 담고 있음
 * TransactionService에서 데이터가 가공이 된 후 createTransaction 메서드의 매개변수로 넘어감
 */
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
    @NumberFormat(pattern = "###,###")
    private Long amount;

    // TODO: memo 받아오기

}
