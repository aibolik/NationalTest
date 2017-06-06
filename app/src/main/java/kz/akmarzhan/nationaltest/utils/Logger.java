package kz.akmarzhan.nationaltest.utils;

import android.util.Log;

import kz.akmarzhan.nationaltest.Application;

/**
 * Created by Akmarzhan Raushanova on 5/20/2017.
 * Working on NationalTest. MobiLabs
 * You can contact me at: akmarzhan.raushnanova@is.sdu.edu.kz
 */

public class Logger {

    public static void d(String tag, String message) {
        if(Application.DEBUG) {
            Log.d(tag, message);
        }
    }

}
