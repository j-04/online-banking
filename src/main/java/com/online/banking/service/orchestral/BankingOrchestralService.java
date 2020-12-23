package com.online.banking.service.orchestral;

import com.online.banking.dto.AccountDto;
import com.online.banking.dto.CardDto;
import com.online.banking.dto.UserDto;
import com.online.banking.service.BankingApplicationService;
import com.online.banking.service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BankingOrchestralService implements BankingService {
    @Autowired
    private BankingApplicationService bankingApplicationService;

    @Override
    public ResponseEntity<?> getUsers() {
        return new ResponseEntity<>(this.bankingApplicationService.getUsers(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUserById(Long id) {
        return new ResponseEntity<>(this.bankingApplicationService.getUserById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUserAccounts(Long userId) {
        return new ResponseEntity<>(this.bankingApplicationService.getUserAccounts(userId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAccountByNumber(String number) {
        return new ResponseEntity<>(this.bankingApplicationService.getAccountByNumber(number), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getCardByNumber(String number) {
        return new ResponseEntity<>(this.bankingApplicationService.getCardByNumber(number), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createUser(UserDto userDto) {
        this.bankingApplicationService.createUser(userDto);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createAccount(AccountDto accountDto) {
        this.bankingApplicationService.createAccount(accountDto);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createCard(String accountNumber, CardDto cardDto) {
        this.bankingApplicationService.createCard(accountNumber, cardDto);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> blockCard(String cardNumber) {
        this.bankingApplicationService.blockCard(cardNumber);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> blockAccount(String accountNumber) {
        this.bankingApplicationService.blockAccount(accountNumber);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> closeAccount(String accountNumber) {
        this.bankingApplicationService.closeAccount(accountNumber);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> closeCard(String cardNumber) {
        this.bankingApplicationService.closeCard(cardNumber);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> putMoneyOnAccount(Integer money, String accountNumber) {
        this.bankingApplicationService.putMoneyOnAccount(money, accountNumber);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> putMoneyOnCard(Integer money, String cardNumber) {
        this.bankingApplicationService.putMoneyOnCard(money, cardNumber);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getMoneyFromAccount(Integer money, String accountNumber) {
        return new ResponseEntity<>(this.bankingApplicationService.getMoneyFromAccount(money, accountNumber), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getMoneyFromCard(Integer money, String cardNumber) {
        return new ResponseEntity<>(this.bankingApplicationService.getMoneyFromCard(money, cardNumber), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> moveMoneyFromAccountToAccount(Integer money, String accountNumberFrom, String accountNumberTo) {
        this.bankingApplicationService.moveMoneyFromAccountToAccount(money, accountNumberFrom, accountNumberTo);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> moveMoneyFromCardToAccount(Integer money, String cardNumberFrom, String accountNumberTo) {
        this.bankingApplicationService.moveMoneyFromCardToAccount(money, cardNumberFrom, accountNumberTo);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> moveMoneyFromCardToCard(Integer money, String cardNumberFrom, String cardNumberTo) {
        this.bankingApplicationService.moveMoneyFromCardToCard(money, cardNumberFrom, cardNumberTo);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
