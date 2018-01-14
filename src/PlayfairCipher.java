import java.util.Scanner;

public class PlayfairCipher {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter key: ");
        String key = input.nextLine().toUpperCase();
        char[][] table = new char[5][5];

        //проверка за коректност на входните данни
        key = correctEntry(key.replaceAll("\\s+", ""));

        /* задаваме като начална стойност на използваните букви една буква,
        за да поберем 26 цифрената азбука в 25 полета */
        String usedLetters = skippedLetter(key);

        String letters = key + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        byte currentLetter = 0;
        int column;

        for (int row = 0; row < 5; row++) {
            column = 0;
            while (column < 5) {
                if (isNewLetter(letters.charAt(currentLetter), usedLetters)) {
                    table[row][column] = letters.charAt(currentLetter);
                    usedLetters += table[row][column];
                    column++;
                }
                currentLetter++;
            }
        }

        printTable(table);

        System.out.print("Enter the message to encrypt: ");
        String messageText = input.nextLine().toUpperCase();

        //проверка за коректност на входните данни
        messageText = correctEntry(messageText.replaceAll("\\s+", ""));

        messageText = insertX(messageText);

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //!!!!probvai pri kodiraneto da polzvash naparavo messageText vmesto diagram!!!!

        int diagramLength = messageText.length() / 2;
        char[][] diagram = new char[diagramLength][2];
        int letter = 0;
        for (int i = 0; i < diagram.length; i++) {
            for (int j = 0; j < 2; j++) {
                diagram[i][j] = messageText.charAt(letter);
                System.out.print(diagram[i][j] + " ");
                letter++;
            }
            System.out.println();
        }
        encodeMessage(diagram, table);
    }

    public static String encodeMessage(char[][] diagram, char[][] table) {
        StringBuilder encriptedMessage = new StringBuilder();
        for (int i = 0; i < diagram.length; i++) {
            encriptedMessage.append(encodeGroup(diagram[i][0], diagram[i][1], table));
        }
        return encriptedMessage.toString();
    }

    public static String encodeGroup(char firstLetter, char secondLetter, char[][] table) {
        int[][] indexRow = new int[1][2];
        int[][] indexCol = new int[1][2];
        boolean isFirstFound = false;
        boolean isSecondFound = false;
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                if (table[i][j] == firstLetter) {
                    indexRow[0][0] = i;
                    indexCol[0][0] = j;
                    isFirstFound = true;
                } else if (table[i][j] == secondLetter) {
                    indexRow[0][1] = i;
                    indexCol[0][1] = j;
                    isSecondFound = true;
                }
            }
            if (isFirstFound && isSecondFound) {
                break;
            }
        }
        //!!!!razdeli na dva metoda!!!!!
        if (indexRow[0][0]!=indexRow[0][1]){
            if(indexCol[0][0]!=indexCol[0][1]){

            }
        }
    }

    public static String correctEntry(String message) {
        while (!message.matches("^[ A-Za-z]+$") || message.trim().length() <= 0) {
            System.out.println("Incorrect entry! Please use only letters.");
            message = new Scanner(System.in).nextLine().toUpperCase();
        }
        return message;
    }

    public static String skippedLetter(String key) {
        if (!key.contains("J")) {
            return "J";
        } else if (!key.contains("I")) {
            return "I";
        } else {
            return "Q";
        }
    }

    public static boolean isNewLetter(char letter, String usedLetters) {
        return !usedLetters.contains(Character.toString(letter));
    }

    public static void printTable(char[][] table) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static String insertX(String message) {
        StringBuilder text = new StringBuilder(message);
        for (int i = 1; i < text.length(); i += 2) {
            if (text.charAt(i) == text.charAt(i - 1)) {
                text.insert(i, "X");
            }
        }
        if (text.length() % 2 != 0) {
            text.append("X");
        }
        return text.toString();
    }
}

