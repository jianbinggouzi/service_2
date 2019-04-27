package com.deepblue.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "t_login_log")
public class LoginLog extends BaseDomain {

	@Id
	@Column(name = "login_log_id")
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	private String LoginLogId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "ip")
	private String ip;

	@Column(name = "login_datetime")
	private Date time;

	public String getLoginLogId() {
		return LoginLogId;
	}

	public void setLoginLogId(String loginLogId) {
		LoginLogId = loginLogId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
