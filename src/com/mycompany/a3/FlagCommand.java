package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class FlagCommand extends Command{
	private Object target;
	
	public FlagCommand(Object target) {
		super("Collide with Flag");			// overrides button text
		this.target = target;
	}
	public FlagCommand() {
		super("FlagCollision");
	}
	
	@Override
	public void actionPerformed(ActionEvent ev){
		System.out.println("Flag command is invoked...");
		((GameWorld)target).setFlag(true);	// set flag true
	}

}