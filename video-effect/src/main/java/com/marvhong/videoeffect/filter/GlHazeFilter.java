package com.marvhong.videoeffect.filter;

import android.opengl.GLES20;
import com.marvhong.videoeffect.filter.base.GlFilter;
import com.marvhong.videoeffect.utils.OpenGlUtils;

/**
 * 迷雾
 * Created by sudamasayuki on 2018/01/06.
 */

public class GlHazeFilter extends GlFilter {

    private static final String FRAGMENT_SHADER =
            "#extension GL_OES_EGL_image_external : require\n" +
                    "precision mediump float;" +
                    "varying vec2 vTextureCoord;" +
                    "uniform samplerExternalOES sTexture;\n" +
                    "uniform lowp float distance;" +
                    "uniform highp float slope;" +

                    "void main() {" +
                    "highp vec4 color = vec4(1.0);" +

                    "highp float  d = vTextureCoord.y * slope  +  distance;" +

                    "highp vec4 c = texture2D(sTexture, vTextureCoord);" +
                    "c = (c - d * color) / (1.0 -d);" +
                    "gl_FragColor = c;" +    // consider using premultiply(c);
                    "}";

    private float distance = 0.2f;
    private float slope = 0.0f;

    public GlHazeFilter() {
        this(0.2f, 0.0f);
    }

    public GlHazeFilter(float distance, float slope) {
        super(OpenGlUtils.DEFAULT_VERTEX_SHADER, FRAGMENT_SHADER);
        this.distance = distance;
        this.slope = slope;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(final float distance) {
        this.distance = distance;
    }

    public float getSlope() {
        return slope;
    }

    public void setSlope(final float slope) {
        this.slope = slope;
    }

    @Override
    public void onDraw() {
        GLES20.glUniform1f(getHandle("distance"), distance);
        GLES20.glUniform1f(getHandle("slope"), slope);
    }
}
