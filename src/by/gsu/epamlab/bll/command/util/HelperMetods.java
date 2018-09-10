package by.gsu.epamlab.bll.command.util;

import by.gsu.epamlab.bll.ConstantsBLL;

public final class HelperMetods {
    public static int[] strToArrInt(String str) {
        String[] strIds = str.split(ConstantsBLL.DELIMITER);
        int[] arrInt = new int[strIds.length];
        for (int i = 0; i < arrInt.length; i++) {
            arrInt[i] = Integer.parseInt(strIds[i]);
        }
        return arrInt;
    }
}
