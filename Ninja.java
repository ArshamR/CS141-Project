package edu.csupomona.cs.cs141.classProject;


/**
 * Class representing a ninja
 *
 */
public class Ninja extends ActiveAgent{

	
	/**
	 * The symbol you see on the grid, represents a ninja
	 */
	private String symbol;
	
	/**
	 * boolean true if Ninja is dead, false otherwise.
	 */
	private boolean isDead;
	
	
	/**
	 * Constructor for ninja
	 * Initializes symbol to N, and IsDead to false. 
	 */
	public Ninja()
	{
		symbol = "N";
		isDead = false;
	}
	
	/* (non-Javadoc)
	 * @see edu.csupomona.cs.cs141.classProject.ActiveAgent#getSymbol()
	 */
	public String getSymbol()
	{
		return symbol;
	}
}
