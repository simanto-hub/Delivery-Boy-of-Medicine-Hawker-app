package wrteam.ekart.dboy.helper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AlertDialog;

import wrteam.ekart.dboy.R;
import wrteam.ekart.dboy.activity.LoginActivity;

public class Session {

    public static final String PREFER_NAME = "EKart_dboy";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_BONUS = "bonus";
    public static final String KEY_BALANCE = "balance";
    public static final String KEY_STATUS = "status";
    public static final String KEY_CREATED_AT = "date_created";
    public static final String KEY_FCM_ID = "fcm_id";
    public static final String IS_USER_LOGIN = "IsUserLoggedIn";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;


    public Session(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences (PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit ();
    }

    public void createUserLoginSession(String fcmId, String id, String name, String mobile, String password, String address, String bonus, String balance, String status, String created_at) {
        editor.putBoolean (IS_USER_LOGIN, true);
        editor.putString (KEY_FCM_ID, fcmId);
        editor.putString (KEY_ID, id);
        editor.putString (KEY_NAME, name);
        editor.putString (KEY_MOBILE, mobile);
        editor.putString (KEY_PASSWORD, password);
        editor.putString (KEY_ADDRESS, address);
        editor.putString (KEY_BONUS, bonus);
        editor.putString (KEY_BALANCE, balance);
        editor.putString (KEY_STATUS, status);
        editor.putString (KEY_CREATED_AT, created_at);
        editor.commit ();
    }

    public String getData(String id) {
        return pref.getString (id, "");
    }

    public void setData(String id, String val) {
        editor.putString (id, val);
        editor.commit ();
    }

    public boolean isUserLoggedIn() {
        return pref.getBoolean (IS_USER_LOGIN, false);
    }

    public void logoutUser(final Activity activity) {

        editor.clear ();
        editor.commit ();

        Intent i = new Intent (activity, LoginActivity.class);
        i.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity (i);
        activity.finish ();

    }

    public void logoutUserConfirmation(final Activity activity) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder (_context);
        // Setting Dialog Message
        alertDialog.setTitle (R.string.logout);
        alertDialog.setMessage (R.string.logout_msg);
        alertDialog.setCancelable (false);
        alertDialog.setIcon (R.drawable.ic_logout_dialog);
        final AlertDialog alertDialog1 = alertDialog.create ();

        // Setting OK Button
        alertDialog.setPositiveButton (R.string.yes, new DialogInterface.OnClickListener () {
            public void onClick(DialogInterface dialog, int which) {
                editor.clear ();
                editor.commit ();

                Intent i = new Intent (activity, LoginActivity.class);
                i.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity (i);
                activity.finish ();
            }
        });
        alertDialog.setNegativeButton (R.string.no, new DialogInterface.OnClickListener () {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog1.dismiss ();
            }
        });
        // Showing Alert Message
        alertDialog.show ();

    }
}
