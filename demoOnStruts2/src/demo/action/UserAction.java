package demo.action;

import demo.User;

public class UserAction {
	private User user;

	public UserAction() {
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public String doUserLogin() {
		// user=new User();
		if (user.getUsername().equals("111") && user.getPassword().equals("111")) { // 写为两个等号
																						// 不能用等号要用equals()
			return "success";
		}
		// return "success"; //所以结果返回true 用户名密码无限制
		else {
			return "login";
			// return null;
		}
	}
}
