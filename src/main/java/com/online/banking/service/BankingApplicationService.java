package com.online.banking.service;

import com.online.banking.dto.AccountDto;
import com.online.banking.dto.CardDto;
import com.online.banking.dto.UserDto;
import com.online.banking.entity.Account;
import com.online.banking.entity.Card;
import com.online.banking.entity.Role;
import com.online.banking.entity.User;
import com.online.banking.exception.AccountNotFoundException;
import com.online.banking.exception.CardNotFoundException;
import com.online.banking.exception.UserNotFoundException;
import com.online.banking.mapper.AccountMapper;
import com.online.banking.mapper.CardMapper;
import com.online.banking.mapper.UserMapper;
import com.online.banking.repository.AccountRepository;
import com.online.banking.repository.CardRepository;
import com.online.banking.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankingApplicationService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final UserMapper userMapper;
    private final AccountMapper accountMapper;
    private final CardMapper cardMapper;

    public BankingApplicationService(UserRepository userRepository,
                                     CardMapper cardMapper,
                                     AccountMapper accountMapper,
                                     UserMapper userMapper,
                                     AccountRepository accountRepository,
                                     CardRepository cardRepository) {
        this.userRepository = userRepository;
        this.cardMapper = cardMapper;
        this.accountMapper = accountMapper;
        this.userMapper = userMapper;
        this.accountRepository = accountRepository;
        this.cardRepository = cardRepository;
    }

    public List<UserDto> getUsers() {
        return this.userRepository.findAll().stream()
                .map(this.userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isEmpty())
            throw new UserNotFoundException();
        return this.userMapper.toUserDto(userOptional.get());
    }

    public List<AccountDto> getUserAccounts(Long userId) {
        Optional<User> userOptional = this.userRepository.findById(userId);
        if (userOptional.isEmpty())
            throw new UserNotFoundException();

        User user = userOptional.get();
        return user.getAccountSet().stream()
                .map(this.accountMapper::toAccountDto)
                .collect(Collectors.toList());
    }

    public List<CardDto> getUserCards(Long userId) {
        Optional<User> userOptional = this.userRepository.findById(userId);
        if (userOptional.isEmpty())
            throw new UserNotFoundException();

        User user = userOptional.get();
        return user.getAccountSet().stream()
                .map(Account::getCard)
                .map(this.cardMapper::toCardDto)
                .collect(Collectors.toList());
    }

    public AccountDto getAccountByNumber(String number) {
        Optional<Account> accountOptional = this.accountRepository.findByNumber(number);
        if (accountOptional.isEmpty())
            throw new AccountNotFoundException();
        return this.accountMapper.toAccountDto(accountOptional.get());
    }

    public CardDto getCardByNumber(String number) {
        Optional<Card> cardOptional = this.cardRepository.findByNumber(number);
        if (cardOptional.isEmpty())
            throw new CardNotFoundException();
        return this.cardMapper.toCardDto(cardOptional.get());
    }

    @Transactional
    public void createUser(UserDto userDto) {
        if (userDto == null)
            throw new RuntimeException();

        User user = this.userMapper.toUser(userDto);
        user.setRole(Role.ADMIN);
        this.userRepository.save(user);
    }

    @Transactional
    public void createAccount(AccountDto accountDto) {
        if (accountDto == null)
            throw new RuntimeException();
        Account account = this.accountMapper.toAccount(accountDto);
        account.setAvailableTill(OffsetDateTime.now().plusYears(3));
        this.accountRepository.save(account);
    }

    @Transactional
    public void createCard(String accountNumber, CardDto cardDto) {
        if (accountNumber == null)
            throw new RuntimeException();

        if (cardDto == null)
            throw new RuntimeException();

        Optional<Account> accountOptional = this.accountRepository.findByNumber(accountNumber);
        if (accountOptional.isEmpty())
            throw new AccountNotFoundException();

        Account account = accountOptional.get();
        if (account.getCard() != null)
            throw new RuntimeException();

        Card card = this.cardMapper.toCard(cardDto);
        account.setCard(card);
        card.setAccount(account);

        this.accountRepository.save(account);
        this.cardRepository.save(card);
    }

    @Transactional
    public void blockCard(String cardNumber) {
        if (cardNumber == null)
            throw new RuntimeException();

        Optional<Card> cardOptional = this.cardRepository.findByNumber(cardNumber);
        if (cardOptional.isEmpty())
            throw new CardNotFoundException();

        Card card = cardOptional.get();
        card.setBlocked(true);

        this.cardRepository.save(card);
    }

    @Transactional
    public void blockAccount(String accountNumber) {
        if (accountNumber == null)
            throw new RuntimeException();

        Optional<Account> accountOptional = this.accountRepository.findByNumber(accountNumber);
        if (accountOptional.isEmpty())
            throw new AccountNotFoundException();

        Account account = accountOptional.get();
        account.setBlocked(true);

        this.accountRepository.save(account);
    }

    @Transactional
    public void closeAccount(String accountNumber) {
        if (accountNumber == null)
            throw new RuntimeException();

        Optional<Account> accountOptional = this.accountRepository.findByNumber(accountNumber);
        if (accountOptional.isEmpty())
            throw new AccountNotFoundException();

        Account account = accountOptional.get();
        account.setClosed(true);
        account.getCard().setClosed(true);

        this.accountRepository.save(account);
    }

    @Transactional
    public void closeCard(String cardNumber) {
        if (cardNumber == null)
            throw new RuntimeException();

        Optional<Card> cardOptional = this.cardRepository.findByNumber(cardNumber);
        if (cardOptional.isEmpty())
            throw new CardNotFoundException();

        Card card = cardOptional.get();
        card.setClosed(true);

        this.cardRepository.save(card);
    }

    @Transactional
    public void putMoneyOnAccount(Integer money, String accountNumber) {
        if (money == null)
            throw new RuntimeException();

        if (accountNumber == null)
            throw new RuntimeException();

        Optional<Account> accountOptional = this.accountRepository.findByNumber(accountNumber);
        if (accountOptional.isEmpty())
            throw new AccountNotFoundException();

        Account account = accountOptional.get();
        Integer currentMoney = account.getMoney();

        account.setMoney(currentMoney + money);

        this.accountRepository.save(account);
    }

    @Transactional
    public void putMoneyOnCard(Integer money, String cardNumber) {
        if (money == null)
            throw new RuntimeException();

        if (cardNumber == null)
            throw new RuntimeException();

        Optional<Card> cardOptional = this.cardRepository.findByNumber(cardNumber);
        if (cardOptional.isEmpty())
            throw new CardNotFoundException();

        Card card = cardOptional.get();
        Account account = card.getAccount();
        Integer currentMoney = account.getMoney();

        account.setMoney(currentMoney + money);

        this.accountRepository.save(account);
        this.cardRepository.save(card);
    }

    @Transactional
    public Integer getMoneyFromAccount(Integer money, String accountNumber) {
        if (money == null)
            throw new RuntimeException();

        if (accountNumber == null)
            throw new RuntimeException();

        Optional<Account> accountOptional = this.accountRepository.findByNumber(accountNumber);
        if (accountOptional.isEmpty())
            throw new AccountNotFoundException();

        Account account = accountOptional.get();
        Integer currentMoney = account.getMoney();

        if (money > currentMoney)
            throw new RuntimeException();

        Integer resultMoney = currentMoney - money;
        account.setMoney(resultMoney);

        this.accountRepository.save(account);

        return resultMoney;
    }

    @Transactional
    public Integer getMoneyFromCard(Integer money, String cardNumber) {
        if (money == null)
            throw new RuntimeException();

        if (cardNumber == null)
            throw new RuntimeException();

        Optional<Card> cardOptional = this.cardRepository.findByNumber(cardNumber);
        if (cardOptional.isEmpty())
            throw new CardNotFoundException();

        Card card = cardOptional.get();
        Account account = card.getAccount();
        Integer currentMoney = account.getMoney();

        if (money > currentMoney)
            throw new RuntimeException();

        Integer resultMoney = currentMoney - money;
        account.setMoney(resultMoney);

        this.accountRepository.save(account);
        this.cardRepository.save(card);

        return resultMoney;
    }

    @Transactional
    public void moveMoneyFromAccountToAccount(Integer money, String accountNumberFrom, String accountNumberTo) {
        if (money == null)
            throw new RuntimeException();

        if (accountNumberFrom == null)
            throw new RuntimeException();

        if (accountNumberTo == null)
            throw new RuntimeException();

        Optional<Account> accountOptional1 = this.accountRepository.findByNumber(accountNumberFrom);
        Optional<Account> accountOptional2 = this.accountRepository.findByNumber(accountNumberTo);

        if (accountOptional1.isEmpty() || accountOptional2.isEmpty())
            throw new AccountNotFoundException();

        Account account1 = accountOptional1.get();
        Account account2 = accountOptional2.get();

        Integer account1Money = account1.getMoney();
        Integer account2Money = account2.getMoney();

        if (money > account1Money)
            throw new RuntimeException();

        account1.setMoney(account1Money - money);
        account2.setMoney(account2Money + money);

        this.accountRepository.save(account1);
        this.accountRepository.save(account2);
    }

    @Transactional
    public void moveMoneyFromCardToAccount(Integer money, String cardNumberFrom, String accountNumberTo) {
        if (money == null)
            throw new RuntimeException();

        if (cardNumberFrom == null)
            throw new RuntimeException();

        if (accountNumberTo == null)
            throw new RuntimeException();

        Optional<Card> cardOptional = this.cardRepository.findByNumber(cardNumberFrom);
        Optional<Account> accountOptional2 = this.accountRepository.findByNumber(accountNumberTo);

        if (cardOptional.isEmpty())
            throw new CardNotFoundException();

        if (accountOptional2.isEmpty())
            throw new AccountNotFoundException();

        Account account1 = cardOptional.get().getAccount();
        Account account2 = accountOptional2.get();

        Integer account1Money = account1.getMoney();
        Integer account2Money = account2.getMoney();

        if (money > account1Money)
            throw new RuntimeException();

        account1.setMoney(account1Money - money);
        account2.setMoney(account2Money + money);

        this.accountRepository.save(account1);
        this.accountRepository.save(account2);
    }

    @Transactional
    public void moveMoneyFromCardToCard(Integer money, String cardNumberFrom, String cardNumberTo) {
        if (money == null)
            throw new RuntimeException();

        if (cardNumberFrom == null)
            throw new RuntimeException();

        if (cardNumberTo == null)
            throw new RuntimeException();

        Optional<Card> cardOptional1 = this.cardRepository.findByNumber(cardNumberFrom);
        Optional<Card> cardOptional2 = this.cardRepository.findByNumber(cardNumberTo);

        if (cardOptional1.isEmpty())
            throw new CardNotFoundException();

        if (cardOptional2.isEmpty())
            throw new AccountNotFoundException();

        Account account1 = cardOptional1.get().getAccount();
        Account account2 = cardOptional2.get().getAccount();

        Integer account1Money = account1.getMoney();
        Integer account2Money = account2.getMoney();

        if (money > account1Money)
            throw new RuntimeException();

        account1.setMoney(account1Money - money);
        account2.setMoney(account2Money + money);

        this.accountRepository.save(account1);
        this.accountRepository.save(account2);
    }
}
