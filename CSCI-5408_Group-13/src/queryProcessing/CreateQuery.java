package queryProcessing;

import java.util.*;
import java.util.logging.Logger;

public class CreateQuery {

	public static List<String> CREATE_DUMP_LIST = new ArrayList<>();
	public static Map<String,List<String>> FOREIGN_KEY_LIST = new HashMap<>();

	public Map<String, Map<String, Map<String, List<String>>>> createQueryOperations(String query) {

		Map<String, Map<String, Map<String, List<String>>>> mapDatabaseData = new HashMap<>();
		Map<String, Map<String, List<String>>> mapTableStructure = new HashMap<>();
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

		if(query.contains("constraint")){
			String[] arrToGetFKName = query.split("constraint");
			String[] arrRelation = arrToGetFKName[1].split("foreign key");
			String FKName =  arrRelation[0].trim();

			String rightSplit = arrRelation[1].trim();
			String[] arrSplitSubString = rightSplit.split("references");
			String currentTableFKColumn = arrSplitSubString[0].replace("(","").replace(")","").trim();

			String[] tempSplit = arrSplitSubString[1].split(" ");
			String FKTable = tempSplit[1].trim();
			String FKTableColumnName = tempSplit[3].trim();

			List<String> arrayRelationData = new ArrayList<>();
			arrayRelationData.add(currentTable);
			arrayRelationData.add(currentTableFKColumn);
			arrayRelationData.add(FKTable);
			arrayRelationData.add(FKTableColumnName);

			FOREIGN_KEY_LIST.put(FKName,arrayRelationData);
		}
		return mapDatabaseData;
	}
}

// create table student ( id int primary key , name varchar(255) , department varchar(255) ) ;
// create table course  ( course_id int primary key , course_title varchar(255) ) ;
// create table grade ( grade_id int primary key , student_id int , course_id int , constraint fk1 foreign key (student_id) references student ( student_id ) );