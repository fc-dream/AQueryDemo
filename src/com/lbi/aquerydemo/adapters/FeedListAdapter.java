package com.lbi.aquerydemo.adapters;

import java.util.List;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.lbi.aquerydemo.R;
import com.lbi.aquerydemo.entities.Feed;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FeedListAdapter extends ArrayAdapter<Feed>{

	private Context context;
	private int feedItemResourceId;
	private AQuery $;

	public FeedListAdapter(Context context,int feedItemResourceId, List<Feed> feeds, AQuery $) {
		super(context, feedItemResourceId, feeds);
		this.context = context;
		this.feedItemResourceId = feedItemResourceId;
		this.$ = $;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder; 
		if( convertView ==  null ){
						
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(this.feedItemResourceId, null);
			holder = new ViewHolder();
			
			holder.feedTitle = (TextView) convertView.findViewById(R.id.feed_title);
			holder.feedPubDate = (TextView) convertView.findViewById(R.id.feed_date);
			holder.feedThumbnail = (ImageView) convertView.findViewById(R.id.feed_thumbnail);
			holder.feedThumbnailProgress = (ProgressBar) convertView.findViewById(R.id.feed_thumbnail_progress);
			
			convertView.setTag(holder);
			Log.i("=====","COVERTVIEW NULL for position:["+position+"] -"+" item:["+getItem(position).toString()+"]");
			
		}else{
		    holder = (ViewHolder) convertView.getTag();
		}
		
		Feed feed  = getItem(position);
		AQuery $$ = $.recycle(convertView);
		//AQuery $$ = new AQuery(convertView);
		//Log.d(FeedListAdapter.class.getName(),feed.toString());
		$$.id(holder.feedTitle).text(feed.getTitle());
		$$.id(holder.feedPubDate).text(feed.getPubDate());
		if(feed.getThumbnailUrl()!=null){		    
		    boolean memCache = true;
		    boolean fileCache = true;

		    //$$.id(cacheConvertView.feedThumbnail).progress(cacheConvertView.feedThumbnailProgress).image(feed.getThumbnailUrl(), memCache, fileCache, 0, 0, null, 0, 1.0f);
		    $$.id(holder.feedThumbnail).progress(holder.feedThumbnailProgress).image(feed.getThumbnailUrl(),memCache, fileCache,0, 0, null, 0, 1.0f / 1.0f);
		    //$$.id(cacheConvertView.feedThumbnail).progress(cacheConvertView.feedThumbnailProgress).image(feed.getThumbnailUrl(),memCache, fileCache);
		    //$$.id(cacheConvertView.feedThumbnail).progress(cacheConvertView.feedThumbnailProgress).image(feed.getThumbnailUrl(),memCache, fileCache,0, 0, null, AQuery.FADE_IN_NETWORK, 1.0f);
		    
		    
		   /* $$.id(cacheConvertView.feedThumbnail).image(feed.getThumbnailUrl(), true, true, 0, 0, new BitmapAjaxCallback(){

		        @Override
		        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status){
		                if(getItem(0)){
		                    
		                    iv.setImageBitmap(bm);
		                }

		                
		        }
		        
		});*/
		    
		    
		}
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView feedTitle;
		TextView feedPubDate;
		ImageView feedThumbnail;
		ProgressBar feedThumbnailProgress;
	}
}
