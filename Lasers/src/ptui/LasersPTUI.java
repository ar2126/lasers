package ptui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author aidan_000 on 4/12/2016.
 * @author Eli
 */
public class LasersPTUI {

    Character[][] safe;
    int col = 0;
    int row = 0;

    public static void takeInput(LasersPTUI laser){
        Scanner sc = new Scanner(System.in);
        while (true){

            laser.redrawLaser();
            String next = "";
            System.out.print(">");
            next = sc.nextLine();
            if (next.length() == 0){
                continue;
            }
            String[] items = next.split(" ");
            if (next.charAt(0) == 'a' || next.charAt(0) == 'r') {
                if (items.length == 3) {
                    if (items[0].equals("a") || items[0].equals("add") || items[0].charAt(0) == 'a') {
                        laser.addLaser(Integer.parseInt(items[1]), Integer.parseInt(items[2]));
                        laser.redrawLaser();
                        System.out.println(laser.toString());
                    } else if (items[0].equals("r") || items[0].equals("remove") || items[0].charAt(0) == 'r') {
                        laser.removeLaser(Integer.parseInt(items[1]), Integer.parseInt(items[2]));
                        laser.redrawLaser();
                        System.out.println(laser.toString());
                    }
                }
                else {
                    System.out.println("Incorrect coordinates");
                }
            }
            else if (next.equals("d") || next.equals("display") || next.charAt(0) == 'd')
                System.out.println(laser.toString());

            else if (next.equals("h") || next.equals("help") || next.charAt(0) == 'h')
                laser.displayHelp();

            else if (next.equals("q") || next.equals("quit") || next.charAt(0) == 'q')
                System.exit(0);

            else if (next.equals("v") || next.equals("verify") || next.charAt(0) == 'v') {
                laser.redrawLaser();
                laser.verify();
                System.out.println(laser.toString());
            }
            else{
                System.out.println("Unrecognized command: " + next);
            }

        }
    }


    public LasersPTUI() {
        super();
    }

    public static void main(String[] args)throws FileNotFoundException{
        if(args.length > 0){
            String initial = args[0];
            LasersPTUI laser = new LasersPTUI(initial);
            System.out.println(laser.toString());
            if(args.length == 2){
                String input = args[1];
                Scanner file = new Scanner(new File(input));
                while(file.hasNextLine()){
                    laser.redrawLaser();
                    String next = file.nextLine();
                    if (next.length() == 0){
                        continue;
                    }
                    String[] items = next.split(" ");
                    if (next.charAt(0) == 'a' || next.charAt(0) == 'r') {
                        if (items.length == 3) {
                            if (items[0].equals("a") || items[0].equals("add") || items[0].charAt(0) == 'a') {
                                laser.addLaser(Integer.parseInt(items[1]), Integer.parseInt(items[2]));
                                laser.redrawLaser();
                                System.out.println(laser.toString());
                            } else if (items[0].equals("r") || items[0].equals("remove") || items[0].charAt(0) == 'r') {
                                laser.removeLaser(Integer.parseInt(items[1]), Integer.parseInt(items[2]));
                                laser.redrawLaser();
                                System.out.println(laser.toString());
                            }
                        }
                        else {
                            System.out.println("Incorrect coordinates");
                        }
                        laser.redrawLaser();
                    }
                    else if (next.equals("d") || next.equals("display") || next.charAt(0) == 'd')
                        System.out.println(laser.toString());

                    else if (next.equals("h") || next.equals("help") || next.charAt(0) == 'h')
                        laser.displayHelp();

                    else if (next.equals("q") || next.equals("quit") || next.charAt(0) == 'q')
                        System.exit(0);

                    else if (next.equals("v") || next.equals("verify") || next.charAt(0) == 'v') {
                        laser.redrawLaser();

                        laser.verify();
                        System.out.println(laser.toString());
                    }
                    else{
                        System.out.println("Unrecognized command: " + next);
                    }

                }
                file.close();
                takeInput(laser);
            }

            if (args.length == 1)
            {
                takeInput(laser);
            }

        }
        else{
            System.out.println("Usage: java LasersPTUI safe-file [input]");
            System.exit(0);
        }

    }

    /**
     * Constructs the grid and reads the file with the given rows and columns in the 1st text file
     * @param init the initial file containing the first safe setup
     * @throws FileNotFoundException if file doesn't exist
     *
     * @author Aidan Rubenstein
     */
    public LasersPTUI(String init) throws FileNotFoundException {
        Scanner s1 = new Scanner(new File(init));
        row = s1.nextInt();
        col = s1.nextInt();
        safe = new Character[row][col];
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                safe[i][j] = s1.next().charAt(0);
            }
        }
    }

    /**
     * Displays a string result of the safe layout during/after modification
     * @return display a string representation of the grid at any given point
     *
     * @author Aidan Rubenstein
     */
    public String toString(){
        String display = "";
        display += "  ";
        for(int i = 0; i < col; i++){
            display += i%10 + " ";
        }
        display += "\n  ";
        for(int hori = 0; hori < (col*2) - 1; hori++){
            display += "-";
        }
        display += "\n";
        for(int r = 0; r < row; r++){
            display += r%10 + "|";
            for(int c = 0; c < col - 1; c++){
                display += safe[r][c] + " ";
            }
            display += safe[r][col - 1];
            if (r < row - 1) display += "\n";
        }
        return display;
    }

    /**
     * Displays instructions for the user to enter
     *
     * @author Aidan Rubenstein
     */
    public void displayHelp(){
        System.out.println("a|add r c: Add laser to (r,c)");
        System.out.println("d|display: Display safe");
        System.out.println("h|help: Print this help message");
        System.out.println("q|quit: Exit program");
        System.out.println("r|remove r c: Remove laser from (r,c)");
        System.out.println("v|verify: Verify safe correctness");
    }
    /**
     * @author Eli
     */
    private void removeLaser(int r, int c){
        if (r < 0 || r >= row || c < 0 || c >= col || safe[r][c] != 'L')
        {
            System.out.println("Error removing laser at: (" + r + ", " + c + ")");
            return;
        }
        safe[r][c] = '.';
        for (int i = r - 1; i >= 0; i --){
            if (safe[i][c] != '*'){
                break;
            }
            safe[i][c] = '.';
        }
        for (int i = c - 1; i >= 0; i --){
            if (safe[r][i] != '*'){
                break;
            }
            safe[r][i] = '.';
        }
        for (int i = r + 1; i < row; i ++){
            if (safe[i][c] != '*'){
                break;
            }
            safe[i][c] = '.';
        }
        for (int i = c + 1; i < col; i ++){
            if (safe[r][i] != '*'){
                break;
            }
            safe[r][i] = '.';
        }
        for (int i = 0; i < row; i ++) {
            for (int j = 0; j < col; j++) {
                if (safe[i][j] == 'L'){
                    safe[i][j] = '.';
                    addLaser2(i, j);
                }
            }
        }

        System.out.println("Laser removed at: (" + r + ", " + c + ")");
    }
    /**
     * @author Eli
     */
    private void addLaser(int r, int c){
        if (r < 0 || r >= row || c < 0 || c >= col || safe[r][c] != '.')
        {
            System.out.println("Error adding laser at: (" + r + ", " + c + ")");
            return;
        }
        safe[r][c] = 'L';
        for (int i = r - 1; i >= 0; i --){
            if (safe[i][c] != '.'){
                break;
            }
            safe[i][c] = '*';
        }
        for (int i = c - 1; i >= 0; i --){
            if (safe[r][i] != '.'){
                break;
            }
            safe[r][i] = '*';
        }
        for (int i = r + 1; i < row; i ++){
            if (safe[i][c] != '.'){
                break;
            }
            safe[i][c] = '*';
        }
        for (int i = c + 1; i < col; i ++){
            if (safe[r][i] != '.'){
                break;
            }
            safe[r][i] = '*';
        }
        System.out.println("Laser added at: (" + r + ", " + c + ")");
    }
    /**
     * @author Eli
     */
    private void addLaser2(int r, int c){
        if (safe[r][c] != '.')
        {
            return;
        }
        safe[r][c] = 'L';
        for (int i = r - 1; i >= 0; i --){
            if (safe[i][c] != '.' && safe[i][c] != '*'){
                break;
            }
            safe[i][c] = '*';
        }
        for (int i = c - 1; i >= 0; i --){
            if (safe[r][i] != '.' && safe[r][i] != '*'){
                break;
            }
            safe[r][i] = '*';
        }
        for (int i = r + 1; i < row; i ++){
            if (safe[i][c] != '.' && safe[i][c] != '*'){
                break;
            }
            safe[i][c] = '*';
        }
        for (int i = c + 1; i < col; i ++){
            if (safe[r][i
                    ] != '.' && safe[r][i] != '*'){
                break;
            }
            safe[r][i] = '*';
        }
    }
    private boolean checkCollision(int r, int c){
        for (int i = r - 1; i >= 0; i --){
            if (safe[i][c] == 'L'){
                return false;
            }
            else if (safe[i][c] != '*') {
                break;
            }
        }
        for (int i = c - 1; i >= 0; i --){
            if (safe[r][i] == 'L'){
                return false;
            }
            else if (safe[r][i] != '*') {
                break;
            }
        }
        for (int i = r + 1; i < row; i ++){
            if (safe[i][c] != 'L'){
                return false;
            }
            else if (safe[i][c] != '*') {
                break;
            }
        }
        for (int i = c + 1; i < col; i ++){
            if (safe[r][i] != 'L'){
                return false;
            }
            else if (safe[r][i] != '*') {
                break;
            }
        }
        return true;
    }
    public void redrawLaser(){
        for (int i = 0; i < row; i ++) {
            for (int j = 0; j < col; j++) {
                if (safe[i][j] == 'L'){
                    safe[i][j] = '.';
                    addLaser2(i, j);
                }
            }
        }
    }
    /**
     * @author Eli
     * @author Aiden
     */
    public void verify(){
        redrawLaser();
        for (int i = 0; i < row; i ++){
            for (int j = 0; j < col; j ++){
                if (safe[i][j] == '.')
                {
                    System.out.println("Error verifying at: (" + i + ", " + j + ")\n");
                    return;
                }
                else if (safe[i][j] == 'L' && checkCollision(i, j)) {
                    System.out.println("Error verifying at: (" + i + ", " + j + ")\n");
                    return;
                } else if (safe[i][j] == '0' ||
                        safe[i][j] == '1' ||
                        safe[i][j] == '2' ||
                        safe[i][j] == '3' ||
                        safe[i][j] == '4'){
                    int total = Integer.parseInt(safe[i][j].toString());
                    if (j > 0 && safe[i][j-1] == 'L'){
                        total --;
                    }
                    if (j < col - 1 && safe[i][j+1] == 'L'){
                        total --;
                    }
                    if (i > 0 && safe[i - 1][j] == 'L'){
                        total --;
                    }
                    if (i < row - 1 && safe[i + 1][j] == 'L'){
                        total --;
                    }


                    if (j > 0 && i > 0 &&safe[i - 1][j-1] == 'L'){
                        total --;
                    }
                    if (j < col - 1 && i > 0 &&safe[i - 1][j+1] == 'L'){
                        total --;
                    }
                    if (i < row-1 && j > 0 &&safe[i + 1][j - 1] == 'L'){
                        total --;
                    }
                    if (i < row - 1 && j < col - 1 && safe[i + 1][j + 1] == 'L'){
                        total --;
                    }
                    if (total != 0){
                        System.out.println("Error verifying at: (" + i + ", " + j + ")\n");
                        return;
                    }

                }
            }
        }

        //...

        System.out.println("Safe is fully verified!");
    }
}