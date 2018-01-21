package vn.com.fcxiaolin.sushi_app.activity;

import android.content.Intent;
import android.drm.DrmStore;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vn.com.fcxiaolin.sushi_app.R;
import vn.com.fcxiaolin.sushi_app.adapter.CategoryAdapter;
import vn.com.fcxiaolin.sushi_app.adapter.ProductAdapter;
import vn.com.fcxiaolin.sushi_app.model.Cart;
import vn.com.fcxiaolin.sushi_app.model.Category;
import vn.com.fcxiaolin.sushi_app.model.Product;
import vn.com.fcxiaolin.sushi_app.utils.CheckConnection;
import vn.com.fcxiaolin.sushi_app.utils.Server;

public class NavigationActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private ViewFlipper viewFlipper;
    private NavigationView navigationView;
    private ListView listView;
    private DrawerLayout drawerLayout;
    private RecyclerView mainRecyclerView;
    private ArrayList<Category> categoryList;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Product> listProduct;
    private ProductAdapter productAdapter;

    public static ArrayList<Cart> listCart;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.navigation_screen);
//        drLayout=(DrawerLayout) findViewById(R.id.drawerlayout);
//        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//
//        drToggle=new ActionBarDrawerToggle(this,drLayout,toolbar,R.string.nav_open,R.string.nav_close);
//        drLayout.setDrawerListener(drToggle);
//       drToggle.syncState();
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        if(drToggle.onOptionsItemSelected(item))
//            return true;
//        return super.onOptionsItemSelected(item);
//    }

    private void initComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        listView = (ListView) findViewById(R.id.listView);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mainRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        categoryList = new ArrayList<Category>();
        categoryAdapter = new CategoryAdapter(categoryList, getApplicationContext());
        listView.setAdapter(categoryAdapter);
        listProduct = new ArrayList<Product>();
        productAdapter = new ProductAdapter(getApplicationContext(), listProduct);
        mainRecyclerView.setHasFixedSize(true);
        mainRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        mainRecyclerView.setAdapter(productAdapter);

        //
        if (listCart != null) {

        } else {
            listCart = new ArrayList<>();
        }

        if (drawerLayout == null)
            System.out.println("DrawerLayout not found");

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            initViewFlipperAction();
            initToolbarAction();
            getCategoryData();
            getNewProductData();
            catchOnItemListView();
        } else {
            CheckConnection.showMessage(getApplicationContext(), "Kiểm tra lại kết nối Internet");
            finish();
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

    private void catchOnItemListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(NavigationActivity.this, SushiActivity.class);
                            intent.putExtra("id_category", categoryList.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showMessage(getApplicationContext(), "Kiểm tra lại kết nối!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(NavigationActivity.this, SukiyakiActivity.class);
                            intent.putExtra("id_category", categoryList.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showMessage(getApplicationContext(), "Kiểm tra lại kết nối!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(NavigationActivity.this, SobaActivity.class);
                            intent.putExtra("id_category", categoryList.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showMessage(getApplicationContext(), "Kiểm tra lại kết nối!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(NavigationActivity.this, DrinkActivity.class);
                            intent.putExtra("id_category", categoryList.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showMessage(getApplicationContext(), "Kiểm tra lại kết nối!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(NavigationActivity.this, KhacActivity.class);
                            intent.putExtra("id_category", categoryList.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showMessage(getApplicationContext(), "Kiểm tra lại kết nối!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    // loai san pham
    private void getCategoryData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.categoryUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int id = 0;
                    String categoryName = "";
                    String imageLink = "";
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            categoryName = jsonObject.getString("name");
                            imageLink = jsonObject.getString("image_link");

                            categoryList.add(new Category(id, categoryName, imageLink));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    categoryAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showMessage(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    // san pham moi nhat
    private void getNewProductData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.newProductUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int id = 0;
                    String name = "";
                    float price = 0;
                    String image = "";
                    String description = "";
                    int idcategory = 0;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            name = jsonObject.getString("name");
                            price = (float) jsonObject.getDouble("price");
                            image = jsonObject.getString("image_link");
                            description = jsonObject.getString("description");
                            idcategory = jsonObject.getInt("id_category");
                            listProduct.add(new Product(id, name, price, image, description, idcategory));
                            productAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showMessage(getApplicationContext(), "Loi !" + error.toString());
                System.out.println(error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        initComponents();
    }

    private void initToolbarAction() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout != null)
                    drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void initViewFlipperAction() {
        ArrayList<String> imageUrls = new ArrayList<String>();
        imageUrls.add("http://www.menu-tokyo.jp/tradition/img/sb_03.jpg");
        imageUrls.add("http://www.menu-tokyo.jp/tradition/img/sb_02.jpg");
        imageUrls.add("http://www.menu-tokyo.jp/tradition/img/sk_05.jpg");
        imageUrls.add("http://www.menu-tokyo.jp/tradition/img/sk_01.jpg");

        for (int i = 0; i < imageUrls.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(imageUrls.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }

        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);

        Animation animSlideIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animSlideOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animSlideIn);
        viewFlipper.setOutAnimation(animSlideOut);
    }
}


