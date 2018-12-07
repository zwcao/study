package org.study.tools.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.xhtmlrenderer.swing.Java2DRenderer;
import org.xhtmlrenderer.util.FSImageWriter;

public class XHTML2Image {

	private String inputFilename = "source.xhtml";
	private String outputFilename = "output.png";

	private int widthImage = 740;
	private int heightImage = 795;

	public void convertToImage() throws IOException {
		System.out
				.println("Calling convertToImage inputFilename=" + inputFilename + " outputFilename=" + outputFilename);
		final File f = new File(inputFilename);
		final Java2DRenderer renderer = new Java2DRenderer(f, widthImage, heightImage);
		final BufferedImage img = renderer.getImage();
		final FSImageWriter imageWriter = new FSImageWriter();
		imageWriter.setWriteCompressionQuality(0.9f);
		imageWriter.write(img, outputFilename);
		System.out.println("Done with rendering");

	}

	public static void main(final String[] args) throws Exception {
		// 1. 表转化转换
//		HTML2XHTML.htmlCovertTohtml("html2Image.html", "html2Image.xhtml");

		final XHTML2Image renderer = new XHTML2Image();
		renderer.inputFilename = "html2Image.xhtml";
		renderer.outputFilename = "out.png";
		System.out.println("Usage : XHTMLToImage INPUTFILE.xhtml OUTPUTFILE.png <width> <height>");

		renderer.widthImage = 740;
		renderer.heightImage = 790;
		renderer.convertToImage();
	}

}
