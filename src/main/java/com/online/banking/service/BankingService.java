package com.online.banking.service;

import com.online.banking.dto.AccountDto;
import com.online.banking.dto.CardDto;
import com.online.banking.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BankingService {
    ResponseEntity<?> getUsers();
    ResponseEntity<?> getUserById(Long id);

    ResponseEntity<?> getUserAccounts(Long userId);
    ResponseEntity<?> getAccountByNumber(String number);
    ResponseEntity<?> getCardByNumber(String number);

    ResponseEntity<?> createUser(UserDto userDto);
    ResponseEntity<?> createAccount(AccountDto accountDto);
    ResponseEntity<?> createCard(String accountNumber, CardDto cardDto);

    ResponseEntity<?> blockCard(String cardNumber);
    ResponseEntity<?> blockAccount(String accountNumber);

    ResponseEntity<?> closeAccount(String accountNumber);
    ResponseEntity<?> closeCard(String cardNumber);

    ResponseEntity<?> putMoneyOnAccount(Integer money, String accountNumber);
    ResponseEntity<?> putMoneyOnCard(Integer money, String cardNumber);
    ResponseEntity<?> getMoneyFromAccount(Integer money, String accountNumber);
    ResponseEntity<?> getMoneyFromCard(Integer money, String cardNumber);

    ResponseEntity<?> moveMoneyFromAccountToAccount(Integer money, String accountNumberFrom, String accountNumberTo);
    ResponseEntity<?> moveMoneyFromCardToAccount(Integer money, String cardNumberFrom, String accountNumberTo);
    ResponseEntity<?> moveMoneyFromCardToCard(Integer money, String cardNumberFrom, String cardNumberTo);
}
