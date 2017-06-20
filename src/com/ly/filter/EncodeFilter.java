package com.ly.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EncodeFilter implements Filter {

	private String encode;
	
	@Override
	public void destroy() {
		

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//解决响应乱码问题
		response.setContentType("text/html;charset="+encode);
		//解决POST方式提交问题的乱码问题
		request.setCharacterEncoding(encode);
		//放行
		chain.doFilter(new MyRequest((HttpServletRequest)request), response);
	}
	/**
	 * 装饰者设计模式
	 * 修改request对象中的和请求参数相关的三个方法，分别在这三个方法中添加处理乱码的代码
	 * 虽然request对象内部请求参数的乱码仍然存在，但是之后在Servlet中接受参数时，调用接参
	 * 的方法是修改后具体乱码处理方法，所以获取的值是被处理后的
	 */
	private class MyRequest extends HttpServletRequestWrapper{
		HttpServletRequest request;
		public MyRequest(HttpServletRequest request){
			super(request);
			this.request = request;  
		}
		@Override
		public String getParameter(String name) {
				try {
					if("POST".equals(request.getMethod())){
						request.setCharacterEncoding(encode);
						return request.getParameter(name);
					}else if("GET".equals(request.getMethod())){
						String tmp = request.getParameter(name);
						return tmp == null? null : new String(tmp.getBytes("ISO8859-1"),"utf-8");
					}else{
						return request.getParameter(name);
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					throw new RuntimeException(e.getMessage());
				}
			}
	
		@Override
		public Map<String, String[]> getParameterMap() {
			try {
				if("POST".equals(request.getMethod())){
					request.setCharacterEncoding(encode);
					return request.getParameterMap();
				}else if("GET".equals(request.getMethod())){
					//从request对象获取参数的map对象
					Map<String,String[]> map = request.getParameterMap();
					for(Map.Entry<String, String[]>entry : map.entrySet()){
						String vals[] = entry.getValue();
						for(int i = 0; i<vals.length; i++){
							vals[i] = new String(vals[i].getBytes("ISO8859-1"),encode);
						}
					}
					return map;
				}else{
					return request.getParameterMap();
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
		@Override
		public String[] getParameterValues(String name) {
			try {
				if("POST".equals(request.getMethod())){
					request.setCharacterEncoding(encode);
					return request.getParameterValues(name);
				}else if("GET".equals(request.getMethod())){
					String vals[] = request.getParameterValues(name);
					if(vals!= null){
						for(int i = 0; i<vals.length; i++){
							vals[i] = new String(vals[i].getBytes("ISO8859-1"),encode);
						}
					}
					return vals;
				}else{
					return request.getParameterValues(name);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
		
	}
		
	
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		encode = config.getInitParameter("encode");
	}


}
