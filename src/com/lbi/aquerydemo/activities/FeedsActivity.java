package com.lbi.aquerydemo.activities;

import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.androidquery.util.AQUtility;
import com.lbi.aquerydemo.R;

import com.lbi.aquerydemo.adapters.FeedListAdapter;
import com.lbi.aquerydemo.entities.Feed;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class FeedsActivity extends Activity {

    private AQuery $$;
    private ProgressDialog progressDialog;
    private ArrayAdapter<Feed> feedAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        AjaxCallback.setNetworkLimit(14);// default is 4        
        BitmapAjaxCallback.setCacheLimit(80);// default is 20

        super.onCreate(savedInstanceState);

        setContentView(R.layout.feeds_activity);

        $$ = new AQuery(FeedsActivity.this);
        $$.id(R.id.textView1).text("Hello Aquery!!");

        feedAdapter = new FeedListAdapter(FeedsActivity.this, R.layout.feed_item, new ArrayList<Feed>(), $$);

        progressDialog = new ProgressDialog(FeedsActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setInverseBackgroundForced(false);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setTitle("Fetching Feeds...");
        progressDialog.setMessage("Please wait or touch to cancel");

        String xmlFeedUrl = "https://raw.github.com/cpeppas/AQueryDemo/master/bbc_demo.xml";
        long expire = 15 * 60 * 1000;
        $$.progress(progressDialog).ajax(xmlFeedUrl, XmlPullParser.class, expire, this, "xmlPullParserCallback");

    }

    public void xmlPullParserCallback(String url, XmlPullParser xpp, AjaxStatus status) {
        Feed feedItem = null;
        try {
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if ("item".equals(xpp.getName())) {
                        feedItem = new Feed();
                    } else if (feedItem != null) {
                        if ("title".equals(xpp.getName())) {
                            feedItem.setTitle(xpp.nextText());
                        } else if ("description".equals(xpp.getName())) {
                            feedItem.setDescription(xpp.nextText());
                        } else if ("pubDate".equals(xpp.getName())) {
                            feedItem.setPubDate(xpp.nextText());
                        } else if ("thumbnail".equals(xpp.getName()) && "144".equals(xpp.getAttributeValue(null,"width"))) {
                            feedItem.setThumbnailUrl(xpp.getAttributeValue(null, "url"));
                        } else if ("thumbnail".equals(xpp.getName()) && "999".equals(xpp.getAttributeValue(null,"width"))) {
                            feedItem.setMainImageUrl(xpp.getAttributeValue(null, "url"));
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG && feedItem != null && "item".equals(xpp.getName())) {
                    feedAdapter.add(feedItem);
                    feedAdapter.notifyDataSetChanged();
                }
                eventType = xpp.next();
            }            
            $$.id(R.id.textView1).text(" "+feedAdapter.getCount()+" rss items are fetched on "+status.getDuration()+"ms");
            $$.id(R.id.feed_list).adapter(feedAdapter);
            $$.id(R.id.feed_list).itemClicked(FeedsActivity.this, "feedItemClickListener");
        }
        catch (Exception e) {
            AQUtility.report(e);
            if(status.getCode() == AjaxStatus.NETWORK_ERROR){
                Toast.makeText(FeedsActivity.this, " Network error - Internet connection is not active", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    public void feedItemClickListener(AdapterView<?> parent, View v, int pos, long id) {
        Feed feedItem = (Feed) parent.getItemAtPosition(pos);
        Intent feedArticleAtivity = new Intent(FeedsActivity.this, FeedArticleActivity.class);
        feedArticleAtivity.putExtra("feedTitle", feedItem.getTitle());        
        feedArticleAtivity.putExtra("feedDescription", feedItem.getDescription());        
        feedArticleAtivity.putExtra("feedPubDate", feedItem.getPubDate());        
        feedArticleAtivity.putExtra("feedMainImageUrl", feedItem.getMainImageUrl());        
        startActivity(feedArticleAtivity);
    }
        
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    protected void onDestroy() {
        super.onDestroy();
        // clean the file cache when root activity exit
        // the resulting total cache size will be less than 3M
        if (isTaskRoot()) {
            AQUtility.cleanCacheAsync(this);
        }
        Log.i(FeedsActivity.class.getName()," onDestroy is invoked - Cache is now cleared!!");
    }
}
