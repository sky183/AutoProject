package com.sb.auto.model;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

/*@Getter
@Setter*/
@Data
@Entity
//@Table(name = "USER")
public class UserEntity {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
//    @Id @GeneratedValue
    private Integer userSeq;

    @Column(unique = true)
    private String userId;

    private String userPassword;

    private String userRole;

    private String userName;

    private String phone;

    private int point;

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.userPassword = passwordEncoder.encode(this.userPassword);
    }
}
