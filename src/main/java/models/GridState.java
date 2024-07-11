package models;

import exeptions.IllegalMoveException;
import exeptions.NoValidMoveLeftException;

import java.util.Arrays;
import java.util.stream.IntStream;

public class GridState {
    /**
     * 0 = empty
     * 1 = X
     * -1 = O
     */
    private final int[][] grid = new int[3][3];
    private int turnCount = 0;
    private static boolean isX = true;
    public GridState() {
    }
    private GridState(int x, int y, int turnCount) {
        grid[x][y] = isX ? 1 : -1;
        this.turnCount = turnCount;
        isX = !isX;
    }
    public boolean hasNoSpace() {
        return Arrays.stream(grid).flatMapToInt(Arrays::stream).noneMatch(x -> x == 0);
    }
    public boolean isXWin() {
        boolean isColWin = IntStream.range(0, grid[0].length)
                .map(i -> Arrays.stream(grid)
                        .mapToInt(row -> row[i])
                        .sum())
                .anyMatch(sum -> sum == 3);
        boolean isRowWin = Arrays.stream(grid)
                .mapToInt(row -> Arrays.stream(row).sum())
                .anyMatch(sum -> sum == 3);
        int diagonalSum = 0;
        for (int i = 0; i < grid.length; i++) {
            diagonalSum += grid[i][i];
        }
        boolean isDiagonalWin = diagonalSum == 3;
        return isColWin || isRowWin || isDiagonalWin;
    }
    public boolean isOWin() {
        boolean isColWin = IntStream.range(0, grid[0].length)
                .map(i -> Arrays.stream(grid)
                        .mapToInt(row -> row[i])
                        .sum())
                .anyMatch(sum -> sum == -3);
        boolean isRowWin = Arrays.stream(grid)
                .mapToInt(row -> Arrays.stream(row).sum())
                .anyMatch(sum -> sum == -3);
        int diagonalSum = 0;
        for (int i = 0; i < grid.length; i++) {
            diagonalSum += grid[i][i];
        }
        boolean isDiagonalWin = diagonalSum == -3;
        return isColWin || isRowWin || isDiagonalWin;
    }
    public GridState makeMove(int cellID)
    throws  IllegalMoveException, NoValidMoveLeftException {
        if(cellID < 1 || cellID > 9)
            throw new IllegalMoveException("Invalid Cell [" + cellID + "].\n Must be within [1:9] (inclusive).");
        int x = (cellID-1)%3 +1;
        int y = (int) (double) (cellID / 3);
        return makeMove(x + 1, y + 1);
    }
    public GridState makeMove(int colPosition, int rowPosition)
    throws  IllegalMoveException, NoValidMoveLeftException {
        if (colPosition <= 0 || colPosition > 3 || rowPosition <= 0 || rowPosition > 3)
            throw new IllegalMoveException(
                    "Invalid Cell [" + colPosition + ":" + rowPosition + "].\n Must be within [1:3] (inclusive).");
        int _x = colPosition - 1,
                _y = rowPosition - 1;
        if (grid[_x][_y] != 0)
            throw new IllegalMoveException("Invalid Cell [" + colPosition + ":" + rowPosition + "].");
        if (!hasWinner() && !hasNoSpace())
            throw new NoValidMoveLeftException();
        return new GridState(_x, _y, turnCount + 1);
    }
    public boolean hasWinner (){
        return isOWin() || isXWin();
    }
    public int[][] getGrid(){
        return grid;
    }
    public int getTurnCount(){
        return turnCount;
    }
}
