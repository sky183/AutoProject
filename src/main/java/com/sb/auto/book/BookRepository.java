package com.sb.auto.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    @Query("select b from BookEntity b where b.author.id = ?#{principal.accountEntity.id}")
    List<BookEntity> findCurrentUserBooks();
}
