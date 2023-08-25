package com.secbro.drools.model.mrule;

import java.math.BigDecimal;
import java.util.Date;

public class CustProdInfo {
    private String custNo;
    private String prodNo;
    private BigDecimal bal;

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getProdNo() {
        return prodNo;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
    }

    public BigDecimal getBal() {
        return bal;
    }

    public void setBal(BigDecimal bal) {
        this.bal = bal;
    }
}
