package queryProcessing;

import java.util.*;

public class CreateQuery {
	public void createQueryOperations(String query) {

		System.out.println("create");

		// Ex: Map<"CollageManagement",Map<"student",Map<"id",List<["111",112""]>>>>
		// Ex: Map<"CollageManagement",Map<"student",Map<"name",List<["deep","prabhnoor"]>>>>
		// Ex: Map<"CollageManagement",Map<"student",Map<"department",List<["MACS","MACS"]>>>>


		Map<String,Map<String,Map<String,List<String>>>> objDatabaseData = new HashMap<>();

		// Map<"id", List<["int","pk"]>>
		// Map<"name", List<"varchar">>
		// Map<"department", List<"varchar">>
		Map<String,List<String>> mapTableStructure = new HashMap<>();

		String[] splitQueryForProcessing = query.split(" ");
		splitQueryForProcessing[0].replace("create","");

		try{

		}catch(IllegalArgumentException e){
			System.out.println();
		}

//		CREATE TABLE Persons (
//				PersonID int,
//				LastName varchar(255),
//				FirstName varchar(255),
//				Address varchar(255),
//				City varchar(255)
//		);

//		table : student
//		tStart
//		tStructStart
//		col : id int | pk
//		col : name varchar
//		col : department varchar
//		tStructEnd
//		rowStart
//		id : 111
//		name : deep
//		department : MACS
//		rowEnd
//		rowStart
//		id : 112
//		name : deep
//		department : MACS
//		rowEnd
//		tEnd


		String formattedStringToStore = "";
		for(String str : splitQueryForProcessing){
			if(str.equalsIgnoreCase("table")){

				formattedStringToStore += "\ntable : " + splitQueryForProcessing[Arrays.asList(splitQueryForProcessing).indexOf(str) + 1];
				formattedStringToStore += "\ntstart";
				formattedStringToStore += "\ntStructStart";
			}
			else if(str.equalsIgnoreCase("(")){
				List<String> tempList = new ArrayList<>();
				tempList.add(splitQueryForProcessing[Arrays.asList(splitQueryForProcessing).indexOf(str) + 2]);
				mapTableStructure.put(splitQueryForProcessing[Arrays.asList(splitQueryForProcessing).indexOf(str) + 1],tempList);

				//formattedStringToStore += "\ncol : " + splitQueryForProcessing[Arrays.asList(splitQueryForProcessing).indexOf(str) + 1] + splitQueryForProcessing[Arrays.asList(splitQueryForProcessing).indexOf(str) + 2];
			}
			else if(str.equalsIgnoreCase(")")){
				String pkConstratint = splitQueryForProcessing[Arrays.asList(splitQueryForProcessing).indexOf(str) + 1];
				if(pkConstratint != null ){
					if(pkConstratint.equalsIgnoreCase("constraint")){

					}
				}
			}
			else if(str.equalsIgnoreCase(",")){
				formattedStringToStore += "\ncol : " + splitQueryForProcessing[Arrays.asList(splitQueryForProcessing).indexOf(str) + 1] + splitQueryForProcessing[Arrays.asList(splitQueryForProcessing).indexOf(str) + 2];
			}
		}
	}
}