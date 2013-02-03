package com.wave.action.user;

import java.io.IOException;

import com.wave.action.BasicAciton;
import com.wave.model.Major;
import com.wave.model.User;
import com.wave.service.MajorService;
import com.wave.service.UserService;
import com.wave.util.CodeUtil;
import com.wave.util.KvdbUtil;
import com.wave.util.Util;

public class CreateAction extends BasicAciton {
	private int deptid;// ����majorid �ɲ���
	private int majorid;// �߼�����Ա������Ա����ʦ��ѡ��ѧ����ѡ
	private int level;// ��ѡ��ֻ�ܴ����ȵ�ǰ�˻��͵����ͣ�ע�����������ƣ�ֻ�ܴ����ڶ��õ��Ǽ���
	private String username;

	private int gender;// ��ѡ
	private String email;// ��ѡ
	private String password;// ��ѡ,������usernameһ��
	private String screenname;// ��ѡ

	private UserService userService;
	private MajorService majorService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public MajorService getMajorService() {
		return majorService;
	}

	public void setMajorService(MajorService majorService) {
		this.majorService = majorService;
	}

	public int getDeptid() {
		return deptid;
	}

	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}

	public int getMajorid() {
		return majorid;
	}

	public void setMajorid(int majorid) {
		this.majorid = majorid;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getScreenname() {
		return screenname;
	}

	public void setScreenname(String screenname) {
		this.screenname = screenname;
	}

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		if (Util.isNull(username) ) {
			setErrorCode(400,"����username����");// ��������
			return super.execute();
		}
		if(level == 0){
			setErrorCode(400,"����levelΪ0");// ��������
			return super.execute();
		}
		if((deptid == 0 && majorid == 0) ){
			setErrorCode(400,"����deptid��majorid����ͬʱΪ0");// ��������
			return super.execute();
		}
		
		if((level == 10 && majorid == 0)){
			setErrorCode(400,"����level��majorid����");// ��������
			return super.execute();
		}
		

		// �ж�Ȩ��
		if (!checkLevel()) {
			setErrorCode(403);// û��Ȩ��
			return super.execute();
		}

		if ( majorid != 0) {
			Major major = majorService.queryMajorById(majorid);
			deptid = major.getDeptid();
		}
		
		User user=userService.addUser(getUser());
		if(user==null){
			setErrorCode(500);
			
		}else{
			response.setCode(200);
			response.setObject(user);
		}

		return super.execute();
	}

	public User getUser() {
		User user = new User();
		user.setUsername(username);
		user.setLevel((byte) level);
		if (!Util.isNull(email))
			user.setEmail(email);
		if (!Util.isNull(screenname))
			user.setScreenname(screenname);
//		if (gender != 0)
			user.setGender((byte) gender);
		if(majorid!=0)
			user.setMajor_id(majorid);
		user.setDepartment_id(deptid);
		user.setPassword(parsePassword());
		return user;
	}
	
	public String  parsePassword(){
		if(Util.isNull(password)){
			password=username;
		}
		return CodeUtil.get256Encode(password);
	}

	// ����Ȩ��
	public boolean checkLevel() {
		String name = (String) request.getAttribute("username");
		User user = null;
		try {
			user = KvdbUtil.getUserFromKv(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return user.getLevel() > level ? true : false;
	}
}
