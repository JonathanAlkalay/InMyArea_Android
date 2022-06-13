package com.example.inmyarea_android.feed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inmyarea_android.R;

import com.example.inmyarea_android.model.VideoItem;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultAllocator;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder>{
    private List<VideoItem> videoItemList;


    public VideoAdapter(List<VideoItem> videoItemList) {
        this.videoItemList = videoItemList;
    }


    VideoClipRV_Fragment.OnItemClickListener listener;
    public void setOnItemClickListener(VideoClipRV_Fragment.OnItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.singelvideo,
                        parent,
                        false
                ),listener
        );

    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.setVideoData(videoItemList.get(position));


    }

    @Override
    public int getItemCount() {
        if(videoItemList!=null)
        return videoItemList.size();
        return 0;
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder{

        PlayerView videoView;
        TextView title,des;
        ProgressBar videoPB;
        ExoPlayer player;
        //Minimum Video you want to buffer while Playing
        private int MIN_BUFFER_DURATION = 2000;
        //Max Video you want to buffer during PlayBack
        private int MAX_BUFFER_DURATION = 5000;
        //Min Video you want to buffer before start Playing it
        private int MIN_PLAYBACK_START_BUFFER = 1500;
        //Min video You want to buffer when user resumes video
        private int MIN_PLAYBACK_RESUME_BUFFER = 2000;


        public VideoViewHolder(@NonNull View itemView,VideoClipRV_Fragment.OnItemClickListener listener) {
            super(itemView);
            videoView=itemView.findViewById(R.id.videoHolder_EP);
            title=itemView.findViewById(R.id.videoTitle_TV);
            des=itemView.findViewById(R.id.videoDesc_TV);
            videoPB=itemView.findViewById(R.id.singalVideo_PB);
            videoPB.setVisibility(View.VISIBLE);

            LoadControl loadControl = new DefaultLoadControl.Builder()
                    .setAllocator(new DefaultAllocator(true, 16))
                    .setBufferDurationsMs(MIN_BUFFER_DURATION,
                            MAX_BUFFER_DURATION,
                            MIN_PLAYBACK_START_BUFFER,
                            MIN_PLAYBACK_RESUME_BUFFER)
                    .setTargetBufferBytes(-1)
                    .setPrioritizeTimeOverSizeThresholds(true).createDefaultLoadControl();

            TrackSelector trackSelector = new DefaultTrackSelector();
            player = new ExoPlayer.Builder(videoView.getContext()).setTrackSelector(trackSelector)
                    .setLoadControl(loadControl).build();
            player.setRepeatMode(Player.REPEAT_MODE_ONE);
            videoView.setPlayer(player);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(pos);
                }
            });
        }

         void setVideoData(VideoItem videoItem) {
            title.setText(videoItem.videoTitle);
            des.setText(videoItem.videoDesc);
             MediaItem mediaItem = MediaItem.fromUri(videoItem.videoURL);
             player.setMediaItem(mediaItem);
             player.prepare();
             videoPB.setVisibility(View.GONE);
             player.play();




        }

    }
}
