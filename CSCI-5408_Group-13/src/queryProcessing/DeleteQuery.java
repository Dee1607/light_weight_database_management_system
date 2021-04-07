package queryProcessing;

import java.util.List;
import java.util.Map;

public class DeleteQuery {
	public Map<String,Map<String, Map<String, List<String>>>> deleteQueryOperations(String query, Map<String,Map<String, Map<String, List<String>>>> mapExistingData) {
		
		System.out.println("delete");
		Map<String, Map<String, List<String>>> tempTables = mapExistingData.get("CollageManagement");

		query = query.replaceAll(";","");
		query = query.replaceAll(",","");
		query = query.replaceAll("\\(","");
		query = query.replaceAll("\\)","");

		String[] tableNameData = query.split(" ");
		Map<String,List<String>> tempColumn = tempTables.get(tableNameData[2]);

		String[] split = query.split("where");
		String[] rightSplit = split[1].split("=");

		int index = -1;
		List<String> tempList = tempColumn.get(rightSplit[0].trim());
		index = tempList.indexOf(rightSplit[1].trim());

		if(index >= 0){
			for(String str : tempColumn.keySet()){
				List<String> temparoryData = tempColumn.get(str);
				temparoryData.remove(index);
				tempColumn.replace(str,temparoryData);
			}
			tempTables.replace(tableNameData[2].trim(),tempColumn);
			mapExistingData.replace("CollageManagement", tempTables);
		}

		return mapExistingData;
	}
}

//delete from student where id = 111 ;