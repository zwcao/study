package org.study.tools.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.w3c.tidy.Tidy;

/**
 * 格式化html，转换成标准HTML
 * 
 * @author czw
 */
public class HTML2XHTML {
	
	public static void htmlCovertTohtml(String sourceFilename, String targetFilename) {
		Tidy tidy = new Tidy();
		tidy.setInputEncoding("UTF-8");
		tidy.setOutputEncoding("UTF-8");
		// 每行的最多字符，如果为0，不自动换行
		tidy.setWraplen(0);
		// 是否保持属性中的空白字符
		tidy.setLiteralAttribs(true);
		// 需要转换的文件，当然你也可以转换URL的内容
		FileInputStream in;
		FileOutputStream out;
		try {
			in = new FileInputStream(sourceFilename);
			out = new FileOutputStream(targetFilename);
			// 输出的文件
			tidy.parse(in, out);
			// 转换完成关闭输入输出流
			out.close();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
