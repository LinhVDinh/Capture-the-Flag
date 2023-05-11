package com.mycompany.a3;

import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;

import com.codename1.charts.util.ColorUtil;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class GameWorld extends Observable{
	private GameObjectCollection ObjectCollection = new GameObjectCollection();
	private int timer;
	private int lives;
	private int maxMultiplier = 0;
	private int height;
	private int width;
	private int elapsedTime = 0;
	private boolean sound = false;
	private boolean about = false;
	private boolean help = false;
	private boolean flag = false;
	private boolean exit = false;
	
	// sounds
	private Sound checkpoint;
	private Sound healed;
	private Sound attacked;
	private BGSound bg;
	
	
	// Create Flags
		int flagSize = 50;							// set flag size
		Flag flag1 = new Flag(1, flagSize, 200.0f, 200.0f);
		Flag flag2 = new Flag(2, flagSize, 200.0f, 800.0f);
		Flag flag3 = new Flag(3, flagSize, 700.0f, 800.0f);
		Flag flag4 = new Flag(4, flagSize, 900.0f, 400.0f);
			
		// Create Ant
		
		Ant ant = Ant.getAnt(flag1.getLocation().getX(), flag1.getLocation().getY());
			
		// Create Spider
		Spider spider1 = new Spider();
		Spider spider2 = new Spider();
			
		// Create FoodStation
		FoodStation foodStation1 = new FoodStation();
		FoodStation foodStation2 = new FoodStation();
		FoodStation foodStation3 = new FoodStation();
		FoodStation foodStation4 = new FoodStation();
		
		
			
	public void init(){ 
		ant.setWorld(this);
		flag1.setWorld(this);
		flag2.setWorld(this);
		flag3.setWorld(this);
		flag4.setWorld(this);
		foodStation1.setWorld(this);
		foodStation2.setWorld(this);
		foodStation3.setWorld(this);
		foodStation4.setWorld(this);


		// add objects to 
		ObjectCollection.add(flag1);
		ObjectCollection.add(flag2);
		ObjectCollection.add(flag3);
		ObjectCollection.add(flag4);
		ObjectCollection.add(ant);
		ObjectCollection.add(spider1);
		ObjectCollection.add(spider2);
		ObjectCollection.add(foodStation1);
		ObjectCollection.add(foodStation2);
		ObjectCollection.add(foodStation3);
		ObjectCollection.add(foodStation4);
		
		lives = 3;									// initialize lives
		timer = 0;									// initialize timer
		
		
	} 
	
	public void accelerate() {						// increase speed of ant
		if (ant.getSpeed() < ant.getMaximumSpeed()) // don't increase above maximum speed
			ant.setSpeed(ant.getSpeed() + 1);
		setChanged();								
		notifyObservers(this);						// update view
	}
	public void brake() {							// decrease speed of ant
		if (ant.getSpeed() > 0)						// don't decrease below 0
			ant.setSpeed(ant.getSpeed() - 1);
		setChanged();
		notifyObservers(this);						// update view
	}
	public void clock(int timer) {
		soundCheck();								// check if sound is enabled/disabled

		
		
		// collisions checker
		IIterator iter = this.getCollection().getIterator();
		while (iter.hasNext()){
			ICollider curObj = (ICollider)iter.getNext();	// get a collidable object
			// check if this object collides with any OTHER object
			
			IIterator iter2 = this.getCollection().getIterator();
			while (iter2.hasNext()) {
				ICollider otherObj = (ICollider) iter2.getNext(); // get a collidable object
				// check for collision
				if (curObj != otherObj) {
					if (curObj.collidesWith((GameObject)otherObj)){
						curObj.handleCollision((GameObject)otherObj);
					}
				}
			}
		}
		
		elapsedTime += timer;											// increment timer
		IIterator Iterator = ObjectCollection.getIterator();
		while (Iterator.hasNext()) {								// go through all objects
			GameObject mObj = (GameObject)Iterator.getNext();
			if (mObj instanceof Ant){
				Ant Obj = (Ant)mObj;
				if (Obj.getFoodLevel() > 0 && Obj.getHealthLevel() > 0) {			// if health and food level is higher than 1
					Obj.move(timer);													// move
					if (elapsedTime%1000==0)
						Obj.setFoodLevel(Obj.getFoodLevel()-Obj.getFoodConsumptionRate()); // lower food level after move
					
					
				}
			else {
				if (lives > 1) {												// while there are still lives
				lives--;														// decrease lives
					
				ObjectCollection = new GameObjectCollection();					// clear the collection
					
				// add all objects again
				ObjectCollection.add(flag1);
				ObjectCollection.add(flag2);
				ObjectCollection.add(flag3);
				ObjectCollection.add(flag4);
				ObjectCollection.add(ant);
				ObjectCollection.add(spider1);
				ObjectCollection.add(spider2);
				ObjectCollection.add(new FoodStation());
				ObjectCollection.add(new FoodStation());
				ObjectCollection.add(new FoodStation());
				ObjectCollection.add(new FoodStation());
					
				// reset ant
				ant.setMaximumSpeed(5);
				ant.setFoodLevel(25);
				ant.setHealthLevel(5);
				ant.setLastFlagReached(1);
				ant.setLocation(flag1.getLocation().getX(), flag1.getLocation().getY());
				}
				else {												// no more lives, you lose
					System.out.print("Game over, you failed!");
					System.exit(0);								
				}
			}
			}
			else if (mObj instanceof Spider) {			// move spiders
				Spider Obj = (Spider)mObj;
				Obj.move(timer);
			}
		}
		setChanged();
		notifyObservers(this);
	}

	public void left() {
		ant.turnLeft();
		setChanged();
		notifyObservers(this);							// update view
	}
	public void right() {
		ant.turnRight();
		setChanged();
		notifyObservers(this);							// update view
	}
	public void map() {														// show locations of all objects to console
		IIterator Iterator = ObjectCollection.getIterator();
		while (Iterator.hasNext()) {
			System.out.println(Iterator.getNext());	
		}
		System.out.println();												// new line
		 
	}
	public void updateFlag(int flag) {
		if (ant.getLastFlagReached() + 1 == flag)	{						// if flag reached is the next flag
			ant.setLastFlagReached(flag);									// update last reached
			if (sound)
				checkpoint.play();
		}
		System.out.println("Ant collided with flag "+ flag);
		if (ant.getLastFlagReached() == 4)									// if all flags are reached, win game
			System.out.print("Game over, you win! Total time: " + timer);

		setChanged();
		notifyObservers(this);								// update view
	}

	public void foodStationCollision() {
		FoodStation foodStation5 = new FoodStation();			// create new food station
		foodStation5.setWorld(this);
		ObjectCollection.add(foodStation5);							// add to game
		if (sound)
			healed.play();
		setChanged();
		notifyObservers(this);							// update view
	}
	public void spiderCollision() {
		ant.setHealthLevel(ant.getHealthLevel()-1);								// lower health of ant											
		ant.setColor(0, 0, ColorUtil.blue(ant.getColor()) - 25);				// color of ant represents health
		speedLimit();			
		if (sound)
			attacked.play();
		setChanged();	
		notifyObservers(this);								// update view
	}
	public void speedLimit() {
		maxMultiplier = maxMultiplier + 1;					// this is to keep track of original maximum speed
															// multiply maxMultiplier by 5 to get original maximum speed
		ant.setMaximumSpeed(ant.getMaximumSpeed() - 1);
		if (ant.getMaximumSpeed() < 0)						// if less than 0 set to 0
			ant.setMaximumSpeed(0);
		if (ant.getSpeed() > ant.getMaximumSpeed())			// if speed is higher, decrease to maximum
			ant.setSpeed(ant.getMaximumSpeed());
		setChanged();
		notifyObservers(this);								// update view
	}
	public void createSounds() {
		healed = new Sound("heal 2.wav");
		checkpoint = new Sound("buy1.wav");
		attacked = new Sound("dk_2.wav");
		bg = new BGSound("water4.wav");
		bg.run();											// run the code
	}
	
	
	public void setHeight(int h) {
		height = h;
	}
	public void setWidth(int w) {
		width = w;
	}
	public void setSound(boolean b) {
		sound = b;		
		setChanged();
		notifyObservers(this);							// update view
	}
	public void setAbout(boolean b) {
		about = b;
		setChanged();
		notifyObservers(this);							// update view
	}
	public void setHelp(boolean b) {
		help = b;
		setChanged();
		notifyObservers(this);							// update view
	}
	public void setFlag(boolean b) {
		flag = b;
		setChanged();
		notifyObservers(this);							// update view
	}
	public void setExit(boolean b) {
		exit = b;
		setChanged();
		notifyObservers(this);							// update view
	}
	public void soundCheck() {
		if (sound)
			bg.play();
		else
			bg.pause();

	}
	public boolean getAbout() {
		return about;
	}	
	public boolean getHelp() {
		return help;
	}
	public int getTime() {
		return elapsedTime/1000;
	}
	public int getLives() {
		return lives;
	}

	public boolean getSound() {
		return sound;
	}

	public boolean getFlag() {
		return flag;
	}
	public boolean getExit() {
		return exit;
	}
	public GameObjectCollection getCollection() {
		return ObjectCollection;
	}




}
