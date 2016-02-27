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

	private String payType;

	private String extrakType;

	private String orderPic;

	private String remark;
	
	private String email;

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

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType == null ? null : payType.trim();
	}

	public String getExtrakType() {
		return extrakType;
	}

	public void setExtrakType(String extrakType) {
		this.extrakType = extrakType == null ? null : extrakType.trim();
	}

	public String getOrderPic() {
		return orderPic;
	}

	public void setOrderPic(String orderPic) {
		this.orderPic = orderPic == null ? null : orderPic.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}