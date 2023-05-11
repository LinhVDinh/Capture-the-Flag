package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class SoundCommand extends Command{
	private Object target;
	
	public SoundCommand(Object target) {
		super("Sound");										
		this.target = target;
	}
	public SoundCommand() {
		super("Sound");
	}
	
	@Override
	public void actionPerformed(ActionEvent ev){
		System.out.println("Sound command is invoked...");
		if (((GameWorld)target).getSound() == false)	// set sound on or off
			((GameWorld)target).setSound(true);
		else
			((GameWorld)target).setSound(false);
		}

}