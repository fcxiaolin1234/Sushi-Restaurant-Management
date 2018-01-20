package vn.com.fcxiaolin.sushi_app.adapter;

import android.content.Context;
import android.provider.BaseColumns;
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

public class SukiyakiAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> listSukiyaki;
    public SukiyakiAdapter(Context context, ArrayList<Product> listSukiyaki){
        this.context = context;
        this.listSukiyaki = listSukiyaki;
    }
    @Override
    public int getCount() {
        return listSukiyaki.size();
    }

    @Override
    public Object getItem(int position) {
        return listSukiyaki.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public  class ViewHolder{
        public TextView txtNameSukiyaki;
        public TextView txtPriceSukiyaki;
        public TextView txtDesSukiyaki;
        public ImageView imgSukiyaki;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SukiyakiAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new SukiyakiAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.sushi_item, null);
            viewHolder.txtNameSukiyaki = convertView.findViewById(R.id.name_list_view);
            viewHolder.txtPriceSukiyaki = convertView.findViewById(R.id.price_list_view);
            viewHolder.txtDesSukiyaki = convertView.findViewById(R.id.mota_list_view);
            viewHolder.imgSukiyaki = convertView.findViewById(R.id.img_list_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SukiyakiAdapter.ViewHolder) convertView.getTag();
        }
        Product product = (Product) getItem(position);
        viewHolder.txtNameSukiyaki.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPriceSukiyaki.setText("Giá: " + decimalFormat.format(product.getPrice()) + " Đ");
        viewHolder.txtDesSukiyaki.setMaxLines(2);
        viewHolder.txtDesSukiyaki.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtDesSukiyaki.setText(product.getDescription());
        Picasso.with(context).load(product.getImage())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimageerror)
                .into(viewHolder.imgSukiyaki);
        return convertView;
    }

}
