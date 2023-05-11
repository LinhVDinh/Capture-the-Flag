package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;

public class ScoreView extends Container implements Observer{	
	
	private GameWorld gw;
	Label Time = new Label("0  ");
	Label Lives = new Label("0  ");
	Label Flag = new Label("0  ");
	Label Food = new Label("0    ");
	Label Health = new Label("0  ");
	Label Sound = new Label("OFF");
	
	public ScoreView(GameWorld gw) {
		this.gw = gw;										// save reference
		gw.addObserver(this);								// add this as an observer
		
		this.setLayout(new FlowLayout(Component.CENTER));

		// display everything
		this.add(new Label("Time: "));
		this.add(Time);
		this.add(new Label("Lives Left: "));
		this.add(Lives);
		this.add(new Label("Last Flag Reached: "));
		this.add(Flag);
		this.add(new Label("Food Level: "));
		this.add(Food);
		this.add(new Label("Health Level: "));
		this.add(Health);
		this.add(new Label("Sound: "));
		this.add(Sound);
	}
	
	@Override
	public void update(Observable observable, Object data) {
		// getting data into local variable
		Time.setText(String.valueOf(((GameWorld)data).getTime()));
		Lives.setText(String.valueOf(((GameWorld)data).getLives()));
		Flag.setText(String.valueOf(((GameWorld)data).ant.getLastFlagReached()));
		Food.setText(String.valueOf(((GameWorld)data).ant.getFoodLevel()));
		Health.setText(String.valueOf(((GameWorld)data).ant.getHealthLevel()));
		
		// check if sound is checked
		if (((GameWorld)data).getSound())
			Sound.setText("ON");
		else
			Sound.setText("OFF");
		
		
		this.repaint();
	}

}
