package edu.csupomona.cs.cs141.classProject;

import java.io.Serializable;
import java.util.Stack;


/**
 * This class represents the Tile, which makes the grid.
 * Can hold a room, player and/or item.
 *
 */
public class Tile implements Serializable{
	
	/**
	 * Constant string that represents an empty space used in debug mode.
	 */
	final static String EMPTY = " ";
	/**
	 * Constant String that represents darkness used in non-debug mode.
	 */
	final static String DARK = "X";
	
	/**
	 * Constant String that represents the symbol of a room.
	 */
	final static String ROOM = "R";
	
	/**
	 * String that is used when displaying the grid.
	 */
	String space;
	/**
	 * The type of powerUp the tile has
	 */
	String powerUp;
	/**
	 * Room object if the tile has one.
	 */
	Room room;
	/**
	 * Player object, can hold spy or ninjas
	 */
	ActiveAgent player;
	/**
	 * The power up the tile has
	 */
	PowerUp item;
	/**
	 * Boolean that is true if debug mode is on, false otherwise
	 */
	private boolean debug;
	/**
	 * Boolean true if tile has room, false otherwise.
	 */
	private boolean hasRoom;
	/**
	 * Boolean true if the tile has an item, false otherwise
	 * 
	 */
	private boolean hasItem;
	/**
	 * boolean that is true if the tile has a player, false otherwise.
	 */
	private boolean hasPlayer;
	/**
	 * boolean that is true if the tile specifically has a spy on it.
	 */
	private boolean hasSpy;
	/**
	 * Constructor for an empty tile.
	 */
	public Tile()
	{
		space= EMPTY;
		room = null;
		player = null;
		item = null;
	}
	
	/**
	 * Constructor for a tile with a room
	 * @param r: Room to be set on the tile
	 */
	public Tile(Room r)
	{
		space = ROOM;
		room = r;
		hasRoom = true;
	}
	/**
	 * Constructor for a tile with the Spy on it.
	 * @param guy: Spy object
	 */
	public Tile(Spy guy)
	{
		space = guy.getSymbol();
		player = guy;
		hasSpy = true;
		hasPlayer = true;
	}
	/**
	 * Constructor for a tile with a ninja on it.
	 * @param guy: Ninja to be placed on the tile
	 */
	public Tile(Ninja guy)
	{
		space = guy.getSymbol();
		player = guy;
		hasPlayer = true;
	}
	/**
	 * Constructor for a tile with an item on it
	 * @param i : type of powerUp
	 * @param s : The symbol of the powerUp
	 */
	public Tile(PowerUp i, String s)
	{
		space =s;
		powerUp = s;
		item =i;
		hasItem = true;
	}
	
	/**
	 * Checks if the tile has a room
	 * @return true if it has a room, false otherwise
	 */
	public boolean hasRoom()
	{
		return hasRoom;		
	};
	/**
	 * Sets the current tile to a room
	 * @param r- room to be set on current tile
	 */
	public void setRoom(Room r){
		room = r;
		space = ROOM;
		hasRoom = true;
	}
	/**
	 * Sets the tile to debug on/off
	 * @param b True if debug is on, false otherwise.
	 */
	public void setDebug(boolean b){
		debug = b;
	}
	/**
	 * Checks if the tile is on debug mode or not
	 * @return true if it is on debug, false otherwise.
	 */
	public boolean isDebug(){
		return debug;
	}
	
	/**
	 * Checks if the tile has an Item on it or not
	 * @return true if it has an item, false otherwise.
	 */
	public boolean hasItem()
	{
		return hasItem;		
	};
	/**
	 * Sets the space of the current tile to the string parameter.
	 * @param s - the symbol the tile will show
	 */
	public void setSpace(String s)
	{
		space =s;
		
	}
	
	/**
	 * Sets whether there is a spy on the tile or not.
	 * @param b true if there is a spy, false otherwise.
	 */
	public void setSpy(boolean b){
		hasSpy = b;		
	}
	
	/**
	 * Sets a player on the tile
	 * @param player - player being passed, spy or Ninja
	 */
	public void setPlayer(ActiveAgent player)
	{
		this.player = player;
		hasPlayer = true;
		
	}
	
	/**
	 * Shows the space of the current Tile
	 * @return the symbol of the tile
	 */
	public String getSpace()
	{
		return space;
	}
	/**
	 * The powerUp on the tile
	 * @return  - the item object on the tile
	 */
	public PowerUp getItem(){
		return item;
	}
	
	/**
	 * Checks to see if there is a room on the tile
	 * @return true if there is a room, false otherwise
	 */
	public Room getRoom()
	{
		return room;
	}
	/**
	 * Removes the player from the tile, and sets the space if there is somthing else on
	 * it.
	 */
	public void removePlayer()
	{
		player = null;
		hasPlayer = false;	
		if(this.hasItem())
		{
			setSpace(powerUp);
		}
		else if(this.hasRoom())
		{
			setSpace(ROOM);
		}
		else if(isDebug())
		{
			setSpace(EMPTY);
		}
		else{
			setSpace(DARK);
		}
	}
	/**
	 * Removes the item from the tile.
	 */
	public void removeItem()
	{
		item = null;
		hasItem = false;
		powerUp = EMPTY;
		
	}
	
	/**
	 * Called when the spy looks, will warn the spy if there is a ninja.
	 * Clears the space if its dark.
	 */
	public void look(){
		if(hasPlayer()){
			setSpace("N");
			System.out.println("Ninja ahead!");
		}
		else if(hasItem()){
				setSpace(powerUp);
			}
		else if(!hasRoom()){
			setSpace(EMPTY);
		}
	}
	
	/**
	 * Shows the current powerUp on the tile
	 * @return the type of powerUp that is on the tile.
	 */
	public String getPowerUp(){
		return powerUp;
	}
	
	/**
	 * Checks to see if the tile has a player on it.
	 * @return true if there is a player, false otherwise.
	 */
	public boolean hasPlayer()
	{
		return hasPlayer;		
	};
	/**
	 * Gets the player object on the tile.
	 * @return - the player object on the tile
	 */
	public ActiveAgent getPlayer(){
		return player;
	}
	
	/**
	 * Checks to see if the tile doesnt have a player or a room.
	 * @return true if it has neither a player nor a room, false otherwise.
	 */
	public boolean isEmpty()
	{
		boolean result = true;
		if(hasPlayer()|| hasRoom())
		{
			result = false;
		}
		return result;
	}
	/**
	 * Checks to see if there is a spy on the tile.
	 * @return true if there is a spy, false otherwise.
	 */
	public boolean hasSpy(){
		return hasSpy;
	}
}
