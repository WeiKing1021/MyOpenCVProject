package me.weiking1021.opencv;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import com.sun.javafx.css.FontFace;

public class Video {

	public static void run() throws Exception {
		
		VideoCapture camera = new VideoCapture();
		camera.open(0);
		
		int width = (int) camera.get(Videoio.CAP_PROP_FRAME_WIDTH);
		int height = (int) camera.get(Videoio.CAP_PROP_FRAME_HEIGHT);

		JFrame jframe = new JFrame("Title");
		jframe.setSize(width, height);
		
	    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    JLabel vidpanel = new JLabel();
	    jframe.setContentPane(vidpanel);
	    jframe.setVisible(true);
	    
	    CascadeClassifier face_dectecter = new CascadeClassifier("D:\\opencvlib\\opencv\\data\\haarcascades\\haarcascade_frontalface_alt.xml");
	    
	    face_dectecter.load("D:\\opencvlib\\opencv\\data\\haarcascades\\haarcascade_frontalface_default.xml");
	    
	    Mat frame = new Mat();

	    int blur = 80;
	    
	    while (true) {
	        if (camera.read(frame)) {
	        	
	        	MatOfRect face_detections = new MatOfRect();

	        	face_dectecter.detectMultiScale(frame, face_detections);

	        	Rect[] rects = face_detections.toArray();
	        	
	        	Imgproc.blur(frame, frame, new Size(blur, blur));
	        	
	        	int count = 0;
	        	
	        	if (rects != null && rects.length > 0) {
	        		
	        		count = rects.length;
	        		
	        		for (Rect rect : rects) {
	        			
	        			Point start_point = new Point(rect.x - 2, rect.y - 2);
	        			Point end_point = new Point(rect.x + rect.width, rect.y + rect.height);
	        			
	        			Imgproc.rectangle(frame, start_point, end_point, Scalar.all(255));
	        		}
	        	}
	        	
	        	BufferedImage text_image = (BufferedImage) HighGui.toBufferedImage(frame);
	        	
	    		Graphics2D graphics = text_image.createGraphics();
	    		
	    		Font font = new Font(Font.SERIF, Font.PLAIN, 20);
	    		
	    		graphics.setColor(Color.WHITE);
	    		graphics.setFont(font);
	    		graphics.drawString("已發現 " + count + " 張臉", 20, graphics.getFontMetrics().getAscent() + 50);
	        	
	        	frame = Util.image2cvMat(text_image);
	        	
	        	//Imgproc.putText(frame, "已發現 " + count + " 張臉", new Point(0, 50), 1, 2.0, Scalar.all(255));
	        	
	        	//Imgproc.putText(frame, "XDD", new Point(0, 50), 1, 2.0, new Scalar(Math.random() * 255, Math.random() * 255, Math.random() * 255));
	        	
	        	//Imgproc.rectangle(frame, new Point(0, 0), new Point(400, 400), new Scalar(0, 0, 255));
	        	
	            ImageIcon image = new ImageIcon(HighGui.toBufferedImage(frame));
	            
	            vidpanel.setIcon(image);
	            vidpanel.repaint();

	        }
	    }
	}
}
