package com.sb.auto.book;


import com.sb.auto.account.AccountEntity;

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
    private AccountEntity author;

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

    public AccountEntity getAuthor() {
        return author;
    }

    public void setAuthor(AccountEntity author) {
        this.author = author;
    }
}
