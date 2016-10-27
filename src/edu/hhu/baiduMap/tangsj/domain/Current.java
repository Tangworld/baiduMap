package edu.hhu.baiduMap.tangsj.domain;

public class Current {

	private String username;
	private String password;
	private String priority;
	
	public Current(){
		
	}
	
	
	public Current(String username, String password, String priority) {
		super();
		this.username = username;
		this.password = password;
		this.priority = priority;
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
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	
}
