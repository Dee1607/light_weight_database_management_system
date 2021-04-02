package queryProcessing;

import presentationlayer.DisplayToGetUserChoice;

public class GetQuery {
	
	DisplayToGetUserChoice objGetData = null;

	public String getQueryFromUser() {
		
		objGetData = new DisplayToGetUserChoice();
		String query = objGetData.displayMessageGetStringChoiceFromUser("Enter Your Query Here -> ");
		
		return query;
	}
}
