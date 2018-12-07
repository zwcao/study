package org.study.tools.utils;

import java.io.File;

import gui.ava.html.Html2Image;
import gui.ava.html.image.generator.HtmlImageGenerator;

/**
 * html文件转图片
 * 
 * @author czw
 */
public class Html2ImageUtils {

	public static void main(String[] args) throws Exception {
		 html2Image1();
//		html2Image2();
	}

	/**
	 * 优先推荐
	 */
	public static void html2Image1() {
		Html2Image.fromFile(new File("html2Image.html")).getImageRenderer().saveImage("hello-world1.png");
	}

	/**
	 * 对css支持不够好
	 * 
	 * @throws Exception
	 */
	public static void html2Image2() throws Exception {
		HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
		imageGenerator.loadUrl(new File("html2Image.html").toURI().toURL());
		imageGenerator.getBufferedImage();
		imageGenerator.saveAsImage("hello-world2.png");
	}
}
