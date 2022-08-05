package com.toss.tossclone.controller;

import com.toss.tossclone.dto.AccountFormDto;
import com.toss.tossclone.entity.Bank;
import com.toss.tossclone.entity.Member;
import com.toss.tossclone.repository.BankRepository;
import com.toss.tossclone.repository.MemberRepository;
import com.toss.tossclone.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final BankRepository bankRepository;

    @ModelAttribute("banks")
    public List<Bank> banks() {
        List<Bank> banks = bankRepository.findAll();
        return banks;
    }

    @GetMapping("/new")
    public String AccountForm(Model model) {
        model.addAttribute("accountFormDto", new AccountFormDto());
        return "account/accountForm";
    }

    @PostMapping("/new")
    public String accountForm(@Valid AccountFormDto accountFormDto, BindingResult bindingResult, Model model, Principal principal) {
        if(bindingResult.hasErrors()) {
            return "account/accountForm";
        }

        accountService.saveAccount(accountFormDto, principal.getName());
        return "redirect:/";
    }

}
