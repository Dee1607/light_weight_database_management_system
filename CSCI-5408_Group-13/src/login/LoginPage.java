package login;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import presentationlayer.DisplayToGetUserChoice;

public class LoginPage {

	private String USER_CREDENTIALS_FILE_PATH = "/Users/deeppatel/Desktop/GlobalDataDictionary/users.txt";
	private DisplayToGetUserChoice objGetData = null;
	Map<String,String> MAP_OF_CREDENTIALS = null;

	public boolean getCredentials() 
	{
		MAP_OF_CREDENTIALS = new HashMap<>();
		boolean isCredentialsCorrect = false;

		try{
			File file = new File(USER_CREDENTIALS_FILE_PATH);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line = br.readLine()) != null){
				String[] tempListOfUsers = line.split(":");
				MAP_OF_CREDENTIALS.put(tempListOfUsers[0],tempListOfUsers[1]);
			}

			objGetData = new DisplayToGetUserChoice();

			int count = 0;
			while(!isCredentialsCorrect){
				if(count != 0){
					objGetData.displayMessage("Invalid Password!! Please Enter Again!");
				}
				String username = objGetData.displayMessageGetStringChoiceFromUser("Enter Username: ");
				String password = objGetData.displayMessageGetStringChoiceFromUser("Enter Password: ");
				isCredentialsCorrect = checkUser(username,password,MAP_OF_CREDENTIALS);
				if(count >= 3){
					objGetData.displayMessage("You are Blocked!!");
					return false;
				}
				count++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return isCredentialsCorrect;
	}
	public boolean checkUser(String username, String password,Map<String,String> mapCheckingCredentials) {

		if(mapCheckingCredentials.containsKey(username)){
			if(mapCheckingCredentials.get(username).equals(password)){
				return true;
			}
		}
		return false;
	}
}