package me.weiking1021.opencv;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class OpenCVProject {
	
	private static final int IMG_WIDTH = 100;
	
	private static final int IMG_HEIGHT = 100;
	
	private static final int CV_TYPE = CvType.CV_8UC4;

	public static void main(String[] args) {
		
		nu.pattern.OpenCV.loadShared();
		
		Mat mat = Mat.ones(IMG_WIDTH, IMG_HEIGHT, CV_TYPE);
		
		for (int i=0; i<IMG_WIDTH; i++) {
			for (int j=0; j<IMG_HEIGHT; j++) {
				
				//byte a = (byte) (Math.random() * 255 - 128);
				byte b = (byte) (Math.random() * 255 - 128);
				byte g = (byte) (Math.random() * 255 - 128);
				byte r = (byte) (Math.random() * 255 - 128);
				
				mat.put(i, j, new byte[] {-1, b, g, r});
			}
		}
		
		//Core.subtract(mat, Mat.ones(IMG_WIDTH, IMG_HEIGHT, CV_TYPE), mat);		
		
		BufferedImage image = showResult(mat);
		
		long current_time = System.currentTimeMillis();
		
		saveImage(image, "image_" + current_time);
		
		for (int i=1; i<10; i++) {
			
			Mat new_mat = Mat.zeros(IMG_WIDTH, IMG_HEIGHT, CV_TYPE);
			
//			Imgproc.blur(mat, new_mat, new Size(i, i));
			
			new_mat = mat.clone();
			
			Imgproc.line(new_mat, new Point(0, 0), new Point(1, 1), new Scalar(255.0, 1.0, 1.0, 1.0));
			
			BufferedImage new_image = mat2BufferedImage(new_mat);
			
			saveImage(new_image, "image_" + current_time + " (line" + i + ")");
		}
	}
	
	public static void saveImage(BufferedImage image, String image_name) {
		
		try {
			
			File target_file = new File(new File(""), "./opencv/" + image_name + ".png");
			
			if (!target_file.exists()) {
				
				target_file.getParentFile().mkdirs();
				
				target_file.createNewFile();
			}
			
			ImageIO.write(image, "png", target_file);
			
		} catch (IOException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}	
	}

	public static BufferedImage mat2BufferedImage(Mat target_mat) {
	
		int type = BufferedImage.TYPE_BYTE_GRAY;
		
		if (target_mat.channels() > 3) {
			
			type = BufferedImage.TYPE_4BYTE_ABGR;
		}
		else if (target_mat.channels() > 1) {
			
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		
		int buffer_size = target_mat.channels() * target_mat.cols() * target_mat.rows();
		
		byte[] mat_data_array = new byte[buffer_size];
		
		target_mat.get(0, 0, mat_data_array);
		
		BufferedImage image = new BufferedImage(target_mat.cols(), target_mat.rows(), type);
		
		byte[] image_data_array = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		
		System.arraycopy(mat_data_array, 0, image_data_array, 0, mat_data_array.length);
		
		return image;
	}
	
	public static BufferedImage showResult(Mat target_mat) {
	    
	    BufferedImage bufered_image = mat2BufferedImage(target_mat);

	    int offset_y = 50;
	    
	    try {
	        
	        JFrame frame = new JFrame("My OpenCV project");

	        frame.setSize(512, 512 + offset_y);
	        
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	        
	        ImageIcon image_icon = new ImageIcon(bufered_image.getScaledInstance(512, 512, Image.SCALE_DEFAULT));
	        
	        JLabel label = new JLabel(image_icon);
	        
	        label.setSize(512, 512);
	        
	        label.setLocation(0, offset_y);
	        
	        frame.add(label);
	        
	        frame.setVisible(true);
	    }
	    catch (Exception e) {
	    	
	        e.printStackTrace();
	    }
	    
	    return bufered_image;
	}
}
