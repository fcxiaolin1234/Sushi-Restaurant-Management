package vn.com.fcxiaolin.sushi_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import vn.com.fcxiaolin.sushi_app.R;
import vn.com.fcxiaolin.sushi_app.activity.CartActivity;
import vn.com.fcxiaolin.sushi_app.activity.NavigationActivity;
import vn.com.fcxiaolin.sushi_app.model.Cart;

/**
 * Created by Dell on 1/19/2018.
 */

public class CartAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Cart> listCart;

    public CartAdapter(Context context, ArrayList<Cart> listCart) {
        this.context = context;
        this.listCart = listCart;
    }

    @Override
    public int getCount() {
        return listCart.size();
    }

    @Override
    public Object getItem(int position) {
        return listCart.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_cart_view, null);

            viewHolder.txtName = convertView.findViewById(R.id.txt_name);
            viewHolder.txtPrice = convertView.findViewById(R.id.txt_price);
            viewHolder.imgCart = convertView.findViewById(R.id.img_view_cart);
            viewHolder.btnMinus = convertView.findViewById(R.id.btn_minus);
            viewHolder.btnValue = convertView.findViewById(R.id.btn_value);
            viewHolder.btnPlus = convertView.findViewById(R.id.btn_plus);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Cart cart = (Cart) getItem(position);
        viewHolder.txtName.setText(cart.getTenSp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPrice.setText(decimalFormat.format(cart.getGiaSp()) + " VND");
        Picasso.with(context).load(cart.getImgSp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimageerror)
                .into(viewHolder.imgCart);
        viewHolder.btnValue.setText("" + cart.getSoLuong());

        int soluong = Integer.parseInt(viewHolder.btnValue.getText().toString());

        if (soluong >= 10) {
            viewHolder.btnPlus.setVisibility(View.INVISIBLE);
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
        } else if (soluong <= 1) {
            viewHolder.btnPlus.setVisibility(View.VISIBLE);
            viewHolder.btnMinus.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.btnPlus.setVisibility(View.VISIBLE);
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
        }

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newsl = Integer.parseInt(finalViewHolder.btnValue.getText().toString()) + 1;
                int cursl = NavigationActivity.listCart.get(position).getSoLuong();
                float gia = NavigationActivity.listCart.get(position).getGiaSp();

                NavigationActivity.listCart.get(position).setSoLuong(newsl);
                float newgia = (gia * newsl) / cursl;

                NavigationActivity.listCart.get(position).setGiaSp(newgia);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtPrice.setText(decimalFormat.format(newgia) + " VND");

                CartActivity.eventUltil();

                if (newsl > 9) {
                    finalViewHolder.btnPlus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(newsl));
                } else {
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(newsl));
                }
            }
        });

        viewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newsl = Integer.parseInt(finalViewHolder.btnValue.getText().toString()) - 1;
                int cursl = NavigationActivity.listCart.get(position).getSoLuong();
                float gia = NavigationActivity.listCart.get(position).getGiaSp();

                NavigationActivity.listCart.get(position).setSoLuong(newsl);
                float newgia = (gia * newsl) / cursl;

                NavigationActivity.listCart.get(position).setGiaSp(newgia);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtPrice.setText(decimalFormat.format(newgia) + " VND");

                CartActivity.eventUltil();

                if (newsl < 2) {
                    finalViewHolder.btnMinus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(newsl));
                } else {
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(newsl));
                }
            }
        });

        return convertView;
    }

    public class ViewHolder {
        public TextView txtName;
        public TextView txtPrice;
        public ImageView imgCart;
        public Button btnMinus;
        public Button btnValue;
        public Button btnPlus;
    }
}
