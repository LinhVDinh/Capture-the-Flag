package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class TickCommand extends Command{
	private Object target;
	
	public TickCommand(Object target) {
		super("Tick");							// overrides button text
		this.target = target;
	}
	public TickCommand() {
		super("Tick");
	}
	
	@Override
	public void actionPerformed(ActionEvent ev){
		System.out.println("Tick command is invoked...");
		((GameWorld)target).clock(20);			// calls clock()
	}

}