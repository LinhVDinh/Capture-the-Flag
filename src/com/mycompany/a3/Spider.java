package com.mycompany.a3;
import java.util.Random;
import java.util.Vector;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Spider extends Movable{
	Random random = new Random();
	private final float width = 1728;						// width border
	private final float height = 1102;						// height border
	private Vector<GameObject> collisionVector = new Vector<GameObject>();	// vector for collisions

	
	public Spider() {
		super();									// set random location
		this.setHeading(random.nextInt(360));
		this.setSpeed(random.nextInt(6) + 5);		// random speed from 5-10
		super.setColor(0, 0, 0);
		super.setSize(random.nextInt(51)+50);
	}
	
	
	// overloaded move to have +- random(5) heading
	public void move() {
		
		int newHeading = this.getHeading() + (random.nextInt(11) - 5);   // heading +- 5 random
		this.setHeading(newHeading);
		
		float deltaX = (float) (Math.cos(Math.toRadians(90-this.getHeading()))*this.getSpeed());
		float deltaY = (float) (Math.sin(Math.toRadians(90-this.getHeading()))*this.getSpeed());
		float newLocationX;
		float newLocationY;
		
		
		newLocationX = this.getLocation().getX() + deltaX;
		newLocationY = this.getLocation().getY() + deltaY;
		
		// set boundary
		if (newLocationX < 0)															// in the negative
			newLocationX = 0;															// reset to 0
		if (newLocationY < 0)															// in the negative
			newLocationY = 0;															// reset to 0
		if (newLocationX > width)														// past borders
			newLocationX = width;														// reset to border
		if (newLocationY > height)														// past borders
			newLocationY = height;														// reset to border
		
		this.setLocation(newLocationX, newLocationY);
	}
	// override so that color is unable to change after instantiation
	public void setColor() {
		
	}

	
	// display information
	public String toString() {
		return "{Spider}: " + super.toString();
	}


	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		int x1 = (int)(getLocation().getX() - getSize()/2 + pCmpRelPrnt.getX());
		int x2 = (int)(getLocation().getX() + getSize()/2 + pCmpRelPrnt.getX());
		int x3 = (int)((getLocation().getX() + pCmpRelPrnt.getX()));
		
		int y1 = (int)(getLocation().getY() - getSize()/2 + pCmpRelPrnt.getY());
		int y2 = (int)(getLocation().getY() - getSize()/2 + pCmpRelPrnt.getY());
		int y3 = (int)(getLocation().getY() + getSize()/2 + pCmpRelPrnt.getY());
		
		int xPoints[] = {x1,x2,x3};
		int yPoints[] = {y1,y2,y3};
		// set points for x and y
		
		g.setColor(ColorUtil.BLACK);
		g.drawPolygon(xPoints,  yPoints, 3);
	}


	@Override
	public boolean collidesWith(GameObject otherObject) {
		boolean result = false;
		int thisCenterX = (int)(this.getLocation().getX()); // find centers
		int thisCenterY = (int)(this.getLocation().getY());
		int otherCenterX = (int)(otherObject.getLocation().getX() + (otherObject.getSize()/2));
		int otherCenterY = (int)(otherObject.getLocation().getY() + (otherObject.getSize()/2));
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
	public void handleCollision(GameObject otherObject) {
		// TODO Auto-generated method stub
		
	}
}
