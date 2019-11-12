package org.firstinspires.ftc.teamcode.stick_game;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utils.Binary;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    GameManager(int rows, HardwareMap map, Telemetry telemetry) {

        this.moveManager = new MoveManager(map, telemetry);

        final int firstRowSticks = 1;
        final int stickDifferenceBetweenRows = 2;

        for (int i = 0; i < rows; i++) {
            gameRows.add(firstRowSticks + (i * stickDifferenceBetweenRows));
            board.add(new GameRow(i, gameRows.get(i), moveManager));
        }

    }

    // A list of the numbers of sticks in each row
    private List<Integer> gameRows = new ArrayList<>();

    // A representation of the whole board
    private List<GameRow> board = new ArrayList<>();

    // Tracks the status of the current game
    private GameStatus gameStatus = GameStatus.waitingToStart;

    // Tracks the current player
    private Player currentPlayer = Player.human;

    // Keeps track of which row the human player has removed sticks from to lock all other rows
    private Integer rowBlock = null;

    // Reference to MoveManager. For simplicity and communication reasons, this class fill handle
    // all communication between [GameLoop] and [MoveManager].
    MoveManager moveManager;

    /// Returns the number of sticks present in the given row. Not their status, only the number of sticks at the start, where
    /// the human row has index 0.
    int sticksAtRow(int row) {
        return gameRows.get(row);
    }

    void remove(int row, int stick) {
        if (rowBlock == null || rowBlock == row) {
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

        if (gameStatus != GameStatus.finished) {
            currentPlayer = currentPlayer == Player.human ? Player.robot : Player.human;
        }
    }

    public List<GameRow> board() { return board; }
    GameStatus status() { return gameStatus; }
    Integer rowBlock() { return rowBlock; }
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
    human,
    robot
}