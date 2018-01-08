import java.util.Scanner;

public class PlayfairCipher {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter key: ");
        String key = input.nextLine().toUpperCase();

        //проверка за коректност на входните данни
        while (!isCorrectEntry(key)) {
            System.out.println("Incorrect entry! Please use only letters.");
            key = new Scanner(System.in).nextLine().toUpperCase();
        }

        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] keyLetters = key.replaceAll("\\s+", "").toCharArray();
        char[][] table = new char[5][5];

        /* задаваме като начална стойност на използваните букви една буква,
        за да поберем 26 цифрената азбука в 25 полета */
        String usedLetters = skippedLetter(key);
        System.out.println("usedLetters=" + usedLetters);

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

        while (!isCorrectEntry(messageText)) {
            System.out.println("Incorrect entry! Please use only letters.");
            messageText = new Scanner(System.in).nextLine().toUpperCase();
        }

        char[] message = messageText.replaceAll("\\s+", "").toCharArray();
        int counter = 0;
        int[] xIndexes = new int[message.length];

        /* намеране на броя на двойките, съставени от 2 еднакви букви,
        и запаметяване на техните позиции в отделен масив xIndexes */
        for (int i = 1; i < message.length; i += 2) {
            if (message[i] == message[i - 1]) {
                xIndexes[counter] = i;
                counter++;
                i--;
            }
        }

        //разделяне на съобщението на групи от по 2 букви
        int diagramLength = message.length / 2 + counter;
        char[][] diagram = new char[diagramLength][2];
        int index = 0;
        int group = 0;
        for (int i = 1; i < message.length; i += 2) {
            //разделяне на двойка еднакви букви ог една група на 2 отделни групи чрез Х
            if (i == xIndexes[index]) {
                diagram[group][0] = message[i - 1];
                diagram[group][1] = 'X';
                group++;
                i++;
                index++;
            }
            diagram[group][0] = message[i - 1];
            diagram[group][1] = message[i];
            group++;
        }

        if ((message.length + counter) % 2 != 0) {
            diagram[diagramLength - 1][0] = message[message.length - 1];
            diagram[diagramLength - 1][1] = 'X';
        }

        for (int i = 0; i < diagram.length; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(diagram[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static boolean isCorrectEntry(String entry) {
        //проверява дали String-а се състои от букви и интервал, но не и само от интервал
        return entry.matches("^[ A-Za-z]+$") && entry.trim().length() > 0;
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

