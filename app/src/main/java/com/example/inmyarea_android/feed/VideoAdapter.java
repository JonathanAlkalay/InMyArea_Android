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
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerView;

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
        return videoItemList.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder{

        PlayerView videoView;
        TextView title,des;
        ProgressBar videoPB;
        ExoPlayer player;


        public VideoViewHolder(@NonNull View itemView,VideoClipRV_Fragment.OnItemClickListener listener) {
            super(itemView);
            videoView=itemView.findViewById(R.id.videoHolder_EP);
            title=itemView.findViewById(R.id.videoTitle_TV);
            des=itemView.findViewById(R.id.videoDesc_TV);
            videoPB=itemView.findViewById(R.id.singalVideo_PB);
            videoPB.setVisibility(View.VISIBLE);
            player = new ExoPlayer.Builder(videoView.getContext()).build();
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
