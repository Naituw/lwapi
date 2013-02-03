package com.wave.filter;

import it.sauronsoftware.base64.Base64;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.wave.model.User;
import com.wave.service.UserService;
import com.wave.util.CodeUtil;
import com.wave.util.KvdbUtil;
import com.wave.util.Response;

public class LoginFilter implements Filter {
	@Autowired
	private static UserService userService;
	private com.wave.util.Response resultResponse = new Response();
	// ��ͨ�û� ���Է��ʵ�url
	private final static String studentUrl = "/account/update_profile.json"+","+"/account/update_avatar.json"+","+"/departments/show.json"+","+"/majors/show.json"+"/theses/apply.json" ;
	// ����Ա ���Է��ʵ�url
	private final static String  adminUrl = "/users/create.json"+","+"/departments/show.json"+","+"/teachers/leveldown.json"+","+"/teachers/levelup.json";
	// �߼�����Ա
	private final static String  advAdminUrl = "" ;
	// ��������Ա�û� ���Է��ʵ�url
	private final static String  superAdminUrl =  "/department/create.json";
	// ��������Ա�û� ���Է��ʵ�url
	private final static String  teacherUrl = "/theses/create_title.json"+","+ "/theses/verify.json"+","+"/theses/";


	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
//		System.out.println("--------filter---------");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		req.setCharacterEncoding("utf-8");
		setHttp(request, response);
	
		
		String questStr=request.getMethod();
//		System.out.println("methods:"+questStr);
		if(!questStr.equals("GET")&&!questStr.equals("POST"))
		return ;
		
		String url = request.getServletPath();
//		System.out.println("url:"+url);
		//��½�ӿ�������֤�������½�ӿ�
		if (url.equals("/account/verify_credentials.json")) {
			chain.doFilter(request, response);
			return;
		}
		
		
		//��ȡ����ֵ���401����Ҫ��֤��Ϣ
		String authStr = request.getHeader("Auth");
//		System.out.println("authStr:"+authStr);
		if (authStr == null || "".equals(authStr)) {
			print(response, 502);
			return;
		}

		//��kvdb�л�ȡ���ݻ��ߴ����ݿ��ȡ�û����׳��쳣��֤����������
		User user = null;
			try {
				user = getUser(authStr);
			} catch (Exception e) {
				
				System.out.println("��½�쳣��"+e.getMessage());
				print(response, 502);
				return ;
			}
			System.out.println("user==null?"+(user==null)+",quanxuan:"+check(url, user));
		//�ж��û��Ƿ���Ȩ�޷�������
		if (user != null && check(url, user)) {
			request.setAttribute("username", user.getUsername());
			chain.doFilter(request, response);
		} else {
			System.out.println("error:403 user==null");
			print(response, 403);
		}

	}

	//����http����Ϣ
	private void setHttp(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "Auth,X-File-Size,X-File-Name");
		response.setHeader("Access-Control-Allow-Methods",
				"POST, GET, OPTIONS");
	}

	private void print(HttpServletResponse response, int code) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			resultResponse.setCode(code);
			String resultStr = new Gson().toJson(resultResponse);
			out.print(resultStr);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void init(FilterConfig arg0) throws ServletException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		userService = (UserService) ac.getBean("userService");
	}

	public User getUser(String headerStr) throws ClassNotFoundException, IOException  {
		String subStr = headerStr.substring(6);
		String endStr = Base64.decode(subStr, "UTF-8");
		String code[] = endStr.split(":");
		String username = code[0];
		String password = CodeUtil.get256Encode(code[1]);
		User user = KvdbUtil.getUserFromKv(username);
		 	
		 if (user != null&&user.getUsername().equals(password)) {
			 System.out.println("��kvdb�л�ȡuser��Ϊ��");
			 return user;
		 } else {
			 System.out.println("kvdb��û�и��û�-->login");
			 User u= userService.login(username, password);
			 KvdbUtil.setUserToKv(u);
			 return u;
		 }
	}


	private boolean check(String uri, User user) {
		System.out.println("uri---->"+uri+",spuerAdminUrl:"+superAdminUrl.toString());
		int count = 0;
		if (superAdminUrl.toString().indexOf(uri) > -1) {
			count = 80;
		} else if (advAdminUrl.toString().indexOf(uri) > -1) {
			count = 70;
		} else if (adminUrl.toString().indexOf(uri) > -1) {
			count = 60;
		} else if (teacherUrl.toString().indexOf(uri) > -1) {
			count = 30;
		} else if (studentUrl.toString().indexOf(uri) > -1)
			count = 10;
		System.out.println("�û���Ȩ��ֵΪ��" + user.getLevel() + ", uri��" + uri
				+ ",��Ҫ��Ȩ��ֵΪ��" + count);
		return user.getLevel() >= count;
	}

}
