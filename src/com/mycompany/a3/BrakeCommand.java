package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class BrakeCommand extends Command{
	private Object target;
	
	public BrakeCommand(Object target) {
		super("Brake");							// overrides button text
		this.target = target;
	}
	public BrakeCommand() {
		super("Brake");
	}
	
	@Override
	public void actionPerformed(ActionEvent ev){
		System.out.println("Brake command is invoked...");
		((GameWorld)target).brake();			// calls brake
		}

}
