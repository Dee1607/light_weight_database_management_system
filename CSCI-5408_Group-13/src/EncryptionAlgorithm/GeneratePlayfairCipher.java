package EncryptionAlgorithm;

import java.util.LinkedHashSet;
import java.util.Set;

public class GeneratePlayfairCipher {

	private String KEY = "";
	public static final String allAlphabates = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private char[][] MATRIX = new char[5][5];

	public GeneratePlayfairCipher(String key) {
		this.KEY = key;
	}

	/*
	 * method() - Creates matrix from a key
	 * 
	 * parameters - String key
	 * 
	 * returns 5x5 matrix
	 */
	public char[][] createMatrix(String key) {

		int count = 0;

		String keyWithoutRedundency = removeRedundentChar(key);
		String keyValueWithJ = removeRedundentChar(keyWithoutRedundency.concat(allAlphabates));

		StringBuilder keyValueWithoutJ = new StringBuilder();
		for (int check = 0; check < keyValueWithJ.length(); check++) {
			if (keyValueWithJ.charAt(check) == 'J') {
				keyValueWithoutJ = new StringBuilder(keyValueWithJ);
				keyValueWithoutJ.setCharAt(check, 'I');
			}
		}

		String keyWithoutRedundentData = removeRedundentChar(keyValueWithoutJ.toString());
		for (int i = 0; i < MATRIX.length; i++) {
			for (int j = 0; j < MATRIX[i].length; j++) {

				MATRIX[i][j] = keyWithoutRedundentData.charAt(count);
				count++;
			}
		}
		return MATRIX;
	}

	/*
	 * method() - get the cipher text
	 * 
	 * parameters - key matrix and plain text
	 * 
	 * returns cipher text
	 */
	public String getCipherText(char[][] matrix, String plainText) {

		String plainTextDevided = "";

		for (int i = 0; i < plainText.length(); i++) {
			for (int j = i + 1; j < plainText.length(); j++) {
				if (plainText.charAt(i) != plainText.charAt(j)) {
					plainTextDevided += String.valueOf(plainText.charAt(i)).concat(String.valueOf(plainText.charAt(j)));
					i = j + 1;
					j = i;
				} else {
					plainTextDevided += String.valueOf(plainText.charAt(i)).concat(String.valueOf('X'));
					i = i + 1;
				}
				if (i == (plainText.length() - 1)) {

					if (plainText.charAt(i) == 'Z') {
						plainTextDevided += String.valueOf(plainText.charAt(i)).concat(String.valueOf('X'));
					} else {
						plainTextDevided += String.valueOf(plainText.charAt(i)).concat(String.valueOf('Z'));
					}
					i = i + 1;
				}
			}
		}
		String[] plainTextArray = plainTextDevided.split("(?<=\\G.{2})");
		String cipherText = beginEncryption(plainTextArray);

		return cipherText;
	}

	/*
	 * method() - helper method to do encryption
	 * 
	 * parameters - array of plain text data
	 * 
	 * returns - cipher Text
	 */
	private String beginEncryption(String[] plainTextArray) {
		String encryptedMessage = new String();

		char firstLetter;
		char secondLetter;
		int firstTwoCharacters[] = new int[2];
		int secondTwoCharacters[] = new int[2];

		for (int i = 0; i < plainTextArray.length; i++) {
			firstLetter = plainTextArray[i].charAt(0);
			secondLetter = plainTextArray[i].charAt(1);
			firstTwoCharacters = GetMatrixIndex(firstLetter);
			secondTwoCharacters = GetMatrixIndex(secondLetter);
			if (firstTwoCharacters[0] == secondTwoCharacters[0]) {
				if (firstTwoCharacters[1] < 4) {
					firstTwoCharacters[1]++;
				} else {
					firstTwoCharacters[1] = 0;
				}
				if (secondTwoCharacters[1] < 4) {
					secondTwoCharacters[1]++;
				} else {
					secondTwoCharacters[1] = 0;
				}
			} else if (firstTwoCharacters[1] == secondTwoCharacters[1]) {
				if (firstTwoCharacters[0] < 4) {
					firstTwoCharacters[0]++;
				} else {
					firstTwoCharacters[0] = 0;
				}
				if (secondTwoCharacters[0] < 4) {
					secondTwoCharacters[0]++;
				} else {
					secondTwoCharacters[0] = 0;
				}
			} else {
				int temp = firstTwoCharacters[1];
				firstTwoCharacters[1] = secondTwoCharacters[1];
				secondTwoCharacters[1] = temp;
			}
			encryptedMessage = encryptedMessage + MATRIX[firstTwoCharacters[0]][firstTwoCharacters[1]]
					+ MATRIX[secondTwoCharacters[0]][secondTwoCharacters[1]];
		}
		return encryptedMessage;
	}

	/*
	 * method() - get a plain text from cipher text
	 * 
	 * parameters - cipher text to be converted
	 * 
	 * returns - plain text
	 */
	public String getPlainText(String cipherText) {

		String plainText = new String();

		String[] cipherTextToArray = cipherText.split("(?<=\\G.{2})");

		char firstLetter;
		char secondLetter;
		int firstTwoCharacters[] = new int[2];
		int secondTwoCharacters[] = new int[2];

		for (int i = 0; i < cipherTextToArray.length; i++) {
			firstLetter = cipherTextToArray[i].charAt(0);
			secondLetter = cipherTextToArray[i].charAt(1);
			firstTwoCharacters = GetMatrixIndex(firstLetter);
			secondTwoCharacters = GetMatrixIndex(secondLetter);

			if (firstTwoCharacters[0] == secondTwoCharacters[0]) {
				if (firstTwoCharacters[1] > 0) {
					firstTwoCharacters[1]--;
				} else {
					firstTwoCharacters[1] = 4;
				}
				if (secondTwoCharacters[1] > 0) {
					secondTwoCharacters[1]--;
				} else {
					secondTwoCharacters[1] = 4;
				}
			} else if (firstTwoCharacters[1] == secondTwoCharacters[1]) {
				if (firstTwoCharacters[0] > 0) {
					firstTwoCharacters[0]--;
				} else {
					firstTwoCharacters[0] = 4;
				}
				if (secondTwoCharacters[0] > 0) {
					secondTwoCharacters[0]--;

				} else {
					secondTwoCharacters[0] = 4;
				}
			} else {
				int temp = firstTwoCharacters[1];
				firstTwoCharacters[1] = secondTwoCharacters[1];
				secondTwoCharacters[1] = temp;
			}

			plainText = plainText + MATRIX[firstTwoCharacters[0]][firstTwoCharacters[1]]
					+ MATRIX[secondTwoCharacters[0]][secondTwoCharacters[1]];
		}
		return plainText;
	}

	/*
	 * method() - get index of particular character on Matrix
	 * 
	 * parameters - character to find index
	 * 
	 * returns - index of character on matrix
	 */
	private int[] GetMatrixIndex(char letter) {
		int[] index = new int[2];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (MATRIX[i][j] == letter) {
					index[0] = i;
					index[1] = j;
					break;
				}
			}
		}
		return index;
	}

	/*
	 * method() - removes redundant data
	 * 
	 * parameters - key with redundant data
	 * 
	 * returns - removed redundancies from data
	 */
	private String removeRedundentChar(String keyWithRedundentData) {
		char[] chars = keyWithRedundentData.toCharArray();
		Set<Character> charSet = new LinkedHashSet<Character>();
		for (char c : chars) {
			charSet.add(c);
		}

		StringBuilder sb = new StringBuilder();
		for (Character character : charSet) {
			sb.append(character);
		}

		String str = sb.toString();
		return str;
	}

}