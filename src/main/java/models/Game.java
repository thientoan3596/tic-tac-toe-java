package models;

import exeptions.NoValidMoveLeftException;

import java.util.*;

public class Game {
    private final Random rand = new Random();
    private static final Scanner scanner = new Scanner(System.in);
    private final List<GridState> states = new ArrayList<>();
    private final HashSet<Integer> usedCell = new HashSet<>();

    public Game() {
        reset();
    }

    public void loop() {
        while (true) {
            draw();
            System.out.println("Your turn");
            int cellID;
            while (true) {
                String input = scanner.nextLine();
                try {
                    if (input.equalsIgnoreCase("quit")) {
                        System.out.println("Bye!");
                        return;
                    }
                    cellID = Integer.parseInt(input);
                    GridState newGridState = states.get(states.size() - 1).makeMove(cellID);
                    states.add(newGridState);
                    usedCell.add(cellID);
                    break;
                } catch (NumberFormatException numberFormatException) {
                    System.out.println("Not a number, please try again!");
                } catch (IllegalArgumentException illegalArgumentException) {
                    System.out.println("Not a valid move, please try again!");
                } catch (NoValidMoveLeftException noValidMoveLeftException) {
                    System.out.println("Internal Error!%n Exiting");
                    return;
                }
            }
            if (states.get(states.size() - 1).hasWinner()) {
                draw();
                System.out.println("You won!");
                if (mainMenu() == 2) {
                    System.out.println("bye");
                    return;
                }
                reset();
            }
            if (states.get(states.size() - 1).hasNoSpace()) {
                draw();
                if (mainMenu() == 2) {
                    System.out.println("bye");
                    return;
                }
                reset();
            }
            int[] validMoves = Arrays.stream(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}).filter(x -> !usedCell.contains(x)).toArray();
            int cpuSelectedCell = validMoves[rand.nextInt(validMoves.length)];
            usedCell.add(cpuSelectedCell);
            states.add(states.get(states.size() - 1).makeMove(cpuSelectedCell));
            if (states.get(states.size() - 1).hasWinner()) {
                draw();
                System.out.println("You lose!");
                if (mainMenu() == 2) {
                    System.out.println("bye");
                    return;
                }
                reset();
            }
            if (states.get(states.size() - 1).hasNoSpace()) {
                draw();
                if (mainMenu() == 2) {
                    System.out.println("bye");
                    return;
                }
                reset();
            }
        }
    }

    public int mainMenu() {
        String input;
        while (true) {
            System.out.printf("1. New game%n2. Quit%nYour choice: ");
            input = scanner.nextLine();
            if (input.equals("1")) {
                return 1;
            }
            if (input.equals("2")) {
                return 2;
            }
        }
    }

    public void draw() {
        clearScreen();
        int[][] lastState = states.get(states.size() - 1).getGrid();
        for (int i = 0; i < lastState.length; i++) {
            for (int j = 0; j < lastState[0].length; j++) {
                String val = lastState[i][j] == 0 ? String.valueOf((i * 3) + j + 1) : lastState[i][j] == 1 ? "X" : "O";
                System.out.printf("%s%s", val, j < lastState[0].length - 1 ? "|" : "");
            }
            System.out.println();
            System.out.println("-----");
        }
    }

    private void reset() {
        states.clear();
        states.add(new GridState());
        usedCell.clear();
    }

    private void clearScreen() {
        if(System.getProperty("os.name").contains("Linux")) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } else if (System.getProperty("os.name").contains("Windows")) {
            for (int i = 0; i < 20; i++) {
                System.out.println();
            }
        }
    }
}
