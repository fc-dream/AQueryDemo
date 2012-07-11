package com.lbi.aquery.activities;

import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.androidquery.util.XmlDom;
import com.lbi.aquery.R;
import com.lbi.aquery.R.id;
import com.lbi.aquery.R.layout;
import com.lbi.aquery.R.menu;
import com.lbi.aquery.entities.Feed;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {

	private AQuery $$;
	private ProgressDialog progressDialog;
	private List<Feed> feedItems;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		$$ = new AQuery(MainActivity.this);
		$$.id(R.id.textView1).text("Hello Aquery!!");

		progressDialog = new ProgressDialog(MainActivity.this);
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(true);
		progressDialog.setInverseBackgroundForced(false);
		progressDialog.setCanceledOnTouchOutside(true);
		progressDialog.setTitle("Fetching Feeds...");

		String xmlFeedUrl = "http://feeds.bbci.co.uk/news/rss.xml";
		$$.progress(progressDialog).ajax(xmlFeedUrl, XmlPullParser.class, this,
				"xmlPullParserCallback");

	}

	public void xmlPullParserCallback(String url, XmlPullParser xpp,
			AjaxStatus status) {
		feedItems = new ArrayList<Feed>();
		Feed feedItem = null;
		try {
			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					if("item".equals(xpp.getName())){
						feedItem = new Feed();
					}else if(feedItem!=null){
						if("title".equals(xpp.getName())){
							feedItem.setTitle(xpp.getText());
						}else if("description".equals(xpp.getName())){
							feedItem.setDescription(xpp.getText());
						}else if("media:thumbnail".equals(xpp.getName()) && "66".equals(xpp.getAttributeValue(null, "width"))){
							feedItem.setThumbnailUrl(xpp.getAttributeValue(null, "url"));
						}						
					}
				}else if(eventType == XmlPullParser.END_TAG && feedItem!=null && "item".equals(xpp.getName())){
					feedItems.add(feedItem);
				} 
				eventType = xpp.next();
			}
		} catch (Exception e) {
			AQUtility.report(e);
		}
		// showResult(images, status);
		$$.id(R.id.textView1).text("feeds size: " + feedItems.size());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
