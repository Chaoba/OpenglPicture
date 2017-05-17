package com.mushuichuan.openglpitcure;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static android.content.ContentValues.TAG;

public class Square {

    private String vertexShaderCode;
    private String fragmentShaderCode;

    private final FloatBuffer vertexBuffer;
    private final ShortBuffer drawListBuffer;
    private final int mProgram;
    private int mPositionHandle;
    private int mMVPMatrixHandle;
    private int mTotalCount = 16;
    private int mIndex = 0;
    static final int COORDS_PER_VERTEX = 3;
    static float squareCoords[] = {
            -1.0f, 1.0f, 0.0f,   // top left
            -1.0f, -1.0f, 0.0f,   // bottom left
            1.0f, -1.0f, 0.0f,   // bottom right
            1.0f, 1.0f, 0.0f}; // top right

    private final short drawOrder[] = {0, 1, 2, 0, 2, 3};

    private final int vertexStride = COORDS_PER_VERTEX * 4;

    public Square(Context context) {
        if (context != null) {
            try {
                InputStream in = context.getResources().getAssets().open("MathPictures.java");
                int n;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while ((n = in.read()) != -1) {
                    baos.write(n);
                }
                byte[] buff = baos.toByteArray();
                baos.close();
                in.close();
                fragmentShaderCode = new String(buff, "UTF-8");
                fragmentShaderCode = fragmentShaderCode.replaceAll("\\r\\n", "\n");

                in = context.getResources().getAssets().open("SquareVertex.java");
                baos = new ByteArrayOutputStream();
                while ((n = in.read()) != -1) {
                    baos.write(n);
                }
                buff = baos.toByteArray();
                baos.close();
                in.close();
                vertexShaderCode = new String(buff, "UTF-8");
                vertexShaderCode = vertexShaderCode.replaceAll("\\r\\n", "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        int vertexShader = MyGLRenderer.loadShader(
                GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(
                GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
    }


    public void draw(float[] mvpMatrix) {
        long start = System.nanoTime();
        GLES20.glUseProgram(mProgram);
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");

        GLES20.glUniform1f(GLES20.glGetUniformLocation(mProgram, "index"), mIndex);
        MyGLRenderer.checkGlError("glGetUniformLocation");
        MyGLRenderer.checkGlError("glUniform1f");
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
        long end = System.nanoTime();
        Log.d(TAG, "time cost:" + (end - start) + " for picture " + mIndex % mTotalCount);
    }

    public void showNextPicture() {
        mIndex++;
        mIndex = mIndex % mTotalCount;
    }

    public void showPreviousPicture() {
        mIndex--;
        if (mIndex < 0) {
            mIndex = mTotalCount - 1;
        }
    }
}