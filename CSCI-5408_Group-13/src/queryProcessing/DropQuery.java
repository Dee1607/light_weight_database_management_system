package queryProcessing;

import java.util.List;
import java.util.Map;

public class DropQuery {
	public Map<String,Map<String, Map<String, List<String>>>> dropQueryOperations(String query, Map<String,Map<String, Map<String, List<String>>>> mapExistingData) {


		Map<String, Map<String, List<String>>> tempTables = mapExistingData.get("CollageManagement");

		String[] tableNameData = query.split(" ");

		tempTables.remove(tableNameData[2]);
		mapExistingData.replace("CollageManagement",tempTables);

		return mapExistingData;
	}
}
