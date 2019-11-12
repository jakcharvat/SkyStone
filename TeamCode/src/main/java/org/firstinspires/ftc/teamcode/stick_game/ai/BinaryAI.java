package org.firstinspires.ftc.teamcode.stick_game.ai;

import org.firstinspires.ftc.teamcode.stick_game.GameManager;
import org.firstinspires.ftc.teamcode.stick_game.GameRow;
import org.firstinspires.ftc.teamcode.utils.Binary;

import java.util.ArrayList;
import java.util.List;

public class BinaryAI extends AI {

    //region Constructor
    public BinaryAI(GameManager manager) {
        super(manager);
    }
    //endregion

    //region Removing Sticks
    @Override
    public boolean removeStick() throws CouldNotRemoveStickException {

        //region Initial Calculations
        final List<Integer> totalBinary = manager.totalBinary().asList();

        // Total number of rows that aren't empty
        int nonEmptyRows = 0;

        // Rows that have more than one stick left
        int nonSingleRows = 0;

        // Loop through the game board and count the non empty and non single rows
        for (GameRow row : manager.board()) {
            if (!row.isEmpty()) nonEmptyRows += 1;
            if (row.currentNumberOfSticks() > 1) nonSingleRows += 1;
        }
        //endregion

        //region Only one row with more than one stick left
        /// If there is only one row with more than one stick left, remove either all or all but one stick
        /// from that row, depending on how many rows still have more than one stick, in order to force the
        /// opponent into a losing position by leaving an odd number of sticks for the human player.
        if (nonSingleRows == 1) {
            Integer nonSingleRowNumber = null;

            List<GameRow> board = manager.board();
            for (int i = 0; i < board.size(); i++) {
                GameRow row = board.get(i);
                if (row.currentNumberOfSticks() > 1) nonSingleRowNumber = i;
            }

            if (nonSingleRowNumber == null) throw new CouldNotRemoveStickException();

            boolean removeWholeRow = nonEmptyRows % 2 == 0;

            manager.removeAllToTheRight(nonSingleRowNumber, removeWholeRow ? 0 : 1);
            return true;
        }
        //endregion

        //region Calculating difference necessary to reach a winning position
        /// Holds the binary representation of what sticks need to be removed in order to place the
        /// robot in a winning position. ([manager.totalBinary) must be all even integers.
        final List<Integer> differenceList = new ArrayList<>();

        /// Actually calculates the difference for [differenceList]
        for (int bit : totalBinary) {
            if (bit % 2 == 1) {
                differenceList.add(1);
            } else if (!differenceList.isEmpty()) {
                differenceList.add(0);
            }
        }
        //endregion

        //region Impossible to play a winning move
        /// Difference to reach a winning move is 0, meaning that the opponent has played the winning move and
        /// the robot has no possible winning move. In this situation, just remove a random stick and hope the
        /// opponent plays a non-winning move later down the line.
        if (differenceList.isEmpty()) {
            AI ai = new RandomAI(manager);
            ai.removeStick();

            return true;
        }
        //endregion

        final Binary difference = new Binary(differenceList);

        //region Find a row that can be fully removed to place the robot in a winning position
        /// Attempt to find a row the binary representation of which corresponds to the difference list,
        /// meaning that removing the whole row would result in a winning move.
        Integer matchingIndex = null;
        for (int i = 0; i < manager.board().size(); i++) {
            GameRow row = manager.board().get(i);
            if (row.binary().equals(difference)) {
                matchingIndex = i;
                break;
            }
        }

        if (matchingIndex != null) {
            manager.removeAllToTheRight(matchingIndex, 0);
            return true;
        }
        //endregion

        /// Since all previous checks failed, the only option remains to check every row
        /// and see if it meets the pattern we need to remove in order to play a winning move.
        /// For performance reasons there is no need to check rows which already have less
        /// sticks than the minimum number of sticks we need to remove (represented by
        /// [differenceList], so [legalRows] contain only rows that are longer than [differenceList],
        /// rows that we should check for a winning move
        final List<List<Integer>> legalRows = new ArrayList<>();

        for (GameRow row : manager.board()) {
            if (row.binary().asList().size() >= difference.asList().size()) {
                legalRows.add(row.binary().asList());
            }
        }

        return attemptToFindRemovableSectionOfRow(legalRows, differenceList);
    }


    //region Attempt to find removable section of a row that would lead to a winning position
    /// When I wrote this I knew how it worked. Now I don't. It works though, so DON'T TOUCH!!
    private boolean attemptToFindRemovableSectionOfRow(List<List<Integer>> rows, List<Integer> differenceList) {

        boolean hasSucceededRemovingRow = false;
        boolean hasIncreasedDifference = false;

        List<Integer> longerDifferenceList = new ArrayList<>();
        longerDifferenceList.add(0);

        /// Loop through all rows
        for (List<Integer> row : rows) {
            if (row.size() == differenceList.size()) {
                List<Integer> finalBinary = new ArrayList<>();

                for (int i = 0; i < row.size(); i++) {
                    int bit = row.get(i);
                    int differenceBit = differenceList.get(i);

                    if (differenceBit == 1) finalBinary.add(bit == 1 ? 0 : 1); else finalBinary.add(bit);
                }

                /// Check if the new binary contains any leading zeros and remove them
                boolean leadingZero = true;
                List<Integer> clearedRowList = new ArrayList<>();
                for (Integer bit : row) {
                    if (bit == 0 && leadingZero) {
                        continue;
                    }

                    leadingZero = false;
                }

                Integer indexOfMatchingRow = null;
                for (int i = 0; i < manager.board().size(); i++) {
                    GameRow rowToCheck = manager.board().get(i);

                    if (rowToCheck.binary().asList().equals(clearedRowList)) {
                        indexOfMatchingRow = i;
                        break;
                    }
                }

                int newNumberOfSticks = new Binary(finalBinary).asInt();

                boolean canRemoveRow;
                try {
                    //noinspection ConstantConditions
                    canRemoveRow = newNumberOfSticks <= manager.board().get(indexOfMatchingRow).currentNumberOfSticks();
                } catch (Exception e) {
                    canRemoveRow = false;
                }

                if (canRemoveRow) {
                    manager.removeAllToTheRight(indexOfMatchingRow, newNumberOfSticks);

                    hasSucceededRemovingRow = true;
                    break;
                }

            } else {
                if (differenceList.size() <= row.size() && !hasIncreasedDifference) {
                    longerDifferenceList.addAll(differenceList);
                    hasIncreasedDifference = true;
                }
            }
        }

        if (!hasSucceededRemovingRow) hasSucceededRemovingRow = attemptToFindRemovableSectionOfRow(rows, longerDifferenceList);

        return hasSucceededRemovingRow;
    }
    //endregion

    //endregion

}
