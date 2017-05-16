package com.mushuichuan.openglpitcure;

import android.content.Context;
import android.opengl.GLES20;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Square {

    private String vertexShaderCode;
    private String fragmentShaderCode;

    private final FloatBuffer vertexBuffer;
    private final ShortBuffer drawListBuffer;
    private final int mProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;
    private int mIndex;
    private int mTotalCount = 2;
    static final int COORDS_PER_VERTEX = 3;
    static float squareCoords[] = {
            -0.5f, 0.5f, 0.0f,   // top left
            -0.5f, -0.5f, 0.0f,   // bottom left
            0.5f, -0.5f, 0.0f,   // bottom right
            0.5f, 0.5f, 0.0f}; // top right

    private final short drawOrder[] = {0, 1, 2, 0, 2, 3};

    private final int vertexStride = COORDS_PER_VERTEX * 4;

    float color[] = {0.2f, 0.709803922f, 0.898039216f, 1.0f};

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
        GLES20.glUseProgram(mProgram);
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");

        GLES20.glUniform1f(GLES20.glGetUniformLocation(mProgram, "index"), mIndex);
        MyGLRenderer.checkGlError("glGetUniformLocation");
        MyGLRenderer.checkGlError("glUniform1f");
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

    public void showNextPicture() {
        mIndex = (++mIndex) % mTotalCount;
    }
}