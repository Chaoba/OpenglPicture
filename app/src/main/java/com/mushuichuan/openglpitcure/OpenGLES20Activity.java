package com.mushuichuan.openglpitcure;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;

public class OpenGLES20Activity extends Activity {

    private MyGLSurfaceView mGLView;
    MyHandelr myHandelr;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ViewGroup body = (ViewGroup) findViewById(R.id.body);
        mGLView = new MyGLSurfaceView(this);
        body.addView(mGLView);
        findViewById(R.id.previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGLView.showPrevious();
            }
        });
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGLView.showNext();
            }
        });
        myHandelr = new MyHandelr();
        myHandelr.sendEmptyMessageDelayed(0, 500);
    }

    class MyHandelr extends Handler {
        @Override
        public void handleMessage(Message msg) {
            mGLView.showNext();
            myHandelr.sendEmptyMessageDelayed(0, 500);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLView.onResume();
    }
}