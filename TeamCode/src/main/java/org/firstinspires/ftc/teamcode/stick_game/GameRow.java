package org.firstinspires.ftc.teamcode.stick_game;

import org.firstinspires.ftc.teamcode.utils.Binary;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GameRow {

    //region Constructor
    GameRow(int rowNumber, int numberOfSticks, MoveManager moveManager) {
        this.originalNumberOfSticks = numberOfSticks;
        this.rowNumber = rowNumber;
        this.currentNumberOfSticks = this.originalNumberOfSticks;
        this.binaryNumberOfSticks = new Binary(this.currentNumberOfSticks);

        for (int i = 0; i < originalNumberOfSticks; i++) {
            sticks.add(new Stick(rowNumber, i, moveManager));
        }
    }
    //endregion

    //region Properties
    final private int originalNumberOfSticks;
    final private int rowNumber;
    private int currentNumberOfSticks;
    private Binary binaryNumberOfSticks;

    final private List<Stick> sticks = new ArrayList<>();
    //endregion

    //region Removing Sticks
    void remove(int number) {
        sticks.get(number).remove();

        currentNumberOfSticks --;
        binaryNumberOfSticks = new Binary(currentNumberOfSticks);
    }

    void removeAllToRight(int number) {
        List<Stick> orderedStickList = orderedSticks();

        for (int i = number; i < currentNumberOfSticks; i++) {
            orderedStickList.get(i).remove();
        }

        currentNumberOfSticks = number;
        binaryNumberOfSticks = new Binary(currentNumberOfSticks);
    }
    //endregion

    //region Getters
    public List<Stick> sticks() {
        return sticks;
    }
    private List<Stick> orderedSticks() {
        final List<Stick> notRemovedSticks = new ArrayList<>();
        final List<Stick> removedSticks = new ArrayList<>();

        for (Stick stick : sticks) {
            if (stick.isRemoved()) {
                removedSticks.add(stick);
            } else {
                notRemovedSticks.add(stick);
            }
        }

        final List<Stick> orderedStickList = new ArrayList<>();

        orderedStickList.addAll(notRemovedSticks);
        orderedStickList.addAll(removedSticks);

        return orderedStickList;
    }
    int originalNumberOfSticks() {
        return originalNumberOfSticks;
    }
    public int currentNumberOfSticks() {
        return currentNumberOfSticks;
    }
    public Binary binary() {
        return binaryNumberOfSticks;
    }
    public boolean isEmpty() {
        return currentNumberOfSticks == 0;
    }
    public int rowNumber() { return rowNumber; }

    @NotNull
    public String toString() {

        StringBuilder finalString = new StringBuilder();

        for (int i = 0; i < sticks.size(); i++) {
            if (i == 0) {
                finalString.append(sticks.get(i));
            } else {
                finalString.append(" ").append(sticks.get(i));
            }
        }

        return finalString.toString();
    }
    //endregion

}
