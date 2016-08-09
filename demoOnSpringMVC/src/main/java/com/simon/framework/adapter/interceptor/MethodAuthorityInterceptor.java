/**
 * Title: MethodAuthorityInterceptor.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.adapter.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Controller;

/**
 * @ClassName: MethodAuthorityInterceptor <br>
 * @Description: Controler拦截器.暂时未使用 <br>
 */
@Deprecated
public class MethodAuthorityInterceptor implements MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {

		Class<?> clazz = invocation.getThis().getClass();

		if (clazz.isAnnotationPresent(Controller.class)) {
			Controller controller = clazz.getAnnotation(Controller.class);
			String controllerName = controller.value().trim();
			String methodName = invocation.getMethod().getName();

			System.out.println("controllerName:" + controllerName);
			System.out.println("methodName:" + methodName);
		}

		// if (noAuthorith) {
		// return null;
		// }
		// SpringContextHolder.getApplicationContext().findAnnotationOnBean(arg0,
		// arg1)
		// HttpServletRequest request = null;
		// ActionMapping mapping = null;
		// Object[] args = invocation.getArguments();
		// for (int i = 0 ; i < args.length ; i++ )
		// {
		// if (args[i] instanceof HttpServletRequest) request =
		// (HttpServletRequest)args[i];
		// if (args[i] instanceof ActionMapping) mapping =
		// (ActionMapping)args[i];
		// }

		return invocation.proceed();
	}

}
