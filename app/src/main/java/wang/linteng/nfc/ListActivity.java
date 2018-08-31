package wang.linteng.nfc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import wang.linteng.tool.Sd;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 文件路径
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() +"/linteng/card.txt";
        // 检查文件是否存在
        if(!Sd.fileExist(filePath)){
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();

        }else{
            // 读取数据
            List<String> data = Sd.readTxtRow(filePath);
            // 去重
            List<String> dataUnique = Sd.removeDuplicateWithOrder(data);
            // 显示
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataUnique);
            ListView listview=(ListView)findViewById(R.id.list_view);
            listview.setAdapter(adapter);
            // 提示条数
            Toast.makeText(this, "共条"+dataUnique.size()+"记录", Toast.LENGTH_SHORT).show();
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

        if (id == R.id.action_main) {
            Intent intent = new Intent(ListActivity.this,MainActivity.class) ;
            startActivity(intent) ;
            this.finish();
        } else if (id == R.id.action_cal) {
            Intent intent = new Intent(ListActivity.this,NetActivity.class) ;
            startActivity(intent) ;
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}