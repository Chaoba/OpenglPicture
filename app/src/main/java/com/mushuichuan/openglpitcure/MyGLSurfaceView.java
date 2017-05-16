package com.mushuichuan.openglpitcure;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.View;


public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        mRenderer = new MyGLRenderer(getContext());
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRenderer.showNextPicture();
                requestRender();
            }
        });
    }

}
