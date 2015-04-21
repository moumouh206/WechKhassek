package com.mba2dna.wechkhassek.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mba2dna.wechkhassek.R;
import com.mba2dna.wechkhassek.app.AppController;
import com.mba2dna.wechkhassek.model.Artisan;

public class CustomListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<Artisan> movieItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public CustomListAdapter(Activity activity, List<Artisan> movieItems) {
		this.activity = activity;
		this.movieItems = movieItems;
	}

	@Override
	public int getCount() {
		return movieItems.size();
	}

	@Override
	public Object getItem(int location) {
		return movieItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.list_row, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		/*
		 * NetworkImageView thumbNail = (NetworkImageView) convertView
		 * .findViewById(R.id.thumbnail);
		 */
		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView rating = (TextView) convertView.findViewById(R.id.rating);
		TextView genre = (TextView) convertView.findViewById(R.id.genre);
		TextView year = (TextView) convertView.findViewById(R.id.releaseYear);
		TextView lat = (TextView) convertView.findViewById(R.id.lat);
		TextView lang = (TextView) convertView.findViewById(R.id.lang);
		TextView Calltxt = (TextView) convertView.findViewById(R.id.Calltxt);
		ImageView calls = (ImageView) convertView.findViewById(R.id.star);

		String fontBold = "fonts/NexaBold.ttf";
		String fontLight = "fonts/NexaLight.ttf";
		Typeface tf = Typeface.createFromAsset(activity.getAssets(), fontBold);
		Typeface tl = Typeface.createFromAsset(activity.getAssets(), fontLight);
		// getting movie data for the row
		Artisan m = movieItems.get(position);

		// thumbnail image
		// thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
		// thumbNail.setImageDrawable(R.drawable.logo);

		// title
		title.setText(m.getName());
		title.setTypeface(tf);
		// rating
		rating.setText(m.getspecialite());
		rating.setTypeface(tl);
		// genre
		genre.setText(m.getAdress());
		genre.setTypeface(tl);
		// release year
		year.setText(m.getTele());
		year.setVisibility(View.GONE);
		// release year
		lat.setText(m.getLat());
		lat.setVisibility(View.GONE);
		// release year
		Calltxt.setText(m.getCalls());
		Calltxt.setVisibility(View.GONE);
		// release year
		lang.setText(m.getLang());
		lang.setVisibility(View.GONE);
		if (m.getCalls() == null || Integer.valueOf(m.getCalls()) < 5) {
			calls.setVisibility(View.GONE);
		} else {
			calls.setVisibility(View.VISIBLE);
		}

		return convertView;
	}

}
