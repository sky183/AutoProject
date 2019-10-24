package com.sb.auto.common;

import com.sb.auto.account.AccountEntity;
import com.sb.auto.account.AccountService;
import com.sb.auto.book.BookEntity;
import com.sb.auto.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DefaultDataGenerator implements ApplicationRunner {

    @Autowired
    AccountService accountService;

    @Autowired
    BookRepository bookRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        AccountEntity keesun = createUser("keesun");
        AccountEntity whiteship = createUser("whiteship");
        createBook("spring", keesun);
        createBook("hibernate", whiteship);
    }

    private void createBook(String title, AccountEntity keesun) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(title);
        bookEntity.setAuthor(keesun);
        bookRepository.save(bookEntity);
    }

    private AccountEntity createUser(String usename) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername(usename);
        accountEntity.setPassword("123");
        accountEntity.setRole("USER");
        return accountService.createNew(accountEntity);
    }
}
