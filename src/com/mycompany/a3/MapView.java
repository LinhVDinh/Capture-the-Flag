package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Graphics;
import com.codename1.ui.TextField;
import com.codename1.ui.plaf.Border;
import com.codename1.charts.models.Point;


public class MapView extends Container implements Observer{
	private GameWorld gw;
	private boolean paused = false;
	private boolean reposition = false;
	private boolean selected;
	
	public MapView(GameWorld gw) {
		this.gw = gw;										// save this as reference
		gw.addObserver(this);
		
        getAllStyles().setBorder(Border.createLineBorder(5, ColorUtil.rgb(255, 0, 0)));
        getAllStyles().setBgTransparency(255);
    }
	
	@Override
	public void update(Observable observable, Object data) {
		((GameWorld)data).map();								// call map
		if (((GameWorld)data).getAbout()) {						// if About is clicked
			Dialog.show("Linh Dinh", "CSC133\nA2", new Command("OK"));
			((GameWorld)data).setAbout(false);					// reset the about flag
		}
		if (((GameWorld)data).getHelp()) {
			Dialog.show("Help", "a to accelerate\nb to brake\nl to turn left\nr to turn right\nf to collide with food"
					+ "station\ng to collide with spider\nt to tick", new Command("OK"));
			((GameWorld)data).setHelp(false);					// reset the help flag
		}
		if (((GameWorld)data).getFlag()) {						// if the flag collision button is pressed 
			((GameWorld)data).setFlag(false);
			TextField myTextField = new TextField();
			Command cOK = new Command("OK");					// OK button
			Command c = Dialog.show("Enter flag number:", myTextField, cOK);	
			if (c == cOK) {														// when the user presses OK
				if (Integer.valueOf(myTextField.getText()) == 1 || 				// if the value is 1-9
						Integer.valueOf(myTextField.getText()) == 2 || 
						Integer.valueOf(myTextField.getText()) == 3 || 
						Integer.valueOf(myTextField.getText()) == 4 || 
						Integer.valueOf(myTextField.getText()) == 5 || 
						Integer.valueOf(myTextField.getText()) == 6 || 
						Integer.valueOf(myTextField.getText()) == 7 || 
						Integer.valueOf(myTextField.getText()) == 8 || 
						Integer.valueOf(myTextField.getText()) == 9)
				((GameWorld)data).updateFlag(Integer.valueOf(myTextField.getText()));
			} // c == cOK
		} //flag
		if (((GameWorld)data).getExit()) {						// check exit flag
			((GameWorld)data).setExit(false);					// reset exit flag
			boolean bOK = Dialog.show("Confirm Quit", "Are you sure you want to exit?","OK", "Cancel" );
			if (bOK)
				Display.getInstance().exitApplication();		// exit application
		}
	this.repaint();
			
	} // update
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		IIterator Iterator = gw.getCollection().getIterator();
		while (Iterator.hasNext()) {
			GameObject obj = (GameObject)Iterator.getNext();
			obj.draw(g, new Point(getX(), getY()));
		}
		
		
	}
	public void pause(boolean b) {
		paused = b;
	}
	public void position(boolean b) {
		reposition = b;
	}
	public boolean selected() {
		return selected;
	}
	public void pointerPressed(int x, int y) {
		if (paused) {
			x = x - getParent().getAbsoluteX();
			y = y - getParent().getAbsoluteY();

			Point pPtrRelPrnt = new Point(x, y);
			Point pCmpRelPrnt = new Point(getX(), getY());
			
			IIterator Iterator = gw.getCollection().getIterator();
			while (Iterator.hasNext()) {						// go through all objects
				Object shape = Iterator.getNext();
				if (shape instanceof Fixed) {
					if (((Fixed)shape).isSelected()){			// check if there is already selected
						if (reposition) {
							((Fixed)shape).setLocation(x- 190 , y - 60);
							reposition = false;
							selected = false;
							((Fixed)shape).setSelected(false);						// reset it to unselected
						}
					}
					else {
						if(((Fixed) shape).contains(pPtrRelPrnt, pCmpRelPrnt)) {
							((Fixed) shape).setSelected(true);
							selected = true;
						}
						else
							((Fixed)shape).setSelected(false);
					
					}
				}
			}//while
			
		}//if
		repaint(); 
	}
}