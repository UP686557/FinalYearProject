/**
 * Created by adampiper on 15/10/15.
 */

package com.example.adampiper.finalyearproject;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.os.FileObserver;
import android.provider.MediaStore;

import com.google.android.glass.content.Intents;

import java.io.File;


public class RecordActivity extends Activity {

    private static final int CAMERA_VID_REQUEST = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        takeVideo();
    }

    private void takeVideo(){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, CAMERA_VID_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == CAMERA_VID_REQUEST && resultCode == RESULT_OK) {
            String videoPath = data.getStringExtra(Intents.EXTRA_VIDEO_FILE_PATH);

            processVideoWhenReady(videoPath);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void processVideoWhenReady(final String videoPath){
        final File videoFile = new File(videoPath);
            if (videoFile.exists()){
            //Process video
        }
        else {
                final File parentDirectory = videoFile.getParentFile();
                FileObserver observer = new FileObserver(parentDirectory.getPath(), FileObserver.CLOSE_WRITE | FileObserver.MOVED_TO) {
                    private boolean isFileWritten;

                    @Override
                    public void onEvent(int event, String path) {
                        if (!isFileWritten) {
                            //make sure file was created in directory expected
                            File affectedFile = new File(parentDirectory, path);
                            isFileWritten = affectedFile.equals(videoFile);

                            if (isFileWritten) {
                                stopWatching();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        processVideoWhenReady(videoPath);
                                    }
                                });
                            }
                        }

                    }
                };

                observer.startWatching();
            }
    }
}

