package com.toss.tossclone.controller;

import com.toss.tossclone.dto.AccountFormDto;
import com.toss.tossclone.entity.Account;
import com.toss.tossclone.entity.Bank;
import com.toss.tossclone.repository.BankRepository;
import com.toss.tossclone.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final BankRepository bankRepository;

    @GetMapping("/new")
    public String AccountForm(Model model) {
        List<Bank> banks = bankRepository.findAll();

        model.addAttribute("accountFormDto", new AccountFormDto());
        model.addAttribute("banks", banks);
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

    @GetMapping("/list")
    public String findMyAccount(Model model, Principal principal) {
        // TODO: AccountDto를 만들어서 View에 필요한 부분만 뽑아내기 -> Query 부분도 수정 필요

        log.info("principal.name = {}", principal.getName());

        List<Account> accounts = accountService.findMyAccounts(principal.getName());
        model.addAttribute("accounts", accounts);
        return "account/accountList";
    }

}
