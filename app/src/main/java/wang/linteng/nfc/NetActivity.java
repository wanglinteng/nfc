package wang.linteng.nfc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NetActivity extends AppCompatActivity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        webview = (WebView) findViewById(R.id.webview);
        webview.loadUrl("http://www.linteng.wang");
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_main) {
            Intent intent = new Intent(NetActivity.this,MainActivity.class) ;
            startActivity(intent) ;
            this.finish();
        } else if (id == R.id.action_list) {
            Intent intent = new Intent(NetActivity.this,ListActivity.class) ;
            startActivity(intent) ;
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}