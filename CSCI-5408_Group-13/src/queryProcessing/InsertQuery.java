package queryProcessing;

import java.util.*;

public class InsertQuery {
	public Map<String, Map<String, Map<String, List<String>>>> insertQueryOperations(String query, Map<String,Map<String, Map<String, List<String>>>> mapExistingData) {

		Map<String, Map<String, List<String>>> tempTables = mapExistingData.get("CollageManagement");

		query = query.replaceAll(";","");
		query = query.replaceAll(",","");
		query = query.replaceAll("\\(","");
		query = query.replaceAll("\\)","");

		String[] tableNameData = query.split(" ");
		Map<String,List<String>> tempColumn = tempTables.get(tableNameData[2]);

		String[] arrSplit = query.split("values");

		String[] leftData = arrSplit[0].replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("  ", " ").split(" ");
		String[] rightData = arrSplit[1].replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("   ", " ").replaceAll("  ", " ").split(" ");

		List<String> leftElements = new ArrayList<>();
		for(int i=3; i<leftData.length; i++){
			if(!leftData[i].equals("") && !leftData[i].equals(" ")){
				leftElements.add(leftData[i]);
			}
		}

		List<String> rightElements = new ArrayList<>();
		for(int i=0; i<rightData.length; i++){
			if(!rightData[i].equals("") && !rightData[i].equals(" ")){
				rightElements.add(rightData[i]);
			}
		}

		for(int j=0; j<leftElements.size(); j++){
			List<String> tempColumnValues = tempColumn.get(leftElements.get(j));
			tempColumnValues.add(rightElements.get(j));
			tempColumn.replace(leftElements.get(j),tempColumnValues );
		}

		tempTables.replace(tableNameData[2], tempColumn);
		mapExistingData.replace("CollageManagement", tempTables);

		return mapExistingData;
	}
















	public String extractDataFromString(String tempString){
		String finalString = tempString;

		if(tempString.startsWith("(") | tempString.startsWith(")") | tempString.startsWith("'") | tempString.startsWith("=") | tempString.startsWith(",")){
			finalString = tempString.substring(1, tempString.length());
		}

		if(finalString.endsWith("(") | finalString.endsWith(")") | finalString.endsWith("'") | finalString.endsWith("=") | finalString.endsWith(",")){
			finalString = finalString.substring(0, finalString.length());
		}

		if(finalString.endsWith("(") | finalString.endsWith(")") | finalString.endsWith("'") | finalString.endsWith("=") | finalString.endsWith(",")){
			finalString = finalString.substring(0, finalString.length());
		}

		return finalString;
	}
}
//insert into student ( id , name , department ) values ( 1 , 'TOM', 'MACS' );