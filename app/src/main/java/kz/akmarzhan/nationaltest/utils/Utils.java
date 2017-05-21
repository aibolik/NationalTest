package kz.akmarzhan.nationaltest.utils;

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

}
