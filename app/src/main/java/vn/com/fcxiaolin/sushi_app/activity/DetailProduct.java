package vn.com.fcxiaolin.sushi_app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import vn.com.fcxiaolin.sushi_app.R;
import vn.com.fcxiaolin.sushi_app.model.Cart;
import vn.com.fcxiaolin.sushi_app.model.Product;

public class DetailProduct extends AppCompatActivity {
    Toolbar toolbar;
    TextView nameproduct, priceproduct, motaproduct;
    Spinner spinnerproduct;
    ImageView imgProduct;
    Button btnAddCart;
    //
    int id = 0;
    String name = "";
    float price = 0;
    String image = "";
    String description = "";
    int idcategory = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        initDetailProduct();
        acctionToolBar();
        getDataDetailProduct();
        CatchEventSpinner();

        //
        EventBtnAddToCart();

    }

    private void EventBtnAddToCart() {
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NavigationActivity.listCart.size() > 0) {
                    int soluong = Integer.parseInt(spinnerproduct.getSelectedItem().toString());
                    boolean exist = false;

                    for (int i = 0; i < NavigationActivity.listCart.size(); i++) {
                        if (NavigationActivity.listCart.get(i).getId() == id) {
                            NavigationActivity.listCart.get(i).setSoLuong(NavigationActivity.listCart.get(i).getSoLuong() + soluong);

                            if (NavigationActivity.listCart.get(i).getSoLuong() > 10) {
                                NavigationActivity.listCart.get(i).setSoLuong(10);
                            }

                            NavigationActivity.listCart.get(i).setGiaSp(price * NavigationActivity.listCart.get(i).getSoLuong());
                            exist = true;
                        }
                    }

                    if (!exist) {
//                        int soluong = Integer.parseInt(spinnerproduct.getSelectedItem().toString());
                        float newPrice = soluong * price;

                        NavigationActivity.listCart.add(new Cart(id, name, newPrice, image, soluong));
                    }
                } else {
                    int soluong = Integer.parseInt(spinnerproduct.getSelectedItem().toString());
                    float newPrice = soluong * price;

                    NavigationActivity.listCart.add(new Cart(id, name, newPrice, image, soluong));
                }

                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
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

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, soluong);
        spinnerproduct.setAdapter(arrayAdapter);
    }

    private void getDataDetailProduct() {
//        int id = 0;
//        String name = "";
//        float price = 0;
//        String image = "";
//        String description = "";
//        int idcategory = 0;
        Product product = (Product) getIntent().getSerializableExtra("informationofproduct");
        id = product.getId();
        name = product.getName();
        price = product.getPrice();
        image = product.getImage();
        description = product.getDescription();
        idcategory = product.getIdcategory();
        nameproduct.setText(name);
        motaproduct.setText(description);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        priceproduct.setText("Giá: " + decimalFormat.format(price) + " Đ");
        Picasso.with(getApplicationContext())
                .load(image)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimageerror)
                .into(imgProduct);
    }

    private void acctionToolBar() {
    }

    public void initDetailProduct() {
        imgProduct = (ImageView) findViewById(R.id.img_detail_product);
        nameproduct = (TextView) findViewById(R.id.name_detail_product);
        toolbar = (Toolbar) findViewById(R.id.toolbar_detail_product);
        priceproduct = (TextView) findViewById(R.id.price_detail_product);
        motaproduct = (TextView) findViewById(R.id.mota_detail_product);
        spinnerproduct = (Spinner) findViewById(R.id.spinner_detail_product);
        btnAddCart = (Button) findViewById(R.id.btn_detail_product);
    }

}
