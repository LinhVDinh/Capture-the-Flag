package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PauseCommand extends Command{
	private Object target;
	
	public PauseCommand(Object target) {
		super("Pause");					// overrides button text
		this.target = target;
	}
	public PauseCommand() {
		super("Pause");
	}
	
	@Override
	public void actionPerformed(ActionEvent ev){
		System.out.println("Pause command is invoked...");
		((Game) target).pause();		// calls pause
	}
	

}
