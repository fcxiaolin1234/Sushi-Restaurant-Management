package vn.com.fcxiaolin.sushi_app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vn.com.fcxiaolin.sushi_app.R;
import vn.com.fcxiaolin.sushi_app.utils.CheckConnection;
import vn.com.fcxiaolin.sushi_app.utils.Server;

public class CustomerDetailActivity extends AppCompatActivity {

    EditText edtxtCustomer;
    EditText edtxtPhone;
    EditText edtxtEmail;
    Button btnConfirm;
    Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.customer_detail_layout);
        initComponents();

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            onBtnEvent();
        } else {
            CheckConnection.showMessage(getApplicationContext(), "No connection!");
        }
    }

    private void onBtnEvent() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = edtxtCustomer.getText().toString().trim();
                final String phone = edtxtPhone.getText().toString().trim();
                final String email = edtxtEmail.getText().toString().trim();

                if (name.length() > 0 && phone.length() > 0 && email.length() > 0) {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.orderUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String responseOrder) {
                            try {
                                Log.i("ResponseOrder", responseOrder);
                            if (Integer.parseInt(responseOrder) > 0) {
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                final StringRequest request = new StringRequest(Request.Method.POST, Server.orderDetailUrl, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")) {
                                            NavigationActivity.listCart.clear();
                                            CheckConnection.showMessage(getApplicationContext(), "Order successful");

                                            Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                                            startActivity(intent);
                                            CheckConnection.showMessage(getApplicationContext(), "Please continue shopping");
                                        }
                                        else {
                                            CheckConnection.showMessage(getApplicationContext(), "Order failed");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();

                                        for (int i = 0; i < NavigationActivity.listCart.size(); i++) {
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("id_order", responseOrder);
                                                jsonObject.put("id_product", NavigationActivity.listCart.get(i).getId());
                                                jsonObject.put("product_name", NavigationActivity.listCart.get(i).getTenSp());
                                                jsonObject.put("price", NavigationActivity.listCart.get(i).getGiaSp());
                                                jsonObject.put("quantity", NavigationActivity.listCart.get(i).getSoLuong());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            jsonArray.put(jsonObject);
                                        }

                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("json", jsonArray.toString());

                                        return hashMap;
                                    }
                                };

                                queue.add(request);
                            }}
                            catch(Exception e) {
                                e.printStackTrace();
                                }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("customer_name", name);
                            hashMap.put("phone", phone);
                            hashMap.put("email", email);
                            return hashMap;
                        }
                    };

                    requestQueue.add(stringRequest);
                } else {
                    CheckConnection.showMessage(getApplicationContext(), "Check data!");
                }
            }
        });
    }

    private void initComponents() {
        edtxtCustomer = findViewById(R.id.edittext_ten_khach_hang);
        edtxtPhone = findViewById(R.id.edittext_so_dien_thoai);
        edtxtEmail = findViewById(R.id.edittext_email);
        btnConfirm = findViewById(R.id.btn_xac_nhan);
        btnReturn = findViewById(R.id.btn_tro_ve);
    }
}
