package edu.csupomona.cs.cs141.classProject;

import java.io.Serializable;


/**
 * Class representing a Room. Room can hold an Item which is the briefcase.
 *
 */
public class Room implements Serializable{

	/**
	 * The item in the room.
	 */
	Item item;
	/**
	 * If the room has a briefcase or not.
	 */
	private boolean hasBriefcase;
	
	/**
	 * By default no room starts with a briefcase
	 */
	public Room()
	{
		hasBriefcase = false;
	}
	
	/**
	 * Sets the briefcase in a room
	 */
	public void setBriefcase(){
		hasBriefcase = true;
	}
	
	/**
	 * @return true is the room has a briefcase false otherwise
	 */
	public boolean checkBriefcase()
	{
		return hasBriefcase;
	}
}
