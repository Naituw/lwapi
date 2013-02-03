package com.wave.service;

import java.util.List;

import com.wave.model.User;

/**
 * ��˵����
 * 
 * @author ����: ����
 * @version ����ʱ�䣺2012-12-25 
 */
public interface UserService {

	/**�����û�
	 * @param user screenname��gender��level��departmentid,majorid
	 * @return
	 */
	public abstract User addUser(User user);
	/**�����û���Ϣ
	 * @param user  email,gender ,screename,password��ѡ 
	 * @return
	 */
	public abstract User updateUser(User user);
	
	/**
	 * ɾ��һ���û�
	 * 
	 * @param userid
	 *            �û���id
	 */
	public abstract void deleteUser(long userId);
	/**
	 * ��ѯһ���û�
	 * 
	 * @param userid
	 *            �û���id
	 * @return �û�ʵ��
	 */
	public abstract User queryUserById(long userId);
	/**
	 * �������е��Ñ�
	 * 
	 * @return
	 */
	public abstract List<User> findAllUser();
	/**
	 * �Ñ���½
	 * 
	 * @param username
	 *            �Ñ���
	 * @param password
	 *            ����
	 * @return �û�ʵ��
	 */
	public abstract User login(String username,String password);

	public abstract void addUser(String username, String password,
			String screenname);
	/**�����û�ͷ��
	 * @param user 
	 * @return
	 */
	public abstract User updateUserPicUrl(User user);
	/**��ȡĳ���û�
	 * @param userid  �û�id
	 * @return
	 */
	public User findUser(int userid);
	/**��ȡȨ�ޱ�level����û�
	 * @param depart_id	����id
	 * @param major_id	רҵid
	 * @param count		��ҳ������ʾ������
	 * @param page		ҳ��
	 * @param level		Ȩ��
	 * @return  
	 */
	public List<User>getUserLittlerLevel(int depart_id,int major_id,int count,int page,int level );
	
	/**��ȡȨ�ޱ�levelС���û�
	 * @param depart_id	����id
	 * @param major_id	רҵid
	 * @param count		��ҳ������ʾ������
	 * @param page		ҳ��
	 * @param level		Ȩ��
	 * @return 
	 * @return
	 */
	public List<User>getUserBiggerLevel(int depart_id,int major_id,int count,int page,int level ,int userlevel);
	/**��ҳ��ȡĳ��ϵ����ʦ
	 * @param depart_id
	 * @param count
	 * @param page
	 * @return
	 */
	public List<User>getTeacher(int depart_id,int count,int page);
	/**��ȡȨ�޵���level���û�
	 * @param depart_id	����id
	 * @param major_id	רҵid
	 * @param count		��ҳ������ʾ������
	 * @param page		ҳ��
	 * @param level		Ȩ��
	 * @return 
	 * @return
	 */
	public List<User>getUser(int depart_id,int major_id,int count,int page,int level );
	
	public  User updateUserPassword(User user);

	/**����ĳ����ʦΪ������ʦ
	 * ����ʲô��
	 * @param id
	 */
	public User levelup(int id);
	public User levelup(String id);
	/**ȡ��ĳ����ʦ�����ʸ�
	 * @param id
	 */
	public User leveldown(int id);
	public User leveldown(String id);
	public User updateUserAll(User user);
	public User queryTeacher(int id);
	public abstract User findUser(String username);
}