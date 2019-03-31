package com.jl.mis.dto;

/**
 * @author
 * @Version 1.0
 * @Data 2018/6/8 15:11
 */
public class AttributesDTO {
   private int customerId;
   private String  customerName;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
