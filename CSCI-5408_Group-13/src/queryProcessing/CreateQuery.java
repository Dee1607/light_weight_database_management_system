package queryProcessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CreateQuery {

	public static List<String> CREATE_DUMP_LIST = new ArrayList<>();
	public static Map<String,List<String>> FOREIGN_KEY_LIST = new HashMap<>();
	public List<String> GDD_TABLES = new ArrayList<>();

	public Map<String, Map<String, Map<String, List<String>>>> createQueryOperations(String query, Map<Boolean, List<String>> loginStatus) {

		Logger logger = Logger.getLogger("GeneralLog");
		Logger eventLogger = Logger.getLogger("eventLog");
		FileHandler fh;
		FileHandler fh1;

		try{
			String fileName = "GDD.txt";
			File file = new File(fileName);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			String allLinesData = "";
			while((line = br.readLine()) != null){
				allLinesData += line + " ";
				String[] tempSplitArray = allLinesData.split("-");
				GDD_TABLES.add(tempSplitArray[0]);
			}

		}catch(Exception e){
			e.printStackTrace();
		}

		Map<String, Map<String, Map<String, List<String>>>> mapDatabaseData = new HashMap<>();
		Map<String, Map<String, List<String>>> mapTableStructure = new HashMap<>();
		Map<String, List<String>> mapColumn = new HashMap<>();

		String currentTable = "";
		String[] splitQueryForProcessing = query.split(" ");
		splitQueryForProcessing[0].replace("create", "");



		if(!GDD_TABLES.contains(splitQueryForProcessing[2])){
			try {
				fh = new FileHandler("generalLog.log",true);
				long startTime = System.nanoTime();
				logger.addHandler(fh);

				SimpleFormatter formatter = new SimpleFormatter();
				fh.setFormatter(formatter);

				for (int i = 0; i < splitQueryForProcessing.length; i++) {
					if (splitQueryForProcessing[i].equalsIgnoreCase("create")) {

						currentTable = splitQueryForProcessing[i+2];
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
						mapDatabaseData.put(loginStatus.get(true).get(0), mapTableStructure);
					}
				}
				CREATE_DUMP_LIST.add(query);

				long stopTime = System.nanoTime();
				long timeToExecute = stopTime-startTime;
				String time= String.valueOf(timeToExecute);
				String str = "Create query :"+ query+"||"+"Time to execute:"+time;
				logger.info(str);

			} catch (IllegalArgumentException e) {
				System.out.println("Invalid Query");
			}catch (Exception e){
				try {
					fh1=new FileHandler("eventLog.log",true);
					eventLogger.addHandler(fh1);
					String exception = e.getMessage();
					eventLogger.info(exception);
				} catch (IOException Exception){
					Exception.printStackTrace();
				}
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

			try{
				String formattedString = "";
				Path path = Path.of("GDD.txt");
				if(!Files.exists(path)){
					Files.createFile(path);
				}
				formattedString += currentTable+"-"+loginStatus.get(true).get(1) +"-"+ loginStatus.get(true).get(0);
				Files.write(path,(formattedString+"\n").getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);

			}catch(Exception e){
				e.printStackTrace();
			}
			System.out.println("Table Created Successfully !!");
		}
		else{
			System.out.println("Table Already Exist !!");
		}
		return mapDatabaseData;
	}
}

// create table student ( id int primary key , name varchar(255) , department varchar(255) ) ;
// create table course  ( course_id int primary key , course_title varchar(255) ) ;

// create table professor ( id int primary key , name varchar(255) , department varchar(255) ) ;
// create table courseSelector ( cs_id int primary key , professor_id int , course_id int , constraint fk1 foreign key (professor_id) references professor ( professor_id ) );

// create table grade ( grade_id int primary key , student_id int , course_id int , grade int , constraint fk1 foreign key (student_id) references student ( student_id ) );
// insert into grade ( grade_id , student_id , course_id ) values ( 12 , 1200 , 12000 ) ;