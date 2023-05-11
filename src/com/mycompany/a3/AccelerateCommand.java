package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class AccelerateCommand extends Command{
	private Object target;
	
	public AccelerateCommand(Object target) {
		super("Accelerate");					// overrides button text
		this.target = target;
	}
	public AccelerateCommand() {
		super("Accelerate");
	}
	
	@Override
	public void actionPerformed(ActionEvent ev){
		System.out.println("Accelerate command is invoked...");
		((GameWorld) target).accelerate();		// calls accelerate
	}
	

}
