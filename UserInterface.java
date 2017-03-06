package edu.csupomona.cs.cs141.classProject;

import java.util.Scanner;


/**
 * This is the userinterface class. It displays all the messages to the console and 
 * reads the users input.
 *
 */
public class UserInterface {
	
	/**
	 * Displays at the beginning of the game
	 * @param sc - The scanner that reads the input
	 * @return - the users selection.
	 */
	public int mainMenu(Scanner sc){
		int result;
		System.out.println("Welcome to the game, what would you like to do?");
		System.out.println("1.New game");
		System.out.println("2.Load game");
		System.out.println("3.Exit");
		result = sc.nextInt();
		return result;
	};
	/**
	 * Asks the user if he wants to shoot or move
	 * @param sc - The scanner that reads the input
	 * @return - the users selection.
	 */
	public String options(Scanner sc){
		String result;
		System.out.println("What would you like to do?");
		System.out.println("1. Shoot");
		System.out.println("2. Move");
		result = sc.next();
		return result;
	};
	/**
	 * Displays the victory screen if the spy wins
	 */
	public void victory(){
		System.out.println("Congratulations, you win!");
	};
	/**
	 * Displays the defeat screen if the spy lost
	 */
	public void defeat(){
		System.out.println("\n\nYou have lost!");
	};
	/**
	 * Displays options: 
	 * 1.Look
	 * 2.Debug Mode
	 * 3.Save
	 * 4.Exit
	 * @param sc - The scanner that reads the input
	 * @return - the users selection.	 */
	public int lOptions(Scanner sc){
		int result;
		System.out.println("What would you like to do?");
		System.out.println("1.Look");
		System.out.println("2.Debug mode");
		System.out.println("3.Save");
		System.out.println("4.Exit");
		result = sc.nextInt();
		return result;
		};
		
	/**
	 * Displays the movement options and allows the user
	 * to select a direction.
	 * @param sc - The scanner that reads the input
	 * @return - the users selection.
	 */
	public String movementOptions(Scanner sc)
	{
		String result;
		System.out.println("Here are your options:");
		System.out.println("1.W (Move up)");
		System.out.println("2.A (Move left)");
		System.out.println("3.S (Move down)");
		System.out.println("4.D (Move right)");
		result = sc.next().toUpperCase();
		return result;
	};
	
	/**
	 * Displays the shoot options and allows the user to 
	 * select a direction.
	 * @param sc - The scanner that reads the input
	 * @return - the users selection.
	 */
	public String shootOptions(Scanner sc){
		String result;
		System.out.println("Here are your options");
		System.out.println("W: Shoot North");
		System.out.println("A: Shoot West");
		System.out.println("S: Shoot South");
		System.out.println("D: Shoot East");
		result = sc.next().toUpperCase();
		return result;
	}
	
	/**
	 * Displays the possible directions to look
	 * @param sc - The scanner that reads the input
	 * @return - the users selection.
	 */
	public String lookOptions(Scanner sc){
		String result;
		System.out.println("Here are your options");
		System.out.println("W: Look North");
		System.out.println("A: Look West");
		System.out.println("S: Look South");
		System.out.println("D: Look East");
		result = sc.next().toUpperCase();
		return result;
	}
	/**
	 * Displays when the spy dies
	 */
	public void died()
	{
		System.out.println("\nYou died!!\n\n");
	}
}
