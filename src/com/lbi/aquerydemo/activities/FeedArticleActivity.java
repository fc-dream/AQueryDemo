package com.lbi.aquerydemo.activities;

import com.androidquery.AQuery;
import com.lbi.aquerydemo.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class FeedArticleActivity extends Activity {
    private AQuery $;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);

        setContentView(R.layout.feed_article_view);
        
        $ = new AQuery(FeedArticleActivity.this);
        
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            $.id(R.id.feed_main_title).text(extras.getString("feedTitle"));
            $.id(R.id.feed_description).text(extras.getString("feedDescription"));
            $.id(R.id.feed_pubdate).text(extras.getString("feedPubDate"));
            Bitmap preset = $.getCachedImage(extras.getString("feedMainImageUrl"));            
            $.id(R.id.feed_main_image).image(extras.getString("feedMainImageUrl"), true, true, 0, R.drawable.ic_launcher, preset, AQuery.FADE_IN, AQuery.RATIO_PRESERVE);
        }

    }
}
