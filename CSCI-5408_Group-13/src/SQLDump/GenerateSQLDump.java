package SQLDump;

import FormattedFileWriter.SQLFileWriter;
import queryProcessing.CreateQuery;

import java.io.FileWriter;
import java.util.List;

public class GenerateSQLDump {

    public void fetchSQLDumpData(){
        CreateQuery objCreateQuery = new CreateQuery();
        List<String> objDumpData = objCreateQuery.CREATE_DUMP_LIST;

        SQLFileWriter objFileWriter = new SQLFileWriter();
        objFileWriter.createDumpFiles(objDumpData);
    }
}
