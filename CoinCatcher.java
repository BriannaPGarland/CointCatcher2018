//2018 Programming Final Project 
//This is an java applet that is a game called coin catcher that has it so the user can move a character back 
//and forth across the screen to catch falling coins. if they catch them their score goes up but if they don't
//their score goes down and they lose a life. Each game the player has 3 lives 



//This program was written in Java on the compiler called "eclipse" 
//there are two classes that are used for graphics that are classes that we did not write but simply use. 
//The solid object class is a class created by a high school student at warren hills regional highschool and was not written by us 
//the Joe applet class is a class is a class also written by a high school student from warren hills regional high school to allow the applet 
// to run without flickering because without it the game would flash the graphics on and off everytime it repaints
//these classes are not thigs that we are submitting to be graded the only thing we are submitting as ours is this class. everything we wrote in 
//this class is our work. we simply use the other classes in our class, we are not taking credit for the other classes. 

//We pledge our honor that we have abidided by the Stevens Honor system 
//Brianna Garland, Maddy Cohen, Sanjana Chopra, Lauren Chew 

import java.awt.*;
import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.ImageObserver;
import java.applet.*;
import java.awt.event.*;
import java.util.*;



public class CoinCatcher extends JoeApplet implements KeyListener

{
	//This is where the images that are in the game are initialized to an Image variable 
	Image startUp, gameScreen, mario, luigi, coin;
	Image winScreen, loseScreen, instructionScreen,turtleShell;
	Image peach, daisy, bowser, toad, yoshi; 
	
	Random gen = new Random();
	
	Font myFont = new Font("SansSerif", 50, 50 );
	
	//booleans to show the different screens in order to display the different screens at differnt times 
	boolean showStartUp =true;
	boolean showGameScreen = false;
	boolean showWin = false;
	boolean showLose = false;
	boolean showInstru =false;
	
	//these are the booleans to let the game know what character the user wants to play with wqd
	boolean mc = false;
	boolean lc= false;
	boolean pc =false;
	boolean dc = false;
	boolean tc = false;
	boolean bc = false;
	boolean yc = false;
	
	//These variables are from a class that I will cite at the bottom 
	//They essentially make rectangles around images so that the computer
	//can tell whether or not they are in the same area or not. 
	SolidObject marioObject = new SolidObject();
	SolidObject coinObject = new SolidObject();
	SolidObject shellObject = new SolidObject();
	
	
	//These control the initial positions of the coin that falls in the game
	int coinX = 100;
	int coinY=100;
	//This array allows the coin to fall randomly at the x positions of the ints that are in the array 
	int coinMove[] = {0,305,23,123,700,50,129,678,360,100,507,170,160,201,230,800,47,10,900,570,780,450};
	
	
	int lives = 3;
	int score = 0;
	
	//These are the initial position of the mario/luigi character
	int marioX=50;
	int marioY=690;
	
	//shell speed
	int shellX=100;
	int shellY=100;
	int shellMove[] = {0,305,23,123,700,50,129,678,360,100,507,170,160,201,230,800,47,10,900,570,780,450};
	
	//mario/luigi speed; The increments at which mario's position changes by when the player presses the key 
	int mSpeedx = 20;
	int mSpeedy = 20;
	
	//This is the initial coin speed that it falls down the screen at 
	int coinSpeed= 15;
	
	//this is the initial speed of the coin 
	int shellSpeed = 10;
	
	
	//This init method intializes everything when the applet is launched 
	public void init()
	{
		startUp = getImage(getCodeBase(),"startUp.PNG");
		mario = getImage(getCodeBase(),"Mario.png");
		coin = getImage(getCodeBase(),"Coin.png");
		gameScreen = getImage(getCodeBase(),"gameBackground.PNG");
		winScreen = getImage(getCodeBase(),"winningScreen.PNG");
		loseScreen = getImage(getCodeBase(),"losingScreen.PNG");
		instructionScreen = getImage(getCodeBase(),"instructions.PNG");
		luigi = getImage(getCodeBase(),"Luigi.png");
		turtleShell = getImage(getCodeBase(),"RedShell.png");
		peach= getImage(getCodeBase(),"peach.png");
		daisy=  getImage(getCodeBase(), "daisy.png");
		toad=getImage(getCodeBase(),"toad.png");
		bowser=getImage(getCodeBase(),"Bowser.png");
		yoshi = getImage(getCodeBase(),"Yoshi_SSBU.png");
		
		
		
		addKeyListener(this);
		
	}

//---------------------METHODS THAT DRAW GAME PIECES ------------------------
	//this will draw the coin on the screen 
	public void drawCoin(Graphics art)
	{
		//draw the coin on the screen and makes the solid object at the same location 
		art.drawImage(coin, coinX, coinY, 50,50, this);
		coinObject.makeSolidObject(coinX, coinY, 50, 50);
		
		//This if statement says that if the coin is at the top of the window for it to move down unless
		//it is colliding with the mario object or hitting the ground. If it hits ground or mario, it changes
		//the score and the lives and resets the coin back at the top of the screen 
		if(coinY<=800)
		{
			//checking if Mario "Caught" the coin"
			if(coinObject.isCollidingWith(marioObject))
			{
				score++;
				coinY=0;
				coinX= coinMove[gen.nextInt(coinMove.length)];
				
			}
			coinY=coinY+coinSpeed;
		}
		else
		{
			//If the code gets here then the coin hit the ground
			coinY=0;
			coinX= coinMove[gen.nextInt(coinMove.length)];
			score --;
			lives --;
		}
		
		
	}
	
	public void drawShell(Graphics art)
	{
		//draw the shell on the screen and makes the solid object at the same location 
		art.drawImage(turtleShell, shellX, shellY, 50,50, this);
		shellObject.makeSolidObject(shellX, shellY, 50, 50);
		
	
		if(shellY<=800)
		{
			//checking if Mario "Caught" the shell"
			if(shellObject.isCollidingWith(marioObject))
			{
				score--;
				lives--;
				coinY=0;
				shellX= shellMove[gen.nextInt(shellMove.length)];
				
			}
			shellY=shellY+shellSpeed;
		}
		else
		{
			//If the code gets here then the shell hit the ground
			shellY=0;
			shellX= shellMove[gen.nextInt(shellMove.length)];
		}
		
	}
	//This method sees if the user entered that they wanted to play as Mario or Luigi and base on that
	//assigns the corresponding image. 
	public void drawMario(Graphics art)
	{
		if(mc)
		{
			art.drawImage(mario, marioX, marioY, 175, 175, this);
		}
		else if(lc)
		{
			art.drawImage(luigi, marioX, marioY, 175, 175, this);
		}
		else if(pc) 
		{
			art.drawImage(peach, marioX, marioY, 175, 175, this);
		}
		else if(dc)
		{
			art.drawImage(daisy, marioX, marioY, 175, 175, this);
		}
		else if(tc)
		{
			art.drawImage(toad, marioX, marioY, 175, 175, this);
		}
		else if (bc)
		{
			art.drawImage(bowser, marioX, marioY, 175, 175, this);
		}
		else if (yc)
		{
			art.drawImage(yoshi, marioX, marioY, 175, 175, this);
		}
		
		//this part of the code keeps mario on the screen
		if(marioX<0)
		{
			marioX=1000;
		}
		else if(marioX>1000)
		{
			marioX=0;
		}
		
		marioObject.makeSolidObject(marioX, marioY, 175, 175);
		
		
	}
	
	
//----------------METHODS THAT DRAW ALL OF THE SCREENS -------------
	//This will draw the start screen 
	public void startScreen(Graphics art)
	{
		art.drawImage(startUp, 5, 5, 1200,1000,this);
		
	}
	
	//this will show the game background 
	public void backdrop(Graphics art)
	{
		art.drawImage(gameScreen, 5, 5, 1200,1000,this);
	}
	
	//this will show the winning screen
	public void winnerScreen(Graphics art)
	{
		art.drawImage(winScreen, 5, 5, 1200,1000,this); 
	}
	
	//this will show the losing screen 
	public void loserScreen(Graphics art)
	{
		art.drawImage(loseScreen, 5,5,1200,1000,this);
		
	}
	
	//this will show the instruction screen 
	public void instructScreen(Graphics art)
	{
		art.drawImage(instructionScreen, 5, 5, 1200, 1000, this);
	}
	
//-----------------METHODS FOR GAME PLAY-----------------------

//------------------METHOD THAT RUNS THE PROGRAM--------------------
	//This method checks which of the screens are true and based on that carries out the code that corresponds to the screen 
	public void paint(Graphics art)
	{
		//Start up screen just involves setting the size of the screen and drawing the image related to it 
		if(showStartUp)
		{
			setSize(1200,1000);
			startScreen(art);
			
		}
		
		//game screen involves drawing the background image, mario, and the coin so all of those methods have to be called here.
		//It also involves drawing the strings on the screen for the score and lives varible so that the player knows how they 
		// are doing in the game. It also has to check whether or not the player has lost or if the player has won.
		if(showGameScreen)
		{
			
			setSize(1200,1000);
			backdrop(art);
			drawMario(art);
			drawCoin(art);
			drawShell(art);
			
			
			//font and lives varibles on the screen 
			art.setColor(Color.white);
			art.setFont(myFont);
			art.drawString("Score ="+score, 30, 50);
			art.drawString("Lives: " +lives, 980, 50);
			
			//checking if player lost because lives cannot go below zero 
			if(lives<0)
			{
				showLose = true;
				showStartUp= false;
				showGameScreen = false;
				showWin=false;
				
			}
			
			//checking if player won by checking if the score is above a certain point 
			if(score>15)
			{
				showLose = false;
				showStartUp= false;
				showGameScreen = false;
				showWin=true;
				lives = 100000;
			}
		}
		
		//The win screen involves setting the size and drawing the winner screen image 
		if(showWin)
		{
			setSize(1200,1000);
			winnerScreen(art);
		}
		
		//The lose screen involves drawing the image, setting the size and also displaying 
		//the final score that the user ended with before they ran out of lives 
		if(showLose)
		{
			setSize(1200,1000);
			loserScreen(art);
			
			art.setColor(Color.black);
			art.setFont(myFont);
			art.drawString(" "+score, 650, 350);
		}
		
		//instruction screen involves setting the size and drawing the image 
		if(showInstru)
		{
			setSize(1200,1000);
			instructScreen(art);
		}
		
		//this repaint allows for the program to be draw again and again, constantly updating 
		repaint();
	}

	
//------------THESE ARE ALL OF THE KEY LISTENER METHODS-----------------
	@Override
	public void keyPressed(KeyEvent e) 
	{
		int key = e.getKeyCode();
		
		//this says that if the right arrow key is being pressed that the mario object will move to the right 
		if(key==e.VK_RIGHT)
		{
			marioX=marioX+mSpeedx;
		}
		
		//this says that if the left key is being pressed that the mario object will move to the left 
		if(key==e.VK_LEFT)
		{
			marioX=marioX-mSpeedx;
		}
		
		//this says that if the space key is pressed that the game will go to the instruction screen 
		if(key==e.VK_SPACE)
		{
			showStartUp = false;
			showGameScreen = false;
			showInstru=true;
		}
		
		//this says that if the 'x' key is pressed than the game will return to the start up screen and reset all of the 
		//varible so that the player is able to play again without restarting the game.
		if(key==e.VK_X)
		{
			showStartUp= true;
			showGameScreen = false;
			showInstru=false;
			showWin=false;
			showLose=false;
			mc=false;
			lc =false;
			score=0;
			lives=3;
		}
		
		//this makes it so that if the 'm' key is pressed that the user can play as mario 
		if(key==e.VK_M)
		{
			showStartUp= false;
			showGameScreen = true;
			showInstru=false;
			showWin=false;
			showLose=false;
			mc =true;
		}
		
		//this makes it so that if the 'L' key is pressed that the user can play as Luigi 
		if(key==e.VK_L)
		{
			showStartUp= false;
			showGameScreen = true;
			showInstru=false;
			showWin=false;
			showLose=false;
			lc = true;
			
		}
		
		//this makes it so that if the 'p' key is pressed that the user can play as princess Peach 
		if(key==e.VK_P)
		{
			showStartUp= false;
			showGameScreen = true;
			showInstru=false;
			showWin=false;
			showLose=false;
			pc = true;	
		}
		
		//this makes it so that if the d key is pressed the user can play as princess daisy 
		if(key==e.VK_D)
		{
			showStartUp= false;
			showGameScreen = true;
			showInstru=false;
			showWin=false;
			showLose=false;
			dc = true;	
		}
		
		if(key==e.VK_T)
		{
			showStartUp= false;
			showGameScreen = true;
			showInstru=false;
			showWin=false;
			showLose=false;
			tc = true;	
		}
		
		if(key==e.VK_B)
		{
			showStartUp= false;
			showGameScreen = true;
			showInstru=false;
			showWin=false;
			showLose=false;
			bc = true;	
		}
		
		if(key==e.VK_Y)
		{
			showStartUp= false;
			showGameScreen = true;
			showInstru=false;
			showWin=false;
			showLose=false;
			yc = true;	
		}


		
		
	}
 
	//--------------------UNUSED METHODS -----------------
	//These methods are unused but have to be here since we implemented the KeyListener. The Program is unable to 
	//run without these methods. 
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	


	

}
