package presentationlayer;

import java.util.Scanner;

public class DisplayToGetUserChoice
{
    private Scanner objToGetValue = new Scanner(System.in);
    public int displayMessageGetNumberChoiceFromUser(String stringToBeDisplayed)
    {
        System.out.println("=================================================================");
        System.out.print(stringToBeDisplayed);
        int userServiceCategoryChoice = objToGetValue.nextInt();
        System.out.println("=================================================================");
        return userServiceCategoryChoice;
    }

    public String displayMessageGetStringChoiceFromUser(String stringToBeDisplayed)
    {
        System.out.println("=================================================================");
        System.out.print(stringToBeDisplayed);
        String userStringChoice = objToGetValue.next();
        System.out.println("=================================================================");
        return userStringChoice;
    }
}