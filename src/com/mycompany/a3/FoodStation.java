package com.mycompany.a3;

import java.util.Vector;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.mycompany.a3.Fixed;

public class FoodStation extends Fixed{
	private int capacity;
	private Vector<GameObject> collisionVector = new Vector<GameObject>();	// vector for collisions
	private boolean empty = false;
	private boolean selected;
	
	// Constructor
	public FoodStation() {
		super();							// set random location and size
		this.setCapacity(this.getSize());
		super.setColor(0, 255, 0);
	}
	
	// getters
	public int getCapacity() {
		return this.capacity;
	}
	
	// setters
	public void setCapacity(int input) {
		this.capacity = input;
	}

	// displays information
	public String toString() {
		String parentString = super.toString();					// output size, location, and color
		String childString = "[Capacity]: " + this.getCapacity();
		
		return "{FoodStation}: " + parentString + childString;	// output foodstation info
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		
		// change color if empty
		if (!empty)
			g.setColor(ColorUtil.GREEN);
		else
			g.setColor(ColorUtil.GRAY);
		
		if (isSelected()) 
			g.drawRect((int)(this.getLocation().getX() + pCmpRelPrnt.getX()), (int)(this.getLocation().getY() + 
					pCmpRelPrnt.getY()), getSize(), getSize());
		else
			g.fillRect((int)(this.getLocation().getX() + pCmpRelPrnt.getX()), (int)(this.getLocation().getY() + 
					pCmpRelPrnt.getY()), getSize(), getSize());
		g.setColor(ColorUtil.BLACK);
		g.drawString(String.valueOf(this.getCapacity()),(int)(getLocation().getX() + pCmpRelPrnt.getX()),
				(int)(getLocation().getY() + pCmpRelPrnt.getY()));
	}

	@Override
	public boolean collidesWith(GameObject otherObject) {
		boolean result = false;
		int thisCenterX = (int)(this.getLocation().getX() + (getSize()/2)); // find centers
		int thisCenterY = (int)(this.getLocation().getY() + (getSize()/2));
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
		return result ;
	}

	@Override
	public void handleCollision(GameObject otherObject) {
		if (!collisionVector.contains(otherObject)) {
			if (otherObject instanceof Ant) {
				empty = true;
			}
		}
	}

	@Override
	public void setSelected(boolean b) {
		selected = b;
	}

	@Override
	public boolean isSelected() {
		return selected;
	}

	@Override
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		int px = (int)pPtrRelPrnt.getX(); // pointer location relative to
		int py = (int)pPtrRelPrnt.getY(); // parent’s origin
		int xLoc = (int)(pCmpRelPrnt.getX()+ this.getLocation().getX());// shape location relative
		int yLoc = (int)(pCmpRelPrnt.getY()+ this.getLocation().getY());// to parent’s origin
		if ((px >= xLoc) && (px <= xLoc+getSize())
				&& (py >= yLoc) && (py <= yLoc+getSize()) )
		return true; else return false;}
}

