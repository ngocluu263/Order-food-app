package Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ozu94.ordercoffee.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import Data.Ordering;

/**
 * Created by Ozu94 on 12/19/2015.
 */
public class ListTable extends BaseAdapter {

    private Context mContext;
    private ArrayList<Ordering> arr;

    public ListTable(Context mContext, ArrayList<Ordering> arr) {
        super();
        this.mContext = mContext;
        this.arr = arr;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        int rtValue = 0;
        if (arr != null) {
            rtValue = arr.size();
        }
        return rtValue;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arr.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            // set layout cho convertView
            LayoutInflater mInflater = ((Activity) mContext).getLayoutInflater();
            row = mInflater.inflate(R.layout.activity_odering_item, parent, false);

            // init va setup ViewHolder
            holder = new ViewHolder();
            holder.tvName = (TextView) row.findViewById(R.id.tvName);
            holder.tvNumber = (TextView) row.findViewById(R.id.tvNumber);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        // lay data insert vao view
        final Ordering data = arr.get(pos);
        if (data != null) {
            holder.tvName.setText(String.valueOf(data.getNumTable()));
            holder.tvNumber.setText(String.valueOf(data.getTotalPrice()));
        }

        return row;
    }

    public class ViewHolder {
        TextView tvName;
        TextView tvNumber;
    }

}
