package wrteam.ekart.dboy.helper;


import android.annotation.SuppressLint;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import wrteam.ekart.dboy.R;


public class Utils {


    @SuppressLint ("ClickableViewAccessibility")
    public static void setHideShowPassword(final EditText edtPassword) {
        edtPassword.setTag (Constant.SHOW);
        edtPassword.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int DRAWABLE_RIGHT = 2;


                if (event.getAction () == MotionEvent.ACTION_UP) {
                    if (event.getRawX () >= (edtPassword.getRight () - edtPassword.getCompoundDrawables ()[DRAWABLE_RIGHT].getBounds ().width ())) {
                        if (edtPassword.getTag ().equals (Constant.SHOW)) {
                            edtPassword.setCompoundDrawablesWithIntrinsicBounds (R.drawable.ic_password, 0, R.drawable.ic_hide, 0);
                            edtPassword.setTransformationMethod (null);
                            edtPassword.setTag (Constant.HIDE);
                        } else {
                            edtPassword.setCompoundDrawablesWithIntrinsicBounds (R.drawable.ic_password, 0, R.drawable.ic_show, 0);
                            edtPassword.setTransformationMethod (new PasswordTransformationMethod ());
                            edtPassword.setTag (Constant.SHOW);
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
