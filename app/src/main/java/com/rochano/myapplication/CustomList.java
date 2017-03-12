package com.rochano.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rochano.myapplication.imageutils.ImageDownloaderTask;
import com.rochano.myapplication.model.Shop;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by rochano-pc on 2/27/2017.
 */

public class CustomList extends ArrayAdapter<Shop> {
    private final List<Shop> itemList;
    private static LayoutInflater inflater = null;

    public CustomList(Activity context, List<Shop> itemList) {
        super(context, R.layout.list_single, itemList);
        this.itemList = itemList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Shop getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.list_single, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) view.findViewById(R.id.title);
            holder.txtDetail = (TextView) view.findViewById(R.id.detail);
            holder.imageView = (ImageView) view.findViewById(R.id.img);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Shop item = getItem(position);
        holder.txtTitle.setText(item.getShopNameJp());
        holder.txtDetail.setText(item.getShopNameEn());
        if (holder.imageView != null) {
            new ImageDownloaderTask(holder.imageView).execute(item.getImageLogo());
        }

        return view;
    }

    static class ViewHolder {
        TextView txtTitle;
        TextView txtDetail;
        ImageView imageView;
    }
}
