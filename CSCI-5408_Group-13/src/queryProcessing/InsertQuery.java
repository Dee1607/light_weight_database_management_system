package queryProcessing;

import FormattedFileWriter.SQLFileWriter;

import java.util.*;

public class InsertQuery {
	public Map<String, Map<String, Map<String, List<String>>>> insertQueryOperations(String query, Map<String,Map<String, Map<String, List<String>>>> mapExistingData,Map<Boolean, List<String>> dbInfo) {

		Map<String, Map<String, List<String>>> tempTables = mapExistingData.get(dbInfo.get(true).get(0));

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
		mapExistingData.replace(dbInfo.get(true).get(0), tempTables);

		SQLFileWriter objWriter = new SQLFileWriter();
		objWriter.writeIntoSQLFile(mapExistingData);

		System.out.println("Data inserted Successfully !!");
		return mapExistingData;
	}
}

// insert into courseSelector ( cs_id , professor_id , course_id ) values ( 123 , 123 , 123 ) ;
// insert into student ( id , name , department ) values ( 1 , 'TOM', 'MACS' ) ;
// insert into grade ( grade_id , student_id , course_id ) values ( 1 , 'asd' , 'asd' ) ;