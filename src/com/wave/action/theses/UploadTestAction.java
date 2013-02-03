package com.wave.action.theses;

import java.io.File;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;

import com.sina.sae.storage.SaeStorage;
import com.sina.sae.util.SaeUserInfo;
import com.wave.action.BasicAciton;
import com.wave.util.ImageUtils;

public class UploadTestAction extends BasicAciton {
	private File upload;// ʵ���ϴ��ļ�

	private String uploadContentType; // �ļ�����������

	private String uploadFileName; // �ϴ��ļ���

	private String fileCaption;// �ϴ��ļ�ʱ�ı�ע

	private ServletContext context;
	private int user_id;
	
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public ServletContext getContext() {
		return context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}

	public String execute() {

		try {
			// ����ļ��ľ���·��
			String targetDirectory = SaeUserInfo.getSaeTmpPath();
			// �ж�ͼƬ�ĸ�ʽ
			String str = "";
			if (uploadFileName.endsWith("jpg")) {
				str = "jpg";
			} else if (uploadFileName.endsWith("img")) {
				str = "img";
			} else if (uploadFileName.endsWith("png")) {
				str = "png";
			}

			String filename="user_"+user_id+"."+str;
			//�ϴ�Դ�ļ���SaeStorage��ȥ
			File target = new File(targetDirectory, filename);
			System.out.println("file name :" + uploadFileName);
			FileUtils.copyFile(upload, target);
			setUploadFileName(target.getPath());// targetDirectory�����ļ��Ĵ��·��
			
			SaeStorage ss = new SaeStorage();
			ss.upload("pic", target.getPath() ,filename);
			
			
			com.wave.util.ImageUtil.abscut(target.getPath());
			ImageUtils.scale2(target.getPath(), targetDirectory+"user_min_"+user_id+"."+str, 51, 51, true);
			
			
			// ʹ��upload�����ϴ�����Ϊimage��
			ss.upload("picmin", targetDirectory+"user_min_"+user_id+"."+str ,filename);
			
			
			String url=ss.getUrl("pic", filename);
		} catch (Exception e) {
			setErrorCode(502,e.getMessage());
			return super.execute();
		}
		return super.execute();

	}

	public String getFileCaption() {
		return fileCaption;
	}

	public void setFileCaption(String fileCaption) {
		this.fileCaption = fileCaption;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setServletContext(ServletContext context) {
		this.context = context;
	}
}
