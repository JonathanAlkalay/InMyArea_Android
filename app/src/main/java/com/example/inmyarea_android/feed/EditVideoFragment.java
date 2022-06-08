package com.example.inmyarea_android.feed;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.inmyarea_android.R;
import com.example.inmyarea_android.login.RegisterBusinessFragment2Args;
import com.example.inmyarea_android.model.Listeners;
import com.example.inmyarea_android.model.Users.Business;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class EditVideoFragment extends Fragment {

    ProgressBar progressBar;
    ProgressDialog progressDialog;
    public static final int REQUEST_PICK_VIDEO = 3;
    private VideoView mVideoView;
    private Uri video;
    View view;

    // Current playback position (in milliseconds).
    private int mCurrentPosition = 0;

    // Tag for the instance state bundle.
    private static final String PLAYBACK_TIME = "play_time";
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_edit_video, container, false);
        String email=   EditVideoFragmentArgs.fromBundle(getArguments()).getBusinessid();
        progressBar=view.findViewById(R.id.progressbar_editvideo);
        progressBar.setVisibility(View.GONE);
        progressDialog=new ProgressDialog(getActivity());
        Button videoB=view.findViewById(R.id.choosevideo_editvideoB);
        Button save= view.findViewById(R.id.save_editvideoB);
        activity=getActivity();

        mVideoView = view.findViewById(R.id.video_editvideoVV);

        videoB.setOnClickListener(v -> {
            Intent pickVideoIntent = new Intent(Intent.ACTION_GET_CONTENT);
            pickVideoIntent.setType("video/*");
            startActivityForResult(pickVideoIntent, REQUEST_PICK_VIDEO);
        });

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }

        save.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);

            save.setEnabled(false);
            if (video == null|| video.equals("")){
                Toast.makeText(getActivity(), "please select a video ", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                save.setEnabled(true);

            } else {
                uploadVideo(email);
            }

        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_PICK_VIDEO) {
                if (data != null) {
                    Toast.makeText(getActivity(), "Video file chosen: " + data.getData(),
                            Toast.LENGTH_LONG).show();
                    video = data.getData();
                    //videoPath =generatePath(video,getActivity());
                    if (video != null){
                        mVideoView.setVideoURI(video);
                        //mVideoView.setVideoPath(videoPath);
                    }
                    initializePlayer(video);
                    // uploadFile(video.getPath());

                }
            }
        }
        else if (resultCode != Activity.RESULT_CANCELED) {
            Toast.makeText(getActivity(), "Sorry, there was an error!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current playback position (in milliseconds) to the
        // instance state bundle.
        outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition());
    }


    private void initializePlayer(Uri uri) {
        // Show the "Buffering..." message while the video loads.
        progressBar.setVisibility(VideoView.VISIBLE);
        if (uri != null) {
            mVideoView.setVideoURI(uri);
        }
        // Listener for onPrepared() event (runs after the media is prepared).
        mVideoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {

                        // Hide buffering message.
                        progressBar.setVisibility(VideoView.INVISIBLE);

                        // Restore saved position, if available.
                        if (mCurrentPosition > 0) {
                            mVideoView.seekTo(mCurrentPosition);
                        } else {
                            // Skipping to 1 shows the first frame of the video.
                            mVideoView.seekTo(1);
                        }

                        // Start playing!
                        mVideoView.start();
                    }
                });
        mVideoView.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        Toast.makeText(getActivity(),
                                "Playback completed",
                                Toast.LENGTH_SHORT).show();

                        // Return the video position to the start.
                        mVideoView.seekTo(0);
                    }
                });
    }

    private void uploadVideo(String email) {
        if (video != null) {
            // Progress Listener for loading
            // percentage on the dialog box
            // Create a storage reference from our app
            StorageReference storageRef = Listeners.instance.GetStorageReference(email);

            // Delete the file
            storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // File deleted successfully
                    progressBar.setVisibility(View.GONE);
                    upload(email);

                }
            }).addOnFailureListener(exception -> {
                Toast.makeText(getActivity(), "Failed " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            });



        }
    }

    public void upload(String email){
        Listeners.instance.GetStorageReference(email).putFile(video)
                .addOnSuccessListener(taskSnapshot -> {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    progressDialog.dismiss();
                    String downloadUri =uriTask.getResult().toString();
                    Listeners.instance.updateVideoPath(email, downloadUri, data -> {
                        Toast.makeText(getActivity(), "Video updated", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(view).popBackStack();
                    });

                })
                .addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show())
                .addOnProgressListener(taskSnapshot -> {
                    // show the progress bar
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                });
    }


}