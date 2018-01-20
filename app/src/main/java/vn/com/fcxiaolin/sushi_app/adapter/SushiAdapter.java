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
import com.squareup.picasso.Target;

import java.text.DecimalFormat;
import java.util.ArrayList;

import vn.com.fcxiaolin.sushi_app.R;
import vn.com.fcxiaolin.sushi_app.model.Product;

/**
 * Created by ASUS on 1/18/2018.
 */

public class SushiAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> listSushi;
    public SushiAdapter(Context context, ArrayList<Product> listSushi){
        this.context = context;
        this.listSushi = listSushi;
    }
    @Override
    public int getCount() {
        return listSushi.size();
    }

    @Override
    public Object getItem(int position) {
        return listSushi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public  class ViewHolder{
        public TextView txtNameSushi;
        public TextView txtPriceSushi;
        public TextView txtDesSushi;
        public ImageView imgSushi;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView ==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.sushi_item,null);
            viewHolder.txtNameSushi = convertView.findViewById(R.id.name_list_view);
            viewHolder.txtPriceSushi = convertView.findViewById(R.id.price_list_view);
            viewHolder.txtDesSushi = convertView.findViewById(R.id.mota_list_view);
            viewHolder.imgSushi = convertView.findViewById(R.id.img_list_view);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product product = (Product) getItem(position);
        viewHolder.txtNameSushi.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPriceSushi.setText("Giá: "+ decimalFormat.format(product.getPrice())+" Đ");
        viewHolder.txtDesSushi.setMaxLines(2);
        viewHolder.txtDesSushi.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtDesSushi.setText(product.getDescription());
        Picasso.with(context).load(product.getImage())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimageerror)
                .into(viewHolder.imgSushi);
        return convertView;
    }
}
