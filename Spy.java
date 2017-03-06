package edu.csupomona.cs.cs141.classProject;


/**
 * Class representing the spy. 
 * Spy can have a gun, look, shoot, and move.
 *
 */
public class Spy extends ActiveAgent{
	
	
	/**
	 * Constant String that represent the image of a Spy on the grid
	 */
	final private String SYMBOL = "S";
	/**
	 * The gun object of the Spy
	 */
	private Gun gun;
	/**
	 * Integer that contains the number of lives the Spy has left
	 */
	private int numLives;
	/**
	 * Boolean that is true if the Spy is invincible, false otherwise.
	 */
	private boolean isInvincible;
	/**
	 * Boolean that is true if the spy has the briefcase, false otherwise.
	 */
	private boolean hasBriefcase;
	/**
	 * Boolean that is true if the spy has the radar powerUp, false otherwise..
	 */
	private boolean hasRadar;
	/**
	 * Boolean that is true if the spy is in a room, false otherwise.
	 */
	private boolean inRoom;
	
	
	/**
	 * Constructor for the Spy class
	 * Initializes the fields to default starting values.
	 */
	public Spy()
	{
		gun = new Gun();
		x = Engine.STARTING_X;
		y = Engine.STARTING_Y;
		symbol = SYMBOL;
		hasBriefcase = false;
		isInvincible = false;
		hasRadar = false;
		inRoom = false;
		numLives = 3;
	}
	
	/**
	 * Shoots the gun if it has ammo
	 * @return True if the gun has ammo, false otherwise.
	 */
	public boolean shoot()
	{
		boolean result;
		result = gun.shoot();
		return result;
	}
	/**
	 * Returns numLives
	 * @return - The number of lives the Spy has
	 */
	public int getLives(){
		return numLives;
	}
	/**
	 * Called when the player dies, decrements the number of lives by one
	 */
	public void LoseLife(){
		if(numLives >=1){
			numLives--;
		}
	}
	
	/**
	 * Called when the spy enters or leaves a room. 
	 * @param b: True if the spy entered a room, False if the spy left a room.
	 */
	public void setInRoom(boolean b){
		inRoom = b;
	}
	/**
	 * Checks if the spy is in a room or not.
	 * @return- true if spy is in a room, false otherwise.
	 */
	public boolean inRoom(){
		return inRoom;
	}
	
	/**
	 * Returns the spy's gun.
	 * @return- the gun object
	 */
	public Gun getGun(){
		return gun;
	}
	
	/**
	 * Called when the spy gets the briefcase
	 */
	public void gotBriefcase()
	{
		hasBriefcase = true;
	}
	/**
	 * Called when the spy gets the invincibility powerUp and when he loses it.
	 * @param s: True when he picks it up, false when it runs out.
	 */
	public void setInvincible(boolean s){
		isInvincible = s;
	}
	/**
	 * Called when the spy picks up the ammo powerUp.
	 * The gun gains 1 ammo.
	 */
	private void ammo(){
		gun.gainAmmo();
		
	}
	/**
	 * Called when the spy picks up the Radar powerUp
	 */
	private void gotRadar(){
		hasRadar = true;
	}
	/**
	 * Checks if the spy has the radar
	 * @return true if spy has radar, false otherwise
	 */
	public boolean hasRadar(){
		return hasRadar;
	}
	
	/**
	 * Checks if the spy has the invicible powerUp
	 * @return- true if spy is invinicble, false otherwise.
	 */
	public boolean isInvincible(){
		return isInvincible;
	}
	
	/**
	 * Checks if the spy has the briefcase
	 * @return- true if spy has briefcase, false otherwise.
	 */
	public boolean hasBriefcase(){
		return hasBriefcase;
	}
	
	/**
	 * Picks up an item and grants the proper bonus dependning
	 * the which power up was picked up.
	 * @param item - the power up that was picked up.
	 */
	public void pickUp(PowerUp item){
		String temp = item.getType();
		switch(temp)
		{
			case Engine.INVINCIBILITY: setInvincible(true);
			System.out.println("Picked up invincibility!");
			break;
			case Engine.AMMO: ammo();
			System.out.println("Picked up ammo!");
			break;
			case Engine.RADAR: gotRadar();
			System.out.println("Picked up a radar!");
			break;		
		}
	};

}
