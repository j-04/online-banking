package com.online.banking.controller;

import com.online.banking.dto.AccountDto;
import com.online.banking.dto.CardDto;
import com.online.banking.dto.UserDto;
import com.online.banking.service.BankingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Banking operations", description = "Базовые банковские операции")
public class BankingController {
    @Autowired
    private BankingService bankingService;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return this.bankingService.getUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        return this.bankingService.getUserById(id);
    }

    @GetMapping("/users/{id}/accounts")
    public ResponseEntity<?> getUserAccounts(@PathVariable("id") Long userId) {
        return this.bankingService.getUserAccounts(userId);
    }

    @GetMapping("/accounts/{number}")
    public ResponseEntity<?> getAccountByNumber(@PathVariable("number") String number) {
        return this.bankingService.getAccountByNumber(number);
    }

    @GetMapping("/cards/{number}")
    public ResponseEntity<?> getCardByNumber(@PathVariable("number") String number) {
        return this.bankingService.getCardByNumber(number);
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        return this.bankingService.createUser(userDto);
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> createAccount(@RequestBody AccountDto accountDto) {
        return this.bankingService.createAccount(accountDto);
    }

    @PostMapping("/accounts/{accountNumber}/cards")
    public ResponseEntity<?> createCard(@PathVariable("accountNumber") String accountNumber,
                                        @RequestBody CardDto cardDto) {
        return this.bankingService.createCard(accountNumber, cardDto);
    }

    @PutMapping("/cards/{cardNumber}/block")
    public ResponseEntity<?> blockCard(@PathVariable("cardNumber") String cardNumber) {
        return this.bankingService.blockCard(cardNumber);
    }

    @PutMapping("/accounts/{accountNumber}/block")
    public ResponseEntity<?> blockAccount(@PathVariable("accountNumber") String accountNumber) {
        return this.bankingService.blockAccount(accountNumber);
    }

    @PutMapping("/accounts/{accountNumber}/close")
    public ResponseEntity<?> closeAccount(@PathVariable("accountNumber") String accountNumber) {
        return this.bankingService.closeAccount(accountNumber);
    }

    @PutMapping("/cards/{cardNumber}/close")
    public ResponseEntity<?> closeCard(@PathVariable("cardNumber}") String cardNumber) {
        return this.bankingService.closeCard(cardNumber);
    }

    @PutMapping("/accounts/{accountNumber}/money/{money}/put")
    public ResponseEntity<?> putMoneyOnAccount(@PathVariable("money") Integer money,
                                               @PathVariable("accountNumber") String accountNumber) {
        return this.bankingService.putMoneyOnAccount(money, accountNumber);
    }

    @PutMapping("/cards/{cardNumber}/money/{money}/put")
    public ResponseEntity<?> putMoneyOnCard(@PathVariable("money") Integer money,
                                            @PathVariable("cardNumber") String cardNumber) {
        return this.bankingService.putMoneyOnCard(money, cardNumber);
    }

    @PutMapping("/accounts/{accountNumber}/money/{money}/get")
    public ResponseEntity<?> getMoneyFromAccount(@PathVariable("money") Integer money,
                                                 @PathVariable("accountNumber") String accountNumber) {
        return this.bankingService.getMoneyFromAccount(money, accountNumber);
    }

    @PutMapping("/cards/{cardNumber}/money/{money}/get")
    public ResponseEntity<?> getMoneyFromCard(Integer money, String cardNumber) {
        return this.bankingService.getMoneyFromCard(money, cardNumber);
    }

    @PutMapping("/accounts/{accountNumberFrom}/money/{money}/move/account/{accountNumberTo}")
    public ResponseEntity<?> moveMoneyFromAccountToAccount(@PathVariable("money") Integer money,
                                                           @PathVariable("accountNumberFrom") String accountNumberFrom,
                                                           @PathVariable("accountNumberTo") String accountNumberTo) {
        return this.bankingService.moveMoneyFromAccountToAccount(money, accountNumberFrom, accountNumberTo);
    }

    @PutMapping("/cards/{cardNumberFrom}/money/{money}/move/account/{accountNumberTo}")
    public ResponseEntity<?> moveMoneyFromCardToAccount(@PathVariable("money") Integer money,
                                                        @PathVariable("cardNumberFrom") String cardNumberFrom,
                                                        @PathVariable("accountNumberTo") String accountNumberTo) {
        return this.bankingService.moveMoneyFromCardToAccount(money, cardNumberFrom, accountNumberTo);
    }

    @PutMapping("/cards/{cardNumberFrom}/money/{money}/move/{cardNumberTo}")
    public ResponseEntity<?> moveMoneyFromCardToCard(@PathVariable("money") Integer money,
                                                     @PathVariable("cardNumberFrom") String cardNumberFrom,
                                                     @PathVariable("cardNumberTo") String cardNumberTo) {
        return this.bankingService.moveMoneyFromCardToCard(money, cardNumberFrom, cardNumberTo);
    }
}
