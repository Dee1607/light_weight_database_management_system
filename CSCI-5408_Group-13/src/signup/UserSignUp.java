package signup;

import EncryptionAlgorithm.EncryptData;
import presentationlayer.DisplayToGetUserChoice;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class UserSignUp {
    public void registerUser() {

        DisplayToGetUserChoice objGetData = new DisplayToGetUserChoice();


        objGetData.displayMessage("######## Signup Page ########");
        String username = objGetData.displayMessageGetStringChoiceFromUser("Enter Username: ");
        String password = objGetData.displayMessageGetStringChoiceFromUser("Enter Password: ");

        String formattedString = "";
        try{
            Path path = Path.of("users.txt");
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            formattedString += username + ":" + password;

            Files.write(path,(formattedString+"\n").getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            objGetData.displayMessage("Signup Successful !!");

        }catch(Exception e){
            System.out.println("Signup failed !!");
        }

    }
}
