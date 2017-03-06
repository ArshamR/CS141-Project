package edu.csupomona.cs.cs141.classProject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;


/**
 * Engine class contains the grid and all other objects. Controls the game and
 * conditions.
 *
 */
public class Engine implements Serializable {

	/**
	 * Constant that holds the value that represents UP
	 */
	final static String UP = "W";
	
	/**
	 * Constant that holds the value that represents Down
	 */
	final static String DOWN = "S";
	/**
	 * Constant that holds the value that represents Left
	 */
	final static String LEFT = "A";
	/**
	 * Constant that holds the value that represents Right
	 */
	final static String RIGHT = "D";
	
	/**
	 * Constant string that represents the Invinicility power up
	 */
	final static String INVINCIBILITY = "Invincible";

	/**
	 * Constant string that represents the Ammo power up
	 */
	final static String AMMO = "Ammo";

	/**
	 * Constant string that represents the Radar power up
	 */
	final static String RADAR = "Radar";
	
	/**
	 * Constant integer that represents the starting X position of the Spy
	 */
	final static int STARTING_X = 0;
	
	
	/**
	 * Constant integer that represents the starting Y position of the Spy
	 */
	final static int STARTING_Y = 8;

	/**
	 * Constant integer representing the number of Rooms on the grid
	 */
	final static int NUM_ROOMS = 9;
	
	
	/**
	 * Constant integer representing the number of rows the grid has
	 */
	final static int NUM_ROWS = 9;
	/**
	 * Constant integer representing the number of columns
	 */
	final static int NUM_COLUMNS = 9;
	/**
	 * Constant integer representing the number of Assassins
	 */
	final static int NUM_ASSASSINS = 6;
	
	/**
	 * Constant integer representing the row number for the first row of rooms
	 */
	final static int ROOM_ROW_ONE = 2;
	
	/**
	 * Constant integer representing the row number for the second row of rooms
	 */
	final static int ROOM_ROW_TWO = 4;
	
	/**
	 * Constant integer representing the row number for the third row of rooms
	 */
	final static int ROOM_ROW_THREE = 6;
	
	/**
	 * Constant integer represent the number of Items on the board
	 */
	final static int NUM_ITEMS = 3;
	
	
	/**
	 * Constant integer representing the number of lives the Spy has
	 */
	final static int NUM_LIVES = 3;
	
	/**
	 * Scanner object used to read input throughout the program
	 */
	Scanner sc;
	/**
	 * String used when reading directional input
	 */
	String direction;
	/**
	 * UserInterface object used to display messages.
	 */
	UserInterface ui;
	
	/**
	 * 2D array of Tile objects that represents the grid.
	 * I forgot why it is static but I'm to afraid to change it at this point.
	 */
	static Tile grid[][];
	
	/**
	 * Array that holds the ninja assassins
	 */
	Ninja ninja[];
	
	
	/**
	 * Array holding and initializing the powerups
	 */
	PowerUp items[] = { new PowerUp(INVINCIBILITY),
				        new PowerUp(AMMO),
				        new PowerUp(RADAR)};
	
	/**
	 * Array holding the space values for the powerUps
	 * (Lazy/bad design i know)
	 */
	String space[] = { "I", "A", "O"};
	
	/**
	 * Spy object representing the character the user controls
	 */
	Spy player;
	
	/**
	 * Array holding the rooms;
	 */
	Room rooms[];
	
	/**
	 * Number of lives the spy has
	 */
	int numLives = 3;
	
	/**
	 * The position of the briefcase
	 * Also forgot why this is static
	 */
	static int briefcasePos;
	/**
	 * Count variable that is used when invincibility is picked up
	 */
	int count = 6;
	
	
	/**
	 * Giving briefcase a random value  0-8
	 */
	int briefcase = randomRoom();	
	
	/**
	 * Integer used when reading integer inputs from user
	 */
	int option = 1;
	/**
	 * Flag that is checked when debug is turned on/off
	 */
	boolean debug = false;
	
	/**
	 * Flag that is checked when the user wants to exit
	 */
	boolean exit = false;
	/**
	 * Flag checked when the user is being a dummy and inputs a bad character
	 */
	boolean dummy = false;
	/**
	 * Checks if the user has looked already or not.
	 */
	boolean hasLooked = false;
	
	/**
	 * Flag checked when user dies 
	 */
	boolean isDead = false;	
	
	
	
	/**
	 * Method that contains the main game loop.
	 * Contains everything the game needs to play
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void start() throws ClassNotFoundException, IOException
	{
		beginOptions();
			options();
		//Main Game loop
			while(!exit && !player.hasBriefcase() && player.getLives() >0)
			{
				if(debug){
					getBriefPos();
				}
				isDead = false;
				displayScreen(grid);
				try{
					//If good input
					if(!dummy){
					//Choose which way to look
					direction = ui.lookOptions(sc);
					look(grid, player, direction);
					displayScreen(grid);
					}
					//if bad input
					if(dummy){
						sc.nextLine();
					}
					//Choose shoot or move
					direction = ui.options(sc);
					
					switch(Integer.parseInt(direction)){
					//Shoot
					case 1: direction = ui.shootOptions(sc);
							shoot(grid, player, direction);
							break;
					//Move
					case 2: direction = ui.movementOptions(sc);
							move(grid,direction,player);
							break;
					}
					isDead = checkKill(grid, player);
				//Move all ninjas
				for(int k = 0;k<NUM_ASSASSINS;k++)
				{
					moveNinjas(grid,ninja[k]);
				}
				if(player.isInvincible()){
					count = fiveTurns(count, player);
					System.out.println(count + " turns left for invincibiliy");
				}
				}catch(InputMismatchException e){
					System.out.println("Enter a valid value!");
				}catch(NumberFormatException e){
					System.out.println("Enter a valid value!");
				}
			
				if(player.hasBriefcase()){
					displayScreen(grid);
					ui.victory();
					break;
				}
				if(player.hasRadar() && !debug){
					getBriefPos();
				}
			//Dead screen
			if(isDead)
			{
				displayScreen(grid);
				resetPlayer(grid, player);
				player.LoseLife();
				
			}
			if(debug && !isDead){
				getBriefPos();
			}
			if(!debug){
				setGridDark(grid);
			}
			if(player.getLives() >0){
			displayScreen(grid);
			options();
			}
		}
		//Losing screen
		if(!exit && !player.hasBriefcase())
		{
			ui.defeat();
		}
	}
	/**
	 * Method to save the game
	 * Serializes: grid, 
	 *  player, 
	 *  Ninjas,
	 *  Rooms
	 * @throws IOException
	 */
	public void save() throws IOException{ 
		FileOutputStream fos = new FileOutputStream ( "save.dat");
		ObjectOutputStream oos = new ObjectOutputStream ( fos );
		oos.writeObject(grid);
		oos.writeObject(player);
		oos.writeObject(ninja);
		oos.writeObject(rooms);
		oos.close();
	}
	/**
	 * Loads the serialized objects back
	 * @return grid even though i dont have to, to late now.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Tile[][] load() throws IOException, ClassNotFoundException{
		FileInputStream fis  = new FileInputStream("save.dat");
		ObjectInputStream ois = new ObjectInputStream(fis);
		Tile[][] grid  = (Tile[][]) ois.readObject();
		player = (Spy) ois.readObject();
		ninja= (Ninja[]) ois.readObject();
		rooms = (Room[]) ois.readObject();
		ois.close();
		return grid;
	}
	
	/**
	 * Displays to the user 3 options : 1 new game, 2: Load game 3: Exit
	 * Initalizes the board accordingly
	 * Should have some parts seperated into seperate functions
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void beginOptions() throws ClassNotFoundException, IOException{
		try{
			boolean flag = false;
			do{
			ui = new UserInterface();
			sc = new Scanner(System.in);
			option = ui.mainMenu(sc);
					
			switch(option)
			{
			case 1: 
				rooms = new Room[NUM_ROOMS];
				ninja = new Ninja[NUM_ASSASSINS];
				player = new Spy();
				
				initNinja(ninja);
				//Initialize Grid
				grid = new Tile[NUM_ROWS][NUM_COLUMNS];
				
				//Set the pieces/players
				initializeBoard(grid, player, rooms, ninja);
				setBriefcasePos(rooms);
				setGridDark(grid);
				displayScreen(grid);
				flag = true;
				break;
			case 2:
				System.out.println("loading!");
				grid = load();
				setGridDark(grid);
				setBriefcasePos(rooms);
				displayScreen(grid);
				flag = true;
				break;
			
			case 3:
				exit = true;
				flag = true;
				break;
			}
			}while(!flag);
			}catch(InputMismatchException e){
				beginOptions();
			}
	}
	
	/**
	 * Displays options to the user: 
	 * 1. Look
	 * 2. Debug
	 * 3. Save
	 * 4. Exit
	 * 
	 * @throws IOException
	 */
	public void options() throws IOException{
		hasLooked = false;
		dummy = false;
		try{
		while(!hasLooked && !exit){
			option = ui.lOptions(sc);
			switch(option){
			case 1: hasLooked = true;
					break;
			case 2: if(!debug){
				setGridDebug(grid);
					getBriefPos();
				displayScreen(grid);
				debug = true;
				}
				else{
					setGridDark(grid);
					displayScreen(grid);
					debug = false;
				}
				break;
			case 3: save();
			System.out.println("Saving!");
			break;
			case 4: exit = true;
			break;
			}
	}
	}catch(InputMismatchException e){
		System.out.println("Invalid input, you lose your \"look\" dummy" );
		dummy = true;
		};
	}
	
	
	/**
	 * Called when Spy picks up Invincibility power up and starts a 5 turn timer, which (purposely) ticks even if you are 
	 * dumb and type in incorrect commands.
	 * @param count: Number of turns left
	 * @param player: Spy with powerUp
	 * @return count decremented
	 */
	public int fiveTurns(int count,Spy player){
		if(count == 1){
			player.setInvincible(false);
		}
	return --count;
	}
	
	/**
	 * Initializes the array of Ninjas
	 * @param ninja: array to be initialized
	 */
	private void initNinja(Ninja ninja[]){
		for(int i = 0; i<NUM_ASSASSINS;i++){
			ninja[i] = new Ninja();
		}
	}
	
	
	/**
	 * Finds where the briefcase is and stores the value
	 * in briefcasePos
	 * @param room: Array to be searched
	 */
	public static void setBriefcasePos(Room[] room){
		for(int i=0; i<NUM_ROOMS;i++){
			if(room[i].checkBriefcase()){
				briefcasePos = i+1;
			}
		}
	}
	
	/**
	 * Prints the position of the brief case to the screen
	 */
	public void getBriefPos(){
		System.out.println("Briefcase in room:" + briefcasePos);
	}
		
	/**
	 * Places the ninjas randomly across the grid
	 * 
	 * @param grid: 2D Array represents the grid
	 * @param ninja: Array of 6 ninjas
	 * @return the updated grid
	 */
	private Tile[][] placeNinjas(Tile grid[][], Ninja ninja[])
	{
		int x;
		int y;
		for(int i = 0;i<NUM_ASSASSINS;i++)
		{
			x = randomNum();
			y = randomNum();
			
			if(grid[y][x].isEmpty())
			{
				grid[y][x] = new Tile(ninja[i]);
				ninja[i].move(y,x);
			}
			else
			{
				i--;				
			}
		}
		return grid;
	};
	
	/**
	 * Places the items on the grid
	 * 
	 * @param grid: 2D Array representing the grid
	 * @param Items: Array of 3 powerUps
	 * @param spce: How the powerUps are seen by the user
	 * @return the updated grid
	 */
	private Tile[][] placeItems(Tile grid[][], PowerUp Items[], String spce[])
	{
		int x; 
		int y;
		
		for(int i = 0;i<NUM_ITEMS;i++)
		{
			x = randomNum();
			y = randomNum();

			if(grid[x][y].isEmpty())
			{
				grid[x][y] = new Tile(Items[i], spce[i]);
			}
			else
			{
				i--;
				
			}
		}
		return grid;
	}
	
	/**
	 * @param grid: 2D Array that represents the Grid
	 * @param ninja: Ninja being moved
	 */
	public void moveNinjas(Tile grid[][], ActiveAgent ninja)
	{
		int temp;
		boolean repeat = false;
		boolean surrounded = checkSurrounded(grid, ninja);
		temp = randomMoveNinja();
		if(!ninja.isDead()){
		switch(temp)
		{
		case 1:repeat = move(grid,UP,ninja);
				break;
		case 2:repeat = move(grid,RIGHT,ninja);
		break;
		case 3:repeat = move(grid,LEFT,ninja);
		break;
		case 4:repeat = move(grid,DOWN,ninja);
		break;			
		}
		if(repeat == false && surrounded == false){
			moveNinjas(grid,ninja);
		}
		}
	};
	
	
	/**
	 * Checks if the Spy is next to a Ninja
	 * 
	 * @param grid: 2D array representing the grid
	 * @param player: The Spy
	 * @return true if spy is adjacent to a ninja, false otherwise
	 */
	public boolean checkKill(Tile grid[][], Spy player){
		boolean result = false;
		int y = player.getXPos();
		int x = player.getYPos();
		
		if(!player.isInvincible() && !player.hasBriefcase()){
		try{
			if(grid[x+1][y].hasPlayer()){
				result = true;
			}
			
			if(grid[x-1][y].hasPlayer()){
				result = true;
			}
		
			if(grid[x][y-1].hasPlayer()){
				result = true;
			}
		
			if(grid[x][y+1].hasPlayer()){
				result = true;
			}
			
		}catch(ArrayIndexOutOfBoundsException e){}
		}
		return result;
	};
	
	
	/**
	 * Used when ninjas move to check if they are surrounded, 
	 * created so it doesnt crash the program is the ninja cant find a valid move
	 * @param grid: 2D Array representing the grid
	 * @param player: Ninja to be moved
	 * @return true if surrounded, false otherwise
	 */
	public boolean checkSurrounded(Tile grid[][], ActiveAgent player){
		boolean result = false;
		boolean flag = false;
		int y = player.getXPos();
		int x = player.getYPos();
		
		//I realize now that this can be down with only one boolean
		try{
			if(grid[x+1][y].isEmpty()){
				flag = true;
			}
		
		if(grid[x-1][y].isEmpty()){
			flag = true;
		}
		
			if(grid[x][y-1].isEmpty()){
				flag = true;
			}
		
			if(grid[x][y+1].isEmpty()){
				flag = true;
			}
			
		}catch(ArrayIndexOutOfBoundsException e){}
		if(!flag)
		{
			result = true;
		}
		return result;
	};
	
	
	/**
	 * Places the rooms on the grid
	 * 
	 * @param grid: 2D array representing the grid
	 * @param room: Array of the rooms
	 * @return updated grid
	 */
	private Tile[][] placeRooms(Tile grid[][], Room room[])
	{
		int temp = 0;
		for(int i=0;i<NUM_ROOMS;i++)
		{
			rooms[i] = new Room();
			if(i == briefcase)
			{
				rooms[i].setBriefcase();
			}
		}
		for(int i = 0;i<grid.length;i++)
		{
			for(int j =0;j<grid.length;j++)
			{
				if(i == ROOM_ROW_ONE || i == ROOM_ROW_TWO || i == ROOM_ROW_THREE)
				{   
					j++;
					grid[i][j].setRoom(room[temp]);
					temp++;
					j++;
				}
			}
		}	
			return grid;
	};
	
	
	/**
	 * Initializes the whole grid
	 * Places the rooms, ninjas and items.
	 * @param grid: 2D Array representing the grid
	 * @param player: The Spy
	 * @param room: array of rooms
	 * @param ninja: Array of Ninjas
	 * @return updated grid
	 */
	public Tile[][] initializeBoard(Tile grid[][], Spy player, Room room[], Ninja ninja[])
	{
		for(int i=0;i<NUM_ROWS;i++)
		{
			for(int j =0;j<NUM_COLUMNS;j++)
			{
				if(player.getXPos() == j && player.getYPos() == i)
				{
					grid[i][j] = new Tile(player);
				}
				else
				{
					grid[i][j] = new Tile();
				}
			}
		}
		grid = placeRooms(grid, room);
		grid = placeNinjas(grid,ninja);
		grid = placeItems(grid, items, space);
		return grid;
	};
	
	
	/**
	 * Sets the grid to dark.
	 * When debug mode is off
	 * @param grid: 2D array representing the grid
	 * @return updated grid
	 */
	public Tile[][] setGridDark(Tile grid[][]){
		for(int i=0;i<NUM_ROWS;i++){
			for(int j =0;j<NUM_COLUMNS;j++){
				grid[i][j].setDebug(false);
				if(!grid[i][j].hasRoom() && !grid[i][j].hasSpy()){
					grid[i][j].setSpace(Tile.DARK);
				}
			}
		}
		return grid;
	}
	
	/**
	 * Shows the position of everything on the grid
	 * @param grid: 2D array that represents the Array
	 * @return the updated grid
	 */
	public Tile[][] setGridDebug(Tile grid[][]){
		for(int i=0;i<NUM_ROWS;i++){
			for(int j =0;j<NUM_COLUMNS;j++){
				grid[i][j].setDebug(true);
				if(grid[i][j].isEmpty() && !grid[i][j].hasItem() ){
					grid[i][j].setSpace(Tile.EMPTY);
				}
				else if(grid[i][j].hasPlayer()){
					grid[i][j].setSpace(grid[i][j].getPlayer().getSymbol());
				}
				else if(grid[i][j].hasRoom()){
					grid[i][j].setSpace(Tile.ROOM);
				}
				else{
					grid[i][j].setSpace(grid[i][j].getPowerUp());
				}
			}
		}
		return grid;
	}

	
	/**
	 * Displays the screen to the console
	 * @param grid 2D array that is the grid being shown 
	 */
	public void displayScreen(Tile grid[][])
	{
		for(int i = 0;i<grid.length;i++)
		{
			System.out.print("*");
			for(int k=0;k<grid.length;k++)
			{
				System.out.print("-----");
			}
			System.out.println("*");
	
			for(int j =0;j<NUM_COLUMNS;j++)
			{
				System.out.print("[ " +grid[i][j].space + " ]");
			}
			System.out.println();
		}
	};
	
	/**
	 * Checks if the move being made is valid.
	 * Checks to see if there is a room or item on a tile.
	 * Makes sure that a spy can only enter/leave a room from the north
	 * @param grid: 2D array representing the grid.
	 * @param xPos: The X coordinate of the player
	 * @param yPos: The y coordinate of the player
	 * @param player: The ActiveAgent being moved
	 * @return True if the move is valid, false otherwise.
	 */
	public boolean checkMove(Tile grid[][],int xPos, int yPos, ActiveAgent player)
	{
		boolean result = false;
		if(player instanceof Spy){
			if(((Spy) player).inRoom()){
				if((yPos == (player.getYPos()-1) && (xPos == player.getXPos()))){
					((Spy) player).setInRoom(false);
					return true;
				}
			
			else{
				return false;
			}
			}
			if(grid[yPos][xPos].hasRoom())
			{
				if(player.getYPos() == (yPos-1)){
					result = true;
					((Spy) player).setInRoom(true);
					if(grid[yPos][xPos].getRoom().checkBriefcase())
					{
						((Spy) player).gotBriefcase();
						System.out.println("Picked up briefcase");
					}
				}
			}
			if(grid[yPos][xPos].hasItem())
			{
				((Spy) player).pickUp(grid[yPos][xPos].getItem());
				grid[yPos][xPos].removeItem();
			}
		}		
		
		if(grid[yPos][xPos].isEmpty()){
			  result = true;
		}
		if(player instanceof Ninja){
			if(xPos == 0 && yPos == 8)
			{
				result = false;
			}
		}
		return result;
		}
	
	
	/**
	 * Updates the space that is being moved into and out of by the player.
	 * 
	 * @param grid: 2D array that is the grid
	 * @param player: Active agent that moved
	 * @param yPos: The players new Y coordinate on the grid.
	 * @param xPos: The players new X coordinate on the grid.
	 * @param temp: The player's old Y coordinate on the grid.
	 * @param temp2: The player's old X coordinate on the grid.
	 */
	public void updateSpace(Tile grid[][],ActiveAgent player, int yPos, int xPos, int temp, int temp2){
		if(player instanceof Spy){
		 grid[yPos][xPos].setSpace(player.getSymbol());
		 grid[yPos][xPos].setSpy(true);
		 grid[temp][temp2].setSpy(false);
		}
		if(player instanceof Ninja && grid[yPos][xPos].isDebug()){
			 grid[yPos][xPos].setSpace(player.getSymbol());
		}
		  grid[yPos][xPos].setPlayer(player);
		  player.move(yPos, xPos);
		  removePlayer(grid,temp, temp2);
	}
	/**
	 * Main move function, moves the player one space on the grid.
	 * 
	 * @param grid: 2D array representing the grid
	 * @param direction: The direction being traveled.
	 * @param player: ActiveAgent being moved
	 * @return true if valid move, false otherwise.
	 */
	public boolean move(Tile grid[][], String direction, ActiveAgent player)
	{
		int xPos = player.getXPos();
		int yPos = player.getYPos();
		int temp = yPos;
		int temp2 = xPos;
		boolean result = false;
		
		try
		{
			switch(direction)
			{
			case UP: yPos--;
						  if(checkMove(grid, xPos, yPos, player)){
						  updateSpace(grid, player, yPos, xPos, temp, temp2);
						  result = true;
						  }	
						  break;
						  
			case DOWN: yPos++;
							 if(checkMove(grid,xPos,yPos,player)){
								 updateSpace(grid, player, yPos, xPos, temp, temp2);
								  result = true;
								  }
							break;
							
			case LEFT: xPos--;
							 if(checkMove(grid,xPos,yPos,player)){
								 updateSpace(grid, player, yPos, xPos, temp, temp2);
								  result = true;
								  }
							break;
							
			case RIGHT:xPos++;
						 if(checkMove(grid,xPos,yPos,player)){
							 updateSpace(grid, player, yPos, xPos, temp, temp2);
							  result = true;
							  }
							break;
			default: System.out.println("Thats an invalid move");
			}
			}
		catch(ArrayIndexOutOfBoundsException e)
		{
			if(player instanceof Spy){
			displayScreen(grid);
			direction = ui.movementOptions(sc);
			move(grid, direction,player);
			}
		}
		return result;
	};
	
	/**
	 * Allows the spy to see two spaces in the direction of his choosing.
	 * 
	 * @param grid: 2D Array representing the grid.
	 * @param player: The spy object
	 * @param direction: The direction to look at
	 */
	public void look(Tile grid[][], Spy player, String direction){
		int x = player.getXPos();
		int y = player.getYPos();
		boolean ninja  = false;
		try{
		switch(direction){
		case UP: grid[y-1][x].look();
				 grid[y-2][x].look();
				 break;
		case DOWN: grid[y+1][x].look();
				   grid[y+2][x].look();
				   break;
		case LEFT: grid[y][x-1].look();
				   grid[y][x-2].look();
				   break;
		case RIGHT: grid[y][x+1].look();
					grid[y][x+2].look();
					break;
		}
		}
		catch(ArrayIndexOutOfBoundsException | InputMismatchException e){}
	}
	
	
	/**
	 * Allows the spy to shoot in a direction, killing the first ninja the bullet comes in contact with.
	 * 
	 * @param grid: 2D Array representing the grid.
	 * @param player: Spy object
	 * @param direction: Direction to shoot in
	 */
	public void shoot(Tile grid[][] , Spy player, String direction){
		
			int x = player.getXPos();
			int y = player.getYPos();
			
			try{
			switch(direction){
				case UP: if(player.shoot()){
					while(y > 0)
					{
						if(grid[y-1][x].hasPlayer()){
							grid[y-1][x].player.died();
							removePlayer(grid, y-1, x);
					}
					y--;
				}
				}
				break;
				case DOWN: if(player.shoot()){
					while(y < NUM_ROWS){
						if(grid[y+1][x].hasPlayer()){
						grid[y+1][x].player.died();
						removePlayer(grid, y+1, x);
						}
					y++;
					}
				}
				break;
				case LEFT: if(player.shoot()){
					while(x > 0){
						if(grid[y][x-1].hasPlayer()){
						grid[y][x-1].player.died();
						removePlayer(grid, y, x-1);
					}
					x--;
					}
				}
				break;
				case RIGHT: if(player.shoot()){
					while(x<NUM_COLUMNS){
					if(grid[y][x+1].hasPlayer()){
						grid[y][x+1].player.died();
						removePlayer(grid, y, x+1);
						break;
					}
					x++;
					}
			}
				break;
				default : System.out.println("Invalid Entry");
		}
			}catch(IndexOutOfBoundsException e){}
			catch(InputMismatchException e){}
		
	};
	
	
	/**
	 * Resets a player at the starting point when he dies
	 * @param grid: 2D Array that is the grid
	 * @param player: Spy object
	 */
	public void resetPlayer(Tile grid[][], Spy player){
		removePlayer(grid, player.getYPos(), player.getXPos());
		ui.died();
		player.move(STARTING_Y, STARTING_X);
		grid[STARTING_Y][STARTING_X].setSpace(player.getSymbol());
		grid[STARTING_Y][STARTING_X].setPlayer(player);
		grid[STARTING_Y][STARTING_X].setSpy(true);
	};
	
	
	/**Generates a random number from 3-8 so a ninja cannot spawn to close to the Spy
	 * @return
	 */
	private int randomNum()
	{
		Random num = new Random();
		int result = num.nextInt((8-3) + 1)+3;
		return result;
	}
	/**
	 * Generates a random number 0-8, used to determine the room the briefcase is in
	 * @return
	 */
	public static int randomRoom()
	{
		Random num = new Random();
		int result = num.nextInt((8-0)+1)+0;
		return result;
	}
	
	/**
	 * Generates a random number 1-4, used to determine which direction the ninja will move in.
	 * @return
	 */
	private int randomMoveNinja()
	{
		Random num = new Random();
		int result = num.nextInt((4-1)+1) + 1;
		return result;
	}
	
	/**
	 * Clears the tile of the player
	 * @param grid: 2D array that is the grid
	 * @param x: X coordinate of the Position. 
	 * @param y: Y coordinate of the Position.
	 */
	public void removePlayer(Tile grid[][], int x, int y)
	{
		grid[x][y].removePlayer();
	}
}