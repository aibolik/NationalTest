package kz.akmarzhan.nationaltest.utils;

import android.util.Pair;

/**
 * Created by Aibol Kussain on 5/21/2017.
 * Working on NationalTest. MobiLabs
 * You can contact me at: aibolikdev@gmail.com
 */

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    public static boolean isValidEmail(String target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean passwordsMatch(String password1, String password2) {
        return !isEmpty(password1) && !isEmpty(password2) && password1.equals(password2);
    }

    public static boolean isEmpty(String target) {
        return target == null || target.isEmpty();
    }

    public static Pair<Integer, Integer> getLevelByExpereience(int exp) {
        int a = 10;
        int b = 20;
        int c;
        if(exp < a) {
            return new Pair<Integer, Integer>(1, a);
        }
        if (exp < b) {
            new Pair<Integer, Integer>(2, b);
        }
        int level = 2;
        while(exp < b) {
            level++;
            c = b;
            b = a + b;
            a = c;
        }
        return new Pair<Integer, Integer>(level, b);
    }
}
