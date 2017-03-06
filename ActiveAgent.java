package edu.csupomona.cs.cs141.classProject;

import java.io.Serializable;


/**
 * This is an abstract class that Spy and Ninja inherit from. It has common methods such as move, and isDead, anything
 * that two humanoids share.
 */
public abstract class ActiveAgent implements Serializable{
	
	/**
	 * Represents the x coordinate of the player on the grid
	 */
	protected int x;
	/**
	 * Represents the y coordinate of the player on the grid
	 */
	protected int y;
	
	/**
	 * The symbol shown on grid that represents what kind of player it is Ninja or Spy
	 */
	protected String symbol;
	/**
	 * boolean value that is true when the player dies
	 */
	private boolean isDead;
	
	/**
	 * Called when a player dies, sets isDead to true
	 */
	public void died(){
		isDead =true;
	}
	
	/** Returns the boolean value isDead
	 *  @return true if player isDead and false otherwise
	 */
	public boolean isDead(){
		return isDead;
	}
	
	/** Gets the players symbol
	 * @return The symbol of the player, N if ninja, S if Spy
	 */
	public String getSymbol()
	{
		return symbol;
	}

	/**
	 * Updates the player's position
	 * @param y : Y position of player
	 * @param x : X position of player
	 */
	public void move(int y, int x)
	{
		this.x = x;
		this.y = y;	
	};

	/**
	 * Returns the X position of the player
	 * @return value of X
	 */
	public int getXPos()
	{
		return x;
	}
	
	/**
	 * Returns the Y position of the player
	 * @return value of Y
	 */
	public int getYPos()
	{
		return y;
	}

}
