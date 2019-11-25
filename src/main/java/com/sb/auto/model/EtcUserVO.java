package com.sb.auto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class EtcUserVO {
    @JsonIgnore
    private Integer userNum;
    private String userId;
    @JsonIgnore
    private String userPw;
}
