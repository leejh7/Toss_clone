package com.toss.tossclone.controller;

import com.toss.tossclone.dto.ReceiverAccountDto;
import com.toss.tossclone.dto.SenderAccountDto;
import com.toss.tossclone.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/new/first")
    public String transactionFormFirst(@ModelAttribute("senderAccountDto") SenderAccountDto senderAccountDto, Principal principal, Model model) {
        List<ReceiverAccountDto> receiverAccountDtos = transactionService.findMyAccounts(principal.getName(), senderAccountDto.getAccountCode());
        model.addAttribute("receiverAccountDtos", receiverAccountDtos);

        return "transaction/transactionFormFirst";
    }

    @PostMapping("/new/first")
    public String transactionFormFirst (
            @Valid @ModelAttribute("senderAccountDto") SenderAccountDto senderAccountDto, BindingResult bindingResult1,
            @Valid @ModelAttribute("receiverAccountDto") ReceiverAccountDto receiverAccountDto, BindingResult bindingResult2)
    {
        // TODO: SenderAccountDto도 검증해주기 / sender의 경우 문제가 있으면 어떻게 처리해아할까?

        if(bindingResult2.hasErrors()) {
            return "redirect:/transaction/new/second";
        }

        return "redirect:/transaction/new/last";
    }

    @GetMapping("/new/second")
    public String transactionSecondGet() {

        return "transaction/transactionFormSecond";
    }

    @PostMapping("/new/second")
    @ResponseBody
    public String transactionFormSecondPost() {
        return "[second / POST] OK";
    }

    @GetMapping("/new/last")
    @ResponseBody
    public String transactionFormLastGet(@ModelAttribute("senderAccountDto") SenderAccountDto senderAccountDto,
                                      @ModelAttribute("receiverAccountDto") ReceiverAccountDto receiverAccountDto) {

        return "transaction/transactionFormLast";
    }

    @PostMapping("/new/last")
    @ResponseBody
    public String transactionFormLastPost(@ModelAttribute("senderAccountDto") SenderAccountDto senderAccountDto,
                                      @Valid @ModelAttribute("receiverAccountDto") ReceiverAccountDto receiverAccountDto) {

        return "[last / POST] OK";
    }
}
