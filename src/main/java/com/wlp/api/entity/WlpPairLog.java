package com.wlp.api.entity;

import java.util.Date;

public class WlpPairLog {

	private String orderPic;

	public String getOrderPic() {
		return orderPic;
	}

	public void setOrderPic(String orderPic) {
		this.orderPic = orderPic == null ? null : orderPic.trim();
	}

	private String id;
	
	/**
	 * 查询条件用，关联邮箱
	 */
	private String email;

	/**
	 * 配对金额
	 */
	private Long pairMoney;

	/**
	 * 支付�?
	 */
	private String fromUser;

	/**
	 * 受益�?
	 */
	private String toUser;

	/**
	 * 交易完成时间
	 */
	private Date orderTime;

	/**
	 * 匹配时间
	 */
	private Date pairTime;

	/**
	 * 交易状�?：状态：0--未完成，1--已完成
	 */
	private String status;
	
	/**
	 * 支付方式：bank--银行,alipay--支付宝?wechat--微信
	 */
	private String payType;

	/**
	 * 提现类型：dynamic--互助提现,help--互助提现
	 */
	private String extrakType;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}