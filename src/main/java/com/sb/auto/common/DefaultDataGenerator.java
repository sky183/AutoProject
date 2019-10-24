package com.sb.auto.common;

import com.sb.auto.account.Account;
import com.sb.auto.account.AccountService;
import com.sb.auto.book.Book;
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
        Account keesun = createUser("keesun");
        Account whiteship = createUser("whiteship");
        createBook("spring", keesun);
        createBook("hibernate", whiteship);
    }

    private void createBook(String title, Account keesun) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(keesun);
        bookRepository.save(book);
    }

    private Account createUser(String usename) {
        Account account = new Account();
        account.setUsername(usename);
        account.setPassword("123");
        account.setRole("USER");
        return accountService.createNew(account);
    }
}
