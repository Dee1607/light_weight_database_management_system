package queryProcessing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SelectQuery {
	
	public void selectQueryOperations(String query, Map<String,Map<String, Map<String, List<String>>>> mapExistingData) {
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
			long stopTime = System.nanoTime();
			long timeToExecute = stopTime-startTime;
			String time= String.valueOf(timeToExecute);
			String str = "Select query :"+ query+"||"+"Time to execute:"+time;
			logger.info(str);

		}catch (IOException ioException){
			try {
				fh1=new FileHandler("eventLog.log",true);
				eventLogger.addHandler(fh1);
				String exception = ioException.getMessage();
				eventLogger.info(exception);
			} catch (IOException Exception){

			}

		}


	}
}

// Select * from student ;