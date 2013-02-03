package com.wave.action.theses;

import com.wave.action.BasicAciton;
import com.wave.model.Title;
import com.wave.model.User;
import com.wave.service.TitleService;
import com.wave.service.UserService;
import com.wave.util.KvdbUtil;
import com.wave.util.Util;

public class ApplyAction extends BasicAciton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private int student_num;
	private int teacherid;// ֻ������ͬϵ�ĳ����ʦ
	private String description;// ������deptid��ѧ���ġ���ѡרҵֻ��ѧ����ͬרҵ
	private UserService userService;
	private TitleService titleService;

	public void setTitle(String title) {
		this.title = title;
	}

	public void setStudent_num(int student_num) {
		this.student_num = student_num;
	}

	public void setTeacherid(int teacherid) {
		this.teacherid = teacherid;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setTitleService(TitleService titleService) {
		this.titleService = titleService;
	}

	@Override
	public String execute() {
		if (Util.isNull(description) ) {
			setErrorCode(400,"description��Ϊ��");
			return super.execute();
		}
		if(Util.isNull(title)){
			setErrorCode(400,"title����Ϊ��");
			return super.execute();
		}
		if(teacherid == 0){
			setErrorCode(400,"teacherid����");
			return super.execute();
		}
		User user = getUser();
		int deptid = user.getDepartment_id();

		if (getDeptid() != deptid) {
			setErrorCode(400,"deptid����������ϵ���id");
			
			return super.execute();
		}
		int majorid = user.getMajor_id();

		Title mytitle = new Title();
		mytitle.setDeptid(deptid);
		mytitle.setAvailableMajors(majorid + "");
		mytitle.setTeacherid(teacherid);
		mytitle.setTitle(title);
		mytitle.setDescription(description);
		mytitle.setRequireInfo("");
		mytitle.setState((byte) 2);
		mytitle.setStudentNum((byte) student_num);
		Title titleFromDbTitle = titleService.add(mytitle);
		if (titleFromDbTitle == null) {
			setErrorCode(403);
			return super.execute();
		}

		response.setCode(200);
		response.setObject(titleFromDbTitle);
		return super.execute();
	}

	public User getUser() {
		String username = (String) request.getAttribute("username");
		try {
			return KvdbUtil.getUserFromKv(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getDeptid() {
		User user = userService.queryTeacher(teacherid);
		if (user == null) {
			return 0;
		}
		return user.getDepartment_id();
	}

}
