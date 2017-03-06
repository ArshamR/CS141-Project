package edu.csupomona.cs.cs141.classProject;

import java.io.Serializable;


/**
 * Class representing a powerUp
 *
 */
public class PowerUp implements Item, Serializable {
	
	
	/**
	 *String that holds the type of PowerUp 
	 */
	private String type;
	
	/**
	 * Constructor for powerUp class
	 * @param t: Type of powerUp
	 */
	public PowerUp(String t)
	{
		type = t;
	}
		
	/**
	 * @return the type of powerUp 
	 */
	public String getType(){
		return type;
	};

}
