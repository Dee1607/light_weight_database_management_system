package queryProcessing;

import java.util.List;
import java.util.Map;

public class SelectQuery {
	
	public void selectQueryOperations(String query, Map<String,Map<String, Map<String, List<String>>>> mapExistingData) {

		Map<String, Map<String, List<String>>> tempTables = mapExistingData.get("CollageManagement");

		query = query.replaceAll(";","");
		query = query.replaceAll(",","");
		query = query.replaceAll("\\(","");
		query = query.replaceAll("\\)","");

		String[] tableNameData = query.split(" ");
		Map<String,List<String>> tempColumn = tempTables.get(tableNameData[3]);


		System.out.println("=================================================================");
		for(String columnName : tempColumn.keySet()){
			System.out.format("%1s%15s%15s", "|",columnName + ":", " |");

			List<String> tempListValues = tempColumn.get(columnName);
			for (int i = 2; i < tempListValues.size(); i++) {
				System.out.format("%1s%15s%15s", "|",tempListValues.get(i), " |");
			}
			System.out.println();
		}
		System.out.println("=================================================================");
	}
}

// Select * from student ;