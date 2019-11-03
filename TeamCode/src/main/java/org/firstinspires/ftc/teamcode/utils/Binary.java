package org.firstinspires.ftc.teamcode.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Binary {

    //region Constructors
    public Binary(int decimal) {
        this.decimal = decimal;

        if (decimal == 0) {
            binaryList.add(0);
            return;
        }

        // This whole thing converts the number into a binary array of 0s and 1s

        double binaryExponent = Math.floor(Math.log(decimal) / Math.log(2));
        int firstIndex = (int) Math.floor(Math.pow(2, binaryExponent));

        binaryList.add(1);
        boolean shouldContinue = true;
        int index = firstIndex;
        int currentNumber = decimal - firstIndex;

        while (shouldContinue) {
            index = (int) Math.floor(index / 2);

            if (index == 0) {
                shouldContinue = false;
                continue;
            }

            if (currentNumber >= index) {
                binaryList.add(1);
                currentNumber = currentNumber - index;
            } else {
                binaryList.add(0);
            }
        }
    }

    public Binary(@NotNull List<Integer> list) {
        binaryList.addAll(list);
    }
    //endregion

    //region Properties
    final private List<Integer> binaryList = new ArrayList<>();
    private int decimal;
    //endregion

    public Binary add(@NotNull Binary otherBinary) {
        List<Integer> original = binaryList;
        List<Integer> other = otherBinary.asList();

        int higherLength = Math.max(original.size(), other.size());

        List<Integer> result = new ArrayList<>();

        for (int index = 0; index < higherLength; index++) {
            int originalArrayIndex = original.size() - higherLength + index;
            int otherArrayIndex = other.size() - higherLength + index;

            int number = 0;

            if (originalArrayIndex >= 0) {
                number += original.get(originalArrayIndex);
            }
            if (otherArrayIndex >= 0) {
                number += other.get(otherArrayIndex);
            }

            result.add(number);
        }

        return new Binary(result);
    }

    //region Getters
    public List<Integer> asList() {
        return binaryList;
    }
    public int asInt() {
        Integer total = 0;
        for (int i = 0; i < binaryList.size(); i++) {
            int bit = binaryList.get(i);
            if (bit == 1 && total != null) {
                int exponent = binaryList.size() - i - 1;
                total += (int) Math.pow(2, exponent);
            } else if (bit != 0) {
                total = null;
            }
        }

        //noinspection ConstantConditions
        return total;
    }
    boolean isEven() {
        boolean isEven = true;

        for (int bit : binaryList) {
            if (bit % 2 == 1) {
                isEven = false;
            }
        }

        return isEven;
    }
    int atIndex(int index) throws Exception {
        double inverseArrayIndex = Math.log(index) / Math.log(2);

        if (inverseArrayIndex != Math.floor(inverseArrayIndex)) {
            throw(new Exception("Binary [] operator was provided an illegal index. You must provide a number representing " +
                    "a binary digit value. The offending index is $index. Two closest legal indices are " +
                    "${Math.pow(2, inverseArrayIndex.floor())} and ${Math.pow(2, inverseArrayIndex.floor() + 1)}"));
        }

        int arrayIndex = binaryList.size() - (int) Math.floor(inverseArrayIndex) - 1;
        int bit;
        try {
            bit = binaryList.get(arrayIndex);
        } catch (Exception e) {
            bit = 0;
        }

        return bit;
    }

    @NotNull
    public String toString() {
        StringBuilder binaryString = new StringBuilder();

        for (int i = 0; i < binaryList.size(); i++) {
            if (i == 0) {
                binaryString.append(binaryList.get(i));
            } else {
                binaryString.append(" ").append(binaryList.get(i));
            }
        }

        return binaryString.toString();
    }
    //endregion

}
