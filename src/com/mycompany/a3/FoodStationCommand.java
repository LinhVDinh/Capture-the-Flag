package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class FoodStationCommand extends Command{
	private Object target;
	
	public FoodStationCommand(Object target) {
		super("Collide with FoodStation");
		this.target = target;
	}
	public FoodStationCommand() {
		super("FoodStation");
	}
	
	@Override
	public void actionPerformed(ActionEvent ev){
		System.out.println("FoodStation command is invoked...");
		((GameWorld)target).foodStationCollision();	// calls FS collision
		}

}