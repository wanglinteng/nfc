package wang.linteng.nfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import wang.linteng.tool.Nfc;
import wang.linteng.tool.Permission;
import wang.linteng.tool.Sd;

public class MainActivity extends AppCompatActivity {

/*
    private TextView main_uid;
    private TextView main_out;
*/

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*
        main_uid = findViewById(R.id.main_uid);
        main_out = findViewById(R.id.main_out);
*/

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(MainActivity.this, "此设备不支持NFC", Toast.LENGTH_LONG).show();
        }
        if (nfcAdapter != null && !nfcAdapter.isEnabled()) {
            Toast.makeText(MainActivity.this, "请打开NFC开关", Toast.LENGTH_LONG).show();
        }

        // sd卡读写权限
        if(Permission.verifyStoragePermissions(this)){
            System.out.println("有权限");
        }else {
            System.out.println("无权限");
            Permission.grantedStoragePermissions(this);
        }

        // 应用默认NFC
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null) {

            // 处理TAG
            String uid = "";
            Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            byte[] data = tagFromIntent.getId();
            uid += Nfc.bytesToHexString(data);//获取卡的UID
//            main_uid.setText(uid);

            // 求CHECK值
            String out = "";
            if(uid.length()!=0){
                List<Integer> list_uid = Nfc.getIntList(uid, 2);
                Integer check = list_uid.get(0) ^ list_uid.get(1) ^ list_uid.get(2) ^ list_uid.get(3);
                out = uid + Integer.toHexString(check).toUpperCase();
//                main_out.setText(out);
            } else {
                Toast.makeText(this, "标签ＩＤ为空", Toast.LENGTH_LONG).show();
            }

            // 写入文件
            Sd.writeTxtToFile(uid + "," + out, Environment.getExternalStorageDirectory().getAbsolutePath() +"/linteng/","card.txt");

            // 提示成功
            Toast.makeText(this, uid + "添加成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_list) {
            Intent intent = new Intent(MainActivity.this,ListActivity.class) ;
            startActivity(intent) ;
            this.finish();
        } else if (id == R.id.action_cal) {
            Intent intent = new Intent(MainActivity.this,NetActivity.class) ;
            startActivity(intent) ;
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Permission.REQUEST_EXTERNAL_STORAGE:
                System.out.println("授权成功");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null); //启动
    }
}
