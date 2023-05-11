package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class LeftCommand extends Command{
	private Object target;
	
	public LeftCommand(Object target) {
		super("Left");					// override button text					
		this.target = target;
	}
	public LeftCommand() {
		super("Left");
	}
	
	@Override
	public void actionPerformed(ActionEvent ev){
		System.out.println("Left command is invoked...");
		((GameWorld)target).left();		// calls left
	}

}
