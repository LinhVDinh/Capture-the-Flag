package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class ExitCommand extends Command{
	private Object target;
	
	public ExitCommand(Object target) {
		super("Exit");	
		this.target = target;
	}
	public ExitCommand() {
		super("Exit");
	}
	
	@Override
	public void actionPerformed(ActionEvent ev){
		System.out.println("Exit command is invoked...");
		((GameWorld)target).setExit(true);				// set exit flag
	}

}