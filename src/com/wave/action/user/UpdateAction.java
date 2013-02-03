package com.wave.action.user;

import java.io.IOException;
import com.wave.action.BasicAciton;
import com.wave.model.Major;
import com.wave.model.User;
import com.wave.service.MajorService;
import com.wave.service.UserService;
import com.wave.util.KvdbUtil;
import com.wave.util.Util;

public class UpdateAction extends BasicAciton {
	private int userid;
	private int deptid;// ����majorid �ɲ���
	private int majorid;// �߼�����Ա������Ա����ʦ��ѡ��ѧ����ѡ
	private int level;// ��ѡ��ֻ�ܴ����ȵ�ǰ�˻��͵����ͣ�ע�����������ƣ�ֻ�ܴ����ڶ��õ��Ǽ���

	private int gender;// ��ѡ
	private String email;// ��ѡ
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
	
	
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
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

	public String getScreenname() {
		return screenname;
	}

	public void setScreenname(String screenname) {
		this.screenname = screenname;
	}

	@Override
	public String execute() {
		if(userid==0){//û��userid����ʾ400����
			setErrorCode(400,"����userid����");
			return super.execute();
		}
		//�ж���û��Ȩ��
		if(!checkLevel()){
			setErrorCode(403);
			return super.execute();
		}
		
		//�ж�
		User user=getUser();
		
		User userFromDb=userService.updateUser(user);
		if(userFromDb==null){
			setErrorCode(502);
			return super.execute();
		}
		//
		KvdbUtil.deleteUser(userFromDb.getUsername());
		response.setCode(200);
		response.setObject(userFromDb);
		return super.execute();
	}

	public User getUser() {
		User user = new User();
		user.setUserid(userid);
		user.setLevel((byte) level);//����Ȩ��
		if (!Util.isNull(email))
			user.setEmail(email);//��������
		if (!Util.isNull(screenname))
			user.setScreenname(screenname);//�����ǳ�
		user.setGender((byte) gender);//�����Ա�
		if (majorid != 0){
			user.setMajor_id(majorid);//����רҵ
			deptid=getDepartmentid();
		}
		user.setDepartment_id(deptid);//����ϵ��
		return user;
	}
	public int getDepartmentid(){
		Major major=majorService.queryMajorById(majorid);
		if(major!=null){
			return major.getDeptid();
		}
		return 0;
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
		System.out.println("userlevel:"+user.getLevel()+",level:"+level);
		return user.getLevel() > level ? true : false;
	}
}
