package com.lbi.aquerydemo.adapters;

import java.util.List;

import com.androidquery.AQuery;
import com.lbi.aquerydemo.R;
import com.lbi.aquerydemo.entities.Feed;

import android.content.Context;
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
		}else{
		    holder = (ViewHolder) convertView.getTag();
		}
		
		Feed feed  = getItem(position);
		
		AQuery $$ = $.recycle(convertView);
		
		$$.id(holder.feedTitle).text(feed.getTitle());
		$$.id(holder.feedPubDate).text(feed.getPubDate());
	    boolean memCache = true;
	    boolean fileCache = true;

	    $$.id(holder.feedThumbnail).progress(holder.feedThumbnailProgress).image(feed.getThumbnailUrl(),
                                        		                                      memCache, 
                                        		                                      fileCache,
                                        		                                      0, 
                                        		                                      R.drawable.ic_launcher,
                                        		                                      null,
                                        		                                      AQuery.FADE_IN_NETWORK,
                                        		                                      1.0f / 1.0f);
		return convertView;
	}
	
	static class ViewHolder{
		TextView feedTitle;
		TextView feedPubDate;
		ImageView feedThumbnail;
		ProgressBar feedThumbnailProgress;
	}
}
