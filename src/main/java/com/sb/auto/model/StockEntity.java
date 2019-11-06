package com.sb.auto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class StockEntity<T> extends EtcUser {
    private Integer number;
    private String nickname;
    private String blance1;
    private String blance2;
    private Integer blance3;
    private Integer blance4;
    private Integer blance5;
    @JsonIgnore
    private T data;
}
