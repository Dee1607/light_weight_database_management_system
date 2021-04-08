package ERD;

import FormattedFileWriter.SQLFileWriter;
import queryProcessing.CreateQuery;

import java.util.List;
import java.util.Map;

public class ERDGenerator {
    public void generateERD(){
        CreateQuery objCreateQuery = new CreateQuery();
        Map<String, List<String>> objForeignKeyData = objCreateQuery.FOREIGN_KEY_LIST;

        SQLFileWriter objWriter = new SQLFileWriter();
        objWriter.createERDFiles(objForeignKeyData);

    }
}