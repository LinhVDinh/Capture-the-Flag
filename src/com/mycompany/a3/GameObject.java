package com.mycompany.a3;
import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;

public abstract class GameObject implements IDrawable, ICollider{
	// declare variables
	private int size;
	private Point location;
	private int width = 1728;
	private int height = 1102;
	private int color;
	private GameWorld world;
	
	Random random = new Random();				// randomizer
	
	public GameObject() {
		location = new Point((float) random.nextInt(width), (float) random.nextInt(height));
		this.setSize(random.nextInt(21) + 30);
	}
	
	// getters
	public Point getLocation() {
		return location;
	}
	public int getSize() {
		return size;
	}
	public int getColor() {
		return color;
	}
	
	// setters
	public void setLocation(float x, float y) {
		this.location.setX(x);
		this.location.setY(y);
	}
	public void setSize(int input) {
		this.size = input;
	}
	public void setColor(int red, int green, int blue) {
		this.color = ColorUtil.rgb(red,green,blue);
	}
	
	// display information
	public String toString() {
		String output; 					// outputs size, location, and color
		output = "[Size]: " + this.getSize() + " [Location]: (" + Math.round(location.getX()) + ", " + Math.round(location.getY()) +
				") [Color]: (" + ColorUtil.red(this.color) + ", " + ColorUtil.green(this.color) + ", " + 
				ColorUtil.blue(this.color) + ")";
		
		return output;
	}
	public void setWorld(GameWorld gw) {this.world = gw;}
	public GameWorld getWorld() {return world;}
	
	
	
}
