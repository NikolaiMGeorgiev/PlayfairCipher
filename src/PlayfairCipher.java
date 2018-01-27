import java.util.Scanner;

public class PlayfairCipher {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter key: ");
        String key = input.nextLine().toUpperCase();
        char[][] table = new char[5][5];

        //проверка за коректност на входните данни
        key = correctEntry(key);

        System.out.print("Enter the message to encode: ");
        String messageText = input.nextLine().toUpperCase();

        //проверка за коректност на входните данни
        messageText = correctEntry(messageText);

        messageText = insertX(messageText);

        /* задаваме като начална стойност на използваните букви една буква,
        за да поберем 26 цифрената азбука в 25 полета */
        String usedLetters = skippedLetter(key);

        //заменя се излишната буква от съобщението
        messageText = messageText.replace(usedLetters, replaceLetter(usedLetters));

        String letters = key + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        byte currentLetter = 0;
        int column;

        //запълване на таблицата
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

        for (int i = 0; i < messageText.length(); i += 2) {
            System.out.print(messageText.charAt(i) + "" + messageText.charAt(i + 1) + " ");
        }
        System.out.println();

        encodeMessage(messageText, table);
    }


    public static String correctEntry(String message) {
        while (!message.matches("^[ A-Za-z]+$") || message.trim().length() <= 0) {
            System.out.println("Incorrect entry! Please use only letters.");
            message = new Scanner(System.in).nextLine().toUpperCase();
        }
        return message.replaceAll("\\s+", "");
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

    public static String replaceLetter(String omittedLetter) {
        switch (omittedLetter) {
            case "J":
                return "I";
            case "I":
                return "J";
            default:
                return "X";
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
        System.out.println();
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

    public static void encodeMessage(String message, char[][] table) {
        int letter1Row;
        int letter2Row;
        int letter1Col;
        int letter2Col;
        int position;
        StringBuilder encodedMessage = new StringBuilder();

        for (int i = 0; i < message.length(); i += 2) {
            position = getPositionInTable(message.charAt(i), table);
            letter1Row = position / 10;
            letter1Col = position % 10;

            position = getPositionInTable(message.charAt(i + 1), table);
            letter2Row = position / 10;
            letter2Col = position % 10;

            encodedMessage.append(getNewLetters(letter1Row, letter2Row,
                    letter1Col, letter2Col, table)).append(" ");
        }

        System.out.println(encodedMessage.toString());
        System.out.println("Playfair cipher: "
                + encodedMessage.toString().replaceAll("\\s+", ""));
    }

    public static int getPositionInTable(char letter, char[][] table) {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                if (table[i][j] == letter) {
                    return i * 10 + j;
                }
            }
        }
        return 0;
    }

    public static String getNewLetters(int rowLetter1, int rowLetter2,
                                       int colLetter1, int colLetter2, char[][] table) {
        if (rowLetter1 != rowLetter2) {
            if (colLetter1 != colLetter2) {
                return table[rowLetter1][colLetter2] + "" + table[rowLetter2][colLetter1];
            } else {
                return getSameColumnLetters(rowLetter1, colLetter1, table)
                        + getSameColumnLetters(rowLetter2, colLetter2, table);
            }
        } else {
            return getSameRowLetters(rowLetter1, colLetter1, table)
                    + getSameRowLetters(rowLetter2, colLetter2, table);
        }
    }

    public static String getSameColumnLetters(int rowLetter, int colLetter, char[][] table) {
        if (rowLetter == 4 && colLetter == 4) {
            return table[0][0] + "";
        } else if (rowLetter + 1 > 4) {
            return table[0][colLetter + 1] + "";
        } else {
            return table[rowLetter + 1][colLetter] + "";
        }
    }

    public static String getSameRowLetters(int rowLetter, int colLetter, char[][] table) {
        if (rowLetter == 4 && colLetter == 4) {
            return table[0][0] + "";
        } else if (colLetter + 1 > 4) {
            return table[rowLetter + 1][0] + "";
        } else {
            return table[rowLetter][colLetter + 1] + "";
        }
    }
}