package queryProcessing;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class UpdateQuery {
	public Map<String,Map<String, Map<String, List<String>>>> updateQueryOperations(String query,Map<String,Map<String, Map<String, List<String>>>> mapExistingData, Map<Boolean, List<String>> dbInfo) {


		Logger logger = Logger.getLogger("GeneralLog");
		Logger eventLogger = Logger.getLogger("eventLog");
		FileHandler fh;
		FileHandler fh1;
		System.out.println("create");

		try {
			fh = new FileHandler("generalLog.log",true);
			long startTime = System.nanoTime();
			logger.addHandler(fh);

			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

			Map<String, Map<String, List<String>>> tempTables = mapExistingData.get(dbInfo.get(true).get(0));

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
				mapExistingData.replace(dbInfo.get(true).get(0), tempTables);
			}

			long stopTime = System.nanoTime();
			long timeToExecute = stopTime-startTime;
			String time= String.valueOf(timeToExecute);
			String str = "Update query :"+ query+"||"+"Time to execute:"+time;
			String str2 = "Update table:"+tableNameData[2];

			logger.info(str);
			logger.info(str2);
		}catch (Exception e){
			try {
				fh1=new FileHandler("eventLog.log",true);
				eventLogger.addHandler(fh1);
				String exception = e.getMessage();
				eventLogger.info(exception);
			} catch (IOException Exception){

			}
		}

		System.out.println("Updation completed Successfully !!");
		return mapExistingData;
	}
}

//update student set name = 'Schmidt' where id = 111;