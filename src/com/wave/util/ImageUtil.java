package com.wave.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import javax.imageio.ImageIO;
public class ImageUtil {
   
    public static void abscut(String srcImageFile) {
    	//��ʼ������x��y 
        try {
        	System.out.println("ͼƬ�ĵ�ַ���ļ�����"+srcImageFile);
            Image img;
            ImageFilter cropFilter;
            // ��ȡԴͼ��
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcWidth = bi.getWidth(); // Դͼ���
            int srcHeight = bi.getHeight(); // Դͼ�߶�
           
            int destLength;
            int x=0,y=0;
            if(srcHeight<srcWidth){
            	destLength=srcHeight;
            	x=(srcWidth-srcHeight)/2;
            }else{
            	destLength=srcWidth;
            	y=(srcHeight-srcWidth)/2;
            }
            
            	 
            
            System.out.println("srcWidth= " + srcWidth + "\tsrcHeight= "
                    + srcHeight);
            System.out.println("destLength:"+destLength);
            
                Image image = bi.getScaledInstance(srcWidth, srcHeight,Image.SCALE_DEFAULT);//��ȡ���ź��ͼƬ��С
                cropFilter = new CropImageFilter(x, y, destLength, destLength);//�ü�����ʼ�������,��͸�
                img = Toolkit.getDefaultToolkit().createImage(
                        new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(destLength, destLength,
                        BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, null); // ���ƽ�ȡ���ͼ
                g.dispose();
                // ���Ϊ�ļ�
                ImageIO.write(tag, "JPEG", new File(srcImageFile));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
    public static void main(String[] args) {
        abscut("E:\\a.jpg");
        System.out.println("�и�ɹ�");
    }}