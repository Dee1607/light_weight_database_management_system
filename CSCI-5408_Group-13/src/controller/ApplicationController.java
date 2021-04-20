package controller;

import ERD.ERDGenerator;
import FormattedFileWriter.SQLFileWriter;
import SQLDump.GenerateSQLDump;
import login.LoginPage;
import presentationlayer.DisplayToGetUserChoice;
import queryProcessing.*;
import signup.UserSignUp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationController
{
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
        System.out.println("What operation would you like to perform:");
        System.out.println("1.Login");
        System.out.println("2.Register");
        int userLoginChoice = objGetData.displayMessageGetNumberChoiceFromUser("Select any from above: ");
        Map<Boolean, List<String>> loginStatus = new HashMap<>();
        LoginPage objLogin = new LoginPage();
        UserSignUp objSignUp = new UserSignUp();

        switch(userLoginChoice){
            case 1:
                loginStatus = objLogin.getCredentials();
                break;
            case 2:
                objSignUp.registerUser(MAP_OF_EXISTING_DATA);
                loginStatus = objLogin.getCredentials();
                break;
            default:
                break;
        }

        if(null != loginStatus) {
            boolean isUserReadyToExit = false;

            while(!isUserReadyToExit){
                System.out.println("What operation would you like to perform:");
                System.out.println("1.Query");
                System.out.println("2.SQL Dumps");
                System.out.println("3.ERD");
                System.out.println("4.Exit");
                int userChoice = objGetData.displayMessageGetNumberChoiceFromUser("Select any from above: ");

                switch(userChoice) {
                    case 1:
                        objSelectQuery = new QuerySelection(MAP_OF_EXISTING_DATA);
                        objSelectQuery.selectQuery(loginStatus);
                        break;
                    case 2:
                        GenerateSQLDump objGenerateDumpFile = new GenerateSQLDump();
                        objGenerateDumpFile.fetchSQLDumpData();
                        break;
                    case 3:
                        ERDGenerator objGenerateERD = new ERDGenerator();
                        objGenerateERD.generateERD();
                        break;
                    case 4:
                        objWriter.writeTables(MAP_OF_EXISTING_DATA);
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