/**
 * Created by adampiper on 15/10/15.
 */

package com.example.adampiper.finalyearproject;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;

import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardBuilder;


public class AboutActivity extends Activity{

    View mView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mView = buildView();
    }

    private View buildView(){
        CardBuilder card = new CardBuilder(this, CardBuilder.Layout.TEXT);
        card.setText("This cycling app has been created by Adam Piper for his final year project.");
        card.addImage(R.drawable.ic_help_50);
        return card.getView();
    }
}
