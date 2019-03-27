package com.onramp.android.takehome;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.net.URI;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.motto));

        /**
         * FAB which sends user to phone
         */
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri number = Uri.parse(getString(R.string.intent_phone_call));
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }
        });
    }

    /**
     *Create menu options consisting of ability to:
     * Order, email and choose from settings which has one option - About
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     *Logic handling option items
     * User is either sent to email, OrderActivity or AboutActivity
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);

        switch(item.getItemId()) {
            case R.id.action_email:
                sendEmail();
                break;

            case R.id.action_order:
                Intent orderIntent = new Intent(MainActivity.this, OrderActivity.class);
                startActivity(orderIntent, options.toBundle());
                break;

            case R.id.action_about:
                Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(aboutIntent, options.toBundle());
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * Email intent logic
     */

    public void sendEmail(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(getString(R.string.intent_email)));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.email_subject));
        emailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_tagline));

        if (emailIntent.resolveActivity(getPackageManager()) != null){
            startActivity(emailIntent);
            return;
        }
    }
}
