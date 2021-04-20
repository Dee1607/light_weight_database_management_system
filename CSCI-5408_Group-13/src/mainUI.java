import FormattedFileReader.SQLFileReader;
import controller.ApplicationController;
import java.util.List;
import java.util.Map;

public class mainUI
{
	public static void main(String[] args)
	{
		SQLFileReader objReader = new SQLFileReader();
		Map<String,Map<String, Map<String, List<String>>>> existingData = objReader.readFileToGetSQL();

		ApplicationController objAppController = new ApplicationController(existingData);
		objAppController.initializeApplication();
	}
}