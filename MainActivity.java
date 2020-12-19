package com.codewithshashank.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
   MediaPlayer mediaPlayer;
   ImageView imageView;
   SeekBar seekBar;
   Button play,pause,next;
   TextView textView;
   boolean isPlaying;int current=0;
   String songs[]={"kaise_hua","bekhayali","shayad","blue_eyes","ae_dil_hai_muskil","aaj_phir","love_dose","pal","hamdard",
           "chaar_botal_vodka","khariyat"};
   public void next(View view)
   {
      if(current<songs.length-1) {
          mediaPlayer.stop();
          mediaPlayer = MediaPlayer.create(this, getResources().getIdentifier(songs[++current], "raw", getPackageName()));
          seekBar.setMax(mediaPlayer.getDuration());
          textView.setText(songs[current]);
          play(play);
      }
   }
    public void previous(View view)
    {   if(current>0) {
        mediaPlayer.stop();
        mediaPlayer = MediaPlayer.create(this, getResources().getIdentifier(songs[--current], "raw", getPackageName()));
        seekBar.setMax(mediaPlayer.getDuration());
        textView.setText(songs[current]);
        play(play);
    }
    }
    public void play(View view)
    {
            mediaPlayer.start();
            isPlaying=true;
            imageView.animate().alpha(1).setDuration(2000);

            pause.animate().alpha(1).setDuration(500);
            play.animate().alpha(0.3f).setDuration(500);
    }
    public void pause(View view)
    {
        mediaPlayer.pause();
        isPlaying=false;
        imageView.animate().alpha(0.3f).setDuration(2000);
        play.animate().alpha(1).setDuration(500);
        pause.animate().alpha(0.3f).setDuration(500);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isPlaying=false;
        imageView=findViewById(R.id.imageView);
        textView=findViewById(R.id.textView);
        play=findViewById(R.id.play);
        pause=findViewById(R.id.pause);
        next=findViewById(R.id.next);
        seekBar=findViewById(R.id.seekBar);
        textView.setText(songs[0]);
        mediaPlayer=MediaPlayer.create(this,R.raw.kaise_hua);
        seekBar.setMax(mediaPlayer.getDuration());
       seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                     if(fromUser) {
                         mediaPlayer.seekTo(progress);
                         play(play);
                     }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                  mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                  mediaPlayer.start();
            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                if(mediaPlayer.getCurrentPosition()==mediaPlayer.getDuration())
                    next(next);
            }
        },0,500);
    }
}
