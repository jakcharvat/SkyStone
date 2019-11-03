package org.firstinspires.ftc.teamcode.stick_game;

import org.firstinspires.ftc.teamcode.utils.Binary;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    GameManager(int rows) {
        final int firstRowSticks = 1;
        final int stickDifferenceBetweenRows = 2;

        for (int i = 0; i < rows; i++) {
            gameRows.add(firstRowSticks + (i * stickDifferenceBetweenRows));
            board.add(new GameRow(i, gameRows.get(i)));
        }
    }

    // A list of the numbers of sticks in each row
    private List<Integer> gameRows = new ArrayList<>();

    // A representation of the whole board
    private List<GameRow> board = new ArrayList<>();

    // Tracks the status of the current game
    private GameStatus gameStatus = GameStatus.waitingToStart;

    // Tracks the current player
    private Player currentPlayer = Player.first;

    // Keeps track of which row the human player has removed sticks from to lock all other rows
    private Integer rowBlock;

    /// Returns the number of sticks present in the given row. Not their status, only the number of sticks at the start, where
    /// the first row has index 0.
    int sticksAtRow(int row) {
        return gameRows.get(row);
    }

    void remove(int row, int stick) {
        if (rowBlock == row || rowBlock == null) {
            board.get(row).remove(stick);
            rowBlock = row;
        }

        checkIfGameFinished();
    }

    public void removeAllToTheRight(int row, int stick) {
        board.get(row).removeAllToRight(stick);

        checkIfGameFinished();
    }

    private void checkIfGameFinished() {
        boolean isEmpty = true;
        for (GameRow row : board) {
            if (!row.isEmpty()) {
                isEmpty = false;
            }
        }

        if (isEmpty) {
            gameStatus = GameStatus.finished;
        }
    }

    void handoffPlayer() {
        rowBlock = null;
        currentPlayer = currentPlayer == Player.first ? Player.second : Player.first;
    }

    public List<GameRow> board() { return board; }
    GameStatus status() { return gameStatus; }
    Player currentPlayer() { return currentPlayer; }
    public Binary totalBinary() {
        Binary totalBinary = new Binary(0);

        for (GameRow row : board) {
            totalBinary = totalBinary.add(row.binary());
        }

        return totalBinary;
    }

    @NotNull
    public String toString() {

        StringBuilder finalString = new StringBuilder();

        for (int i = 0; i < board.size(); i++) {
            if (i == 0) {
                finalString.append(board.get(i));
            } else {
                finalString.append(" ").append(board.get(i));
            }
        }

        return finalString.toString();
    }
}

enum GameStatus {
    waitingToStart,
    ongoing,
    finished
}

enum Player {
    first,
    second
}