package com.toss.tossclone.controller;

import com.toss.tossclone.dto.ReceiverAccountDto;
import com.toss.tossclone.dto.ReceiverAccountFormDto;
import com.toss.tossclone.dto.SenderAccountDto;
import com.toss.tossclone.dto.TransactionFormDto;
import com.toss.tossclone.entity.Account;
import com.toss.tossclone.entity.Bank;
import com.toss.tossclone.entity.Transaction;
import com.toss.tossclone.repository.AccountRepository;
import com.toss.tossclone.repository.BankRepository;
import com.toss.tossclone.repository.TransactionRepository;
import com.toss.tossclone.service.AccountService;
import com.toss.tossclone.service.TransactionService;
import com.toss.tossclone.vo.AccountVo;
import com.toss.tossclone.vo.TransactionVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {

    /**
     * TODO: Post/Redirect/Get 다시 생각해보기 현재 PRG를 지키지 못하고 있음 지킬 수 있는 좋은 방법이 없을까?
     * TODO: validation 후 error message 처리해주기
     */

    private final TransactionService transactionService;
    private final AccountService accountService;

    private final BankRepository bankRepository;
    private final AccountRepository accountRepository;

    @PostMapping("/new/first")
    public String transactionFormFirst (@Valid @ModelAttribute("senderAccountDto") SenderAccountDto senderAccountDto,
                                        BindingResult bindingResult, Principal principal, Model model)
    {
        // TODO: SenderAccountDto도 검증해주기 / sender의 경우 문제가 있으면 어떻게 처리해아할까?
        if(bindingResult.hasErrors()) {
            return "redirect:/account/list";
        }

        List<ReceiverAccountDto> myReceiverAccountDtos = accountService.findMyAccountsExceptMe(principal.getName(), senderAccountDto.getAccountCode());
        model.addAttribute("myReceiverAccountDtos", myReceiverAccountDtos);

        List<ReceiverAccountDto> recentReceiverAccountDtos = transactionService.findRecentTransactionAccounts(principal.getName(), senderAccountDto.getAccountCode());
        model.addAttribute("recentReceiverAccountDtos", recentReceiverAccountDtos);

        return "transaction/transactionFormFirst";
    }

    @PostMapping("/new/second")
    public String transactionFormSecond(
            @Valid @ModelAttribute("senderAccountDto") SenderAccountDto senderAccountDto, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            return "redirect:/account/list";
        }

        List<Bank> banks = bankRepository.findAll();
        model.addAttribute("banks", banks);

        return "transaction/transactionFormSecond";
    }

    @PostMapping("/new/secondToLast")
    public String dataPreprocessing(
            @Valid @ModelAttribute("senderAccountDto") SenderAccountDto senderAccountDto, BindingResult senderBindingResult,
            @Valid @ModelAttribute("receiverAccountFormDto")ReceiverAccountFormDto receiverAccountFormDto, BindingResult receiverBindingResult,
            Model model
    ) {
        if(senderBindingResult.hasErrors()) {
            return "redirect:/account/list";
        }
        if(receiverBindingResult.hasErrors()) {
            return "redirect:/account/list";
        }

        // TransactionService에서 처리해주기
        String senderName = accountRepository.findMemberNameByAccountCode(senderAccountDto.getAccountCode()).orElseThrow(EntityNotFoundException::new);
        // TODO: receiverName 찾아오는 쿼리에서 receiverAccountName도 같이 찾아오기
        String receiverName = accountRepository.findMemberNameByAccountCode(receiverAccountFormDto.getReceiverAccountCode()).orElseThrow(EntityNotFoundException::new);
        String receiverAccountName = accountRepository.findAccountNameByAccountCode(receiverAccountFormDto.getReceiverAccountCode()).orElseThrow(EntityNotFoundException::new);

        ReceiverAccountDto receiverAccountDto = ReceiverAccountDto.of(receiverAccountFormDto, receiverAccountName, receiverName, false);

        model.addAttribute("senderName", senderName);
        model.addAttribute("receiverName", receiverName);
        model.addAttribute("receiverAccountDto", receiverAccountDto);

        return "transaction/transactionFormLast";
    }

    @PostMapping("/new/last")
    public String transactionFormLast(
            @Valid @ModelAttribute("senderAccountDto") SenderAccountDto senderAccountDto, BindingResult senderBindingResult,
            @Valid @ModelAttribute("receiverAccountDto") ReceiverAccountDto receiverAccountDto, BindingResult receiverBindingResult,
            Model model
    ) {
        if(senderBindingResult.hasErrors()) {
            return "redirect:/account/list";
        }
        if(receiverBindingResult.hasErrors()) {
            return "redirect:/account/list";
        }

        log.info("senderAccountDto: {}", senderAccountDto);
        log.info("receiverAccountDto: {}", receiverAccountDto);

        String senderName = accountRepository.findMemberNameByAccountCode(senderAccountDto.getAccountCode()).orElseThrow(EntityNotFoundException::new);
        String receiverName = accountRepository.findMemberNameByAccountCode(receiverAccountDto.getReceiverAccountCode()).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("senderName", senderName);
        model.addAttribute("receiverName", receiverName);

        return "transaction/transactionFormLast";
    }

    @PostMapping("/new/complete")
    public String transactionFormComplete(
            @Valid @ModelAttribute("transactionFormDto") TransactionFormDto transactionFormDto, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "에러!!!";
        }

        transactionService.saveTransaction(transactionFormDto);

        return "redirect:/account/list";
    }

    // --- 여기까지가 거래를 생성하기 위한 컨트롤러 메서드 --- //

    @GetMapping("/all/history")
    public String allHistory(@RequestParam String accountCode, Model model) {

        List<TransactionVo> transactions = transactionService.findAllHistoryByAccountCode(accountCode);
        AccountVo targetAccount = accountService.findMyAccount(accountCode);

        model.addAttribute("targetAccount", targetAccount);
        model.addAttribute("transactions", transactions);

        return "transaction/allHistory";
    }

}
