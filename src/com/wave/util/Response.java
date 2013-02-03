package com.wave.util;

public class Response {
	private int code;
	private String message="ok";
	private Object object;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}
	
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public void setCode(int code) {
		this.code=code;
		switch (code) {
		case 200:
			message="�ɹ�";
			break;
		case 400:
			message="��������";
			break;
		case 401:
			message="��Ҫ��֤��Ϣ";
			break;
		case 403:
			message="û��Ȩ��";
			break;
		case 404:
			message="����ʧ��";
			break;
		case 500:
			message="����������";
		case 502:
			message="����������";
		}
	}

}
