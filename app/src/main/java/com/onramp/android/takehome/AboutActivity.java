package com.onramp.android.takehome;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;


public class AboutActivity extends AppCompatActivity {

        private MediaPlayer mMediaPlayer;


        /*
         * OnCompletion callback that calls .release() on MediaPlayer object
         * when the MediaPlayer is finished
         */
        private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                releaseMediaPlayer();
            }
        };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.about);
            getSupportFragmentManager().beginTransaction().replace(R.id.order_container, new AboutFragment()).commit();

            final ImageButton imageButton = (ImageButton) findViewById(R.id.button);
            imageButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    if (mMediaPlayer != null){
                        if (mMediaPlayer.isPlaying()){
                            mMediaPlayer.pause();
                        } else {
                            mMediaPlayer.start();
                        }
                    } else {
                        mMediaPlayer = MediaPlayer.create(AboutActivity.this, R.raw.about);
                        mMediaPlayer.start();
                        mMediaPlayer.setOnCompletionListener(mCompletionListener);
                    }
                }
            });
        }

    /**
     * Stop media playback when user leaves activity
     */

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Method used to free up mediaplayer resources used in onStop() and onCompletionListner()
     */
    private void releaseMediaPlayer(){
        if (mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }


    /*
     *Callback for change in audio focus
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener(){
        @Override
        public void onAudioFocusChange(int focusChange){
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };

}
