package com.lbi.aquery.adapters;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.androidquery.AQuery;
import com.lbi.aquery.R;
import com.lbi.aquery.entities.Feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ListViewAdapter extends ArrayAdapter<Feed>{

	private Context context;
	private int listItemResourceId;
	private List<Feed> data;
	private AQuery $;

	public ListViewAdapter(Context context,int listItemResourceId, List<Feed> data, AQuery $) {
		super(context, listItemResourceId, data);
		this.context = context;
		this.listItemResourceId = listItemResourceId;
		this.data = data;
		this.$ = $;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolderCache cacheConvertView; 
		if(convertView ==  null){
			
			cacheConvertView = new ViewHolderCache();
			
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(this.listItemResourceId, null);
			
			cacheConvertView.textView = (TextView) convertView.findViewById(R.id.name);
			cacheConvertView.imageView = (ImageView) convertView.findViewById(R.id.tb);
			cacheConvertView.progressBar = (ProgressBar) convertView.findViewById(R.id.pbar);
			
			convertView.setTag(cacheConvertView);
			
		}else{
			cacheConvertView = (ViewHolderCache) convertView.getTag();
		}
		
		JSONObject jo = getItem(position);
		String tb = jo.optJSONObject("image").optString("tbUrl");
		AQuery $$ = $.recycle(convertView);
		$$.id(cacheConvertView.textView).text(jo.optString("titleNoFormatting", "No Title"));
		$$.id(cacheConvertView.imageView).progress(cacheConvertView.progressBar).image(tb, true, true, 0, 0, null, 0, 1.0f);
		
		return convertView;
	}
	
	class ViewHolderCache{
		TextView textView;
		ImageView imageView;
		ProgressBar progressBar;
	}
}
