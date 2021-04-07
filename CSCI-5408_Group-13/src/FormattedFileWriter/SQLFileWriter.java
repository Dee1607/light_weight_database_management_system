package FormattedFileWriter;

import java.util.List;
import java.util.Map;

public class SQLFileWriter {

    public void writeIntoSQLFile(Map<String,Map<String, Map<String, List<String>>>> mapDatabaseData){
        String formattedString = "";

        for(String databaseName : mapDatabaseData.keySet()){

            formattedString += "database : " + databaseName;
            formattedString += "\n" + "dbStart";

            for(String tableName : mapDatabaseData.get(databaseName).keySet()){
                formattedString += "table : " + tableName;
                formattedString += "\n" + "tStart";
                formattedString += "\n" + "tStructStart";

                for(String columnName : mapDatabaseData.get(databaseName).get(tableName).keySet()){

                    formattedString += "\n"+"col : " + columnName + " " + mapDatabaseData.get(databaseName).get(tableName).get(columnName).get(0);

                    if(mapDatabaseData.get(databaseName).get(tableName).get(columnName).get(1) != null){
                        formattedString += " | " + "pk";
                    }
                    if(mapDatabaseData.get(databaseName).get(tableName).get(columnName).get(2) != null){
                        formattedString += "\nrowStart";
                        for(String column : mapDatabaseData.get(databaseName).get(tableName).keySet()){

                        }

                    }
                }
                formattedString += "\n" + "tStructEnd";



                formattedString += "\n" + "tEnd";
            }
            formattedString += "\n" + "dbEnd";
        }
        System.out.println(formattedString);
    }
}