package com.mycompany.a3;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class BGSound implements Runnable{
	private Media m;									// encapulated
	
	public BGSound(String fileName) {
		while (m == null) {
			try 
			{
				InputStream is = Display.getInstance().getResourceAsStream(
						getClass(), "/" + fileName);
				m = MediaManager.createMedia( is , "audio/wav");
			} 
			catch (Exception err)
			{ 
				err.printStackTrace(); 
			}
		}
	}
	public void play() {								// resume
		m.play();
	}
	
	public void pause() {								// pause
		m.pause();
	}
	
	@Override
	public void run() {									// for looping
		m.setTime(0);
		play();
	}
}