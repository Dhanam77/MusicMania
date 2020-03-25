package com.example.musicmania;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

public class MusicPlayerActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button playButton, pauseButton, stopButton;
    int pausePosition;
    private TextView songNameText;
    private SeekBar songSeeker;
    String songName, sname;
    int position;
    private ArrayList<File> songs;
    private Toolbar mToolbar;
    private Thread updateSeekbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        Initialize();

        updateSeekbar = new Thread(new Runnable() {
            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;
                songSeeker.setMax(mediaPlayer.getDuration());


                while (currentPosition < totalDuration) {
                    try {

                        Thread.sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        songSeeker.setProgress(currentPosition);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }
        });



        songSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                mediaPlayer.seekTo(seekBar.getProgress());

            }
        });


        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer == null) {
                    Uri u = Uri.parse(songs.get(position).toString());

                    mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                    mediaPlayer.start();
                } else if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(pausePosition);
                    mediaPlayer.start();
                }
                else if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                    Uri u = Uri.parse(songs.get(position).toString());

                    mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                    mediaPlayer.start();
                }
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.pause();
                    pausePosition = mediaPlayer.getCurrentPosition();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
            }
        });


    }

    private void Initialize() {

        playButton = (Button) findViewById(R.id.play_button);
        pauseButton = (Button) findViewById(R.id.pause_button);
        stopButton = (Button) findViewById(R.id.stop_button);

        songSeeker = (SeekBar) findViewById(R.id.song_seeker);
        songNameText = (TextView) findViewById(R.id.song_name);

        Intent i = getIntent();
        Bundle b = i.getExtras();

        songs = (ArrayList) b.getParcelableArrayList("songs");
        sname = songs.get(position).getName().toString();

        songName = i.getStringExtra("songName");
        songNameText.setText(songName);
        songNameText.setSelected(true);

        position = b.getInt("pos", 0);

        SetupToolbar();


    }

    private void SetupToolbar() {
        mToolbar = findViewById(R.id.music_player_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(songName);


    }
}
