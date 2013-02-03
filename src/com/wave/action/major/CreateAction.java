package com.wave.action.major;

import java.io.IOException;

import com.wave.action.BasicAciton;
import com.wave.model.Department;
import com.wave.model.Major;
import com.wave.model.User;
import com.wave.service.DepartmentsService;
import com.wave.service.MajorService;
import com.wave.util.KvdbUtil;
import com.wave.util.Util;

public class CreateAction extends BasicAciton {
	
	private int deptid;
	private String name;
	private MajorService majorService;
	public void setMajorService(MajorService majorService) {
		this.majorService = majorService;
	}
	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String execute() {
		// TODO Auto-generated method stub
		if(Util.isNull(name)){
			setErrorCode(400,"����name����");
			return super.execute();
		}
		if(deptid==0){
		//�ӵ�ǰ��¼���û���ȡ	
			try {
				
				deptid=getDepartId();
				System.out.println("departid:"+deptid);
			} catch (ClassNotFoundException e) {
				System.out.println("��û�ҵ������쳣��e:"+e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("��ȡ����io�쳣��"+e.getMessage());
				e.printStackTrace();
			}
		}
		Major major=new Major();
		major.setDeptid(deptid);
		major.setName(name);
		Major majorFromDb=majorService.addMajor(major);
		if(majorFromDb==null){
			setErrorCode(500);
		}else {
			response.setCode(200);
			response.setObject(majorFromDb);
		}
		return super.execute();
	}
	public int getDepartId() throws ClassNotFoundException, IOException{
		String username=(String) request.getAttribute("username");
		System.out.println("username:"+username);
		User user=KvdbUtil.getUserFromKv(username);
		return user.getDepartment_id();
	}

}
