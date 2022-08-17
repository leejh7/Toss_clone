package com.toss.tossclone.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 계정 등록 폼(form)에서 받아온 데이터를 담는 DTO
 * Account(Entity)의 생성 메서드(createAccount)의 매개변수로 사용
 */
@Getter @Setter
public class AccountFormDto {

    @NotNull(message = "은행 선택은 필수입니다.")
    private Long bankId;

    @NotBlank(message = "계좌명 입력은 필수입니다.")
    private String name;

    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    @Length(min = 4, max = 4, message = "비밀번호는 4자리로 입력해주세요.")
    private String password;

    private Long balance;
}
