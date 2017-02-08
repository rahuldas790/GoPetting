package rahulkumardas.gopetting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import rahulkumardas.gopetting.R;
import rahulkumardas.gopetting.models.Item;

/**
 * Created by Rahul Kumar Das on 08-02-2017.
 */

public class CartAdapter extends BaseAdapter {

    public CartAdapter(Context context, List<Item> list) {
        this.context = context;
        this.list = list;
    }

    Context context;
    List<Item> list;


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Holder holder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_cart, viewGroup, false);
            holder = new Holder();
            holder.icon = (ImageView) view.findViewById(R.id.iconImage);
            holder.name = (TextView) view.findViewById(R.id.name);

            view.setTag(holder);
        } else {

            holder = (Holder) view.getTag();

        }

//        String imgUrl = "http://api.androidhive.info/images/glide/medium/deadpool.jpg";

        Glide.with(context).load(list.get(i).image)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.icon);

        holder.name.setText(list.get(i).name);

        return view;
    }

    public class Holder {
        public ImageView icon;
        public TextView name;
    }
}
