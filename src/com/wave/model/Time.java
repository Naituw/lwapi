package com.wave.model;

import java.io.Serializable;

public class Time implements Serializable{
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
	
}
