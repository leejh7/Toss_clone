package com.toss.tossclone.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter @Setter
public class MemberFormDto {
    @NotBlank(message = "이름 입력은 필수입니다.")
    private String name;

    @NotBlank(message = "전화번호 입력은 필수입니다.")
    @Pattern(regexp = "[0-9]{10,11}", message = "10~11자리의 숫자만 입력가능합니다")
    private String phoneNum;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String city;
    private String street;
    private String zipcode;

    @NotEmpty(message = "이메일 입력은 필수입니다.")
    @Email(message = "올바르지 않은 이메일 형식입니다.")
    private String email;

    @NotEmpty(message = "비밀번호 입력은 필수입니다.")
    @Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String password;
}
