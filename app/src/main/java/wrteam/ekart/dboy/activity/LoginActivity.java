package wrteam.ekart.dboy.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import wrteam.ekart.dboy.R;
import wrteam.ekart.dboy.helper.ApiConfig;
import wrteam.ekart.dboy.helper.AppController;
import wrteam.ekart.dboy.helper.Constant;
import wrteam.ekart.dboy.helper.PinView;
import wrteam.ekart.dboy.helper.Session;
import wrteam.ekart.dboy.helper.Utils;
import wrteam.ekart.dboy.helper.VolleyCallback;

public class LoginActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText edtLoginPassword, edtLoginMobile, edtProfileOldPassword, edtProfileNewPassword, edtProfileConfirmNewPassword, edtFCode, edtforgotmobile, edtResetPass, edtResetCPass;
    Button btnLogin, btnChangePassword, btnrecover, btnResetPass, btnotpverify;
    String from, fromto, mobile;
    LinearLayout lytlogin, lyt_update_password, lytforgot, lytotp, lytResetPass;
    Session session;
    Activity activity;
    PinView edtotp;
    TextView txtmobileno;

    ////Firebase
    String phoneNumber, firebase_otp;
    FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);
        toolbar = findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);
        getSupportActionBar ().setDisplayShowTitleEnabled (false);

        activity = LoginActivity.this;

        session = new Session (activity);

        btnLogin = findViewById (R.id.btnlogin);
        btnChangePassword = findViewById (R.id.btnChangePassword);
        btnrecover = findViewById (R.id.btnrecover);
        btnResetPass = findViewById (R.id.btnResetPass);
        btnotpverify = findViewById (R.id.btnotpverify);

        edtLoginPassword = findViewById (R.id.edtLoginPassword);
        edtLoginMobile = findViewById (R.id.edtLoginMobile);
        edtFCode = findViewById (R.id.edtFCode);
        edtforgotmobile = findViewById (R.id.edtforgotmobile);
        edtotp = findViewById (R.id.edtotp);
        txtmobileno = findViewById (R.id.txtmobileno);
        edtResetPass = findViewById (R.id.edtResetPass);
        edtResetCPass = findViewById (R.id.edtResetCPass);

        //layouts
        lytlogin = findViewById (R.id.lytlogin);
        lyt_update_password = findViewById (R.id.lyt_update_password);
        lytforgot = findViewById (R.id.lytforgot);
        lytotp = findViewById (R.id.lytotp);
        lytResetPass = findViewById (R.id.lytResetPass);

        from = getIntent ().getStringExtra ("from");
        fromto = getIntent ().getStringExtra ("fromto");
        mobile = getIntent ().getStringExtra ("txtmobile");
        firebase_otp = getIntent ().getStringExtra ("OTP");

        edtProfileOldPassword = findViewById (R.id.edtProfileOldPassword);
        edtProfileNewPassword = findViewById (R.id.edtProfileNewPassword);
        edtProfileConfirmNewPassword = findViewById (R.id.edtProfileConfirmNewPassword);

        edtLoginMobile.setCompoundDrawablesWithIntrinsicBounds (R.drawable.ic_phone, 0, 0, 0);

        edtLoginPassword.setCompoundDrawablesWithIntrinsicBounds (R.drawable.ic_password, 0, R.drawable.ic_show, 0);
        Utils.setHideShowPassword (edtLoginPassword);

        edtLoginPassword.setCompoundDrawablesWithIntrinsicBounds (R.drawable.ic_password, 0, R.drawable.ic_show, 0);

        Utils.setHideShowPassword (edtLoginPassword);

        edtResetPass.setCompoundDrawablesWithIntrinsicBounds (R.drawable.ic_password, 0, R.drawable.ic_show, 0);
        Utils.setHideShowPassword (edtResetPass);

        edtResetCPass.setCompoundDrawablesWithIntrinsicBounds (R.drawable.ic_password, 0, R.drawable.ic_show, 0);
        Utils.setHideShowPassword (edtResetCPass);

        edtProfileOldPassword.setCompoundDrawablesWithIntrinsicBounds (R.drawable.ic_password, 0, R.drawable.ic_show, 0);
        Utils.setHideShowPassword (edtProfileOldPassword);

        edtProfileNewPassword.setCompoundDrawablesWithIntrinsicBounds (R.drawable.ic_password, 0, R.drawable.ic_show, 0);
        Utils.setHideShowPassword (edtProfileNewPassword);

        edtProfileConfirmNewPassword.setCompoundDrawablesWithIntrinsicBounds (R.drawable.ic_password, 0, R.drawable.ic_show, 0);
        Utils.setHideShowPassword (edtProfileConfirmNewPassword);


        if (from != null) {
            if (from.equals ("lyt_update_password")) {

                lytlogin.setVisibility (View.GONE);
                lytforgot.setVisibility (View.GONE);
                lytotp.setVisibility (View.GONE);
                lytResetPass.setVisibility (View.GONE);
                lyt_update_password.setVisibility (View.VISIBLE);

                getSupportActionBar ().setDisplayHomeAsUpEnabled (true);
                setSnackBar (activity, getString (R.string.change_password_msg), getString (R.string.ok), Color.YELLOW);
            } else if (from.equals ("lytforgot")) {

                lytlogin.setVisibility (View.GONE);
                lytforgot.setVisibility (View.VISIBLE);
                lytotp.setVisibility (View.GONE);
                lytResetPass.setVisibility (View.GONE);
                lyt_update_password.setVisibility (View.GONE);
            } else if (from.equals ("lytotp")) {

                lytlogin.setVisibility (View.GONE);
                lytforgot.setVisibility (View.GONE);
                lytotp.setVisibility (View.VISIBLE);
                lytResetPass.setVisibility (View.GONE);
                lyt_update_password.setVisibility (View.GONE);

                txtmobileno.setText (getResources ().getString (R.string.please_type_verification_code_sent_to) + "  " + Constant.country_code + " " + mobile);
            } else if (from.equals ("lytResetPass")) {

                lytlogin.setVisibility (View.GONE);
                lytforgot.setVisibility (View.GONE);
                lytotp.setVisibility (View.GONE);
                lytResetPass.setVisibility (View.VISIBLE);
                lyt_update_password.setVisibility (View.GONE);

            } else if (from.equals ("login")) {
                lytlogin.setVisibility (View.VISIBLE);
                lytforgot.setVisibility (View.GONE);
                lytotp.setVisibility (View.GONE);
                lytResetPass.setVisibility (View.GONE);
                lyt_update_password.setVisibility (View.GONE);
            }
        } else {

            lytlogin.setVisibility (View.VISIBLE);
            lytforgot.setVisibility (View.GONE);
            lytotp.setVisibility (View.GONE);
            lytResetPass.setVisibility (View.GONE);
            lyt_update_password.setVisibility (View.GONE);
        }

        FirebaseInstanceId.getInstance ().getInstanceId ().addOnSuccessListener (new OnSuccessListener<InstanceIdResult> () {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token = instanceIdResult.getToken ();
                AppController.getInstance ().setDeviceToken (token);
                if (! token.equals (session.getData (Session.KEY_FCM_ID))) {
                    AppController.getInstance ().setDeviceToken (token);
                }
            }
        });

        StartFirebaseLogin ();
    }


    public void OnBtnClick(View view) {
        if (AppController.isConnected (activity)) {
            int id = view.getId ();

            if (id == R.id.btnlogin) {
                {
                    String email = edtLoginMobile.getText ().toString ();
                    String password = edtLoginPassword.getText ().toString ();

                    if (ApiConfig.CheckValidation (email, false, false)) {
                        edtLoginMobile.setError (getString (R.string.enter_mobile_number));
                    } else if (ApiConfig.CheckValidation (email, false, true)) {
                        edtLoginMobile.setError (getString (R.string.enter_valid_mobile_number));

                    } else if (ApiConfig.CheckValidation (password, false, false)) {
                        edtLoginPassword.setError (getString (R.string.password_required));
                    } else if (AppController.isConnected (activity)) {

                        ApiConfig.disableButton (activity, btnLogin);

                        Map<String, String> params = new HashMap<String, String> ();
                        params.put (Constant.MOBILE, email);
                        params.put (Constant.PASSWORD, password);
                        params.put (Constant.LOGIN, Constant.GetVal);
                        params.put (Constant.FCM_ID, "" + AppController.getInstance ().getDeviceToken ());
                        AppController.getInstance ().getDeviceToken ();
//                        System.out.println ("=============>>FCM_ID" + AppController.getInstance ().getDeviceToken ());
                        ApiConfig.RequestToVolley (new VolleyCallback () {
                            @SuppressLint ("SetTextI18n")
                            @Override
                            public void onSuccess(boolean result, String response) {
//                                System.out.println ("============" + response);
                                if (result) {
                                    try {
                                        JSONObject jsonObject = new JSONObject (response);
                                        if (! jsonObject.getBoolean (Constant.ERROR)) {
                                            StartMainActivity (jsonObject.getJSONArray (Constant.DATA).getJSONObject (0));
                                            startActivity (new Intent (activity, MainActivity.class).addFlags (Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags (Intent.FLAG_ACTIVITY_NEW_TASK));
                                        } else {
                                            setSnackBar (activity, jsonObject.getString (Constant.MESSAGE), getString (R.string.ok), Color.RED);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace ();
                                    }
                                }
                            }
                        }, activity, Constant.MAIN_URL, params, true);

                    }
                }
            } else if (id == R.id.btnChangePassword) {


                String old_password, new_password, new_confirm_password;

                old_password = edtProfileOldPassword.getText ().toString ();
                new_password = edtProfileNewPassword.getText ().toString ();
                new_confirm_password = edtProfileConfirmNewPassword.getText ().toString ();

                if (ApiConfig.CheckValidation (old_password, false, false)) {
                    edtProfileOldPassword.setError (getString (R.string.old_password_required));
                } else if (ApiConfig.CheckValidation (new_password, false, false)) {
                    edtProfileNewPassword.setError (getString (R.string.new_password_required));
                } else if (ApiConfig.CheckValidation (new_confirm_password, false, false)) {
                    edtProfileConfirmNewPassword.setError (getString (R.string.confirm_password_required));
                } else {

                    ApiConfig.disableButton (activity, btnChangePassword);

                    Map<String, String> params = new HashMap<String, String> ();
                    params.put (Constant.ID, session.getData (Constant.ID));
                    params.put (Constant.NAME, session.getData (Constant.NAME));
                    params.put (Constant.ADDRESS, session.getData (Constant.ADDRESS));
                    params.put (Constant.OLD_PASSWORD, old_password);
                    params.put (Constant.UPDATE_PASSWORD, new_password);
                    params.put (Constant.CONFIRM_PASSWORD, new_confirm_password);
                    params.put (Constant.UPDATE_DELIVERY_BOY_PROFILE, Constant.GetVal);

                    ApiConfig.RequestToVolley (new VolleyCallback () {
                        @RequiresApi (api = Build.VERSION_CODES.M)
                        @SuppressLint ("SetTextI18n")
                        @Override
                        public void onSuccess(boolean result, String response) {
                            //                        System.out.println("============RESPONSE" + response);
                            if (result) {
                                try {
                                    JSONObject jsonObject = new JSONObject (response);
                                    if (! jsonObject.getBoolean (Constant.ERROR)) {
                                        session.logoutUser (activity);
                                    } else {
                                        setSnackBar (activity, jsonObject.getString (Constant.MESSAGE), getString (R.string.ok), Color.RED);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace ();
                                }
                            }
                        }
                    }, activity, Constant.MAIN_URL, params, true);
                }
            } else if (id == R.id.tvForgotPass) {
                startActivity (new Intent (activity, LoginActivity.class).putExtra (Constant.FROM, "lytforgot").addFlags (Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags (Intent.FLAG_ACTIVITY_NEW_TASK));
            } else if (id == R.id.btnrecover) {

                RecoverPassword ();

            } else if (id == R.id.btnotpverify) {
                OTP_Varification ();

            } else if (id == R.id.btnResetPass) {
                ResetPassword ();
            }
        } else {
            setSnackBar (activity, getString (R.string.no_internet_message), getString (R.string.retry), Color.RED);
        }
    }

    public void sentRequest(String phoneNumber) {

        PhoneAuthProvider.getInstance ().verifyPhoneNumber (
                phoneNumber,                     // Phone number to verify
                100,                           // Timeout duration
                TimeUnit.SECONDS,                // Unit of timeout
                activity,        // Activity (for callback binding)
                mCallback);
    }

    public void OTP_Varification() {
        String otptext = edtotp.getText ().toString ().trim ();

        if (ApiConfig.CheckValidation (otptext, false, false)) {
            edtotp.setError (getString (R.string.enter_otp));
        } else {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential (firebase_otp, otptext);
            signInWithPhoneAuthCredential (credential, otptext);

        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential, final String otptext) {
        auth.signInWithCredential (credential)
                .addOnCompleteListener (LoginActivity.this, new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful ()) {
                            //verification successful we will start the profile activity
                            ApiConfig.disableButton (activity, btnotpverify);
                            session.setData (Constant.MOBILE, mobile);
                            startActivity (new Intent (activity, LoginActivity.class).putExtra (Constant.FROM, "lytResetPass").addFlags (Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags (Intent.FLAG_ACTIVITY_NEW_TASK));
                            edtotp.setError (null);
                        } else {

                            //verification unsuccessful.. display an error message
                            String message = "Something is wrong, we will fix it soon...";

                            if (task.getException () instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                            edtotp.setError (message);

                        }
                    }
                });
    }

    private void StartFirebaseLogin() {
        auth = FirebaseAuth.getInstance ();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks () {
            @Override
            public void onVerificationCompleted(@NotNull PhoneAuthCredential phoneAuthCredential) {
            }

            @Override
            public void onVerificationFailed(@NotNull FirebaseException e) {
                setSnackBar (activity, e.getLocalizedMessage (), getString (R.string.ok), Color.RED);
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent (s, forceResendingToken);
                Constant.verificationCode = s;
                String mobileno = "";
                mobileno = edtforgotmobile.getText ().toString ();
                startActivity (new Intent (activity, LoginActivity.class)
                        .putExtra ("from", "lytotp")
                        .putExtra ("txtmobile", mobileno)
                        .putExtra ("OTP", s).addFlags (Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags (Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        };
    }

    public void StartMainActivity(JSONObject jsonObject) {
        try {
            new Session (activity).createUserLoginSession (
                    jsonObject.getString (Constant.FCM_ID),
                    jsonObject.getString (Constant.ID),
                    jsonObject.getString (Constant.NAME),
                    jsonObject.getString (Constant.MOBILE),
                    jsonObject.getString (Constant.PASSWORD),
                    jsonObject.getString (Constant.ADDRESS),
                    jsonObject.getString (Constant.BONUS),
                    jsonObject.getString (Constant.BALANCE),
                    jsonObject.getString (Constant.STATUS),
                    jsonObject.getString (Constant.CREATED_AT));

            session.setData (Constant.BALANCE, jsonObject.getString (Constant.BALANCE));
            session.setData (Constant.ID, jsonObject.getString (Constant.ID));

            Intent intent = new Intent (activity, MainActivity.class);
            intent.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity (intent);
            finish ();
        } catch (JSONException e) {
            e.printStackTrace ();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed ();
        return super.onSupportNavigateUp ();
    }

    public void setSnackBar(final Activity activity, String message, String action, int color) {
        final Snackbar snackbar = Snackbar.make (findViewById (android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction (action, new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                snackbar.dismiss ();
                startActivity (new Intent (activity, LoginActivity.class).putExtra (Constant.FROM, "login").addFlags (Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags (Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        snackbar.setActionTextColor (color);
        View snackbarView = snackbar.getView ();
        TextView textView = snackbarView.findViewById (com.google.android.material.R.id.snackbar_text);
        textView.setMaxLines (5);
        snackbar.show ();
    }

    public void ResetPassword() {
        String newPass = edtResetPass.getText ().toString ().trim ();
        String newConfPass = edtResetCPass.getText ().toString ().trim ();
        if (newPass.equals (newConfPass)) {
            ApiConfig.disableButton (activity, btnResetPass);
            Map<String, String> params = new HashMap<String, String> ();
            params.put (Constant.MOBILE, session.getData (Constant.MOBILE));
            params.put (Constant.PASSWORD, newPass);
            params.put (Constant.DELIVERY_BOY_FORGOT_PASSWORD, Constant.GetVal);
            ApiConfig.RequestToVolley (new VolleyCallback () {
                @Override
                public void onSuccess(boolean result, String response) {
                    if (result) {
                        try {
                            JSONObject object = new JSONObject (response);

                            if (! object.getBoolean (Constant.ERROR)) {
                                startActivity (new Intent (activity, LoginActivity.class).putExtra (Constant.FROM, (Bundle) null).addFlags (Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags (Intent.FLAG_ACTIVITY_NEW_TASK));
                            } else {
                                setSnackBar (activity, object.getString (Constant.MESSAGE), getString (R.string.ok), Color.RED);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace ();
                        }
                    }
                }
            }, activity, Constant.MAIN_URL, params, true);
        } else {
            edtResetPass.setError ("New password and confirm password not matched!");
        }

    }


    public void RecoverPassword() {
        ApiConfig.disableButton (activity, btnrecover);
        final String mobile = edtforgotmobile.getText ().toString ().trim ();
        final String code = Constant.country_code = edtFCode.getText ().toString ().trim ();
        if (ApiConfig.CheckValidation (code, false, false)) {
            edtFCode.setError ("Enter Country Code");
        } else if (ApiConfig.CheckValidation (mobile, false, false)) {
            edtforgotmobile.setError ("Enter Mobile Number");

        } else if (mobile.length () != 0 && ApiConfig.CheckValidation (mobile, false, true)) {
            edtforgotmobile.setError ("Enter valid mobile number");
        } else {

            Map<String, String> params = new HashMap<String, String> ();
            params.put (Constant.MOBILE, mobile);
            params.put (Constant.CHECK_DELIVERY_BOY_BY_MOBILE, Constant.GetVal);
            ApiConfig.RequestToVolley (new VolleyCallback () {
                @Override
                public void onSuccess(boolean result, String response) {
                    if (result) {
                        try {
                            JSONObject object = new JSONObject (response);

                            if (! object.getBoolean (Constant.ERROR)) {
                                phoneNumber = "+" + code + mobile;
                                sentRequest (phoneNumber);
                            } else {
                                setSnackBar (activity, getString (R.string.non_user_msg) + getString (R.string.app_name) + getString (R.string.contact_person), getString (R.string.ok), Color.RED);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace ();
                        }
                    }
                }
            }, activity, Constant.MAIN_URL, params, true);


        }
    }
}