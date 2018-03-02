package hm.game;

import java.util.Random;
import java.util.Scanner;

public class HangMan {

    final static String[] words = { "cat", "dog", "hat", "rat", "log", "boy", "bat", "fat", "sit",
            "what", "hang", "bowl", "sewn", "mark", "tone", "loan", "bone", "room", "doom",
            "think", "stand", "mouse", "house", "sharp", "light", "sound", "clown", "green" };

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        //Application Loop
        while(true) {
            runGame();
            if(!replay()) { break; }
        }
    }

    private static void runGame() {
        Random randNum = new Random();
        String chosenWord = words[randNum.nextInt(words.length)];
        char[] guessedWord = new char[chosenWord.length()];
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        int remainingGuesses = 7;

        createLines(guessedWord);

        //Game Loop
        while(true) {
            setWordLines(guessedWord);
            System.out.print(" Guesses Remaining: " + remainingGuesses + "\n\n");
            System.out.println("Remaining Alphabet:");
            showAlphabet(alphabet);

            char guess = getGuess(alphabet);
            boolean isSuccess = checkGuessWord(guess, guessedWord, chosenWord, alphabet);

            //If the guess was not successful decrement remaining guesses by 1
            if(!isSuccess) {
                remainingGuesses--;
            }

            //Check if the Game is Over
            if(checkWinLose(guessedWord, remainingGuesses, chosenWord)) {
                break;
            }
        }
    }

    /**
     * Checks if the Player has Won or Lost.
     * One of two conditions must be true:
     * 1) The player has run out of guesses is a Loss
     * 2) The player has guessed all the letters in the word is a Win
     * @param guessedWord - Current Guess Word to check with the Chosen Word for a Win
     * @param remaining - How many remaining Guesses the player has.
     * @param chosen - Games Chosen Word
     * Returns: A boolean if the game is really over.
     */
    private static boolean checkWinLose(char[] guessedWord, int remaining, String chosen) {

        StringBuilder guessWord = new StringBuilder();
        guessWord.append(guessedWord);

        if(remaining == 0) {
            System.out.println("\tYOU HAVE RUN OUT OF GUESSES! THE HIDDEN WORD WAS: " + chosen.toUpperCase());
            System.out.println("\tBETTER LUCK NEXT TIME!\n");
            return true;
        }

        if(guessWord.toString().equalsIgnoreCase(chosen)) {
            System.out.println("* * * CONGRATULATIONS! YOU WIN! THE HIDDEN WORD IS: " + chosen.toUpperCase() + " * * *\n");
            return true;
        }

        return false;
    }

    /**
     * Check if the Players Guess is in the Word and change guessed word array.
     * Take out of the Alphabet the Letter Guessed
     * @param guess - Letter Guessed to check
     * @param guessedWord - Array to Update
     * @param chosenWord - The Hidden Word
     * @param alphabet - The current alphabet
     */
    private static boolean checkGuessWord(char guess, char[] guessedWord, String chosenWord, char[] alphabet) {

        int count = 0; //Occurrences of Letter in Chosen Word

        //Change the Guessed Word Array
        for(int i = 0; i < chosenWord.length(); i++) {
            if(guess == chosenWord.charAt(i)) {
                guessedWord[i] = Character.toUpperCase(guess);
                count++;
            }
        }

        //Take out the Letter from the Alphabet
        char upperGuess = Character.toUpperCase(guess);
        for(int j = 0; j < alphabet.length; j++) {
            if(upperGuess == alphabet[j]) {
                alphabet[j] = '_';
                break;
            }
        }

        //Print out if the Letter was found in the Hidden Word or not.
        System.out.println("---------------------------------------------");
        if(count > 0) {
            System.out.println("\n'" + Character.toUpperCase(guess) + "' was found in the word (" + count + ") times\n");
            return true;
        } else {
            System.out.println("\nSorry! That letter is not in the word..\n");
        }

        return false;
    }

    /**
     * Create starting blank lines for guessing
     * @param guessed - Array to store guessed letters: Start default after method '_'
     */
    private static void createLines(char[] guessed) {
       for(int i = 0; i < guessed.length; i++) {
            guessed[i] = '_';
       }
    }

    /**
     * Print out currently guessed letters by the player
     * @param guessed - Array of Guessed Letters
     */
    private static void setWordLines(char[] guessed) {
        for(char letter: guessed) {
            System.out.print(letter + " ");
        }
    }

    /**
     * Get the Players Guess
     * Return: Players Character Guess
     */
    private static char getGuess(char[] alphabet) {
        boolean valid = false;
        char validResult;

        while(true) {
            System.out.print("\n\nYour Guess: ");
            String guess = input.next();

            if(guess.length() == 1) {
                valid = checkValidGuess(guess.toUpperCase().charAt(0), alphabet);
            }

            if (valid) {
                validResult = guess.charAt(0);
                break;
            } else {
                System.out.print("\n\tOops! Please enter a alphabet character from the remaining letters...");
            }
        }

        return validResult;
    }

    /**
     * Check the if guess is a letter and if it's accepted as a guess compared to remaining alphabet
     * @param guessed - Players guessed Character to check for Validity
     * Returns: A boolean if the Player needs to input another character.
     */
    private static boolean checkValidGuess(char guessed, char[] alphabet) {

        for(char letter: alphabet) {
            if(letter == guessed) {
                return true;
            }
        }

        return false;
    }

    /**
     * Show all of the Current Remaining Alphabet Letters Available to the Player
     * @param alphabet - Current Alphabet Array for the Game
     */
    private static void showAlphabet(char[] alphabet) {
        int count = 0;

        for(char letter: alphabet) {
            System.out.print(letter + " ");

            count++;
            if(count == 13) {
                System.out.print("\n");
            }
        }
    }

    /**
     * Ask if the Player would like to play another game
     * Returns: A boolean value to tell the application to stop or play a new game.
     */
    private static boolean replay() {
        while(true) {
            System.out.print("Would you like to replay? [Y/N]: ");
            String answer = input.next();

            if(answer.equalsIgnoreCase("y")) {
                return true;
            } else if(answer.equalsIgnoreCase("n")) {
                break;
            } else {
                System.out.println("\tOops! Please enter either 'Y' or 'N'...");
            }
        }

        return false;
    }
}
