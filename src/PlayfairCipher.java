import java.util.Scanner;

public class PlayfairCipher {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter key: ");
        String key = input.nextLine().toUpperCase();

        //проверка за коректност на входните данни
        key = correctEntry(key);

        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] keyLetters = key.replaceAll("\\s+", "").toCharArray();
        char[][] table = new char[5][5];

        /* задаваме като начална стойност на използваните букви една буква,
        за да поберем 26 цифрената азбука в 25 полета */
        String usedLetters = skippedLetter(key);

        byte currentLetter = 0;
        byte alphabetLetter = 0;

        //разпределяне на ключа в таблицата и допълване с останалите букви от азбуката
        for (int i = 0; i < 5; i++) {
            int j = 0;
            while (j < 5) {
                if (currentLetter < keyLetters.length) {
                    if (isNewLetter(keyLetters[currentLetter], usedLetters)) {
                        table[i][j] = keyLetters[currentLetter];
                        usedLetters += table[i][j];
                        j++;
                    }
                } else {
                    if (isNewLetter(alphabet[alphabetLetter], usedLetters)) {
                        table[i][j] = alphabet[alphabetLetter];
                        usedLetters += table[i][j];
                        j++;
                    }
                    alphabetLetter++;
                }
                currentLetter++;
            }
        }

        printTable(table);

        System.out.print("Enter the message to encrypt: ");
        String messageText = input.nextLine().toUpperCase();

        //проверка за коректност на входните данни
        messageText = correctEntry(messageText);

        messageText = insertX(messageText.replaceAll("\\s+", ""));

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

    public static String correctEntry(String message) {
        while (!message.matches("^[ A-Za-z]+$") || message.trim().length() <= 0) {
            System.out.println("Incorrect entry! Please use only letters.");
            message = new Scanner(System.in).nextLine().toUpperCase();
        }
        return message;
    }

    public static boolean isNewLetter(char letter, String usedLetters) {
        return !usedLetters.contains(Character.toString(letter));
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

    public static void printTable(char[][] table) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
    }

}

