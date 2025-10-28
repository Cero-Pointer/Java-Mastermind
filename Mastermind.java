import java.io.IOException;
import java.util.Scanner;

public class Mastermind {
    private char[][] playfield;
    private char[] solution;
    // Constructor
    public Mastermind() {
        playfield = new char[12][4];
    }
    public static void main(String[] args) {
        Mastermind game = new Mastermind();
        game.startPlayerVsPlayerGame();
    }
    // Clears the console screen
    public static void clrscr(){
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

            else {
                System.out.print("\033[H\033[2J");  
                System.out.flush();  
            }

        } catch (IOException | InterruptedException ex) {}
    }
    // Validates the user input
    private boolean validInput(String input) {
        if(input.length() != 4) {
            System.out.println("Your input is to short/long!");
            return false;
        }
        for(int i = 0; i < input.length(); i++) {
            if(input.charAt(i) != 'R' && input.charAt(i) != 'G' && input.charAt(i) != 'Y' && input.charAt(i) != 'P' &&
               input.charAt(i) != 'B' && input.charAt(i) != 'O') {
                System.out.println("Your input contains wrong characters!");
                return false;
            }
        }
        return true;
    }
    // Checks if the input string matches the solution
    private boolean checkSolution(String inputString) {
        int i = 0;
        while(i < 4 && (solution[i] == inputString.charAt(i))) {
            i++;
        }
        if(i == 4)
            return true;
        else
            return false;
    }
    // Checks if a character is in a char array
    private boolean isInCharArray(char[] input, char c) {
        if(input.length < 1)
            return false;
        for(int i = 0; i < input.length; i++) {
            if(input[i] == c) {
                return true;
            }
        }
        return false;
    }
    // Counts the right colors and positions in the input
    private int[] countRightColorsAndPositions(char[] input) {
        int[] arr = {0,0};
        char[] alreadySeen = new char[4];

        for(int i = 0; i < input.length; i++) { //Right Pos
            if(input[i] == solution[i])
                arr[1]++;
        }
        int k = 0;
        for(int i = 0; i < input.length; i++) { //Right Colors
            for(int j = 0; j < solution.length; j++) {
                if(input[i] == solution[j] && (!isInCharArray(alreadySeen, solution[j]))) {
                    alreadySeen[k] = solution[j];
                    k++;
                    arr[0]++;
                }
            }
        }

        return arr;
    }
    // Method to start the game
    public void startPlayerVsPlayerGame() {
        System.out.println("Coder, please set an order for the right solution\n You can use: 'R','B','Y','P','O','G'"+
                          "\n You can use a color multiple times\n Example: RYYG");
        Scanner sc = new Scanner(System.in);
        String coderInput = sc.nextLine();
        while(!validInput(coderInput)) {
            coderInput = sc.nextLine();
        }
        solution = coderInput.toCharArray();
        clrscr();
        boolean quit = false;
        int i = 0;
        int[] rightCountAndPositions = {0,0};
        while(!quit) {
            System.out.println(this.toString());
            System.out.println(12-i+" turns remaining. "+rightCountAndPositions[1]+" are on the right positions, "+rightCountAndPositions[0]+ " are right colors" +
                              "\nPlease enter your solution: ");
            String encoderInput = sc.nextLine();
            while(!validInput(encoderInput)) {
                encoderInput = sc.nextLine();
            }
            if(checkSolution(encoderInput)) {
                System.out.println("Your input was right! You won!");
                quit = true;
            }
            else {
                rightCountAndPositions = countRightColorsAndPositions(encoderInput.toCharArray());
                for(int j = 0; j < playfield[0].length; j++) {
                    playfield[i][j] = encoderInput.charAt(j);
                }
            }
            i++;
        }
        sc.close();
        System.exit(0);
    }
    // Maybe implement later
    public void startPlayerVsAiGame() {

    }
    // String representation of the playfield
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        for(int i = 0; i < playfield.length; i++) {
            for(int j = 0; j < playfield[0].length; j++) {
                sb.append("["+playfield[i][j]+"]");
            }
            sb.append("\n");
        }
        return sb.toString();
    }


}