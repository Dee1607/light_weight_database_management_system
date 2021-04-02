import login.LoginPage;
import queryProcessing.GetQuery;
import queryProcessing.InsertQuery;
import queryProcessing.SelectQuery;
import queryProcessing.UpdateQuery;
import queryProcessing.AlterQuery;
import queryProcessing.CreateQuery;
import queryProcessing.DeleteQuery;


public class mainUI {
	public static void main(String[] args) {
		
		LoginPage objLogin = new LoginPage();
		
		GetQuery objQuery = new GetQuery();
		InsertQuery objInserteQuery = new InsertQuery();
		SelectQuery objSelectQuery = new SelectQuery();
		DeleteQuery objDeleteQuery = new DeleteQuery();
		CreateQuery objCreateQuery = new CreateQuery();
		UpdateQuery objUpdateQuery = new UpdateQuery();
		AlterQuery objAlterQuery = new AlterQuery();


		boolean loginStatus = objLogin.getCredentials();
		
		if(loginStatus) {
			String queryToImplement = objQuery.getQueryFromUser();
			
			String[] arrOfQueryElements = queryToImplement.split(" ");
			String typeOfQuery = arrOfQueryElements[0].toLowerCase();
			
			switch(typeOfQuery) {
			case "create":
				objCreateQuery.createQueryOperations(queryToImplement);
				break;
			case "insert":
				objInserteQuery.insertQueryOperations(queryToImplement);
				break;
			case "update":
				objUpdateQuery.updateQueryOperations(queryToImplement);
				break;
			case "alter":
				objAlterQuery.alterQueryOperations(queryToImplement);
				break;
			case "select":
				objSelectQuery.selectQueryOperations(queryToImplement);
				break;
			case "delete":
				objDeleteQuery.deleteQueryOperations(queryToImplement);
				break;
			case "drop":
				objCreateQuery.createQueryOperations(queryToImplement);
				break;
			}
		}
	}
}