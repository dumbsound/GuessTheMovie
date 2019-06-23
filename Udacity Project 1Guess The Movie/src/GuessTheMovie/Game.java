package GuessTheMovie;

import java.io.File;
import java.nio.file.*;
import java.util.Scanner;

public class Game {
    Mainclass mainclass = new Mainclass();
    public static char guessedLetter = 0;
    public static int charIndex;
    public int noOfTries;

    StringBuilder strOfTries=new StringBuilder();

    public void userInput() {
        System.out.println("Guess the movie title above!\nPick a letter to start \nYou only have ten wrong tries");
        while (noOfTries < 10) {
            Scanner scanner = new Scanner(System.in);
            guessedLetter = scanner.next().charAt(0);
            System.out.println("You guessed the letter " + guessedLetter);
            if(strOfTries.indexOf(String.valueOf(guessedLetter))==-1){
                getIndexofAnswer();
                if (charIndex == -1) {
                    System.out.println("Wrong!! There are no " + guessedLetter + " in the title! Guess again");
                    noOfTries++;
                    System.out.println("You have "+(10-noOfTries)+" points left");
                } else {
                    System.out.println("Nice! You guessed right");
                }
            }else
            {
                System.out.println("You have already selected this letter - try another letter");
            }
            strOfTries.append(guessedLetter);
            mainclass.revealAnswerByEachLetter();
        }
    }

    public void getIndexofAnswer() {
        charIndex = mainclass.readTitlePick.indexOf(guessedLetter);
    }
}

class Mainclass {
    public static Scanner scanner;
    public static String readTitlePick;
    public int titlePick = (int) (Math.random() * 26);
    public static char[] underscoredChars;
    public static int strUnderscoreChars;
    public static char[] underscoredCharsBeforeConverting;


    File file = new File("movies.txt");

    public void printTitle() {
        try {
            scanner = new Scanner(file);
            readTitlePick = Files.readAllLines(Paths.get("movies.txt")).get(titlePick);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void convertLettersToUnderscores() {

        underscoredCharsBeforeConverting = readTitlePick.toCharArray();
        underscoredChars = readTitlePick.toCharArray();
        for (int i = 0; i < readTitlePick.length(); i++) {
            if (underscoredChars[i] == ' ') {
                System.out.print(" ");
            } else
                underscoredChars[i] = '_';
            System.out.print(underscoredChars[i] + " ");
        }
        System.out.println();
    }

    public void revealAnswerByEachLetter() {

        for (int i = 0; i < readTitlePick.length(); i++) {
            if (underscoredCharsBeforeConverting[i] == Game.guessedLetter) {
                underscoredChars[i] = Game.guessedLetter;
                System.out.print(underscoredChars[i] + " ");
            } else
                System.out.print(underscoredChars[i] + " ");
        }
        checkIfAllLettersGuessed();
    }

    public void checkIfAllLettersGuessed() {
        strUnderscoreChars = String.valueOf(underscoredChars).indexOf('_');
        if (strUnderscoreChars == -1) {
            System.out.println("Congratulations! You guessed all the letters");
        }
    }

    public static void main(String[] args) {

        System.out.println("Welcome to Guess the Movie Title!");
        Mainclass mainclass = new Mainclass();
        mainclass.printTitle();
        mainclass.convertLettersToUnderscores();
        Game game = new Game();
        game.userInput();

    }
}

