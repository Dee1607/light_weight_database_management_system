package FormattedFileReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLFileReader {

    public Map<String,Map<String, Map<String, List<String>>>> readFileToGetSQL(){

        Map<String,Map<String, Map<String, List<String>>>> objDatabaseData = new HashMap<>();
        Map<String, Map<String, List<String>>> objTable = new HashMap<>();
        Map<String, List<String>> objColumn = new HashMap<>();
        List<String> values = new ArrayList<>();
        String currentDB = null;
        String currentTable = null;

        try{
            String fileName = "/Users/deeppatel/Desktop/GlobalDataDictionary/SQL_Data.txt";
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String allLinesData = "";
            while((line = br.readLine()) != null){
                allLinesData += line + " ";
            }
            System.out.println(allLinesData);

            String[] arrayOfSplitData = allLinesData.split(" ");



            for(int i=0; i<arrayOfSplitData.length; i++){
                if(arrayOfSplitData[i].equalsIgnoreCase("database")){
                    objDatabaseData.put(arrayOfSplitData[i+2], null);
                    currentDB = arrayOfSplitData[i+2];
                    i+=3;
                }
                else if(arrayOfSplitData[i].equalsIgnoreCase("table")){
                    objTable.put(arrayOfSplitData[i+2], null);
                    currentTable = arrayOfSplitData[i+2];
                    i+=3;
                }
                else if(arrayOfSplitData[i].equalsIgnoreCase("tStructStart")){
                    int j=i+1;
                    while(!arrayOfSplitData[j].equalsIgnoreCase("tStructEnd")){
                        if(arrayOfSplitData[j].equalsIgnoreCase("col")){
                            List<String> tempList = new ArrayList<>();
                            tempList.add(arrayOfSplitData[j+3]);
                            if(arrayOfSplitData[j+4].equalsIgnoreCase("|")){
                                tempList.add(arrayOfSplitData[j+5]);
                            }
                            else{
                                tempList.add(null);
                            }
                            objColumn.put(arrayOfSplitData[j+2], tempList);
                        }
                        j+=1;
                    }
                    objTable.replace(currentTable, objColumn);
                    i=j;
                }
                else if(arrayOfSplitData[i].equalsIgnoreCase("rowStart")){
                    int j=i+1;
                    //int index = 2;
                    while(!arrayOfSplitData[j].equalsIgnoreCase("rowEnd")){
                        if(!arrayOfSplitData[j].equalsIgnoreCase("|")) {
                            Map<String, List<String>> tempTable = objTable.get(currentTable);
                            String currentCol = arrayOfSplitData[j];
                            List<String> tempList = tempTable.get(arrayOfSplitData[j]);
                            String data = "";

                            j = j + 2;
                            while (!arrayOfSplitData[j].equalsIgnoreCase("|")) {
                                data += arrayOfSplitData[j];
                                j++;
                            }
                            tempList.add(data);
                            tempTable.replace(currentCol, tempList);
                            objTable.replace(currentTable, tempTable);
                            //index += 1;
                        }
                        j += 1;
                    }
                    //objTable.replace(currentTable, objColumn);
                    i=j;
                }
                else if(arrayOfSplitData[i].equalsIgnoreCase("tEnd")){
                    objDatabaseData.replace(currentDB, objTable);
                    currentTable = null;
                    objColumn = new HashMap<>();
                }
                else if(arrayOfSplitData[i].equalsIgnoreCase("dbEnd")){
                    currentDB = null;
                    objTable = new HashMap<>();
                }
            }

        System.out.println("Done");

        } catch(Exception e) {
            e.printStackTrace();
        }
        return objDatabaseData;
    }
}