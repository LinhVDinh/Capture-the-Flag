package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class RightCommand extends Command{
	private Object target;
	
	public RightCommand(Object target) {
		super("Right");							// override button text 
		this.target = target;
	}
	public RightCommand() {
		super("Right");
	}
	
	@Override
	public void actionPerformed(ActionEvent ev){
		System.out.println("Right command is invoked...");
		((GameWorld)target).right();				// calls right command
	}

}