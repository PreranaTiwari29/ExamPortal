package exam.portal.dtos;

import javax.validation.constraints.NotEmpty;

public class LoginDTO {

	@NotEmpty
	private String userid;
	@NotEmpty
	private String pwd;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	
}
