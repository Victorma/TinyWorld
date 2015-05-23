package dasi.util;

import java.util.Random;

/**
 * This utility class is used to handle random operations.
 *
 * @author Gorkin
 */
public final class RandomUtil {
    //****************************************************************************************************
    // Fields:
    //****************************************************************************************************
    
    private static final Random random_ = new Random();

    //****************************************************************************************************
    // Properties:
    //****************************************************************************************************
    
    public static Random getRandom() {
        return random_;
    }

    //****************************************************************************************************
    // Methods:
    //****************************************************************************************************

    public static int getNextInt() {
        return random_.nextInt();
    }

    public static int getNextInt(int max) {
        return random_.nextInt(max);
    }
}
