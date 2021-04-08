package EncryptionAlgorithm;

public class EncryptData {
    public String encryptPassword(String plainText){
        String cipherText = "";
        char[][] playFairMatrix = new char[5][5];

        GeneratePlayfairCipher objGenerateMatrix = null;
        playFairMatrix = objGenerateMatrix.createMatrix("SECRET");

        objGenerateMatrix = new GeneratePlayfairCipher("SECRET");
        playFairMatrix = objGenerateMatrix.createMatrix("SECRET");

        String encyptedCipherText = objGenerateMatrix.getCipherText(playFairMatrix, plainText);
        System.out.println("\nYour Cipher Text is: " + encyptedCipherText);

        return cipherText;
    }
}