package com.online.banking.aspct;

import com.online.banking.entity.Account;
import com.online.banking.entity.User;
import com.online.banking.notificator.BaseNotificator;
import com.online.banking.repository.AccountRepository;
import com.online.banking.repository.CardRepository;
import com.online.banking.repository.UserRepository;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class NotificatorAspect {
    private final String FROM_EMAIL = "destinationemail@bank.com";

    public final List<BaseNotificator> notificatorList;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public NotificatorAspect(List<BaseNotificator> notificatorList,
                             AccountRepository accountRepository,
                             UserRepository userRepository,
                             CardRepository cardRepository) {
        this.notificatorList = notificatorList;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    @Pointcut(value = "execution (public void putMoneyOnAccount(Integer, String)) && args(money, accountNumber)", argNames = "money, accountNumber")
    public void putMoneyOnAccount(Integer money, String accountNumber) {  }

    @Pointcut(value = "execution (public void putMoneyOnCard(Integer, String)) && args(money, cardNumber)", argNames = "money, cardNumber")
    public void putMoneyOnCard(Integer money, String cardNumber) {  }

    @AfterReturning(value = "putMoneyOnAccount(money, accountNumber)", argNames = "money, accountNumber")
    public void afterReturningNotify1(Integer money, String accountNumber) {
        UserDetails principal = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByLogin(principal.getUsername()).get();
        Account account = this.accountRepository.findByNumber(accountNumber).get();
        for (BaseNotificator baseNotificator : notificatorList) {
            baseNotificator.send(FROM_EMAIL, user.getEmail(), "U HAVE GOT SOME MONEY!", String.format("Hey, %s %s! Your money bill is now $%d dollars. Good luck!", user.getName(), user.getLastName(), account.getMoney()));
        }
    }

    @AfterReturning(value = "putMoneyOnCard(money, cardNumber)", argNames = "money, cardNumber")
    public void afterReturningNotify2(Integer money, String cardNumber) {
        UserDetails principal = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByLogin(principal.getUsername()).get();
        Account account = this.cardRepository.findByNumber(cardNumber).get().getAccount();
        for (BaseNotificator baseNotificator : notificatorList) {
            baseNotificator.send(FROM_EMAIL, user.getEmail(), "U HAVE GOT SOME MONEY!", String.format("Hey, %s %s! Your money bill is now $%d dollars. Good luck!", user.getName(), user.getLastName(), account.getMoney()));
        }
    }
}
