package queryProcessing;

import java.util.List;
import java.util.Map;

public class UpdateQuery {
	public Map<String,Map<String, Map<String, List<String>>>> updateQueryOperations(String query,Map<String,Map<String, Map<String, List<String>>>> mapExistingData) {

		System.out.println("create");

		Map<String, Map<String, List<String>>> tempTables = mapExistingData.get("CollageManagement");

		query = query.replaceAll(";","");
		query = query.replaceAll(",","");
		query = query.replaceAll("\\(","");
		query = query.replaceAll("\\)","");

		String[] tableNameData = query.split(" ");
		Map<String,List<String>> tempColumn = tempTables.get(tableNameData[1]);

		String[] splitQueryForProcessing = query.split("set");
		String[] split = splitQueryForProcessing[1].split("where");
		String[] leftSplit = split[0].split("=");
		String[] rightSplit = split[1].split("=");

		int index = -1;
		List<String> tempList = tempColumn.get(rightSplit[0].trim());
		index = tempList.indexOf(rightSplit[1].trim());

		if(index >= 0){
			tempList = tempColumn.get(leftSplit[0].trim());
			tempList.set(index,leftSplit[1].trim());

			tempColumn.replace(leftSplit[0].trim(),tempList);
			tempTables.replace(tableNameData[2].trim(),tempColumn);
			mapExistingData.replace("CollageManagement", tempTables);
		}

		return mapExistingData;
	}
}

//update student set name = 'Schmidt' where id = 111;