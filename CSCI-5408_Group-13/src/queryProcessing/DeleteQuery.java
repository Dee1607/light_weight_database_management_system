package queryProcessing;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class DeleteQuery {
	public Map<String,Map<String, Map<String, List<String>>>> deleteQueryOperations(String query, Map<String,Map<String, Map<String, List<String>>>> mapExistingData) {

		Logger logger = Logger.getLogger("GeneralLog");
		Logger eventLogger = Logger.getLogger("eventlog");
		FileHandler fh;
		FileHandler fh1;
		try{
			fh = new FileHandler("generalLog.log",true);
			long startTime = System.nanoTime();
			logger.addHandler(fh);

			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

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

			long stopTime = System.nanoTime();
			long timeToExecute = stopTime-startTime;
			String time= String.valueOf(timeToExecute);
			String str = "Delete query :"+ query+"||"+"Time to execute:"+time;
			logger.info(str);


		}catch (Exception exception){
			try {
				fh1=new FileHandler("eventLog.log",true);
				eventLogger.addHandler(fh1);
				String e = exception.getMessage();
				eventLogger.info(e);
			} catch (IOException Exception){

			}
		}


		return mapExistingData;
	}
}

//delete from student where id = 111 ;