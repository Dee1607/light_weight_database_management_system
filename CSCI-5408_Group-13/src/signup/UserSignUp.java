package signup;

import EncryptionAlgorithm.EncryptData;
import controller.ApplicationController;
import presentationlayer.DisplayToGetUserChoice;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserSignUp {

    private String USER_CREDENTIALS_FILE_PATH = "users.txt";
    Map<String,String> MAP_OF_CREDENTIALS = null;

    public void registerUser(Map<String,Map<String, Map<String, List<String>>>> existingData) {
        DisplayToGetUserChoice objGetData = new DisplayToGetUserChoice();
        try {
            MAP_OF_CREDENTIALS = new HashMap<>();
            boolean isCredentialsCorrect = false;

            File file = new File(USER_CREDENTIALS_FILE_PATH);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] tempListOfUsers = line.split(":");
                MAP_OF_CREDENTIALS.put(tempListOfUsers[0], tempListOfUsers[1]);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        objGetData.displayMessage("######## Signup Page ########");
        String username = objGetData.displayMessageGetStringChoiceFromUser("Enter Username: ");
        String password = objGetData.displayMessageGetStringChoiceFromUser("Enter Password: ");

        if(checkUser(username,password,MAP_OF_CREDENTIALS)){
            System.out.println("User Already Exist !!");
            ApplicationController objController = new ApplicationController(existingData);
            objController.initializeApplication();
        }
        else
        {
            String formattedString = "";
            try{
                Path path = Path.of("users.txt");
                if (!Files.exists(path)) {
                    Files.createFile(path);
                }

                EncryptData objEncrypt = new EncryptData();
                String cipherText = objEncrypt.encryptPassword(password);

                formattedString += username + ":" + cipherText + "\n";

                Files.write(path,(formattedString).getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                objGetData.displayMessage("Signup Successful !!");

            }catch(Exception e){
                System.out.println("Signup failed !!");
            }
        }
    }

    public boolean checkUser(String username, String password, Map<String,String> mapCheckingCredentials) {
        if(mapCheckingCredentials.containsKey(username)){
            if(mapCheckingCredentials.get(username).equals(password)){
                return true;
            }
        }
        return false;
    }
}