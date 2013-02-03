package com.wave.action.users;

import java.io.IOException;
import java.util.List;

import com.wave.action.BasicAciton;
import com.wave.model.Department;
import com.wave.model.Major;
import com.wave.model.User;
import com.wave.service.DepartmentsService;
import com.wave.service.MajorService;
import com.wave.service.UserService;
import com.wave.util.KvdbUtil;

public class ShowAction extends BasicAciton {
	private int deptid;// ��ѡ
	private int majorid;// ��ѡ
	private int level;// ��ѡ��ֻ��ʾ��ǰlevel�ģ�����minlevel��maxlevel Ӱ��
	private int min_level;// ��ѡ��level���ڻ�������ֵ��
	private int max_level;// ��ѡ��levelС�ڻ�������ֵ��
	private int page = 1;
	private int count = 20;
	private MajorService majorService;
	private UserService userService;
	private DepartmentsService departmentService;



	public DepartmentsService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentsService departmentService) {
		this.departmentService = departmentService;
	}

	public MajorService getMajorService() {
		return majorService;
	}

	public void setMajorService(MajorService majorService) {
		this.majorService = majorService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
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

	public int getMin_level() {
		return min_level;
	}

	public void setMin_level(int min_level) {
		this.min_level = min_level;
	}

	public int getMax_level() {
		return max_level;
	}

	public void setMax_level(int max_level) {
		this.max_level = max_level;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String execute() {
		if (deptid == 0 && majorid == 0) {
			setErrorCode(400, "deptid��majorid����ͬʱΪ0");
			return super.execute();
		}
		User user = null;
		try {
			user = getUser();
		} catch (Exception e) {
			e.printStackTrace();
			setErrorCode(502);
			return super.execute();
		}

		List<User> users = null;
		int userLevel = user.getLevel();
		if (userLevel == 80) {// ��������Աû�κ�����
			// users=setUsers(users);
		} else if (userLevel >= 30 && userLevel <= 70) {// ���˳�������Ա��ѧ���⣬�ܲ鿴��ϵ�������û���Ϣ
			if (majorid != 0) {
				Major major = getMajor(majorid);
				if (user.getDepartment_id() != major.getDeptid()) {
					setErrorCode(403);
					return super.execute();
				}
				// users=setUsers(users);
			} else if (deptid != 0) {
				Department department = getDepartment(deptid);
				if (user.getDepartment_id() != deptid) {
					setErrorCode(403);
					return super.execute();
				}
				// users=setUsers(users);
			}

		} else if (userLevel == 10) {
			if (level != 10) {
				setErrorCode(403);
				return super.execute();
			}

		}
		users = setUsers(users, userLevel);

		if (users == null) {
			setErrorCode(403);
			return super.execute();
		}
		response.setCode(200);
		response.setObject(users);
		return super.execute();
	}

	public List<User> setUsers(List<User> users, int userlevel) {
		if (level != 0) {// ��ȡĳ��Ȩ�޵��û�

			users = userService.getUser(deptid, majorid, count, page, level);
		} else if (min_level != 0) {// ��ȡ�������min_level���û�����������
			users = userService.getUserBiggerLevel(deptid, majorid, count,
					page, min_level, userlevel);
		} else if (max_level != 0) {// ��ȡС��max_level���û�
			users = userService.getUserLittlerLevel(deptid, majorid, count,
					page, max_level);
		}
		System.out.println("users-show max_level:" + max_level + ",level:"
				+ level + ",min_level" + min_level + ",page:" + page
				+ ",majorid:" + majorid + "");
		return users;
	}

	public User getUser() throws ClassNotFoundException, IOException {
		String username = (String) request.getAttribute("username");
		User user = KvdbUtil.getUserFromKv(username);
		return user;
	}

	public Major getMajor(int majorid) {
		return majorService.queryMajorById(majorid);
	}

	public Department getDepartment(int id) {
		return departmentService.queryDepartmentsById(id);
	}
}
