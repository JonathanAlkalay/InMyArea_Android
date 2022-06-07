package com.example.inmyarea_android.feed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.inmyarea_android.R;
import com.example.inmyarea_android.model.Listeners;
import com.example.inmyarea_android.model.ResponseMessages.GetAccountResponseMessage;
import com.example.inmyarea_android.model.ResponseMessages.GetVideoPathResponseMessage;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerView;

import java.io.IOException;

public class BusinessVideoPreviewFragment extends Fragment {

    PlayerView videoView;
    TextView title,des;
    ProgressBar videoPB;
    ExoPlayer player;
    ImageView edit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_business_video_preview, container, false);
        String email=   BusinessVideoPreviewFragmentArgs.fromBundle(getArguments()).getBusinessid();
        videoView=view.findViewById(R.id.preview_videoEP);
        title=view.findViewById(R.id.preview_titleTV);
        des=view.findViewById(R.id.preview_descripitionTV);
        edit=view.findViewById(R.id.preview_editvideoBt);
        videoPB=view.findViewById(R.id.preview_PB);
        videoPB.setVisibility(View.VISIBLE);
        player = new ExoPlayer.Builder(videoView.getContext()).build();
        player.setRepeatMode(Player.REPEAT_MODE_ONE);
        videoView.setPlayer(player);

        Listeners.instance.getAccountByEmail(email, "business", (Listeners.getAccountByEmailListener) data -> {
            title.setText((String)data.getAccount().get("name"));
            des.setText((String)data.getAccount().get("description"));
            Listeners.instance.getVideoPath(email, new Listeners.getVideoPathListener() {
                @Override
                public void onComplete(GetVideoPathResponseMessage data) {
                    MediaItem mediaItem = MediaItem.fromUri(data.getPath());
                    player.setMediaItem(mediaItem);
                    player.prepare();
                    videoPB.setVisibility(View.GONE);
                    player.play();
                }
            });

        });

        edit.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(BusinessVideoPreviewFragmentDirections.actionBusinessVideoPreviewFragmentToEditVideoFragment(email));
        });


        return view;
    }
}