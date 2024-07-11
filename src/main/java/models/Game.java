package models;

import exeptions.NoValidMoveLeftException;

import java.util.*;

public class Game {
    private Random rand = new Random();
    private static Scanner scanner = new Scanner(System.in);
    private List<GridState> states = new ArrayList<>() ;
    private HashSet<Integer> usedCell = new HashSet<>();
    public Game(){
        reset();
    }
    public void loop(){
        while (true){
            draw();
            System.out.println("Your turn");
            int  cellID ;
            while (true){
                String input = scanner.nextLine();
                try{
                    if(input.equalsIgnoreCase("quit")){
                        System.out.println("Bye!");
                        return;
                    }
                    cellID = Integer.valueOf(input);
                    GridState newGridState = states.get(states.size()-1).makeMove(cellID);
                    states.add(newGridState);
                    usedCell.add(cellID);
                }catch (NumberFormatException numberFormatException){
                    System.out.println("Not a number, please try again!");
                    continue;
                }catch (IllegalArgumentException illegalArgumentException){
                   System.out.println("Not a valid move, please try again!");
                   continue;
                }catch (NoValidMoveLeftException noValidMoveLeftException){
                    System.out.println("Internal Error!%n Exiting");
                    return;
                }
                if (states.get(states.size()-1).hasWinner()){
                    System.out.println("You won!");
                    if(mainMenu() == 1)
                        reset();
                    System.out.println("bye");
                    return;
                }
                if (states.get(states.size()-1).hasNoSpace()){
                    System.out.println("Draw!");
                    if(mainMenu() == 1)
                        reset();
                    System.out.println("bye");
                    return;
                }
                int[] validMoves = Arrays.stream(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}).filter(x -> !usedCell.contains(x)).toArray();
                states.get(states.size()-1).makeMove(validMoves[rand.nextInt(validMoves.length)]);
                if (states.get(states.size()-1).hasWinner()){
                        System.out.println("You lose!");
                        if(mainMenu() == 1)
                            reset();
                        System.out.println("bye");
                        return;
                    }
                if (states.get(states.size()-1).hasNoSpace()){
                    System.out.println("Draw!");
                    if(mainMenu() == 1)
                        reset();
                    System.out.println("bye");
                    return;
                }
            }
        }
    }
    public int mainMenu (){
        String input ;
        while(true){
            System.out.printf("1. New game%n2. Quit%nYour choice: ");
            input = scanner.nextLine();
            if (input.equals("1")){
                return 1;
            }
            if (input.equals("2")){
                return 2;
            }
        }
    }
    public void draw(){
        int [][] lastState = states.get(states.size()-1).getGrid();
        for(int i=0;i<lastState.length;i++){
            for(int j=0;j<lastState[0].length;j++){
                String val = lastState[i][j]==0 ? " " : lastState[i][j]==1 ? "X" : "O";
                System.out.printf("%s%s",val,j<lastState[0].length-1 ? "|":"");
            }
            System.out.println("_____");
        }
    }
    private void reset(){
        states.clear();
        usedCell.clear();
    }
}
