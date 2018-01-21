package vn.com.fcxiaolin.sushi_app.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.text.DecimalFormat;

import vn.com.fcxiaolin.sushi_app.R;
import vn.com.fcxiaolin.sushi_app.adapter.CartAdapter;
import vn.com.fcxiaolin.sushi_app.utils.CheckConnection;

public class CartActivity extends AppCompatActivity {

    ListView listViewCart;
    TextView txtAlert;
    static TextView txtTotal;
    Button btnCheckout;
    Button btnContShop;
    Toolbar toolbarCart;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();
        toolbarAction();
        checkData();
        eventUltil();
        onItemEvent();
        onBtnEvent();
    }

    private void onBtnEvent() {
        btnContShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                startActivity(intent);
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NavigationActivity.listCart.size() > 0) {
                    Intent intent = new Intent(getApplicationContext(), CustomerDetailActivity.class);
                    startActivity(intent);
                } else {
                    CheckConnection.showMessage(getApplicationContext(), "Empty cart");
                }
            }
        });
    }

    private void onItemEvent() {
        listViewCart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("Delete item");
                builder.setMessage("Delete this item?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (NavigationActivity.listCart.size() <= 0) {
                            txtAlert.setVisibility(View.VISIBLE);
                        } else {
                            NavigationActivity.listCart.remove(position);

                            if (NavigationActivity.listCart.size() <= 0) {
                                txtAlert.setVisibility(View.VISIBLE);
                            } else {
                                txtAlert.setVisibility(View.INVISIBLE);
                            }

                            cartAdapter.notifyDataSetChanged();
                            eventUltil();
                        }
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cartAdapter.notifyDataSetChanged();
                        eventUltil();
                    }
                });

                builder.show();

                return true;
            }
        });
    }

    public static void eventUltil() {
        float total = 0;

        for (int i = 0; i < NavigationActivity.listCart.size(); i++) {
            total += NavigationActivity.listCart.get(i).getGiaSp();
        }

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTotal.setText(decimalFormat.format(total) + " VND");
    }

    private void checkData() {
        if (NavigationActivity.listCart.size() <= 0) {
            cartAdapter.notifyDataSetChanged();
            txtAlert.setVisibility(View.VISIBLE);
            listViewCart.setVisibility(View.INVISIBLE);
        } else {
            cartAdapter.notifyDataSetChanged();
            txtAlert.setVisibility(View.INVISIBLE);
            listViewCart.setVisibility(View.VISIBLE);
        }
    }

    private void toolbarAction() {
        setSupportActionBar(toolbarCart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarCart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initComponents() {
        listViewCart = findViewById(R.id.list_view_cart);
        txtAlert = findViewById(R.id.txt_alert);
        txtTotal = findViewById(R.id.txt_total_value);
        btnCheckout = findViewById(R.id.btn_checkout);
        btnContShop = findViewById(R.id.btn_continue_shop);
        toolbarCart = findViewById(R.id.toolbar_cart);


        cartAdapter = new CartAdapter(CartActivity.this, NavigationActivity.listCart);
        listViewCart.setAdapter(cartAdapter);
    }
}
