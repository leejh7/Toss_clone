package com.toss.tossclone.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {
    @GetMapping("/new/{accountCode}")
    @ResponseBody
    public String transactionForm(@PathVariable String accountCode) {
        log.info("accountCode= {}", accountCode);
        return accountCode;
    }
}
