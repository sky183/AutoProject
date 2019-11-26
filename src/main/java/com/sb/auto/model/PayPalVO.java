package com.sb.auto.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PayPalVO {
    String itemName;
    String itemNumber;
    String paymentStatus;
    double mcGross;
    double mcFee;
    String mcCurrency;
    String txnId;
    String receiverEmail;
    String payerEmail;
    String custom;

    public void setItemName(Object itemName) {
        this.itemName = (String)itemName;
    }

    public void setItemNumber(Object itemNumber) {
        this.itemNumber = (String)itemNumber;
    }

    public void setPaymentStatus(Object paymentStatus) {
        this.paymentStatus = (String) paymentStatus;
    }

    public void setMcGross(Object mcGross) {
        this.mcGross = Double.parseDouble((String) mcGross);
    }

    public void setMcFee(Object mcFee) {
        this.mcFee = Double.parseDouble((String) mcFee);
    }

    public void setMcCurrency(Object mcCurrency) {
        this.mcCurrency = (String) mcCurrency;
    }

    public void setTxnId(Object txnId) {
        this.txnId = (String) txnId;
    }

    public void setReceiverEmail(Object receiverEmail) {
        this.receiverEmail = (String) receiverEmail;
    }

    public void setPayerEmail(Object payerEmail) {
        this.payerEmail = (String) payerEmail;
    }

    public void setCustom(Object custom) {
        this.custom = (String) custom;
    }
}
