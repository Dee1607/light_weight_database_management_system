package queryProcessing;

import java.util.*;

public class CreateQuery {

	public static List<String> CREATE_DUMP_LIST = new ArrayList<>();

	public Map<String, Map<String, Map<String, List<String>>>> createQueryOperations(String query) {

		// Ex: Map<"CollageManagement",Map<"student",Map<"id",List<["111",112""]>>>>
		// Ex: Map<"CollageManagement",Map<"student",Map<"name",List<["deep","prabhnoor"]>>>>
		// Ex: Map<"CollageManagement",Map<"student",Map<"department",List<["MACS","MACS"]>>>>
		Map<String, Map<String, Map<String, List<String>>>> mapDatabaseData = new HashMap<>();

		// Ex: Map<"student",Map<"id",List<["id","varchar"]>>>
		Map<String, Map<String, List<String>>> mapTableStructure = new HashMap<>();

		// Map<"id", List<["int","pk"]>>
		// Map<"name", List<"varchar">>
		// Map<"department", List<"varchar">>
		Map<String, List<String>> mapColumn = new HashMap<>();

		String currentTable = "";
		String[] splitQueryForProcessing = query.split(" ");
		splitQueryForProcessing[0].replace("create", "");

		try {
			for (int i = 0; i < splitQueryForProcessing.length; i++) {
				if (splitQueryForProcessing[i].equalsIgnoreCase("create")) {
					currentTable = splitQueryForProcessing[i + 2];
					mapTableStructure.put(splitQueryForProcessing[i + 2], null);
					i += 2;
				} else if (splitQueryForProcessing[i].equalsIgnoreCase("(") || splitQueryForProcessing[i].equalsIgnoreCase(",")) {
					List<String> tempList = new ArrayList<>();
					tempList.add(splitQueryForProcessing[i + 2]);
					mapColumn.put(splitQueryForProcessing[i + 1], tempList);

					if (splitQueryForProcessing[i + 3].equalsIgnoreCase("primary")) {
						tempList.add("pk");
						i += 3;
					} else {
						tempList.add(null);
						i += 2;
					}
				} else if (splitQueryForProcessing[i].equalsIgnoreCase(")")) {
					mapTableStructure.put(currentTable, mapColumn);
				} else if (splitQueryForProcessing[i].equalsIgnoreCase(";")) {
					mapDatabaseData.put("database", mapTableStructure);
				}
			}
			CREATE_DUMP_LIST.add(query);
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid Query");
		}
		return mapDatabaseData;
	}
}

// create table student ( id int primary key , name varchar , department varchar ) ;