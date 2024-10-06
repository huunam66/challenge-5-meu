package com.challenge3.app.utils;

public class Helper {


    public static String toCode(long id, int zeroCodelevel, String firstCase) {

        double divideValue = (double) (id + 1) / zeroCodelevel;

//        System.out.println(firstCase + String.valueOf(divideValue).split("\\.")[1]);

        return firstCase + String.valueOf(divideValue).split("\\.")[1];
    }

    public static int calculateCountPage(int limit, int sizeList) {
        float count = (float) sizeList / limit;

        int countInt = (int) count;

        if (countInt < count) return countInt + 1;

        return countInt;
    }

}
