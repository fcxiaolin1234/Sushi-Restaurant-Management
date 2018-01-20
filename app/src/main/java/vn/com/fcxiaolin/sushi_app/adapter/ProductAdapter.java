package vn.com.fcxiaolin.sushi_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.DecimalFormat;
import java.util.ArrayList;

import vn.com.fcxiaolin.sushi_app.R;
import vn.com.fcxiaolin.sushi_app.activity.DetailProduct;
import vn.com.fcxiaolin.sushi_app.model.Product;
import vn.com.fcxiaolin.sushi_app.utils.CheckConnection;

/**
 * Created by ASUS on 1/18/2018.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemHolder> {
    Context context;
    ArrayList<Product> listProduct;

    public ProductAdapter(Context context, ArrayList<Product> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_product,null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        Product product = listProduct.get(position);
        holder.productName.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.productPrice.setText("Giá: "+ decimalFormat.format(product.getPrice())+" Đ");
        Picasso.with(context).load(product.getImage()).placeholder(R.drawable.noimage).error(R.drawable.noimageerror).into( holder.productImg);
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView productImg;
        public TextView productName,productPrice;

        public ItemHolder(View itemView) {
            super(itemView);
            productImg = (ImageView) itemView.findViewById(R.id.productImgView);
            productPrice = (TextView) itemView.findViewById(R.id.productPriceTextView);
            productName = (TextView) itemView.findViewById(R.id.productNameTextView);
            itemView.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(context,DetailProduct.class);
                    intent.putExtra("informationofproduct",listProduct.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.showMessage(context,listProduct.get(getPosition()).getName());
                    context.startActivity(intent);
                }
            });
        }
    }
}
