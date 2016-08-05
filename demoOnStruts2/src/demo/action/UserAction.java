package demo.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

import demo.bean.User;
import demo.util.UserUtils;
import net.sf.json.JSONArray;

public class UserAction {
	private User user;
	private JSONArray jarray; // 创建JSONArray 用于向页面传递Json格式数据（get、set方法要写上）

	public void setJarray(JSONArray jarray) {
		this.jarray = jarray;
	}

	public String doUserLogin() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> request = (Map<String, Object>) actionContext.get("request");
		request.put("a", "a is in request");
		Map<String, Object> application = actionContext.getApplication();
		application.put("c", "c is in application");

		if (user.getUsername().equals("111") && user.getPassword().equals("111")) { // 写为两个等号
			Map<String, Object> session = actionContext.getSession();
			session.put("login_session_user", user.getUsername());
			session.put("login_session_style", "green");
			return "success";
		} else {
			return "login";
		}
	}

	public String initt() {
		return "success";
	}
	public String add() {
		return "success";
	}
	public String modify() {
		return "success";
	}
	public String delete() {
		return "success";
	}

	public String getList() {
		List<User> list = new ArrayList<User>();
		for (int i = 0; i < 11; i++) {
			User u = new User();
			u.setUsername("Simon" + i);
			u.setPassword("Simon" + i);
			list.add(u);
		}
		JSONArray json = new JSONArray().fromObject(list);// 创建JSONArray
		// 用fromObject方法装入list集合
		jarray = json; // 把集合对象转化为Json格式集合对象
		return "success";
	}

	public UserAction() {
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public JSONArray getJarray() {
		return jarray;
	}
}
