package com.wave.action.theses;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.derby.impl.sql.compile.TimeTypeCompiler;

import com.wave.action.BasicAciton;
import com.wave.model.Time;
import com.wave.model.User;
import com.wave.util.KvdbUtil;

/**
 * ����ѡ��ʱ��ͻ�ȡѡ��ʱ�������Ϣ
 * 
 * @author Administrator
 * 
 */
public class TimesAction extends BasicAciton {
	private long teacher_chosen;// ��ʼ����ʦ����ʸ������
	private long title_assign;// ��ʼ���������
	private long title_chosen;// ��ʼѡ�������
	private long compose;// ѡ����������� ����ʼ׫д��
	private long deadline;// ���Ľ�����ʱ��
	private long oral_examination;// ���ʱ��

	public long getTeacher_chosen() {
		return teacher_chosen;
	}

	public void setTeacher_chosen(long teacher_chosen) {
		this.teacher_chosen = teacher_chosen;
	}

	public long getTitle_assign() {
		return title_assign;
	}

	public void setTitle_assign(long title_assign) {
		this.title_assign = title_assign;
	}

	public long getTitle_chosen() {
		return title_chosen;
	}

	public void setTitle_chosen(long title_chosen) {
		this.title_chosen = title_chosen;
	}

	public long getCompose() {
		return compose;
	}

	public void setCompose(long compose) {
		this.compose = compose;
	}

	public long getDeadline() {
		return deadline;
	}

	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}

	public long getOral_examination() {
		return oral_examination;
	}

	public void setOral_examination(long oral_examination) {
		this.oral_examination = oral_examination;
	}

	@Override
	public String execute() {
//		System.out.println("-----time--------");
		String methods = request.getMethod();
		System.out.println("methods:"+methods);
		Time time = new Time();
		try {
			if (methods.equals("GET")) {
				time = getDateImfo();
			} else if (methods.equals("POST")) {
//				if (teacher_chosen == 0 ) {
//					// ������Ϊ0
//					setErrorCode(400,"����teacher_chosen����");
//					return super.execute();
//				}
//				if (title_assign == 0 ) {
//					// ������Ϊ0
//					setErrorCode(400,"����title_assign����");
//					return super.execute();
//				}
//				if (deadline == 0 ) {
//					// ������Ϊ0
//					setErrorCode(400,"����deadline����");
//					return super.execute();
//				}
//				if (oral_examination == 0 ) {
//					// ������Ϊ0
//					setErrorCode(400,"����oral_examination����");
//					return super.execute();
//				}
//				if (compose == 0 ) {
//					// ������Ϊ0
//					setErrorCode(400,"����compose����");
//					return super.execute();
//				}
//				if ( teacher_chosen > title_assign
//						|| title_assign > title_chosen
//						|| title_chosen > compose || compose > deadline
//						|| deadline > oral_examination  ) {
//					// ������Ϊ0
//					setErrorCode(400,"���������ϴ�С�߼�");
//					return super.execute();
//				}

				time = setDateImfo();
				
			}
			if (time != null) {
				response.setCode(200);
				response.setObject(time);
			} else {
				System.out.println("time=null?" + (time == null));
				setErrorCode(404);
			}
		} catch (ClassNotFoundException e) {
			System.out.println("classnotfoundexception:" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("ioexception:" + e.getMessage());
			e.printStackTrace();
		}

		return super.execute();
	}

	public Time getDateImfo() throws ClassNotFoundException, IOException {
		String username = (String) request.getAttribute("username");
		User user = KvdbUtil.getUserFromKv(username);
		Time time = KvdbUtil.getDateInfoFromKv();
		System.out.println("time==null?--->"+(time==null));
		if (user.getLevel() <= 10) {
			time.setTitle_assign(0);
			time.setTeacher_chosen(0);
		}
		return time;
	}

	public Time setDateImfo() throws IOException, ClassNotFoundException {
		Time time = new Time();
		time.setCompose(compose);
		time.setDeadline(deadline);
		time.setOral_examination(oral_examination);
		time.setTeacher_chosen(teacher_chosen);
		time.setTitle_assign(title_assign);
		time.setTitle_chosen(title_chosen);
		KvdbUtil.setDateInFromKv(time);
		return getDateImfo();
	}
}
