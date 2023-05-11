package com.mycompany.a3;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.UITimer; 

public class Game extends Form implements Runnable{
	
	// instantiate worlds and views
	private GameWorld gw;
	private MapView mv;
	private ScoreView sv;
	private boolean paused = false;
	private UITimer timer = new UITimer(this);;					// create instance of timer
	
	Toolbar myToolbar;
	
	// buttons
	Button AccelerateButton = new Button("Accelerate");
	Button LeftButton = new Button("Left");
	Button BrakeButton = new Button("Brake");
	Button RightButton = new Button("Right");
	Button PositionButton = new Button("Position");
	Button PauseButton = new Button("Pause");

	AccelerateCommand AccelerateCMD;
	PauseCommand PauseCMD;
	PositionCommand PositionCMD;
	BrakeCommand BrakeCMD;
	LeftCommand LeftCMD;
	RightCommand RightCMD;
	SoundCommand SoundCMD;
	AboutCommand AboutCMD;
	HelpCommand HelpCMD;
	ExitCommand ExitCMD;
	CheckBox soundCheck;					// checkbox

	
	public Game() {
		gw = new GameWorld(); 	// create Observable GameWorld
		mv = new MapView(gw); 	// create an Observer for the map
		sv = new ScoreView(gw); 	// create an Observer for the game/ant state data

		
		// create east and west boxlayout containers
		Container WestContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		Container EastContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		Container SouthContainer = new Container(new FlowLayout(Component.CENTER));
		
		
		// set entire form to border layout
		this.setLayout(new BorderLayout());
		
		

		// WEST CONTAINER
		AccelerateButton.getAllStyles().setFgColor(ColorUtil.BLACK);
		AccelerateButton.getAllStyles().setBgColor(ColorUtil.BLUE);
		AccelerateButton.getAllStyles().setBgTransparency(255);
		AccelerateButton.getAllStyles().setPadding(Component.TOP, 10);
		AccelerateButton.getAllStyles().setPadding(Component.BOTTOM, 10);
		AccelerateButton.getAllStyles().setMargin(Component.BOTTOM, 5);
		LeftButton.getAllStyles().setFgColor(ColorUtil.BLACK);
		LeftButton.getAllStyles().setBgColor(ColorUtil.BLUE);
		LeftButton.getAllStyles().setBgTransparency(255);
		LeftButton.getAllStyles().setPadding(Component.TOP, 10);
		LeftButton.getAllStyles().setPadding(Component.BOTTOM, 10);
		// EAST CONTAINER
		BrakeButton.getAllStyles().setFgColor(ColorUtil.BLACK);
		BrakeButton.getAllStyles().setBgColor(ColorUtil.BLUE);
		BrakeButton.getAllStyles().setBgTransparency(255);
		BrakeButton.getAllStyles().setPadding(Component.TOP, 10);
		BrakeButton.getAllStyles().setPadding(Component.BOTTOM, 10);
		BrakeButton.getAllStyles().setMargin(Component.BOTTOM, 5);
		RightButton.getAllStyles().setFgColor(ColorUtil.BLACK);
		RightButton.getAllStyles().setBgColor(ColorUtil.BLUE);
		RightButton.getAllStyles().setBgTransparency(255);
		RightButton.getAllStyles().setPadding(Component.TOP, 10);
		RightButton.getAllStyles().setPadding(Component.BOTTOM, 10);
		// SOUTH CONTAINER
		PositionButton.getAllStyles().setFgColor(ColorUtil.WHITE);
		PositionButton.getAllStyles().setBgColor(ColorUtil.BLACK);
		PositionButton.getAllStyles().setBgTransparency(255);
		PositionButton.getAllStyles().setPadding(Component.TOP, 10);
		PositionButton.getAllStyles().setPadding(Component.LEFT, 10);
		PositionButton.getAllStyles().setPadding(Component.RIGHT, 10);
		PositionButton.getAllStyles().setPadding(Component.BOTTOM, 10);
		PositionButton.getAllStyles().setMargin(Component.RIGHT, 5);
		PauseButton.getAllStyles().setFgColor(ColorUtil.BLACK);
		PauseButton.getAllStyles().setBgColor(ColorUtil.BLUE);
		PauseButton.getAllStyles().setBgTransparency(255);
		PauseButton.getAllStyles().setPadding(Component.TOP, 10);
		PauseButton.getAllStyles().setPadding(Component.LEFT, 10);
		PauseButton.getAllStyles().setPadding(Component.RIGHT, 10);
		PauseButton.getAllStyles().setPadding(Component.BOTTOM, 10);
		PauseButton.getAllStyles().setMargin(Component.LEFT, 5);
		
		
		// add to form
		WestContainer.add(AccelerateButton);
		WestContainer.add(LeftButton);
		EastContainer.add(BrakeButton);
		EastContainer.add(RightButton);
		SouthContainer.add(PositionButton);
		SouthContainer.add(PauseButton);

		// COMMANDS
		AccelerateCMD = new AccelerateCommand(gw);
		PauseCMD = new PauseCommand(this);
		PositionCMD = new PositionCommand(this);
		BrakeCMD = new BrakeCommand(gw);
		LeftCMD = new LeftCommand(gw);
		RightCMD = new RightCommand(gw);
		SoundCMD = new SoundCommand(gw);
		AboutCMD = new AboutCommand(gw);
		HelpCMD = new HelpCommand(gw);
		ExitCMD = new ExitCommand(gw);
		soundCheck = new CheckBox("Sound");					// checkbox
		PauseButton.setCommand(PauseCMD);
		PositionButton.setCommand(PositionCMD);
		AccelerateButton.setCommand(AccelerateCMD);
		BrakeButton.setCommand(BrakeCMD);
		LeftButton.setCommand(LeftCMD);
		RightButton.setCommand(RightCMD);
		soundCheck.setCommand(SoundCMD);
		
		// change color of sound checkbox
		soundCheck.getAllStyles().setFgColor(ColorUtil.WHITE);
		soundCheck.getAllStyles().setBgColor(ColorUtil.LTGRAY);
		soundCheck.getAllStyles().setBgTransparency(255);
		
		// KEY LISTENERS
		addKeyListener('a', AccelerateCMD);
		addKeyListener('b', BrakeCMD); 
		addKeyListener('l', LeftCMD);
		addKeyListener('r', RightCMD);
		
		// TOOLBAR
		myToolbar = new Toolbar();
		setToolbar(myToolbar);
		myToolbar.addCommandToSideMenu(AccelerateCMD);
		myToolbar.addComponentToSideMenu(soundCheck);
		myToolbar.addCommandToSideMenu(AboutCMD);
		myToolbar.addCommandToSideMenu(HelpCMD);
		myToolbar.addCommandToSideMenu(ExitCMD);
		
		// add the containers and views
		this.add(BorderLayout.NORTH, sv);
		this.add(BorderLayout.CENTER, mv);
		this.add(BorderLayout.WEST, WestContainer);
		this.add(BorderLayout.EAST, EastContainer);
		this.add(BorderLayout.SOUTH, SouthContainer);
		
		// add 
		this.show();
		gw.init(); 

		// create sounds
		gw.createSounds();
		
		// set size of gw
		gw.setHeight(mv.getHeight());
		gw.setWidth(mv.getWidth());
		
		revalidate();
		
		PositionButton.setEnabled(false);							// disable position
		timer.schedule(20, true, this);								// start the game

	}

	@Override
	public void run() {
		gw.clock(20);												// pass 20ms into clock
		
		paused = false;
		mv.repaint();
	}//run
	public void pause() {
		if (paused) {
			timer.schedule(20, true, this);							// resume game

			mv.pause(false);										// set mv to unpause
			
			// unselect  all objects before resuming game
			IIterator Iterator = gw.getCollection().getIterator();
			while (Iterator.hasNext()) {							
				Object shape = Iterator.getNext();
				if (shape instanceof Fixed) {
					((Fixed) shape).setSelected(false);
				}
			}
			
			PauseButton.setText("Pause");	 						// change resume to pause
			
			// enable/disable
			PositionButton.setEnabled(false);
			AccelerateButton.setEnabled(true);
			LeftButton.setEnabled(true);
			RightButton.setEnabled(true);
			BrakeButton.setEnabled(true);
			
			AccelerateCMD.setEnabled(true);
			/////////// 	STYLES    ///////////
			PositionButton.getDisabledStyle().setFgColor(ColorUtil.WHITE);
			PositionButton.getDisabledStyle().setBgColor(ColorUtil.BLACK);
			AccelerateButton.getAllStyles().setFgColor(ColorUtil.BLACK);
			AccelerateButton.getAllStyles().setBgColor(ColorUtil.BLUE);
			LeftButton.getAllStyles().setFgColor(ColorUtil.BLACK);
			LeftButton.getAllStyles().setBgColor(ColorUtil.BLUE);
			RightButton.getAllStyles().setFgColor(ColorUtil.BLACK);
			RightButton.getAllStyles().setBgColor(ColorUtil.BLUE);
			BrakeButton.getAllStyles().setFgColor(ColorUtil.BLACK);
			BrakeButton.getAllStyles().setBgColor(ColorUtil.BLUE);
			
			myToolbar.addCommandToSideMenu(AccelerateCMD);
			
			// readd key listeners
			addKeyListener('a', AccelerateCMD);
			addKeyListener('b', BrakeCMD); 
			addKeyListener('l', LeftCMD);
			addKeyListener('r', RightCMD);
			
			
			revalidate();

		}
		else{
			timer.cancel();											// pause the game
			paused = true;											// change status
			mv.pause(true);											// update mv resume
			
			PauseButton.setText("Resume");	 						// change pause to resume
			
			// enable/disable
			PositionButton.setEnabled(true);
			AccelerateButton.setEnabled(false);
			LeftButton.setEnabled(false);
			RightButton.setEnabled(false);
			BrakeButton.setEnabled(false);
			AccelerateCMD.setEnabled(false);
			
			// remove key listeners
			removeKeyListener('a', AccelerateCMD);
			removeKeyListener('b', BrakeCMD); 
			removeKeyListener('l', LeftCMD);
			removeKeyListener('r', RightCMD);
			
			// remove command
			removeCommand(AccelerateCMD);
			
			PositionButton.getAllStyles().setFgColor(ColorUtil.BLACK);
			PositionButton.getAllStyles().setBgColor(ColorUtil.BLUE);
			AccelerateButton.getDisabledStyle().setFgColor(ColorUtil.WHITE);
			AccelerateButton.getDisabledStyle().setBgColor(ColorUtil.BLACK);
			LeftButton.getDisabledStyle().setFgColor(ColorUtil.WHITE);
			LeftButton.getDisabledStyle().setBgColor(ColorUtil.BLACK);
			RightButton.getDisabledStyle().setFgColor(ColorUtil.WHITE);
			RightButton.getDisabledStyle().setBgColor(ColorUtil.BLACK);
			BrakeButton.getDisabledStyle().setFgColor(ColorUtil.WHITE);
			BrakeButton.getDisabledStyle().setBgColor(ColorUtil.BLACK);
			
			revalidate();
			}
	}

	public void setPosition() {										// flag for position
		if (mv.selected())
			mv.position(true);
	}
	
	


	
}
