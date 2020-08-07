package wrteam.ekart.dboy.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import wrteam.ekart.dboy.R;
import wrteam.ekart.dboy.helper.AppController;


public class WebViewActivity extends AppCompatActivity {

    public WebView mWebView;
    public String url;
    public Toolbar toolbar;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_web_view);
        toolbar = findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);
        url = getIntent ().getStringExtra ("link");
        mWebView = findViewById (R.id.webView1);
        mWebView.getSettings ().setJavaScriptEnabled (true);
        mWebView.getSettings ().setSupportZoom (true);
        mWebView.setWebViewClient (new WebViewClient ());

        try {
            if (AppController.isConnected (this)) {
                mWebView.loadUrl (url);
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume ( ) {
        super.onResume ();
        mWebView.onResume ();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause ( ) {
        mWebView.onPause ();
        super.onPause ();
    }

    @Override
    protected void onDestroy ( ) {

        super.onDestroy ();
    }

    @Override
    public boolean onKeyDown ( int keyCode,KeyEvent event ) {
        if (event.getAction () == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack ()) {
                        mWebView.goBack ();
                    } else {
                        finish ();
                    }
                    return true;
            }

        }
        return super.onKeyDown (keyCode,event);
    }

    @Override
    protected void onActivityResult ( int requestCode,int resultCode,Intent intent ) {
        super.onActivityResult (requestCode,resultCode,intent);
    }

    @Override
    public boolean onOptionsItemSelected ( MenuItem item ) {
        if (item.getItemId () == android.R.id.home) {
            onBackPressed ();
            return true;
        }
        return super.onOptionsItemSelected (item);
    }

    @Override
    public void onBackPressed ( ) {
        finish ();
        super.onBackPressed ();

    }
}
