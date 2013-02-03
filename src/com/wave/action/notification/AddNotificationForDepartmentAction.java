package com.wave.action.notification;

import com.wave.action.BasicAciton;
import com.wave.model.Notification;
import com.wave.service.NotificationService;

/**
 * ��ĳ��ϵ����ʦ������֪ͨ
 * 
 * @author Administrator
 * 
 */
public class AddNotificationForDepartmentAction extends BasicAciton {
	private int depart_id;// ϵid
	private String title;// ֪ͨ����
	private String content;// ����
	private String url;// ����(��ѡ)
	private int type;// ���� 50Ϊ������ϵ����ʦ 30Ϊ������ϵ��ѧ��

	private NotificationService notificationService;

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public void setDepart_id(int depart_id) {
		this.depart_id = depart_id;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String execute() {
		if (title == null || title.equals("")) {
			setErrorCode(400,"����title����");
			return super.execute();
		}
		if ( content == null|| content.equals("")) {
			setErrorCode(400,"����contentΪ��");
			return super.execute();
		}
		
		if ( depart_id == 0 ) {
			setErrorCode(400,"����depart_id��Ϊ0");
			return super.execute();
		}
		if ( type == 0 ) {
			setErrorCode(400,"����type��Ϊ0");
			return super.execute();
		}
		
		Notification notification=notificationService.addNotificationForDepartment(title, content, depart_id, type,url);
		if(notification==null){
			setErrorCode(404);//����ʧ��
			return super.execute();
		}
		response.setCode(200);
		response.setObject(notification);
		return super.execute();

	}
}
