package Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import Data.FoodData;
import Data.GridItem;

import com.example.ozu94.ordercoffee.R;

/**
 * Created by Ozu94 on 12/13/2015.
 */
public class FoodListAdapter extends ArrayAdapter<FoodData> {

	private Context mContext;
	private int layoutResourceId;
	private ArrayList<FoodData> arr;

	public FoodListAdapter(Context mContext,int layoutResourceId, ArrayList<FoodData> arr) {
		super(mContext, layoutResourceId, arr);
		this.mContext = mContext;
		this.arr = arr;
		this.layoutResourceId = layoutResourceId;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = convertView;
		ViewHolder holder;

		if (row == null) {
			// set layout cho convertView 
			LayoutInflater mInflater = ((Activity) mContext).getLayoutInflater();
			row = mInflater.inflate(R.layout.activity_food_item, parent, false);
			
			// init va setup ViewHolder  
			holder = new ViewHolder();
			holder.ivIcon = (ImageView) row.findViewById(R.id.ivIcon);
			holder.tvNumber = (TextView) row.findViewById(R.id.tvNumber);
			holder.tvPrices = (TextView) row.findViewById(R.id.tvPrices);
			holder.tvName = (TextView) row.findViewById(R.id.tvName);
			
			// set holder cho view
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}

		// lay data insert vao view
		final FoodData data = arr.get(pos);
		if (data != null) {
			holder.tvPrices.setText(data.getprices());
			holder.tvName.setText(data.getname());
			holder.tvNumber.setText(data.getNumber());
			holder.outImage = data.getimg();
			new DownloadAsyncTask().execute(holder);
		}

		return row;
	}

	public class ViewHolder {
		ImageView ivIcon;
		TextView tvName, tvPrices, tvNumber;
		byte[] outImage;
		Bitmap theImage;
	}

	private class DownloadAsyncTask extends AsyncTask<ViewHolder, Void, ViewHolder> {

		@Override
		protected ViewHolder doInBackground(ViewHolder... params) {
			// TODO Auto-generated method stub
			//load image directly
			ViewHolder viewHolder = params[0];
			try {
				//convert byte to bitmap take from contact class
				ByteArrayInputStream imageStream = new ByteArrayInputStream(viewHolder.outImage);
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 8;
				viewHolder.theImage = BitmapFactory.decodeStream(imageStream, null, options);
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("error", "Load Image Failed");
				viewHolder.theImage = null;
			}
			return viewHolder;
		}

		@Override
		protected void onPostExecute(ViewHolder result) {
			// TODO Auto-generated method stub
			if (result.theImage == null) {
				result.ivIcon.setImageResource(R.drawable.postthumb_loading);
			} else {
				result.ivIcon.setImageBitmap(result.theImage);
			}
		}
	}
}
