package com.sb.auto.account;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "USER")
public class UserEntity {

    @Id @GeneratedValue
    private Integer userSeq;

    @Column(unique = true)
    private String userId;

    private String userPassword;

    private String userRole;

    private String userName;

    private String phone;

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.userPassword = passwordEncoder.encode(this.userPassword);
    }
}
