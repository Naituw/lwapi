package com.wave.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wave.model.User;

public interface UserDao {
	/**��ȡ��ʦ
	 * @param id
	 * @return
	 */
	public User queryTeacher(int id);
	public User updateUserAll(User user);
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
	/**
	 * �������е��Ñ�
	 * 
	 * @return
	 */
	List queryAll();

	/**
	 * �Ñ���½
	 * 
	 * @param username
	 *            �Ñ���
	 * @param password
	 *            ����
	 * @return �û�ʵ��
	 */
	User login(@Param("username") String username,
			@Param("password") String password);

	/**
	 * ɾ��һ���û�
	 * 
	 * @param userid
	 *            �û���id
	 */
	void deleteUser(@Param("userid") long userId);

	/**
	 * ��ѯһ���û�
	 * 
	 * @param userid
	 *            �û���id
	 * @return �û�ʵ��
	 */
	User queryUserById(@Param("userid") long userId);

	void addUser(@Param("username") String username,
			@Param("password") String password,
			@Param("screenname") String screenname);

	/**�����û�
	 * @param user screenname��gender��level��departmentid,majorid
	 * @return
	 */
	User addUser(User user);

	/**�����û���Ϣ
	 * @param user  email,gender ,screename,password��ѡ 
	 * @return
	 */
	User updateUser(User user);
	
	/**�����û�ͷ��
	 * @param user 
	 * @return
	 */
	User updateUserPicUrl(User user);
	/**��ȡĳ���û�
	 * @param userid  �û�id
	 * @return
	 */
	public User findUser(int userid);
	/**��ҳ��ȡĳ��ϵ����ʦ
	 * @param depart_id
	 * @param count
	 * @param page
	 * @return
	 */
	public List<User> getTeacher(int depart_id,int count ,int page);
	
	
	/**����ĳ����ʦΪ������ʦ
	 * ����ʲô
	 * @param id
	 */
	public User levelup(int id);
	public User levelup(String id);
	
	/**ȡ��ĳ����ʦ�����ʸ�
	 * @param id
	 */
	public User leveldown(int id);

	User updateUserPassword(User user);

	User leveldown(String id);
	public User findUser(String username);
	
	
	
}