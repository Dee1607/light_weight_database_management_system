package controller;

import FormattedFileWriter.SQLFileWriter;
import login.LoginPage;
import presentationlayer.DisplayToGetUserChoice;
import queryProcessing.*;

import java.lang.invoke.SwitchPoint;
import java.util.List;
import java.util.Map;

public class ApplicationController {

    private Map<String,Map<String, Map<String, List<String>>>> MAP_OF_EXISTING_DATA = null;
    SQLFileWriter objWriter = null;
    DisplayToGetUserChoice objGetData = null;
    QuerySelection objSelectQuery = null;
    public ApplicationController(Map<String,Map<String, Map<String, List<String>>>> existingData)
    {
        this.MAP_OF_EXISTING_DATA = existingData;
        objWriter =new SQLFileWriter();
        objGetData = new DisplayToGetUserChoice();

    }

    public void initializeApplication()
    {
        LoginPage objLogin = new LoginPage();



        boolean loginStatus = objLogin.getCredentials();

        if(loginStatus) {

            boolean isUserReadyToExit = false;

            while(!isUserReadyToExit){
                System.out.println("1.Query");
                System.out.println("2.SQL Dumps");
                System.out.println("3.ERD");
                System.out.println("4.Exit");
                int userChoice = objGetData.displayMessageGetNumberChoiceFromUser("Select any from above: ");

                switch(userChoice) {
                    case 1:
                        objSelectQuery = new QuerySelection(MAP_OF_EXISTING_DATA);
                        objSelectQuery.selectQuery();
                        break;
                    case 2:
                        CreateQuery objCreateToGetDump = new CreateQuery();
                        objWriter.createDumpFiles(objCreateToGetDump.CREATE_DUMP_LIST);
                        break;
                    case 3:
                        break;
                    case 4:
                        isUserReadyToExit = true;
                        break;
                }
            }
        }
        else{
            return;
        }
    }
}