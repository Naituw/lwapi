package com.wave.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.sina.sae.kvdb.SaeKV;
import com.sina.sae.kvdb.SaeKVUtil;
import com.wave.model.Department;
import com.wave.model.Major;
import com.wave.model.Time;
import com.wave.model.User;

public class KvdbUtil {
	private final static SaeKV saeKV = new SaeKV();
	static {
		saeKV.init();
	}

	/**��sae KVDB�л�ȡ�û�ʵ��
	 * @param username �û���
	 * @return  user����
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static User getUserFromKv(String username)
			throws ClassNotFoundException, IOException {
		System.out.println("dbkv return user--->username="+username);
		return SaeKVUtil.deserializable(saeKV.get(username));
	}

	/**���û�����DBKV���ݿ���
	 * @param user �û�ʵ������ֵΪusername
	 * @throws IOException
	 */
	public static void setUserToKv(User user) throws IOException {
		System.out.println("dbkv set user--->username="+user.getUsername());
		saeKV.set(user.getUsername(), SaeKVUtil.serializable(user));
	}
	
	/**��ϵ�����DBKV���ݿ���
	 * @param department ϵ��ʵ������ֵΪdeptid
	 * @throws IOException
	 */
	public static void setDepartmentToKv(Department department)throws IOException{
		System.out.println("dbkv set  department--->deptid="+department.getDeptid());
		saeKV.set("departments:"+department.getDeptid(), SaeKVUtil.serializable(department));
	}
	/**
	 * @param id ϵ��id
	 * @return	ϵ��ʵ��
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Department getDepartmentFromKv(String id)
			throws ClassNotFoundException, IOException {
		System.out.println("dbkv return department--->deptid="+id);
		return SaeKVUtil.deserializable(saeKV.get("departments:"+id));
	}
	/** ������ϵ�����ݴ���kvdb��ȥ
	 * @param departments
	 * @throws IOException
	 */
	public static void setAllDepartmentToKv (List<Department> departments)throws IOException{
		System.out.println("dbkv set alldepartments");
		saeKV.set("allDepartments",  SaeKVUtil.serializable(departments));
	}
	
	/** ������ϵ�����ݴ�kvdbȡ��
	 * @return ϵ�𼯺�
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static List<Department> getAllDepartment() throws ClassNotFoundException, IOException{
		System.out.println("dbkv return alldepartments");
		return SaeKVUtil.deserializable(saeKV.get("allDepartment"));
	}
	/**��רҵ��Ϣ����kvdb��ȥ
	 * @param major �û�ʵ��
	 * @throws IOException
	 */
	public static void setMajorsToKv(Major major) throws IOException{
		System.out.println("dbkv set major:"+major);
		saeKV.set("major:"+major.getMajorid(), SaeKVUtil.serializable(major));
	}
	/**
	 * @param majorid רҵid
	 * @return רҵʵ��
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Major getMajorFromKv(String majorid)throws ClassNotFoundException, IOException{
		System.out.println("dbkv return major:"+majorid);
		return SaeKVUtil.deserializable("departments:"+majorid);
	}
	
	
	public static Time getDateInfoFromKv() throws ClassNotFoundException, IOException{
		System.out.println("dbkv get dateinfo");
		Time time= SaeKVUtil.deserializable(saeKV.get("dateinfo"));
		System.out.println("time:"+(time==null));
		return time;
	}
	public static void setDateInFromKv(Time time) throws IOException{
		System.out.println("dbkv set dateinfo");
		System.out.println("time==null?"+(time==null));
		saeKV.set("dateinfo",SaeKVUtil.serializable(time));
	}
	public static void delete(String key){
		System.out.println("ɾ��key:"+key+"�Ļ���");
		saeKV.rdel(key,key,true,true,1);
	}
	public static void deleteUser(String username){
		System.out.println("ɾ��username:"+username+"�Ļ���");
		delete(username);
	}
}
