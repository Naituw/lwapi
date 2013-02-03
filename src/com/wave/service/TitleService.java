package com.wave.service;

import java.util.List;

import com.wave.model.Title;

public interface TitleService {
	/**��ȡ���е�������Ŀ�����ڲ���
	 * @return
	 */
	public List<Title> getTitles();

	/**��ȡ��������
	 * @param id ����id
	 * @return ����ʵ��
	 */
	public Title query(int id);

	/** �������
	 * @param title ����ʵ��
	 * @return ����ʵ��
	 */
	public Title add(Title title);

	/** �������(����ʦ���ѧ����������͹���Ա�����ʦ���������)
	 * @param id	����id
	 * @param pass	0 ͨ����ʦ��ˣ�1 ͨ����ˣ�2ѧ�����������
	 * @return
	 */
	public Title verify(int id, int pass);

	/**��ȡĳ��ѧ����ѡ������
	 * @param page ҳ�� 
	 * @param count ��ҳ������������
	 * @param id	רҵid
	 * @return
	 */
	public List<Title> getMyTitle(int page, int count, int id);

	/**��ȡĳ����ʦ����˵�����
	 * @param page ҳ��
	 * @param count ����
	 * @param teacherid ��ʦ��id
	 * @return
	 */
	public List<Title> getTitleByState(int page, int count, int teacherid);

	/**��ȡĳ���߼�����Ա����˵�����
	 * @param page
	 * @param count
	 * @param deptid
	 * @return
	 */
	public List<Title> getTitleByStateDept(int page, int count, int deptid);
	
	/**�������ѡ�������м�1����
	 * @param id ����id
	 * @return
	 */
	public Title plus(int id);
}
