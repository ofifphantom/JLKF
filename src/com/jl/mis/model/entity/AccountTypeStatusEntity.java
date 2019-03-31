package com.jl.mis.model.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author
 * @Version 1.0
 * @Data 2018/5/24 8:37
 */
public class AccountTypeStatusEntity implements Serializable {
    private Integer id;
    private String accountTypeStatusName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountTypeStatusName() {
        return accountTypeStatusName;
    }

    public void setAccountTypeStatusName(String accountTypeStatusName) {
        this.accountTypeStatusName = accountTypeStatusName;
    }


}
