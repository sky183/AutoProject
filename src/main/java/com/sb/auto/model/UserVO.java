package com.sb.auto.model;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Data
@Entity
public class UserVO {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer userSeq;

    @Column(unique = true)
    private String userId;

    private String userPw;

    private String userRole;

    private String userName;

    private String email;

    private String phone;

    private int point;

    private String visitDate;

    private String regDate;

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.userPw = passwordEncoder.encode(this.userPw);
    }
}
