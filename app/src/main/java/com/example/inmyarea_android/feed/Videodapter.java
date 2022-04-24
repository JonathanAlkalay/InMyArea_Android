package com.example.inmyarea_android.feed;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inmyarea_android.R;
import com.example.inmyarea_android.model.VideoItem;

import java.util.List;

public class Videodapter extends RecyclerView.Adapter<Videodapter.VideoViewHolder>{
    private List<VideoItem> videoItemList;

    public Videodapter(List<VideoItem> videoItemList) {
        this.videoItemList = videoItemList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.singelvideo,
                        parent,
                        false
                )
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

        VideoView videoView;
        TextView title,des;
        ProgressBar videoPB;


        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView=itemView.findViewById(R.id.videoHolder_VV);
            title=itemView.findViewById(R.id.videoTitle_TV);
            des=itemView.findViewById(R.id.videoDesc_TV);
            videoPB=itemView.findViewById(R.id.singalVideo_PB);
        }

         void setVideoData(VideoItem videoItem) {
            title.setText(videoItem.videoTitle);
            des.setText(videoItem.videoDesc);
            videoView.setVideoPath(videoItem.videoURL);
            videoView.setOnPreparedListener(mediaPlayer -> {
                videoPB.setVisibility(View.GONE);
                mediaPlayer.start();

                float videoRatio =mediaPlayer.getVideoWidth() / (float) mediaPlayer.getVideoHeight();
                float screenRatio= videoView.getWidth()/(float) videoView.getHeight();

                float scale=videoRatio/screenRatio;
                if(scale>= 1f){
                    videoView.setScaleX(scale);
                }else { videoView.setScaleY(1f/scale);}
            });
            videoView.setOnCompletionListener(mediaPlayer -> {
                mediaPlayer.start();
            });


        }

    }
}
