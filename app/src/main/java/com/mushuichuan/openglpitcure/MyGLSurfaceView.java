package com.mushuichuan.openglpitcure;

import android.content.Context;
import android.opengl.GLSurfaceView;


public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        mRenderer = new MyGLRenderer(getContext());
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public void showPrevious(){
        mRenderer.showPreviousPicture();
        requestRender();
    }
    public void showNext(){
        mRenderer.showNextPicture();
        requestRender();
    }
}
