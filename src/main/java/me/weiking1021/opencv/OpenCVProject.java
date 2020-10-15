package me.weiking1021.opencv;

import java.io.IOException;

public class OpenCVProject {

	public static void main(String[] args) {
		
		Util.init();
		
		try {
			
//			Doraemon.run();
			Video.run();
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
