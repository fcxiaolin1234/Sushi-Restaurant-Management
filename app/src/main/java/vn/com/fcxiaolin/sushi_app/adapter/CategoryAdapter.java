package vn.com.fcxiaolin.sushi_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.com.fcxiaolin.sushi_app.R;
import vn.com.fcxiaolin.sushi_app.model.Category;

/**
 * Created by HIEU on 17/01/2018.
 */

public class CategoryAdapter extends BaseAdapter{

    private ArrayList<Category> categoryList;
    private Context context;

    public CategoryAdapter(ArrayList<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder {
        TextView tvCategoryName;
        ImageView ivCategoryImage;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_category_item, null);
            viewHolder.ivCategoryImage = (ImageView) view.findViewById(R.id.imageView_category_item_image);
            viewHolder.tvCategoryName = (TextView) view.findViewById(R.id.textView_category_item_name);

            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();

        }
        Category category = (Category) getItem(i);
        viewHolder.tvCategoryName.setText(category.getName());
        Picasso.with(context).load(category.getImageLink()).placeholder(R.drawable.noimage).error(R.drawable.noimageerror).into(viewHolder.ivCategoryImage);
        return view;
    }
}
