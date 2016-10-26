package edu.hhu.baiduMap.tangsj.domain;

public class User {

	private String username;
	private String password;
	private String gender;
	private String phone;
	private String mail;
	private String birthday;
	private String area;
	private String hobby;
	private String motto;
	
	public User(){
		
	}
	
	public User(String username, String password, String gender, String phone,
			String mail, String birthday, String area, String hobby,
			String motto) {
		super();
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.phone = phone;
		this.mail = mail;
		this.birthday = birthday;
		this.area = area;
		this.hobby = hobby;
		this.motto = motto;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getMotto() {
		return motto;
	}
	public void setMotto(String motto) {
		this.motto = motto;
	}
	
}
