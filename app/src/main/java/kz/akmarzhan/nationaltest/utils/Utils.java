package kz.akmarzhan.nationaltest.utils;

import kz.akmarzhan.nationaltest.models.FibonacciLevel;

/**
 * Created by Akmarzhan Raushanova on 5/21/2017.
 * Working on NationalTest. MobiLabs
 * You can contact me at: akmarzhan.raushnanova@is.sdu.edu.kz
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

    public static FibonacciLevel getLevelByExpereience(int exp) {
        int a = 10;
        int b = 20;
        int c;
        if(exp < a) {
            return new FibonacciLevel(0, a, 1);
        }
        if (exp < b) {
            return new FibonacciLevel(a, b, 2);
        }
        int level = 2;
        while(exp < b) {
            level++;
            c = b;
            b = a + b;
            a = c;
        }
        return new FibonacciLevel(a, b, level);
    }
}
