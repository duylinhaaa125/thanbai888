package model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "User")
public class User implements Serializable{
	@Id
	@Column
	private String userName;
	@Column
	private String passWord;
	@Column
	private int currentMoney;
	
	
	

	public User() {
		super();
	}

	public User(String userName, String passWord, int currentMoney) {
		super();

		this.userName = userName;
		this.passWord = passWord;
		this.currentMoney = currentMoney;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public int getCurrentMoney() {
		return currentMoney;
	}

	public void setCurrentMoney(int currentMoney) {
		this.currentMoney = currentMoney;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", passWord=" + passWord + ", currentMoney=" + currentMoney + "]";
	}
	
	
	
	
	
}
