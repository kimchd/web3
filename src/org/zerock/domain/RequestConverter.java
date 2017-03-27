package org.zerock.domain;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

public class RequestConverter {
	
	public static Object convertin(HttpServletRequest request,
			String className)throws Exception{
		
		Class clz =Class.forName(className);
		Object obj=clz.newInstance();
		BeanInfo info= Introspector.getBeanInfo(clz);
		PropertyDescriptor[] props= info.getPropertyDescriptors();
		
		
		for (PropertyDescriptor pro : props) {
			String name = pro.getName();
			Method setMethod = pro.getWriteMethod();
			if(setMethod ==null)continue;
			Class propType= pro.getPropertyType();
			String paramVal= request.getParameter(name);
			if(paramVal==null)continue;
			
			if(propType.getName().equals("java.lang.String")){
				setMethod.invoke(obj, paramVal);
			}

		}
		
		return obj;
	}
	
	

}
