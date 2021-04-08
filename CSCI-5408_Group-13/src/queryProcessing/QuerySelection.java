package queryProcessing;

import FormattedFileWriter.SQLFileWriter;

import java.util.List;
import java.util.Map;

public class QuerySelection {
    SQLFileWriter objWriter = null;
    Map<String,Map<String, Map<String, List<String>>>> MAP_OF_EXISTING_DATA = null;

    public QuerySelection(Map<String,Map<String, Map<String, List<String>>>> mapExistingData){
        this.MAP_OF_EXISTING_DATA = mapExistingData;
    }
    public void selectQuery(Map<Boolean, List<String>> loginStatus){

        GetQuery objQuery = new GetQuery();
        InsertQuery objInsertQuery = new InsertQuery();
        SelectQuery objSelectQuery = new SelectQuery();
        DeleteQuery objDeleteQuery = new DeleteQuery();
        CreateQuery objCreateQuery = new CreateQuery();
        UpdateQuery objUpdateQuery = new UpdateQuery();
        UseQuery objUseQuery = new UseQuery();
        DropQuery objDropQuery = new DropQuery();
        objWriter = new SQLFileWriter();

        String queryToImplement = objQuery.getQueryFromUser();

        String[] arrOfQueryElements = queryToImplement.split(" ");
        String typeOfQuery = arrOfQueryElements[0].toLowerCase();

        switch(typeOfQuery) {
            case "use":
                objUseQuery.useQueryOperations(queryToImplement);
                break;
            case "create":
                Map<String,Map<String, Map<String, List<String>>>> objDatabaseData = objCreateQuery.createQueryOperations(queryToImplement, loginStatus);
                if(objDatabaseData!=null){
                    objWriter.writeIntoSQLFile(objDatabaseData);
                }
                break;
            case "insert":
                MAP_OF_EXISTING_DATA = objInsertQuery.insertQueryOperations(queryToImplement,MAP_OF_EXISTING_DATA,loginStatus);
                break;
            case "update":
                MAP_OF_EXISTING_DATA = objUpdateQuery.updateQueryOperations(queryToImplement,MAP_OF_EXISTING_DATA,loginStatus);
                break;
            case "select":
                objSelectQuery.selectQueryOperations(queryToImplement, MAP_OF_EXISTING_DATA,loginStatus);
                break;
            case "delete":
                MAP_OF_EXISTING_DATA = objDeleteQuery.deleteQueryOperations(queryToImplement,MAP_OF_EXISTING_DATA,loginStatus);
                break;
            case "drop":
                MAP_OF_EXISTING_DATA = objDropQuery.dropQueryOperations(queryToImplement,MAP_OF_EXISTING_DATA,loginStatus);
                break;
        }
    }
}