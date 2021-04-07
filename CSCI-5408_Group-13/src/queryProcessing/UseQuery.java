package queryProcessing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UseQuery {

    private Map<String,Map<String, Map<String, List<String>>>> objDatabase = new HashMap<>();
    private Map<String, Map<String, List<String>>> objTable = new HashMap<>();
    private Map<String, List<String>> objColumn = new HashMap<>();
    private List<String> objValues = new ArrayList<>();

    public void useQueryOperations(String query) {

        System.out.println("use");

        String[] splitQueryForProcessing = query.split(" ");


    }
}
