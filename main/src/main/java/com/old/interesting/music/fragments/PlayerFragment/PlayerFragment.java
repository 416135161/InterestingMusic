package com.old.interesting.music.fragments.PlayerFragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.PresetReverb;
import android.media.audiofx.Visualizer;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lauzy.freedom.library.Lrc;
import com.lauzy.freedom.library.LrcHelper;
import com.lauzy.freedom.library.LrcView;
import com.old.interesting.music.R;
import com.old.interesting.music.activities.HomeActivity;
import com.old.interesting.music.clickitemtouchlistener.ClickItemTouchListener;
import com.old.interesting.music.customviews.CustomProgressBar;
import com.old.interesting.music.imageLoader.ImageLoader;
import com.old.interesting.music.lyrics.Lyrics;
import com.old.interesting.music.models.LocalTrack;
import com.old.interesting.music.models.SavedDNA;
import com.old.interesting.music.models.Track;
import com.old.interesting.music.models.UnifiedTrack;
import com.old.interesting.music.notificationmanager.AudioPlayerBroadcastReceiver;
import com.old.interesting.music.snappyrecyclerview.CustomAdapter;
import com.old.interesting.music.snappyrecyclerview.SnappyRecyclerView;
import com.old.interesting.music.utilities.DownloadThread;
import com.old.interesting.music.visualizers.VisualizerView;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import newui.data.util.CloudDataUtil;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFragment extends Fragment implements
        AudioPlayerBroadcastReceiver.onCallbackListener {

    public SnappyRecyclerView snappyRecyclerView;
    CustomAdapter customAdapter;

    public static VisualizerView mVisualizerView;
    public static MediaPlayer mMediaPlayer;
    public static Visualizer mVisualizer;
    public static Equalizer mEqualizer;
    public static BassBoost bassBoost;
    public static PresetReverb presetReverb;

    public static boolean isReplayIconVisible = false;

    public static boolean isPrepared = false;

    AVLoadingIndicatorView bufferingIndicator;

    public static CustomProgressBar cpb;

    Pair<String, String> temp;

    TextView currTime, totalTime;

    public ImageView repeatController;
    public ImageView shuffleController;

    public ImageView equalizerIcon;
    public ImageView mainTrackController;
    public ImageView nextTrackController;
    public ImageView previousTrackController;
    public ImageView favouriteIcon;
    public ImageView queueIcon;

    public ImageView saveDNAToggle;

    boolean isFav = false;

    public RelativeLayout bottomContainer;
    public RelativeLayout seekBarContainer;
    public RelativeLayout toggleContainer;

    public ImageView selected_track_image;
    public TextView selected_track_title;
    public TextView selected_track_artist;
    public ImageView player_controller;

    public RelativeLayout smallPlayer;

    ImageView favControllerSp, nextControllerSp;

    ImageLoader imgLoader;
    LrcView mLrcView;

    public SeekBar progressBar;

    public static int durationInMilliSec;
    static boolean completed = false;
    public boolean pauseClicked = false;
    boolean isTracking = false;

    public static boolean localIsPlaying = false;

    public Timer timer;

    public static Track track;
    public static LocalTrack localTrack;

    static boolean isRefreshed = false;

    public PlayerFragmentCallbackListener mCallback;
    public onPlayPauseListener mCallback7;


    public interface PlayerFragmentCallbackListener {
        void onComplete();

        void onPreviousTrack();

        void onEqualizerClicked();

        void onQueueClicked();

        void onPrepared();

        void onFullScreen();

        void onSettingsClicked();

        void onAddedtoFavfromPlayer();

        void onShuffleEnabled();

        void onShuffleDisabled();

        void onSmallPlayerTouched();

        void addCurrentSongtoPlaylist(UnifiedTrack ut);
    }

    public interface onPlayPauseListener {
        void onPlayPause();
    }

    HomeActivity homeActivity;
    public static Context ctx;

    public RelativeLayout spToolbar;
    ImageView overflowMenuAB;
    ImageView spImgAB;
    TextView spTitleAB;
    TextView spArtistAB;

    TextView lyricsStatus;
    AVLoadingIndicatorView lyricsLoadingIndicator;
    public DownloadThread downloadThread;
    public RelativeLayout lyricsContainer;
    public ImageView lyricsIcon;
    public boolean isLyricsVisisble = false;


    public boolean isStart = true;

    long startTrack;
    long endTrack;

    public PlayerFragment() {
        // Required empty public constructor
    }

    public void togglePlayPause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            if (homeActivity.isPlayerVisible) {
                mainTrackController.setImageResource(R.drawable.ic_play_arrow_white_48dp);
                player_controller.setImageResource(R.drawable.ic_queue_music_white_48dp);
                isReplayIconVisible = false;
            } else {
                player_controller.setImageResource(R.drawable.ic_play_arrow_white_48dp);
                mainTrackController.setImageResource(R.drawable.ic_play_arrow_white_48dp);
                isReplayIconVisible = false;
            }
            if (mVisualizer != null)
                mVisualizer.setEnabled(false);
            if (!isStart && mCallback != null)
                mCallback7.onPlayPause();
        } else {
            if (!completed) {
                if (mVisualizer != null)
                    mVisualizer.setEnabled(true);
                if (homeActivity.isPlayerVisible) {
                    mainTrackController.setImageResource(R.drawable.ic_pause_white_48dp);
                    player_controller.setImageResource(R.drawable.ic_queue_music_white_48dp);
                    isReplayIconVisible = false;
                } else {
                    mainTrackController.setImageResource(R.drawable.ic_pause_white_48dp);
                    player_controller.setImageResource(R.drawable.ic_pause_white_48dp);
                    isReplayIconVisible = false;
                }
                mMediaPlayer.start();
                refreshTime();
                if (!isStart && mCallback != null)
                    mCallback7.onPlayPause();
            } else {
                mMediaPlayer.seekTo(0);

                if (mVisualizer != null)
                    mVisualizer.setEnabled(true);
                mMediaPlayer.start();
                refreshTime();
                completed = false;
                if (homeActivity.isPlayerVisible) {
                    mainTrackController.setImageResource(R.drawable.ic_pause_white_48dp);
                    player_controller.setImageResource(R.drawable.ic_pause_white_48dp);
                    isReplayIconVisible = false;
                } else {
                    mainTrackController.setImageResource(R.drawable.ic_pause_white_48dp);
                    player_controller.setImageResource(R.drawable.ic_pause_white_48dp);
                    isReplayIconVisible = false;
                }
            }
        }
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        homeActivity = (HomeActivity) context;
        ctx = context;
        try {
            mCallback = (PlayerFragmentCallbackListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                if (downloadThread != null) {
                    downloadThread.interrupt();
                }
                if (isLyricsVisisble) {
                    isLyricsVisisble = false;
                    lyricsContainer.setVisibility(View.GONE);
                    lyricsIcon.setAlpha(0.5f);
                    mVisualizerView.setVisibility(View.INVISIBLE);
                }
                completed = false;
                pauseClicked = false;
                isPrepared = true;
                mCallback.onPrepared();
                if (homeActivity.isPlayerVisible) {
                    player_controller.setVisibility(View.VISIBLE);
                    player_controller.setImageResource(R.drawable.ic_queue_music_white_48dp);
                } else {
                    player_controller.setVisibility(View.VISIBLE);
                    player_controller.setImageResource(R.drawable.ic_pause_white_48dp);
                }
                togglePlayPause();
                togglePlayPause();
                togglePlayPause();
                bufferingIndicator.setVisibility(View.GONE);
                mainTrackController.setVisibility(View.VISIBLE);
                equalizerIcon.setVisibility(View.INVISIBLE);

                snappyRecyclerView.setCurrentPosition(HomeActivity.queueCurrentIndex);
                snappyRecyclerView.setTransparency();

                new HomeActivity.SaveQueue().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                new HomeActivity.SaveRecents().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            }
        });

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                completed = true;
                if (homeActivity.isPlayerVisible) {
                    mainTrackController.setImageResource(R.drawable.ic_replay_white_48dp);
                    player_controller.setImageResource(R.drawable.ic_replay_white_48dp);
                    isReplayIconVisible = true;
                } else {
                    player_controller.setImageResource(R.drawable.ic_replay_white_48dp);
                    mainTrackController.setImageResource(R.drawable.ic_replay_white_48dp);
                    isReplayIconVisible = true;
                }
                new SaveDNA().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });

        mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                double ratio = percent / 100.0;
                double bufferingLevel = (int) (mp.getDuration() * ratio);
                if (progressBar != null) {
                    progressBar.setSecondaryProgress((int) bufferingLevel);
                }
            }
        });

        mMediaPlayer.setOnErrorListener(
                new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        return true;
                    }
                }
        );

        mMediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        bufferingIndicator.setVisibility(View.VISIBLE);
                        mainTrackController.setVisibility(View.GONE);
                        isPrepared = false;
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        bufferingIndicator.setVisibility(View.GONE);
                        mainTrackController.setVisibility(View.VISIBLE);
                        isPrepared = true;
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (snappyRecyclerView.getCurrentPosition() != HomeActivity.queueCurrentIndex) {
            snappyRecyclerView.scrollToPosition(HomeActivity.queueCurrentIndex);
            snappyRecyclerView.setCurrentPosition(HomeActivity.queueCurrentIndex);
            customAdapter.notifyDataSetChanged();
        }
        snappyRecyclerView.setTransparency();
    }

    @Override
    public void onCallbackCalled(int i) {
        switch (i) {
            case 6:
                mCallback.onPrepared();
                break;
            case 3:
                if (homeActivity.queueCurrentIndex != 0) {
                    mMediaPlayer.pause();
                    mCallback.onPreviousTrack();
                }
                break;
            case 2:
                mMediaPlayer.pause();
                homeActivity.nextControllerClicked = true;
                mCallback.onComplete();
                break;
        }
    }

    @Override
    public void togglePLayPauseCallback() {
        togglePlayPause();
    }

    @Override
    public boolean getPauseClicked() {
        return pauseClicked;
    }

    @Override
    public void setPauseClicked(boolean bool) {
        pauseClicked = bool;
    }

    @Override
    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    private void showLrc(){
        lyricsLoadingIndicator.setVisibility(View.GONE);
        if (homeActivity.streamSelected) {
            initLrc(track.getLrc());
            lyricsStatus.setVisibility(View.GONE);
        } else {
            lyricsStatus.setText("No Lyrics Found!");
            lyricsStatus.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgLoader = new ImageLoader(getContext());

        smallPlayer = (RelativeLayout) view.findViewById(R.id.smallPlayer);
        mLrcView = view.findViewById(R.id.lrc_view);
        mLrcView.setOnPlayIndicatorLineListener(new LrcView.OnPlayIndicatorLineListener() {
            @Override
            public void onPlay(long time, String content) {
                if (isPrepared && !isTracking && getActivity() != null){
                    mMediaPlayer.seekTo((int) time);
                }
            }
        });
        snappyRecyclerView = (SnappyRecyclerView) view.findViewById(R.id.visualizer_recycler);

        bufferingIndicator = (AVLoadingIndicatorView) view.findViewById(R.id.bufferingIndicator);
        currTime = (TextView) view.findViewById(R.id.currTime);
        totalTime = (TextView) view.findViewById(R.id.totalTime);

        repeatController = (ImageView) view.findViewById(R.id.repeat_controller);
        shuffleController = (ImageView) view.findViewById(R.id.shuffle_controller);

        spToolbar = (RelativeLayout) view.findViewById(R.id.smallPlayer_AB);

        overflowMenuAB = (ImageView) view.findViewById(R.id.menuIcon);

        lyricsContainer = (RelativeLayout) view.findViewById(R.id.lyrics_container);
        lyricsIcon = (ImageView) view.findViewById(R.id.lyrics_icon);
        lyricsLoadingIndicator = (AVLoadingIndicatorView) view.findViewById(R.id.lyrics_loading_indicator);
        lyricsStatus = (TextView) view.findViewById(R.id.lyrics_status_text);

        lyricsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLyricsVisisble) {
                    lyricsIcon.setAlpha(1.0f);
                    mVisualizerView.setVisibility(View.GONE);
                    lyricsContainer.setVisibility(View.VISIBLE);
                    lyricsLoadingIndicator.setVisibility(View.VISIBLE);
                    lyricsStatus.setText("Searching Lyrics");
                    lyricsStatus.setVisibility(View.VISIBLE);
                    showLrc();
                } else {
                    lyricsIcon.setAlpha(0.5f);
                    lyricsContainer.setVisibility(View.GONE);
                    mVisualizerView.setVisibility(View.INVISIBLE);
                }
                isLyricsVisisble = !isLyricsVisisble;
            }
        });



        spImgAB = (ImageView) view.findViewById(R.id.selected_track_image_sp_AB);
        spImgAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!homeActivity.isPlayerTransitioning && smallPlayer != null) {
                    homeActivity.hidePlayer();
                    homeActivity.isPlayerVisible = false;
                }
            }
        });
        spTitleAB = (TextView) view.findViewById(R.id.selected_track_title_sp_AB);
        spTitleAB.setSelected(true);

        spArtistAB = (TextView) view.findViewById(R.id.selected_track_artist_sp_AB);

        if (homeActivity.shuffleEnabled) {
            shuffleController.setImageResource(R.drawable.ic_shuffle_filled);
        } else {
            shuffleController.setImageResource(R.drawable.ic_shuffle_alpha);
        }

        if (homeActivity.repeatEnabled) {
            repeatController.setImageResource(R.drawable.ic_repeat_filled);
        } else if (homeActivity.repeatOnceEnabled) {
            repeatController.setImageResource(R.drawable.ic_repeat_once);
        } else {
            repeatController.setImageResource(R.drawable.ic_repeat_alpha);
        }

        repeatController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (homeActivity.repeatOnceEnabled) {
                    homeActivity.repeatOnceEnabled = false;
                    repeatController.setImageResource(R.drawable.ic_repeat_alpha);
                } else if (homeActivity.repeatEnabled) {
                    homeActivity.repeatEnabled = false;
                    homeActivity.repeatOnceEnabled = true;
                    repeatController.setImageResource(R.drawable.ic_repeat_once);
                } else {
                    homeActivity.repeatEnabled = true;
                    repeatController.setImageResource(R.drawable.ic_repeat_filled);
                }
            }
        });

        shuffleController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (homeActivity.shuffleEnabled) {
                    homeActivity.shuffleEnabled = false;
                    shuffleController.setImageResource(R.drawable.ic_shuffle_alpha);
                    mCallback.onShuffleDisabled();
                } else {
                    homeActivity.shuffleEnabled = true;
                    shuffleController.setImageResource(R.drawable.ic_shuffle_filled);
                    mCallback.onShuffleEnabled();
                }
                snappyRecyclerView.scrollToPosition(HomeActivity.queueCurrentIndex);
                snappyRecyclerView.setCurrentPosition(HomeActivity.queueCurrentIndex);
                customAdapter.notifyDataSetChanged();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        snappyRecyclerView.setTransparency();
                    }
                }, 400);
            }
        });

        equalizerIcon = (ImageView) view.findViewById(R.id.equalizer_icon);
        equalizerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onEqualizerClicked();
            }
        });
        equalizerIcon.setVisibility(View.INVISIBLE);

        saveDNAToggle = (ImageView) view.findViewById(R.id.toggleSaveDNA);
        if (homeActivity.isSaveDNAEnabled) {
            saveDNAToggle.setImageResource(R.drawable.ic_save_filled);
        } else {
            saveDNAToggle.setImageResource(R.drawable.ic_save_alpha);
        }

        saveDNAToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (homeActivity.isSaveDNAEnabled) {
                    homeActivity.isSaveDNAEnabled = false;
                    saveDNAToggle.setImageResource(R.drawable.ic_save_alpha);
                } else {
                    homeActivity.isSaveDNAEnabled = true;
                    saveDNAToggle.setImageResource(R.drawable.ic_save_filled);
                }
            }
        });

        mainTrackController = (ImageView) view.findViewById(R.id.controller);
        nextTrackController = (ImageView) view.findViewById(R.id.next);
        previousTrackController = (ImageView) view.findViewById(R.id.previous);

        isFav = false;

        favouriteIcon = (ImageView) view.findViewById(R.id.fav_icon);
        favControllerSp = (ImageView) view.findViewById(R.id.fav_controller_sp);

        if (homeActivity.isFavourite) {
            favouriteIcon.setImageResource(R.drawable.ic_heart_filled_1);
            favControllerSp.setImageResource(R.drawable.ic_heart_filled_1);
            isFav = true;
        } else {
            favouriteIcon.setImageResource(R.drawable.ic_heart_alpha);
            favControllerSp.setImageResource(R.drawable.ic_heart_alpha);
            isFav = false;
        }

        favouriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFav) {
                    favouriteIcon.setImageResource(R.drawable.ic_heart_alpha);
                    favControllerSp.setImageResource(R.drawable.ic_heart_alpha);
                    isFav = false;
                    removeFromFavourite();
                } else {
                    favouriteIcon.setImageResource(R.drawable.ic_heart_filled_1);
                    favControllerSp.setImageResource(R.drawable.ic_heart_filled_1);
                    isFav = true;
                    addToFavourite();
                }
                new HomeActivity.SaveFavourites().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });

        favControllerSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFav) {
                    favouriteIcon.setImageResource(R.drawable.ic_heart_alpha);
                    favControllerSp.setImageResource(R.drawable.ic_heart_alpha);
                    isFav = false;
                    removeFromFavourite();
                } else {
                    favouriteIcon.setImageResource(R.drawable.ic_heart_filled_1);
                    favControllerSp.setImageResource(R.drawable.ic_heart_filled_1);
                    isFav = true;
                    addToFavourite();
                }
                new HomeActivity.SaveFavourites().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });

        queueIcon = (ImageView) view.findViewById(R.id.queue_icon);
        queueIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onQueueClicked();
            }
        });

        selected_track_image = (ImageView) view.findViewById(R.id.selected_track_image_sp);
        selected_track_title = (TextView) view.findViewById(R.id.selected_track_title_sp);
        selected_track_artist = (TextView) view.findViewById(R.id.selected_track_artist_sp);
        player_controller = (ImageView) view.findViewById(R.id.player_control_sp);

        nextControllerSp = (ImageView) view.findViewById(R.id.next_controller_sp);

        nextControllerSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.pause();
                homeActivity.nextControllerClicked = true;
                mCallback.onComplete();
            }
        });

        bottomContainer = (RelativeLayout) view.findViewById(R.id.mainControllerContainer);
        seekBarContainer = (RelativeLayout) view.findViewById(R.id.seekBarContainer);
        toggleContainer = (RelativeLayout) view.findViewById(R.id.toggleContainer);

        mVisualizerView = (VisualizerView) view.findViewById(R.id.myvisualizerview);

        VisualizerView.w = HomeActivity.screen_width;
        VisualizerView.h = HomeActivity.screen_height;
        VisualizerView.conf = Bitmap.Config.ARGB_8888;
        VisualizerView.bmp = Bitmap.createBitmap(VisualizerView.w, VisualizerView.h, VisualizerView.conf);
        HomeActivity.cacheCanvas = new Canvas(VisualizerView.bmp);

        if (HomeActivity.queue != null) {
            customAdapter = new CustomAdapter(getContext(), snappyRecyclerView, HomeActivity.queue.getQueue());
        } else {
            customAdapter = new CustomAdapter(getContext(), snappyRecyclerView, new ArrayList<UnifiedTrack>());
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        snappyRecyclerView.setLayoutManager(linearLayoutManager);
        snappyRecyclerView.setItemAnimator(new DefaultItemAnimator());
        snappyRecyclerView.setAdapter(customAdapter);
        snappyRecyclerView.setActivity(homeActivity);

        snappyRecyclerView.addOnItemTouchListener(new ClickItemTouchListener(snappyRecyclerView) {
            @Override
            public boolean onClick(RecyclerView parent, View view, int position, long id) {
                return false;
            }

            @Override
            public boolean onLongClick(RecyclerView parent, View view, int position, long id) {
                if (homeActivity.isFullScreenEnabled) {
                    homeActivity.isFullScreenEnabled = false;
                    bottomContainer.setVisibility(View.VISIBLE);
                    seekBarContainer.setVisibility(View.VISIBLE);
                    toggleContainer.setVisibility(View.VISIBLE);
                    spToolbar.setVisibility(View.VISIBLE);
                    mCallback.onFullScreen();
                } else {
                    homeActivity.isFullScreenEnabled = true;
                    bottomContainer.setVisibility(View.GONE);
                    seekBarContainer.setVisibility(View.GONE);
                    toggleContainer.setVisibility(View.GONE);
                    spToolbar.setVisibility(View.INVISIBLE);
                    mCallback.onFullScreen();
                }
                return true;
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


        cpb = (CustomProgressBar) view.findViewById(R.id.customProgress);

        smallPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onSmallPlayerTouched();
            }
        });


        track = homeActivity.selectedTrack;
        localTrack = homeActivity.localSelectedTrack;

        if (homeActivity.streamSelected) {
            try {
                durationInMilliSec = track.getDuration();
            } catch (Exception e) {

            }
            if (track.getArtworkURL() != null) {
                Picasso.with(getActivity()).load(track.getArtworkURL()).resize(100, 100).into(selected_track_image);
            } else {
                selected_track_image.setImageResource(R.drawable.ic_default);
            }
            try {
                spTitleAB.setText(track.getTitle());
                selected_track_title.setText(track.getTitle());
                selected_track_artist.setText("");
                spArtistAB.setText("");
            } catch (Exception e) {
            }

        } else {
            try {
                durationInMilliSec = (int) localTrack.getDuration();
            } catch (Exception e) {

            }
            try {
                imgLoader.DisplayImage(localTrack.getPath(), selected_track_image);
            } catch (Exception e) {

            }
            try {
                spTitleAB.setText(localTrack.getTitle());
                selected_track_title.setText(localTrack.getTitle());
                selected_track_artist.setText(localTrack.getArtist());
                spArtistAB.setText(localTrack.getArtist());
            } catch (Exception e) {

            }

        }


        homeActivity.mWifi = homeActivity.connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        try {
            if (homeActivity.streamSelected) {
                if ((homeActivity.settings.isStreamOnlyOnWifiEnabled() && homeActivity.mWifi.isConnected()) || !homeActivity.settings
                        .isStreamOnlyOnWifiEnabled()) {
                    isPrepared = false;
                    mMediaPlayer.reset();
                    mMediaPlayer.setDataSource(track.getStreamURL());
                    mMediaPlayer.prepareAsync();
                    durationInMilliSec = mMediaPlayer.getDuration();
                    track.mDuration = durationInMilliSec;
                } else {
                    mMediaPlayer.pause();
                    homeActivity.nextControllerClicked = true;
                    mCallback.onComplete();
                }
            } else {
                isPrepared = false;
                mMediaPlayer.reset();
                File f = new File(localTrack.getPath());
                if (f.exists()) {
                    mMediaPlayer.setDataSource(localTrack.getPath());
                    mMediaPlayer.prepareAsync();
                } else {
                    Toast.makeText(getContext(), "Error playing " + localTrack.getTitle() + ". Skipping to next track", Toast.LENGTH_SHORT).show();
                    mMediaPlayer.pause();
                    homeActivity.nextControllerClicked = true;
                    mCallback.onComplete();
                }
            }
            bufferingIndicator.setVisibility(View.VISIBLE);
            mainTrackController.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        temp = getTime(durationInMilliSec);
        totalTime.setText(temp.first + ":" + temp.second);

        overflowMenuAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popMenu = new PopupMenu(getContext(), overflowMenuAB);
                popMenu.getMenuInflater().inflate(R.menu.player_overflow_menu, popMenu.getMenu());

                popMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Full Screen")) {
                            if (homeActivity.isFullScreenEnabled) {
                                homeActivity.isFullScreenEnabled = false;
                                bottomContainer.setVisibility(View.VISIBLE);
                                seekBarContainer.setVisibility(View.VISIBLE);
                                toggleContainer.setVisibility(View.VISIBLE);
                                spToolbar.setVisibility(View.VISIBLE);
                                mCallback.onFullScreen();
                            } else {
                                homeActivity.isFullScreenEnabled = true;
                                bottomContainer.setVisibility(View.GONE);
                                seekBarContainer.setVisibility(View.GONE);
                                toggleContainer.setVisibility(View.GONE);
                                spToolbar.setVisibility(View.INVISIBLE);
                                mCallback.onFullScreen();
                            }
                        } else if (item.getTitle().equals("Settings")) {
                            mCallback.onSettingsClicked();
                        } else if (item.getTitle().equals("Add to playlist")) {
                            UnifiedTrack ut = HomeActivity.queue.getQueue().get(HomeActivity.queueCurrentIndex);
                            mCallback.addCurrentSongtoPlaylist(ut);
                        }
                        return true;
                    }
                });

                popMenu.show();
            }
        });


        player_controller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReplayIconVisible) {
                    homeActivity.hasQueueEnded = true;
                    mCallback.onComplete();
                } else {
                    if (!pauseClicked) {
                        pauseClicked = true;
                    }
                    togglePlayPause();
                }
            }
        });

        mainTrackController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReplayIconVisible) {
                    homeActivity.hasQueueEnded = true;
                    mCallback.onComplete();
                } else {
                    if (!pauseClicked) {
                        pauseClicked = true;
                    }
                    togglePlayPause();
                }
            }
        });

        nextTrackController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.pause();
                homeActivity.nextControllerClicked = true;
                mCallback.onComplete();
            }
        });

        previousTrackController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (homeActivity.queueCurrentIndex != 0) {
                    mMediaPlayer.pause();
                    mCallback.onPreviousTrack();
                }
            }
        });

        progressBar = (SeekBar) view.findViewById(R.id.progressBar);
        progressBar.setMax(durationInMilliSec);

        timer = new Timer();
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        if (isPrepared && !isTracking && getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    float[] hsv = new float[3];
                                    hsv[0] = homeActivity.seekBarColor;
                                    hsv[1] = (float) 0.8;
                                    hsv[2] = (float) 0.5;
                                    progressBar.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(Color.HSVToColor(hsv), PorterDuff.Mode.SRC_IN));
                                    cpb.update();
                                }
                            });
                            try {
                                temp = getTime(mMediaPlayer.getCurrentPosition());
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        currTime.setText(temp.first + ":" + temp.second);
                                   }
                                });
                                progressBar.setProgress(mMediaPlayer.getCurrentPosition());
                            } catch (Exception e) {
                                Log.e("MEDIA", e.getMessage() + ":");
                            }
                        }
                    }
                }, 0, 50);

        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                temp = getTime(progress);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        currTime.setText(temp.first + ":" + temp.second);
                        //刷新歌词
                        mLrcView.updateTime(mMediaPlayer.getCurrentPosition());

                    }
                });
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isTracking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mMediaPlayer.seekTo(seekBar.getProgress());
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.start();
                    refreshTime();
                }
                isTracking = false;
            }

        });

        final Button mEndButton = new Button(getContext());
        mEndButton.setBackgroundColor(homeActivity.themeColor);
        mEndButton.setTextColor(Color.WHITE);
    }

    public void addToFavourite() {

        UnifiedTrack ut;

        if (homeActivity.localSelected)
            ut = new UnifiedTrack(true, localTrack, null);
        else
            ut = new UnifiedTrack(false, null, track);

        homeActivity.favouriteTracks.getFavourite().add(ut);
        mCallback.onAddedtoFavfromPlayer();
    }

    public void removeFromFavourite() {

        UnifiedTrack ut;

        if (homeActivity.localSelected)
            ut = new UnifiedTrack(true, localTrack, null);
        else
            ut = new UnifiedTrack(false, null, track);

        for (int i = 0; i < homeActivity.favouriteTracks.getFavourite().size(); i++) {
            UnifiedTrack ut1 = homeActivity.favouriteTracks.getFavourite().get(i);
            if (ut.getType() && ut1.getType()) {
                if (ut.getLocalTrack().getTitle().equals(ut1.getLocalTrack().getTitle())) {
                    homeActivity.favouriteTracks.getFavourite().remove(i);
                    break;
                }
            } else if (!ut.getType() && !ut1.getType()) {
                if (ut.getStreamTrack().getTitle().equals(ut1.getStreamTrack().getTitle())) {
                    homeActivity.favouriteTracks.getFavourite().remove(i);
                    break;
                }
            }
        }
        mCallback.onAddedtoFavfromPlayer();
    }

    protected void initLrc(String lrc){
        List<Lrc> lrcs = LrcHelper.parseLrcFromString(lrc);
        if(lrcs != null && lrcs.size() > 0){
            mLrcView.setLrcData(lrcs);
            mLrcView.resume();
        }else {
            mLrcView.setEmptyContent("No data");
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        super.onDestroy();
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        if (mVisualizer != null) {
            mVisualizer.release();
        }
    }

    public void refresh() {

        isRefreshed = true;

        pauseClicked = false;
        completed = false;
        isTracking = false;

        if (snappyRecyclerView.getCurrentPosition() != HomeActivity.queueCurrentIndex) {
            snappyRecyclerView.scrollToPosition(HomeActivity.queueCurrentIndex);
            snappyRecyclerView.setCurrentPosition(HomeActivity.queueCurrentIndex);
            customAdapter.notifyDataSetChanged();
        }

        VisualizerView.w = HomeActivity.screen_width;
        VisualizerView.h = HomeActivity.screen_height;
        VisualizerView.conf = Bitmap.Config.ARGB_8888;
        VisualizerView.bmp = Bitmap.createBitmap(VisualizerView.w, VisualizerView.h, VisualizerView.conf);
        HomeActivity.cacheCanvas = new Canvas(VisualizerView.bmp);

        if (homeActivity.isPlayerVisible) {
            player_controller.setVisibility(View.VISIBLE);
            player_controller.setImageResource(R.drawable.ic_queue_music_white_48dp);
        } else {
            player_controller.setVisibility(View.VISIBLE);
            player_controller.setImageResource(R.drawable.ic_pause_white_48dp);
        }

        isFav = false;

        if (homeActivity.isFavourite) {
            favouriteIcon.setImageResource(R.drawable.ic_heart_filled_1);
            favControllerSp.setImageResource(R.drawable.ic_heart_filled_1);
            isFav = true;
        } else {
            favouriteIcon.setImageResource(R.drawable.ic_heart_alpha);
            favControllerSp.setImageResource(R.drawable.ic_heart_alpha);
            isFav = false;
        }

        if (homeActivity.shuffleEnabled) {
            shuffleController.setImageResource(R.drawable.ic_shuffle_filled);
        } else {
            shuffleController.setImageResource(R.drawable.ic_shuffle_alpha);
        }

        if (homeActivity.repeatEnabled) {
            repeatController.setImageResource(R.drawable.ic_repeat_filled);
        } else if (homeActivity.repeatOnceEnabled) {
            repeatController.setImageResource(R.drawable.ic_repeat_once);
        } else {
            repeatController.setImageResource(R.drawable.ic_repeat_alpha);
        }

        if (homeActivity.isSaveDNAEnabled) {
            saveDNAToggle.setImageResource(R.drawable.ic_save_filled);
        } else {
            saveDNAToggle.setImageResource(R.drawable.ic_save_alpha);
        }

        equalizerIcon.setVisibility(View.INVISIBLE);

        track = homeActivity.selectedTrack;
        localTrack = homeActivity.localSelectedTrack;

        if (homeActivity.streamSelected) {
            durationInMilliSec = track.getDuration();
            if (track.getArtworkURL() != null) {
                Picasso.with(getActivity()).load(track.getArtworkURL()).resize(100, 100).into(selected_track_image);
            } else {
                selected_track_image.setImageResource(R.drawable.ic_default);
            }
            try {
                spTitleAB.setText(track.getTitle());
                selected_track_title.setText(track.getTitle());
                selected_track_artist.setText("");
                spArtistAB.setText("");
            } catch (Exception e) {
            }
        } else {
            try {
                durationInMilliSec = (int) localTrack.getDuration();
            } catch (Exception e) {

            }
            try {
                imgLoader.DisplayImage(localTrack.getPath(), selected_track_image);
            } catch (Exception e) {

            }
            try {
                spTitleAB.setText(localTrack.getTitle());
                selected_track_title.setText(localTrack.getTitle());
                selected_track_artist.setText(localTrack.getArtist());
                spArtistAB.setText(localTrack.getArtist());
            } catch (Exception e) {

            }
        }


        homeActivity.mWifi = homeActivity.connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        try {
            if (homeActivity.streamSelected) {
                if ((homeActivity.settings.isStreamOnlyOnWifiEnabled() && homeActivity.mWifi.isConnected()) || !homeActivity.settings
                        .isStreamOnlyOnWifiEnabled()) {
                    isPrepared = false;
                    mMediaPlayer.reset();
                    progressBar.setProgress(0);
                    progressBar.setSecondaryProgress(0);
                    mMediaPlayer.setDataSource(track.getStreamURL());
                    mMediaPlayer.prepareAsync();

                    track.mDuration = durationInMilliSec;
                } else {
                    mMediaPlayer.pause();
                    homeActivity.nextControllerClicked = true;
                    mCallback.onComplete();
                }
            } else {
                isPrepared = false;
                mMediaPlayer.reset();
                progressBar.setProgress(0);
                progressBar.setSecondaryProgress(0);
                File f = new File(localTrack.getPath());
                if (f.exists()) {
                    mMediaPlayer.setDataSource(localTrack.getPath());
                    mMediaPlayer.prepareAsync();
                } else {
                    Toast.makeText(getContext(), "Error playing " + localTrack.getTitle() + ". Skipping to next track", Toast.LENGTH_SHORT).show();
                    mMediaPlayer.pause();
                    homeActivity.nextControllerClicked = true;
                    mCallback.onComplete();
                }
            }
            bufferingIndicator.setVisibility(View.VISIBLE);
            mainTrackController.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }


        timer.cancel();
        timer = new Timer();
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        if (isPrepared && !isTracking && getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    float[] hsv = new float[3];
                                    hsv[0] = homeActivity.seekBarColor;
                                    hsv[1] = (float) 0.8;
                                    hsv[2] = (float) 0.5;
                                    progressBar.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(Color.HSVToColor(hsv), PorterDuff.Mode.SRC_IN));
                                    cpb.update();
                                }
                            });
                            try {
                                temp = getTime(mMediaPlayer.getCurrentPosition());
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        currTime.setText(temp.first + ":" + temp.second);
                                    }
                                });
                                progressBar.setProgress(mMediaPlayer.getCurrentPosition());
                            } catch (Exception e) {
                                Log.e("MEDIA", e.getMessage() + ":");
                            }
                        }
                    }
                }, 0, 50);

        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
                temp = getTime(progress);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        currTime.setText(temp.first + ":" + temp.second);
                        //刷新歌词
                        mLrcView.updateTime(progress);

                    }
                });
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                startTrack = System.currentTimeMillis();
                isTracking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                endTrack = System.currentTimeMillis();
                mMediaPlayer.seekTo(seekBar.getProgress());
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.start();
                    refreshTime();
                }
                isTracking = false;
            }

        });
    }

    public void refreshTime() {
        durationInMilliSec = mMediaPlayer.getDuration();
        temp = getTime(durationInMilliSec);
        totalTime.setText(temp.first + ":" + temp.second);
        progressBar.setMax(durationInMilliSec);
    }

    public Pair<String, String> getTime(int millsec) {
        int min, sec;
        sec = millsec / 1000;
        min = sec / 60;
        sec = sec % 60;
        String minS, secS;
        minS = String.valueOf(min);
        secS = String.valueOf(sec);
        if (sec < 10) {
            secS = "0" + secS;
        }
        Pair<String, String> pair = Pair.create(minS, secS);
        return pair;
    }

    public class SaveDNA extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if (homeActivity.isSaveDNAEnabled) {
                Bitmap bmp = mVisualizerView.bmp;

                // Generate base64 encoded bitmap
                String base64encoded = getBase64encodedBitmap(bmp);

                // Create SavedDNA object with relevant values.
                SavedDNA savedDNA;
                if (localIsPlaying) {
                    savedDNA = new SavedDNA(localTrack.getTitle(), true, localTrack.getPath(), null, localTrack.getArtist(), base64encoded);
                } else {
                    savedDNA = new SavedDNA(track.getTitle(), false, null, track.getArtworkURL(), "", base64encoded);
                }

                // Check if a DNA exists for the current song, and remove it.
                for (int i = 0; i < homeActivity.savedDNAs.getSavedDNAs().size(); i++) {
                    if (homeActivity.savedDNAs.getSavedDNAs().get(i).getName().equals(savedDNA.getName())) {
                        homeActivity.savedDNAs.getSavedDNAs().remove(i);
                        break;
                    }
                }

                // Add the DNA to the first position.
                homeActivity.savedDNAs.getSavedDNAs().add(0, savedDNA);

                // If number of DNAs get more than 10, then remove the 11th DNA to keep size as 10.
                // Caution : 0-indexed arrays.
                if (homeActivity.savedDNAs.getSavedDNAs().size() > 10) {
                    homeActivity.savedDNAs.getSavedDNAs().remove(10);
                }

                // Display Toast.
                homeActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ctx, "DNA Saved!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            // Set flags to initial values.
            completed = false;
            isPrepared = false;

            // Pause the MediaPlayer and call onComplete().
            mMediaPlayer.pause();
            mCallback.onComplete();
        }
    }

    public void toggleAlbumArtBackground() {
        snappyRecyclerView.setTransparency();
    }

    public String getBase64encodedBitmap(Bitmap image) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

}