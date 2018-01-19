package vn.com.fcxiaolin.sushi_app.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import vn.com.fcxiaolin.sushi_app.R;
import vn.com.fcxiaolin.sushi_app.model.Product;

/**
 * Created by admin on 19/01/2018
 */

public class SobaAdapter extends BaseAdapter{
    Context context;
    ArrayList<Product> listSoba;
    public SobaAdapter(Context context, ArrayList<Product> listSoba){
        this.context = context;
        this.listSoba = listSoba;
    }
    @Override
    public int getCount() {
        return listSoba.size();
    }

    @Override
    public Object getItem(int position) {
        return listSoba.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public  class ViewHolder{
        public TextView txtNameSoba;
        public TextView txtPriceSoba;
        public TextView txtDesSoba;
        public ImageView imgSoba;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SobaAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new SobaAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.sushi_item, null);
            viewHolder.txtNameSoba = convertView.findViewById(R.id.name_list_view);
            viewHolder.txtPriceSoba = convertView.findViewById(R.id.price_list_view);
            viewHolder.txtDesSoba = convertView.findViewById(R.id.mota_list_view);
            viewHolder.imgSoba = convertView.findViewById(R.id.img_list_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SobaAdapter.ViewHolder) convertView.getTag();
        }
        Product product = (Product) getItem(position);
        viewHolder.txtNameSoba.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPriceSoba.setText("Giá: " + decimalFormat.format(product.getPrice()) + " Đ");
        viewHolder.txtDesSoba.setMaxLines(2);
        viewHolder.txtDesSoba.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtDesSoba.setText(product.getDescription());
        Picasso.with(context).load(product.getImage())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimageerror)
                .into(viewHolder.imgSoba);
        return convertView;
    }
}
