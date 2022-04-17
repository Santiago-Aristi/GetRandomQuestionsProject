package com.kenzie.app;

// import necessary libraries


import java.io.IOException;
import java.util.*;


public class Main {
    /* Java Fundamentals Capstone project:
       - Define as many variables, properties, and methods as you decide are necessary to
       solve the program requirements.
       - You are not limited to only the class files included here
       - You must write the HTTP GET call inside the CustomHttpClient.sendGET(String URL) method
         definition provided
       - Your program execution must run from the main() method in Main.java
       - The rest is up to you. Good luck and happy coding!
     */

    public static void main(String[] args) throws IOException {

        CustomHttpClient customHttpClient = new CustomHttpClient();
        Scanner scanner = new Scanner(System.in);
        String playAgain = "";
        String userEntry = "";
        int questionCount = 10;
        int questionNum = 1;
        int totalUserPoints = 0;

        String allCluesUrl = CustomHttpClient.sendGET(CustomHttpClient.GET_URL);
        CluesList allClues = customHttpClient.getCluesList(allCluesUrl);

        do {

            System.out.println("Welcome to Kenzie Trivia Night. You will be prompted with questions to answer." +
                    " \nTake your best guess and for every right answer you will earn 1 point. \nWrong answers will " +
                    "not be awarded any points. Have fun and enjoy the festivities!");

            while (questionCount >= 1) {
                try {
                    System.out.println("\nThis is question #" + questionNum);
                    int randomQuestion = (int) Math.floor(Math.random() * 100) + 1;

                    CluesDTO clues = allClues.getClues().get(randomQuestion);
                    System.out.println(clues.toString());

                    // Sets the API answer to the manipulated answer
                    clues.setAnswer(manipulateApiString(clues));

                    System.out.println("Enter your answer below to take a guess: ");
                    userEntry = scanner.nextLine();

                    /* Checks for empty String and prints customer exception message if user enters
                       an empty String */
                    try {
                        checkForEmptyString(userEntry);
                    } catch (EmptyEntryException e) {
                        System.out.println(e.getMessage());
                    }

                    // Checks both the API answer to the userEntry and awards points
                    if (checkAnswer(clues, userEntry.toLowerCase()) == true) {
                        totalUserPoints++;
                        System.out.println("That answer is correct. You're really smart!");
                        System.out.println("Your total point(s) are: " + totalUserPoints);
                    }
                    else {
                        System.out.println("The correct answer was - " + clues.getAnswer());
                    }

                    /* Removes the question that printed to the console so it would not come up again
                       during the 10 questions being asked in 1 game */
                    allClues.getClues().remove(randomQuestion);

                    questionCount--;
                    questionNum++;

                } catch (Exception e) {
                    e.getMessage();
                }
            }
            System.out.println("Your total points earned for this game was " + totalUserPoints);
            System.out.println("Would you like to play again? Please type Y or N");
            playAgain = scanner.nextLine();

        } while (playAgain.equalsIgnoreCase("Y"));
        System.out.println("Game Over. Come back any time!");
    }

    // Does a check of the answer from the API and the userAnswer to the console
    public static boolean checkAnswer(CluesDTO clue, String userAnswer) throws IOException, EmptyEntryException {
        if(userAnswer.equals("")){
            return false;
        }
        if (manipulateApiString(clue).contains(manipulateUserString(userAnswer)) ||
                manipulateApiString(clue).equalsIgnoreCase(userAnswer)) {
            return true;
        } else {
            System.out.println("Sorry, that's the wrong answer!");
            return false;
        }
    }

    // Checking for an empty string using custom exception class
    public static void checkForEmptyString(String userAnswer) throws EmptyEntryException {
        if (userAnswer.equals("")) {
            throw new EmptyEntryException("Can't enter an empty answer!");
        }
    }

    // Manipulates the answer from the API
    public static String manipulateApiString(CluesDTO clue){
        String newString = clue.getAnswer().replaceAll("&", "and").replaceAll("-", "")
                .replaceAll("[^a-zA-Z0-9\'-]", " ")
                .toLowerCase().trim();
        return newString;
    }

    // Manipulates the answer from the user
    public static String manipulateUserString(String answer){
        String newString = answer.replaceAll("&", "and").replaceAll("-", "");
        return newString;
    }

}

