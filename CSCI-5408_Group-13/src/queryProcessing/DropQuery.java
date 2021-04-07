package queryProcessing;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class DropQuery {
	public Map<String,Map<String, Map<String, List<String>>>> dropQueryOperations(String query, Map<String,Map<String, Map<String, List<String>>>> mapExistingData) {
		Logger logger = Logger.getLogger("GeneralLog");
		Logger eventLogger = Logger.getLogger("eventLog");
		FileHandler fh;
		FileHandler fh1;

		try {
			fh = new FileHandler("generalLog.log",true);
			long startTime = System.nanoTime();
			logger.addHandler(fh);

			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

			Map<String, Map<String, List<String>>> tempTables = mapExistingData.get("CollageManagement");

			String[] tableNameData = query.split(" ");

			tempTables.remove(tableNameData[2]);
			mapExistingData.replace("CollageManagement",tempTables);

			long stopTime = System.nanoTime();
			long timeToExecute = stopTime-startTime;
			String time= String.valueOf(timeToExecute);
			String str = "Drop query :"+ query+"||"+"Time to execute:"+time;
			String str2 = tableNameData[2]+"removed";
			logger.info(str);
			logger.info(str2);

		}catch (IOException ioException){
			try {
				fh1=new FileHandler("eventLog.log",true);
				eventLogger.addHandler(fh1);
				String exception = ioException.getMessage();
				eventLogger.info(exception);
			} catch (IOException Exception){

			}
		}



		return mapExistingData;
	}
}
