package com.wlp.api.entity;

/**
 * 我的钱包
 * @author Administrator
 *
 */
public class WlpWallet {
    private String id;

    /**
     * 邮箱
     */
	private String email;

	/**
	 * 本金
	 */
	private Long capital;

	/**
	 * 分红
	 */
	private Long bonus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public Long getCapital() {
		return capital;
	}

	public void setCapital(Long capital) {
		this.capital = capital;
	}

	public Long getBonus() {
		return bonus ;
	}

	public void setBonus(Long bonus) {
		this.bonus = bonus;
	}
}