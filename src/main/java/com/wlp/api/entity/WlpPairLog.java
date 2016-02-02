package com.wlp.api.entity;

import java.util.Date;

public class WlpPairLog {
    private String id;

    private Long pairMoney;

    private String fromUser;

    private String toUser;

    private Date orderTime;

    private Date pairTime;

    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Long getPairMoney() {
        return pairMoney;
    }

    public void setPairMoney(Long pairMoney) {
        this.pairMoney = pairMoney;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser == null ? null : fromUser.trim();
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser == null ? null : toUser.trim();
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getPairTime() {
        return pairTime;
    }

    public void setPairTime(Date pairTime) {
        this.pairTime = pairTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}