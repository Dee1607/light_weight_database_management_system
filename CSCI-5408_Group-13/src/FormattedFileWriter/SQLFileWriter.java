package FormattedFileWriter;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SQLFileWriter {

    public void writeIntoSQLFile(Map<String, Map<String, Map<String, List<String>>>> mapDatabaseData) {
        String formattedString = "";

        for (String databaseName : mapDatabaseData.keySet()) {

            formattedString += "database : " + databaseName;
            formattedString += "\n" + "dbStart";

            for (String tableName : mapDatabaseData.get(databaseName).keySet()) {
                formattedString += "\ntable : " + tableName;
                formattedString += "\n" + "tStart";
                formattedString += "\n" + "tStructStart";

                Set<String> tempSet = mapDatabaseData.get(databaseName).get(tableName).keySet();

                for (String columnName : mapDatabaseData.get(databaseName).get(tableName).keySet()) {
                    formattedString += "\n" + "col : " + columnName + " " + mapDatabaseData.get(databaseName).get(tableName).get(columnName).get(0);

                    if (mapDatabaseData.get(databaseName).get(tableName).get(columnName).get(1) != null) {
                        formattedString += " | " + "pk";
                    }
                }
                formattedString += "\n" + "tStructEnd";
                for (String columnName : mapDatabaseData.get(databaseName).get(tableName).keySet()) {
                    for (int i = 2; i < mapDatabaseData.get(databaseName).get(tableName).get(columnName).size(); i++) {
                        formattedString += "\n" + "rowStart";
                        for (String str : tempSet) {
                            formattedString += "\n" + str + " : " + mapDatabaseData.get(databaseName).get(tableName).get(str).get(i) + " | ";
                        }
                        formattedString += "\n" + "rowEnd";
                    }
                    break;
                }
                formattedString += "\n" + "tEnd";
            }
            formattedString += "\n" + "dbEnd" + "\n";
        }
        try{
            Path path = Path.of("databaseData.txt");
            if(!Files.exists(path)){
                Files.createFile(path);
            }
            Files.write(path,formattedString.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.TRUNCATE_EXISTING);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void createDumpFiles(List<String> listOfDumps){
        try{
            Path path = Path.of("sqlDumpStored.sql");
            if(!Files.exists(path)){
                Files.createFile(path);
            }
            for(String query : listOfDumps){
                Files.write(path,(query+"\n").getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void createERDFiles(Map<String,List<String>> foreignKeyData){

        String formattedString = "";

        try{
            Path path = Path.of("ERD.txt");
            if(!Files.exists(path)){
                Files.createFile(path);
            }
            for(String fkName : foreignKeyData.keySet()){
                List<String> tempListOfForeignKey = foreignKeyData.get(fkName);
                formattedString += fkName + ":\n";
                formattedString +=  tempListOfForeignKey.get(0) + "(" + tempListOfForeignKey.get(1) + ")" + "-->" + tempListOfForeignKey.get(2) + "(" + tempListOfForeignKey.get(3) + ")\n";
            }
            Files.write(path,(formattedString+"\n").getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}