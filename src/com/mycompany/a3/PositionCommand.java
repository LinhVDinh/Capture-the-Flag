package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PositionCommand extends Command{
	private Object target;
	
	public PositionCommand(Object target) {
		super("Position");			// overrides button text
		this.target = target;
	}
	public PositionCommand() {
		super("Position");
	}
	
	@Override
	public void actionPerformed(ActionEvent ev){
		System.out.println("Position command is invoked...");
		((Game)target).setPosition();	// set flag true
	}

}