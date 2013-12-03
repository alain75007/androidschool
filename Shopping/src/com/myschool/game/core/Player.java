package com.myschool.game.core;

public class Player {
	private String email;
	private Character character;
	public Player(String email) {
		
	}
	// LOGIN Fragment
	//
	//

	// MainActivity
	// if logged
	//		if has personnage
	//			list "choose your personnage + currentPersonnage"
	//	else
	//			list "choose your personnage" + "previous Personnage"
	//  
	//  if personnage choosen?
	//		start GameActivity
	//
	// GameActivity
	// If exit : "save you character" if not logged
	// if exit : save character and game
	// 
	//	if logged
	//		save game and character
	//		exit
	//	else
	//		"want to save?"
	//		if wanted
	//			LOGGING Fragment (GameActivity.exit())
	//		else
	//			exit
	//	
	//	 - get location and display card
	// 	 - menu : shopping
	//	 - menu : search for somebody
	//	 - menu : news 
	//	 - menu : local news
	//	 - menu : street news
	//	 - menu : search news
	//	 - menu : places around you
	//	 - menu : try to speak to somebody
	//   - menu : goto
	//	 - menu : travel to
	//	 - menu : enter into place
	//	 - menu : fight somebody
	//	 - goto cartoon
	//
	// if personnage 
	// choose personnage
	//   already have personnage?
	//	 	if not logged : LOG PROCESS callback = CHOOSE PLAYER CHARACTER
	//      else CHOOSE PLAYER CHARACTER
	//
	// LOGGING ACTIVITY/FRAGMENT
	// if not already have an account?
	//		choose android account
	//		if no account choosed
	//			return
	// log to to server
	// if log successfull 
	// 		if callback 
	//			callback
	//		else
	//			return
	// 
	// CHOOSE PLAYER EXISTANT CHARACTER
	//   List character
	//   character choosen? 
	//choose character
}
