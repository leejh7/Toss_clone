//package com.toss.tossclone;
//
//import com.toss.tossclone.constant.BankConstant;
//import com.toss.tossclone.entity.Bank;
//import com.toss.tossclone.repository.BankRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//
//@Component
//@RequiredArgsConstructor
//public class BankDataInit {
//
//    private final BankRepository bankRepository;
//
//    @PostConstruct
//    public void init() {
//        Bank kookmin = Bank.builder()
//                        .name("국민").interestRate(BankConstant.KOOKMIN_INTEREST).prefixAccountCode(BankConstant.KOOKMIN_PREFIX)
//                        .build();
//
//        Bank woori = Bank.builder()
//                .name("우리").interestRate(BankConstant.WOORI_INTEREST).prefixAccountCode(BankConstant.WOORI_PREFIX)
//                .build();
//
//        Bank kakao = Bank.builder()
//                .name("카카오").interestRate(BankConstant.KAKAO_INTEREST).prefixAccountCode(BankConstant.KAKAO_PREFIX)
//                .build();
//
//        Bank nonghyup = Bank.builder()
//                .name("농협").interestRate(BankConstant.NONGHYUP_INTEREST).prefixAccountCode(BankConstant.NONGHYUP_PREFIX)
//                .build();
//
//        Bank ibk = Bank.builder()
//                .name("IBK기업").interestRate(BankConstant.IBK_INTEREST).prefixAccountCode(BankConstant.IBK_PREFIX)
//                .build();
//
//        bankRepository.save(kookmin);
//        bankRepository.save(woori);
//        bankRepository.save(kakao);
//        bankRepository.save(nonghyup);
//        bankRepository.save(ibk);
//    }
//}
