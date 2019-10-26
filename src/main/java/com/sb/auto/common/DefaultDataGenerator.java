package com.sb.auto.common;

import com.sb.auto.security.UserEntity;
import com.sb.auto.security.CustomUserDetailService;
import com.sb.auto.etc.BookEntity;
import com.sb.auto.etc.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DefaultDataGenerator implements ApplicationRunner {

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Autowired
    BookRepository bookRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
/*        UserEntity keesun = createUser("keesun");
        UserEntity whiteship = createUser("whiteship");
        createBook("spring", keesun);
        createBook("hibernate", whiteship);*/
    }

    private void createBook(String title, UserEntity keesun) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(title);
        bookEntity.setAuthor(keesun);
        bookRepository.save(bookEntity);
    }

    private UserEntity createUser(String usename) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(usename);
        userEntity.setUserPassword("123");
        userEntity.setUserRole("USER");
        return customUserDetailService.insertUser(userEntity);
    }
}
