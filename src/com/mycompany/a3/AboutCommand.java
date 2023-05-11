package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class AboutCommand extends Command{
	private Object target;
	
	public AboutCommand(Object target) {
		super("About");						// overrides button text
		this.target = target;
	}
	public AboutCommand() {
		super("About");
	}
	
	@Override
	public void actionPerformed(ActionEvent ev){
		System.out.println("About command is invoked...");
		((GameWorld)target).setAbout(true);							// set flag 
		}
}
