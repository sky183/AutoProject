package com.sb.auto.etc;


import com.sb.auto.model.UserVO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BookEntity {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    @ManyToOne
    private UserVO author;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserVO getAuthor() {
        return author;
    }

    public void setAuthor(UserVO author) {
        this.author = author;
    }
}
