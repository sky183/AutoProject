package com.sb.auto.etc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    @Query("select b from BookEntity b where b.author.id = #{principal.userEntity.id}")
    List<BookEntity> findCurrentUserBooks();
}
