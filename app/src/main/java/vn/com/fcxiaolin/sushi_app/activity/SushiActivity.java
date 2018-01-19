package vn.com.fcxiaolin.sushi_app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.ArrayList;

import vn.com.fcxiaolin.sushi_app.R;
import vn.com.fcxiaolin.sushi_app.adapter.SushiAdapter;
import vn.com.fcxiaolin.sushi_app.model.Product;

public class SushiActivity extends AppCompatActivity {
    Toolbar tbSushi;
    ListView lvSushi;
    SushiAdapter sushiAdapter;
    ArrayList<Product> listSushi;
    int idsushi = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sushi);
        initComponent();
    }
    private void initComponent(){
        tbSushi = findViewById(R.id.toolbarSushi);
        lvSushi = findViewById(R.id.listViewSushi);
        listSushi = new ArrayList<>();
        sushiAdapter = new SushiAdapter(getApplicationContext(),listSushi);
        lvSushi.setAdapter(sushiAdapter);
    }
}
