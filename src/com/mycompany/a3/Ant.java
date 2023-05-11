package com.mycompany.a3;

import java.util.Vector;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.mycompany.a3.ISteerable;
import com.mycompany.a3.Movable;

public class Ant extends Movable implements ISteerable{					// able to move
	private static Ant theAnt;
	// declare variables
	private int maximumSpeed;
	private int foodLevel;
	private int foodConsumptionRate;
	private int healthLevel;
	private int lastFlagReached;
	private Vector<GameObject> collisionVector = new Vector<GameObject>();	// vector for collisions

	
	// Constructor
	private Ant() {
		this.setMaximumSpeed(5);
		this.setFoodLevel(25);
		this.setFoodConsumptionRate(5);
		this.setHealthLevel(5);
		this.setLastFlagReached(1);
		super.setColor(0, 0, 255);
		super.setSize(30);
		super.setHeading(0);
		super.setSpeed(1);
	}
	// another constructor for location
	private Ant(float x, float y) {
		this.setMaximumSpeed(5);
		this.setFoodLevel(25);
		this.setFoodConsumptionRate(5);
		this.setHealthLevel(5);
		this.setLastFlagReached(1);
		super.setColor(0, 0, 255);
		super.setSize(30);
		super.setHeading(0);
		super.setSpeed(1);
		this.setLocation(x, y);					// set initial location
	}
	public static Ant getAnt() {
		if (theAnt == null)						// singleton pattern
			theAnt = new Ant();
		return theAnt;
	}
	public static Ant getAnt(float x, float y) {
		if (theAnt == null)						// singleton pattern
			theAnt = new Ant(x, y);
		return theAnt;
	}
	
	
	// getters
	public int getMaximumSpeed() {
		return this.maximumSpeed;
	}
	public int getFoodLevel() {
		return this.foodLevel;
	}
	public int getFoodConsumptionRate() {
		return this.foodConsumptionRate;
	}
	public int getHealthLevel() {
		return this.healthLevel;
	}
	public int getLastFlagReached() {
		return this.lastFlagReached;
	}
	
	// setters
	public void setMaximumSpeed(int input) {
		this.maximumSpeed = input;
	}
	public void setFoodLevel(int input) {
		this.foodLevel = input;
	}
	public void setFoodConsumptionRate(int input) {
		this.foodConsumptionRate = input;
	}
	public void setHealthLevel(int input) {
		this.healthLevel = input;
	}
	public void setLastFlagReached(int input) {
		this.lastFlagReached = input;
	}
	// override so that size cannot be changed once instantiated
	public void setSize() {
		
	}
	// display information
	public String toString() {
		String parentString = super.toString();
		String childString = " [Max Speed]: " + this.getMaximumSpeed() + " [Food Level]: " + this.getFoodLevel() +
				" [Food Consumption Rate]: " + this.getFoodConsumptionRate() + " [Health Level]: " +
				this.getHealthLevel() + " [Last Flag Reached]: " + this.getLastFlagReached();
		return "{Ant}: " + parentString + childString;
	}
	@Override
	public void turnLeft() {					// turn left
		this.setHeading(this.getHeading() - 5);
	}
	@Override
	public void turnRight() {					// turn right
		this.setHeading(this.getHeading() + 5);
	}
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(ColorUtil.BLACK);
		g.fillArc((int)(this.getLocation().getX() + pCmpRelPrnt.getX()), (int)(this.getLocation().getY() + 
				pCmpRelPrnt.getY()), 2*getSize() ,2*getSize(), 0, 360);
	}
	@Override
	public boolean collidesWith(GameObject otherObject) {
		boolean result = false;
		int thisCenterX = (int)(this.getLocation().getX()); // find centers
		int thisCenterY = (int)(this.getLocation().getY());
		int otherCenterX = (int)(otherObject.getLocation().getX());
		int otherCenterY = (int)(otherObject.getLocation().getY());
		// find dist between centers (use square, to avoid taking roots)
		int dx = thisCenterX - otherCenterX;
		int dy = thisCenterY - otherCenterY;
		int distBetweenCentersSqr = (dx*dx + dy*dy);
		// find square of sum of radii
		int thisRadius = this.getSize()/2;
		int otherRadius = otherObject.getSize()/2;
		int radiiSqr = (thisRadius*thisRadius + 2*thisRadius*otherRadius
		+ otherRadius*otherRadius);
		if (distBetweenCentersSqr <= radiiSqr) { result = true ; }
		
		if (result == false && collisionVector.contains(otherObject))		// if an object in collision vector 
			collisionVector.remove(otherObject);							// is no longer colliding
		return result ;
	}
	@Override
	public void handleCollision(GameObject otherObject) {					// collisions
		if (!collisionVector.contains(otherObject)) {
			if (otherObject instanceof Flag) {
				getWorld().updateFlag(((Flag) otherObject).getSequenceNumber());
					
			}
			else if (otherObject instanceof FoodStation) {
				FoodStation fObj = (FoodStation)otherObject;							// downcast
				if (fObj.getCapacity() > 0) {											// if food station is not empty
					this.setFoodLevel(this.getFoodLevel() + fObj.getCapacity());		// increase food to ant
					fObj.setCapacity(0);												// set food station empty
					getWorld().foodStationCollision();
				}
			}
			else if (otherObject instanceof Spider) {
				getWorld().spiderCollision();
			}
			
			System.out.print("COLLISION with " + otherObject + "\n\n\n\n\n\n\n\n");
			collisionVector.add(otherObject);
		}		
	}
	
}
