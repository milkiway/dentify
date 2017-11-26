package com.junction.healthtech.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.annotation.ColorRes;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.junction.healthtech.R;
import com.junction.healthtech.models.User;

public class Utils {
    /*public static final String URL_STORAGE_REFERENCE = "gs://coop-1c5b8.appspot.com";*/
    public static final String URL_STORAGE_REFERENCE = "gs://plameca-fde38.appspot.com";
    public static final String FOLDER_STORAGE_IMG = "avatars";
    public static final String URL_TESTS ="tests";
    public static final int IMAGE_GALLERY_REQUEST = 1;
    public static final int IMAGE_CAMERA_REQUEST = 2;
    private static final String SHARED_PREF_NAME = "com.junction.healthtech";
    public static final String AVATARS = "avatars";
    private static final String SHARED_PREF_NAME_FOR_TOKEN = "com.junction.healthtech.notificationtoken";
    public static final String NOTIFICATION_TOKEN = "notification_token";
    private static boolean switchPosition = false;

    public static <T> T convertInstanceOfObject(Object o, Class<T> clazz) {
        try {
            return clazz.cast(o);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public static void saveSwitchPosition(boolean isTestMode){
        switchPosition = isTestMode;
    }

    public static boolean getSwitchPosition(){
        return switchPosition;
    }

    public static void initToast(Context c, String message) {
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean verificaConexao(Context context) {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        conectado = conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected();
        return conectado;
    }

    public static String local(String latitudeFinal, String longitudeFinal) {
        return "https://maps.googleapis.com/maps/api/staticmap?center=" + latitudeFinal + "," + longitudeFinal + "&zoom=18&size=280x280&markers=color:red|" + latitudeFinal + "," + longitudeFinal;
    }

    public static void hideKeyboard(Window window) {
        window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

//    private static int sColors[] = {
//            R.color.userPlaceholderLightPurpleColor, R.color.userPlaceholderLightRedColor,
//            R.color.userPlaceholderLightGreenColor, R.color.userPlaceholderLightBlueColor, R.color.userPlaceholderLightOrangeColor,
//            R.color.userPlaceholderLightCyanColor, R.color.userPlaceholderLightMagentaColor
//    };

//    @ColorRes
//    public static int getPlaceholderColor(int i) {
//        return sColors[i % (sColors.length)];
//    }

    public static String getFirstLatterUpperCase(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        return String.valueOf(string.charAt(0)).toUpperCase();
    }


    public static int dpFromPx(final Context context, final float px) {
        return (int) (px / context.getResources().getDisplayMetrics().density);
    }

    public static int pxFromDp(final Context context, final float dp) {
        return (int)(dp * context.getResources().getDisplayMetrics().density);
    }

    public static SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getSharedPrefForNotificationToken(Context context) {
        return context.getSharedPreferences(SHARED_PREF_NAME_FOR_TOKEN, Context.MODE_PRIVATE);
    }

    public static String getNotificationToken(Context context) {
        return getSharedPrefForNotificationToken(context).getString(NOTIFICATION_TOKEN, null);
    }

    public static SharedPreferences.Editor getSharedPrefEditor(Context context) {
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).edit();
    }

    public static String getCurrentUserRole(Context context) {
        return getSharedPref(context).getString(User.ROLE, "");
    }

    public static String getCurrentUserPhone(Context context) {
        return getSharedPref(context).getString(User.PHONE, null);
    }

    public static String getCurrentUserName(Context context) {
        return getSharedPref(context).getString(User.USER_NAME, "User");
    }

    public static String getCurrentUserAvatar(Context context) {
        return getSharedPref(context).getString(User.USER_AVATAR, null);
    }

    public static String getCurrentUserId(Context context) {
        return getSharedPref(context).getString(User.USER_ID, null);
    }
}
