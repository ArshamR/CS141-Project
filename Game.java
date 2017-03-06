package edu.csupomona.cs.cs141.classProject;

/**
 * CS 141: Introduction to Programming and problem solving
 * Professor: Edwin Rodriguez
 * 
 * Class Project the game takes place inside a building which will be represented as a grid of 81 squares, every square representing a possible position for different entities in the game (player's avatar, enemies, power-ups), with the exception of 9 special squares which represent rooms, and are equally distributed on the grid.

	The player's character is a spy that is tasked with retrieving a briefcase containing classified enemy documents, which is located in one of the rooms. There's a few caveats:

	The building is pitch-black.
	There are six ninja-assassins patroling the building.
	The spy is initially placed at the bottom left-corner of the grid. The ninjas are randomly located across the grid, with the limitation that they must be, initially, at least 3 squares away from the spy's initial location. The briefcase is randomly located in one of the nine rooms at the start of the game. The rooms can only be accessed from the north side.
	
	Since the room is pitch-black, the spy can't see anything, so he is equipped with night vision goggles that allow him to see only two squares ahead of his position. On every turn, the spy is the first one to take action, and then the ninjas, each in turn. The spy is also equipped with a gun that has only one bullet. At every turn, the spy is allowed the following actions:
	
	The spy can 'look' in any direction, and then perform one of the following actions:.
	Move one square in any available direction.
	Shoot in any direction, if there's still ammo.
	The action of 'looking' in any direction will result in either a 'clear' or 'ninja ahead' signal. The action of 'looking' can only be done once per turn.

	Ninjas will start every turn by checking if the spy is at an adjacent square. If the player is at an adjacent square, the ninja will 'mortally stab' him, killing the spy. Otherwise, the ninja will move one square in a random direction.

	Additionally, there are three power-ups in the game:

	Additional Bullet: like the name says, it will grant the player an additional bullet. If the player hasn't used his initial bullet, the power-up will have no effect.
	Invincibility: this power-up will grant the spy invulnerability to stabbing for five turns.
	Radar: this power-up will automatically display on the screen the location of the briefcase, displaying on the screen the room number where the briefcase is located.
	All power-ups are initially randomly placed across the grid. The player has 3 'lives'. Every time the player is stabbed, the player is repositioned back at the initial position losing one life. If the player loses all lives, the game is over.

	Finally, the player can quit the game at any time and save his progress. Later on, he can restart the game and reload the previous save.
 * 
 * In this assignment the user is given an option of 3 guns, A handgun,shotgun, and rifle. There are five targets to hit. Once the five targets
 * are all successfully hit, the user is given an option to replay or quit.
 * 
 * Team Avatar
 * @author Arsham Ravanipour, Sean Kinnane, Sean Niwa, Anthony Guerrero, Daniel Chow.
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;


public class Game {
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		Engine game = new Engine();
		game.start();
		}
}



