package queryProcessing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectQuery {
	
	public void selectQueryOperations(String query, Map<String,Map<String, Map<String, List<String>>>> mapExistingData) {

		Map<String, Map<String, List<String>>> tempTables = mapExistingData.get("CollageManagement");

		String[] queryWords = query.split(" ");

		Map<String,List<String>> tempColumn = tempTables.get(queryWords[3]);

		for (String columnName : tempColumn.keySet()){
			List<String> columnValues = new ArrayList<>();
			columnValues = tempColumn.get(columnName);
			System.out.print(columnName+":");
			for (int i = 0; i <columnValues.size() ; i++) {
				System.out.print(columnValues.get(i)+"  ");
				System.out.println("");
			}
		}

	}
}

// Select * from student ;