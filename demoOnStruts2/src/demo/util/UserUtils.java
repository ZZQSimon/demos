package demo.util;

import java.util.ArrayList;
import java.util.List;

import demo.bean.User;

public class UserUtils {
	public static List<User> genUsers(){
		List<User> l = new ArrayList<User>();
		for (int i = 0; i < 11; i++) {
			User u = new User();
			u.setUsername("Simon"+i);
			u.setPassword("Simon"+i);
			l.add(u);
		}
		return l;
	}
}
