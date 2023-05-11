package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class HelpCommand extends Command{
	private Object target;
	
	public HelpCommand(Object target) {
		super("Help");			// overrides button text
		this.target = target;
	}
	public HelpCommand() {
		super("Help");
	}
	
	@Override
	public void actionPerformed(ActionEvent ev){
		System.out.println("Help command is invoked...");
		((GameWorld)target).setHelp(true);			// set help flag
		}
}
