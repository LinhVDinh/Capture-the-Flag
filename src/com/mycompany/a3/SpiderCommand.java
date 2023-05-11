package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class SpiderCommand extends Command{
	private Object target;
	
	public SpiderCommand(Object target) {
		super("Collide with Spider");			// overrides button text
		this.target = target;
	}
	public SpiderCommand() {
		super("Spider");
	}
	
	@Override
	public void actionPerformed(ActionEvent ev){
		System.out.println("Spider command is invoked...");
		((GameWorld)target).spiderCollision();	// calls spider collision
		}
}
