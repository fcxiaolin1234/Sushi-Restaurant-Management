package vn.com.fcxiaolin.sushi_app.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.com.fcxiaolin.sushi_app.R;
import vn.com.fcxiaolin.sushi_app.adapter.SobaAdapter;
import vn.com.fcxiaolin.sushi_app.adapter.SushiAdapter;
import vn.com.fcxiaolin.sushi_app.model.Product;
import vn.com.fcxiaolin.sushi_app.utils.CheckConnection;
import vn.com.fcxiaolin.sushi_app.utils.Server;

public class SobaActivity extends AppCompatActivity {

    Toolbar tbSoba;
    ListView lvSoba;
    SobaAdapter sobaAdapter;
    ArrayList<Product> listSoba;
    int idsoba = 3;
    int page=1;
    private View footerView;
    boolean isLoading=false;
    mHandle nHandle;
    boolean limitData=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soba);
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            initComponent();
            getIdloaisp();
            ActionToolBar();
            getProductData( page);
            LoadMoreData();
        }else{
            CheckConnection.showMessage(getApplicationContext(),"Kiểm tra lại kết nối!");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cart:
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void LoadMoreData() {
        lvSoba.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(getApplicationContext(),DetailProduct.class);
                intent.putExtra("informationofproduct",listSoba.get(position));
                startActivity(intent);
            }
        });
        lvSoba.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if(firstVisibleItem+visibleItemCount==totalItemCount&& isLoading==false &&totalItemCount!=0&&limitData==false)
                {
                    isLoading=true;
                    Log.d("onscroll",""+firstVisibleItem+""+visibleItemCount+""+totalItemCount);
                    lvSoba.removeFooterView(footerView);
                    mThread dataThread =new mThread();
                    dataThread.start();
                }

            }
        });
    }

    private void getProductData(int page) {
        final RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String sobaurl= Server.typeProductUrl+String.valueOf(page);
        Log.d("sobaurl",""+sobaurl);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sobaurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String name="";
                float price=0;
                String image="";
                String description="";
                int idcategory=0;
                if(response!=null&&response.length()!=2)
                {
                    lvSoba.removeFooterView(footerView);
                    try
                    {
                        JSONArray jsonArray= new JSONArray(response);
                        for(int i=0; i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject= jsonArray.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            name=jsonObject.getString("name");
                            price=jsonObject.getInt("price");
                            image=jsonObject.getString("image_link");
                            description=jsonObject.getString("description");
                            idcategory=jsonObject.getInt("id_category");
                            listSoba.add(new Product(id,name,price,image,description,idcategory));
                            sobaAdapter.notifyDataSetChanged();

                        }
                    }
                    catch (Exception e)
                    {
                        Log.i("tagconvertstr", "["+e+"]");
                        e.printStackTrace();
                    }
                }
                else
                {
                    limitData=true;
                    lvSoba.removeFooterView(footerView);
                    CheckConnection.showMessage(getApplicationContext(),"Đã hết dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String > params = new HashMap<String, String>();
                params.put("idproduct",String.valueOf(idsoba));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void ActionToolBar() {
        setSupportActionBar(tbSoba);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbSoba.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getIdloaisp()
    {
        idsoba=getIntent().getIntExtra("id_category",-1);
        Log.d("gia tri loai sp",""+idsoba);
    }

    private void initComponent(){
        tbSoba = findViewById(R.id.toolbarSoba);
        lvSoba = findViewById(R.id.listViewSoba);
        listSoba = new ArrayList<>();
        sobaAdapter = new SobaAdapter(getApplicationContext(),listSoba);
        lvSoba.setAdapter(sobaAdapter);
        LayoutInflater layoutInflater=(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView=layoutInflater.inflate(R.layout.progressbar,null);
        nHandle=new mHandle();
    }
    public class mHandle extends Handler {
        @Override
        public void handleMessage (Message msg)
        {
            Log.d("what",""+msg.what);
            switch (msg.what)
            {
                case 0:
                    lvSoba.addFooterView(footerView);
                    break;
                case 1:
                    page++;
                    Log.d("handle","asdbasb");
                    Log.d("page=",""+page);
                    getProductData(page);
                    isLoading=false;
                    break;


            }
            super.handleMessage(msg);
        }

    }
    public class mThread extends  Thread {
        @Override
        public void run() {
            nHandle.sendEmptyMessage(0);
            try{
                Thread.sleep(3000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            Message msg=nHandle.obtainMessage(1);
            nHandle.sendMessage(msg);
            super.run();
        }

    }
}
