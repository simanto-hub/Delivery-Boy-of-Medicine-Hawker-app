package threebigo.medicinehawker.dboy.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import threebigo.medicinehawker.dboy.R;
import threebigo.medicinehawker.dboy.helper.AppController;


public class WebViewActivity extends AppCompatActivity {

    public WebView mWebView;
    public String url,title;
    public Toolbar toolbar;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_web_view);
        toolbar = findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);


        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);
        title = getIntent ().getStringExtra ("title");
        toolbar.setTitle(title);
        url = getIntent ().getStringExtra ("link");
        mWebView = findViewById (R.id.webView1);
        mWebView.getSettings ().setJavaScriptEnabled (true);
        mWebView.getSettings ().setSupportZoom (true);
        mWebView.setWebViewClient (new WebViewClient ());

        try {
            if (AppController.isConnected (this)) {
                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                mWebView.loadUrl (url);
                findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        } catch (Exception e) {
            findViewById(R.id.progressBar).setVisibility(View.GONE);
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
