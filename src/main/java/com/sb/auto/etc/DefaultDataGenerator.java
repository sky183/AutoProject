package com.sb.auto.etc;

import com.sb.auto.model.UserVO;
import com.sb.auto.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DefaultDataGenerator implements ApplicationRunner {

    @Autowired
    UserDetailService userDetailService;

/*    @Autowired
    BookRepository bookRepository;*/

    @Override
    public void run(ApplicationArguments args) throws Exception {
/*        UserVO keesun = createUser("keesun");
        UserVO whiteship = createUser("whiteship");
        createBook("spring", keesun);
        createBook("hibernate", whiteship);*/
    }

    private void createBook(String title, UserVO keesun) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(title);
        bookEntity.setAuthor(keesun);
//        bookRepository.save(bookEntity);
    }

    private UserVO createUser(String usename) {
        UserVO userVO = new UserVO();
        userVO.setUserId(usename);
        userVO.setUserPw("123");
        userVO.setUserRole("USER");
        return userDetailService.insertUser(userVO);
    }
}
