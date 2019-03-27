package com.onramp.android.takehome;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class AboutActivity extends AppCompatActivity {

        private MediaPlayer mMediaPLayer;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.about);

            final Button button = (Button) findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    mMediaPLayer = MediaPlayer.create(AboutActivity.this, R.raw.about);
                    mMediaPLayer.start();
                }
            });
        }
}
