package com.mycompany.a3;

import java.util.Vector;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Flag extends Fixed {
	private int sequenceNumber;
	private Vector<GameObject> collisionVector = new Vector<GameObject>();	// vector for collisions
	private boolean reached = false;
	private boolean selected;
	
	// Constructor
	public Flag() {
		this.setSequenceNumber(1);
		super.setColor(255, 0, 0);
		super.setSize(10);
	}
	// another constructor that accept sequence number, size, and location
	public Flag(int sqncNumber, int size, float x, float y) {
		this.setSequenceNumber(sqncNumber);
		super.setSize(size);
		super.setColor(255, 0, 0);
		super.setLocation(x, y);
	}
	
	// getters 
	public int getSequenceNumber() {
		return this.sequenceNumber;
	}	
	
	// setters
	public void setSequenceNumber(int input) {
		this.sequenceNumber = input;
	}
	// overriding so that flags cannot change color
	public void setColor(int red, int green, int blue) {
		
	}
	// overriding so that flags cannot change size once they are created
	public void setSize() {
		
	}
	
	// displays information
	public String toString() {
		String parentString = super.toString();									// outputs size, location, and color
		String childString = "[Sequence Number]: " + getSequenceNumber();		// outputs sequence number
		
		return "{Flag}: " + parentString + childString;							// return flag info
	}
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		int x1 = (int)(getLocation().getX() + pCmpRelPrnt.getX());
		int x2 = (int)(getLocation().getX() + getSize() + pCmpRelPrnt.getX());
		int x3 = (int)((getLocation().getX() + getSize()/2 + pCmpRelPrnt.getX()));
		
		int y1 = (int)(getLocation().getY() + pCmpRelPrnt.getY());
		int y2 = (int)(getLocation().getY() + pCmpRelPrnt.getY());
		int y3 = (int)(getLocation().getY() + getSize() + pCmpRelPrnt.getY());
		
		int xPoints[] = {x1,x2,x3};
		int yPoints[] = {y1,y2,y3};
		// set points for x and y
		
		// if flag is reached, change color
		if (reached)											// checkpoint marker
			g.setColor(ColorUtil.rgb(0, 255, 255));
		else
			g.setColor(ColorUtil.BLUE);
		
		if (isSelected())										// indicator for selected
			g.drawPolygon(xPoints, yPoints, 3);
		else
			g.fillPolygon(xPoints,  yPoints, 3);
		
		g.setColor(ColorUtil.YELLOW);
		g.drawString(String.valueOf(getSequenceNumber()), (int)(getLocation().getX() + pCmpRelPrnt.getX()) + 15,
				(int)(getLocation().getY() + pCmpRelPrnt.getY()));
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
		return result ;
	}
	@Override
	public void handleCollision(GameObject otherObject) {
		if (!collisionVector.contains(otherObject)) {
			if (otherObject instanceof Ant) {
				Ant mObj = (Ant)otherObject;						// downcast
				if (mObj.getLastFlagReached() == this.getSequenceNumber()) {
					reached = true;
				}
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
		int xLoc = (int)(pCmpRelPrnt.getX()+ this.getLocation().getX() - getSize()/2);// shape location relative
		int yLoc = (int)(pCmpRelPrnt.getY()+ this.getLocation().getY() - getSize()/2);// to parent’s origin
		if ((px >= xLoc) && (px <= xLoc+getSize())
				&& (py >= yLoc) && (py <= yLoc+getSize()) )
		return true; else return false;
	}
}
