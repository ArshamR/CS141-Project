package edu.csupomona.cs.cs141.classProject;

import java.io.Serializable;




/**
 * Class that represent a gun, can shoot, reload and gain ammo.
 *
 */
public class Gun implements Serializable{
	
	/**
	 * Constant Integer representing the maximum ammo the gun can hold.
	 */
	final int MAX_AMMO = 1;
	
	
	/**
	 * Holds the bullet
	 */
	int ammo;
	
	
	/**
	 * Constructor for gun, Initializes ammo. 
	 */
	public Gun()
	{
		ammo = MAX_AMMO; 
	}
	
	/**
	 * Returns the amount of ammo left in the gun
	 * @return
	 */
	public int getAmmo(){
		return ammo;
	}
	
	
	/**
	 * Shoots the gun, reducing ammo by one.
	 * @return true if the gun has ammo, false otherwise.
	 */
	public boolean shoot(){
		boolean result;
		if(ammo >0){
			ammo--;
			result = true;
		}
		else{
			result = false;
			System.out.println("Out of ammo!");
		}
		return result;
	};
	
	/**
	 * Called when ammo powerUp is picked up, adds a bullet to the ammo
	 */
	public void gainAmmo(){
		if(ammo < MAX_AMMO){
			ammo++;
		}
	};

}
