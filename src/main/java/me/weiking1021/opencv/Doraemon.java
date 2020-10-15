package me.weiking1021.opencv;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;

public class Doraemon {

	public static void run() throws IOException {
	
		BufferedImage buffered_image = ImageIO.read(new File(new File(""), "./opencv/doraemon.jpg"));
		
		Mat mat = Util.image2cvMat(buffered_image);
		
		Mat mat_b = mat.clone();
		Mat mat_g = mat.clone();
		Mat mat_r = mat.clone();
		
		for (int i=0; i<mat.rows(); i++) {
			for (int j=0; j<mat.cols(); j++) {
				
				byte[] data = new byte[mat.channels()];
				
				mat.get(i, j, data);
				
				mat_b.put(i, j, new byte[] {data[0], 0, 0});
				mat_g.put(i, j, new byte[] {0, data[1], 0});
				mat_r.put(i, j, new byte[] {0, 0, data[2]});
			}
		}
		
		
		BufferedImage image_b = Util.cvMat2image(mat_b);
		BufferedImage image_g = Util.cvMat2image(mat_g);
		BufferedImage image_r = Util.cvMat2image(mat_r);
		
		Util.showImage("Doraemon Blue", image_b);
		Util.showImage("Doraemon Green", image_g);
		Util.showImage("Doraemon Red", image_r);

		/*Util.saveImage("doraemon_b", image_b);
		Util.saveImage("doraemon_g", image_g);
		Util.saveImage("doraemon_r", image_r);*/
	}
}
