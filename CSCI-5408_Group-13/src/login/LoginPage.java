package login;

import java.util.Scanner;

import presentationlayer.DisplayToGetUserChoice;

public class LoginPage {

	DisplayToGetUserChoice objGetData = null;
	public boolean getCredentials() 
	{
		objGetData = new DisplayToGetUserChoice();
		String username = objGetData.displayMessageGetStringChoiceFromUser("Enter Username: ");
		String password = objGetData.displayMessageGetStringChoiceFromUser("Enter Password: ");
		
		boolean isCredentialsCorrect = checkUser(username,password);
		
		return isCredentialsCorrect;
	}
	public boolean checkUser(String username, String password) {
		
		//Check from remote file/local file.
		
		return true;
	}
	
}
