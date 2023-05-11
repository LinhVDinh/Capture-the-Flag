package com.mycompany.a3;

import com.mycompany.a3.GameObject;

public abstract class Movable extends GameObject{
	private int heading;
	private int speed;
	private final float width = 1728;						// width border
	private final float height = 1102;						// height border
	
	public Movable() {
		super();										// parent constructor
	}
	
	// getters
	public int getHeading() {
		return this.heading;
	}
	public int getSpeed() {
		return this.speed;
	}
	
	// setters
	public void setHeading(int input) {
		this.heading = input;
	}
	public void setSpeed(int input) {
		this.speed = input;
	}
	
	
	public void move(int timer) {
		float deltaX = (float) (Math.cos(Math.toRadians(90-heading))*this.speed * (timer/20));		// difference in x 
		float deltaY = (float) (Math.sin(Math.toRadians(90-heading))*this.speed * (timer/20));		// difference in y
		float newLocationX;
		float newLocationY;
		
		newLocationX = this.getLocation().getX() + deltaX;				// update x
		newLocationY = this.getLocation().getY() + deltaY;				// update y
		
		// set boundary
		if (newLocationX < 0)															// in the negative
			newLocationX = 0;															// reset to 0
		if (newLocationY < 0)															// in the negative
			newLocationY = 0;															// reset to 0
		if (newLocationX > width)														// past borders
			newLocationX = width;														// reset to border
		if (newLocationY > height)														// past borders
			newLocationY = height;														// reset to border
			
		this.setLocation(newLocationX, newLocationY);									// update location
		
	}
	
	// display information
	public String toString() {
		String parentString = super.toString();
		String childString = " [Speed]: " + this.getSpeed() + " [Heading]: " + this.getHeading();
		
		return parentString + childString;
	}
}
