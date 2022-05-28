package com.example.inmyarea_android.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.inmyarea_android.R;
import com.example.inmyarea_android.feed.BaseActivity;
import com.example.inmyarea_android.model.Listeners;
import com.example.inmyarea_android.model.ResponseMessages.MainResponseMessage;
import com.example.inmyarea_android.model.Service;
import com.example.inmyarea_android.model.Users.Business;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;


public class RegisterBusinessFragment2 extends Fragment {

    ProgressBar progressBar;
    public static final int REQUEST_PICK_VIDEO = 3;
    private VideoView mVideoView;
    private Uri video;
    private String videoPath;

    // Current playback position (in milliseconds).
    private int mCurrentPosition = 0;

    // Tag for the instance state bundle.
    private static final String PLAYBACK_TIME = "play_time";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_registe_business2, container, false);
        String category=RegisterBusinessFragment2Args.fromBundle(getArguments()).getCategory();
        String email=RegisterBusinessFragment2Args.fromBundle(getArguments()).getEmail();
        progressBar=view.findViewById(R.id.progressBarregnus2_PB);
        progressBar.setVisibility(View.GONE);
        TextView services=view.findViewById(R.id.services_dropdownTv);
        Button register= view.findViewById(R.id.register_regbusinessBT);
        Button videobt=view.findViewById(R.id.pickvideo_registerBT);
        mVideoView = view.findViewById(R.id.videoView_register);
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<Integer> servList = new ArrayList<>();
        String[] servArray={};

        //video
        videobt.setOnClickListener(v -> {
            Intent pickVideoIntent = new Intent(Intent.ACTION_GET_CONTENT);
            pickVideoIntent.setType("video/*");
            startActivityForResult(pickVideoIntent, REQUEST_PICK_VIDEO);
        });

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }

        MediaController controller = new MediaController(getContext());
        controller.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(controller);

        //services
        Service service=new Service();
        switch (category){
            case "Hair Styling":
                servArray=service.HairStyling;
                break;
            case "Leisure":
                servArray=service.Leisure;
                break;

            case "Pedi&Medi":
                servArray=service.PediMeni;
                break;

            case "Cosmetics":
                servArray=service.Cosmetics;
                break;
        }
        boolean[] selectedServices = new boolean[servArray.length];


        String[] finalServArray = servArray;
        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // set title
                builder.setTitle("Select Services");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(finalServArray, selectedServices, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            servList.add(i);
                            // Sort array list
                            Collections.sort(servList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            servList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder

                        // use for loop
                        for (int j = 0; j < servList.size(); j++) {
                            // concat array value
                            stringBuilder.append(finalServArray[servList.get(j)]);
                            // check condition
                            if (j != servList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        services.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedServices.length; j++) {
                            // remove all selection
                            selectedServices[j] = false;
                            // clear language list
                            servList.clear();
                            // clear text view value
                            services.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });

        register.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            register.setEnabled(false);
//            if (video == null|| video.equals("")){
//                Toast.makeText(getActivity(), "please select a video ", Toast.LENGTH_LONG).show();
//                progressBar.setVisibility(View.GONE);
//                register.setEnabled(true);
//
//            }
             if(stringBuilder.length()==0){
                Toast.makeText(getActivity(), "please select a service ", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                register.setEnabled(true);
                return;
            }else {
                Listeners.instance.getAccountByEmail(email, "business", data -> {
                    HashMap user = data.getAccount();
                    Business busi = new Business(email, (String) user.get("passWord"), (String) user.get("name"),
                            (String) user.get("phoneNumber"), (String) user.get("description"), (String) user.get("category"));

                    ArrayList<String> serv = new ArrayList<String>(Arrays.asList(stringBuilder.toString().split(", ")));
                    busi.setServices(serv);
                    Listeners.instance.updateAccountDetails(email, "business", busi, data1 -> {

                        toFeedActivity(email);

                    });
                    //File file=new File(video.getPath());
//                    InputStream in =  getActivity().getContentResolver().openInputStream(video);
//                    OutputStream out = new FileOutputStream(new File("your_file_here"));
//                    byte[] buf = new byte[1024];
//                    int len;
//                    while((len=in.read(buf))>0){
//                        out.write(buf,0,len);
//                    }
//                    out.close();
//                    in.close();
                    //Listeners.instance.uploadVideo(file, email, data2 -> toFeedActivity(email));
                });
            }

        });

        return view;
    }


    //video settings
    @Override
    public void onPause() {
        super.onPause();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mVideoView.pause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        // Media playback takes a lot of resources, so everything should be
        // stopped and released at this time.
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current playback position (in milliseconds) to the
        // instance state bundle.
        outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition());
    }


//    private void initializePlayer(Uri uri) {
//        // Show the "Buffering..." message while the video loads.
//        progressBar.setVisibility(VideoView.VISIBLE);
//        if (uri != null){
//            mVideoView.setVideoURI(uri);
//        }
//        // Listener for onPrepared() event (runs after the media is prepared).
//        mVideoView.setOnPreparedListener(
//                new MediaPlayer.OnPreparedListener() {
//                    @Override
//                    public void onPrepared(MediaPlayer mediaPlayer) {
//
//                        // Hide buffering message.
//                        progressBar.setVisibility(VideoView.INVISIBLE);
//
//                        // Restore saved position, if available.
//                        if (mCurrentPosition > 0) {
//                            mVideoView.seekTo(mCurrentPosition);
//                        } else {
//                            // Skipping to 1 shows the first frame of the video.
//                            mVideoView.seekTo(1);
//                        }
//
//                        // Start playing!
//                        mVideoView.start();
//                    }
//                });
//
//        // Listener for onCompletion() event (runs after media has finished
//        // playing).
//        mVideoView.setOnCompletionListener(
//                new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mediaPlayer) {
//                        Toast.makeText(getActivity(),
//                                "Playback completed",
//                                Toast.LENGTH_SHORT).show();
//
//                        // Return the video position to the start.
//                        mVideoView.seekTo(0);
//                    }
//                });
//    }

    private void releasePlayer() {
        mVideoView.stopPlayback();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_PICK_VIDEO) {
                if (data != null) {
                    Toast.makeText(getActivity(), "Video content URI: " + data.getData(),
                            Toast.LENGTH_LONG).show();
                    video = data.getData();
                    videoPath =getPath(video);
                    if (video != null){
                        mVideoView.setVideoURI(video);
                    }
                    //initializePlayer(video);
                    // uploadFile(video.getPath());

                }
            }
        }
        else if (resultCode != Activity.RESULT_CANCELED) {
            Toast.makeText(getActivity(), "Sorry, there was an error!", Toast.LENGTH_LONG).show();
        }
    }

    public String getPath(Uri uri) {
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }



    //send email
    private void toFeedActivity(String email) {
        Intent intent = new Intent(getContext(), BaseActivity.class);
        Bundle b = new Bundle();

        b.putString("useremail_id", email); //Your id
        b.putString("type","business");
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
        getActivity().finish();
    }
}