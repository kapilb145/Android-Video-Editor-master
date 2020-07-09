package com.marvhong.videoeffect.filter;

import com.marvhong.videoeffect.filter.base.GlFilter;
import com.marvhong.videoeffect.utils.OpenGlUtils;


/**
 * Applies a cross process effect on video, in which the red and green channels
 * are enhanced while the blue channel is restricted.
 *
 * @author sheraz.khilji
 */
public class CrossProcessEffect extends GlFilter {

    private static final String FRAGMENT_SHADER =
        "#extension GL_OES_EGL_image_external : require\n"
        + "precision mediump float;\n"
        + "uniform samplerExternalOES sTexture;\n"
        + "varying vec2 vTextureCoord;\n" + "void main() {\n"
        + "  vec4 color = texture2D(sTexture, vTextureCoord);\n"
        + "  vec3 ncolor = vec3(0.0, 0.0, 0.0);\n" + "  float value;\n"
        + "  if (color.r < 0.5) {\n" + "    value = color.r;\n"
        + "  } else {\n" + "    value = 1.0 - color.r;\n" + "  }\n"
        + "  float red = 4.0 * value * value * value;\n"
        + "  if (color.r < 0.5) {\n" + "    ncolor.r = red;\n"
        + "  } else {\n" + "    ncolor.r = 1.0 - red;\n" + "  }\n"
        + "  if (color.g < 0.5) {\n" + "    value = color.g;\n"
        + "  } else {\n" + "    value = 1.0 - color.g;\n" + "  }\n"
        + "  float green = 2.0 * value * value;\n"
        + "  if (color.g < 0.5) {\n" + "    ncolor.g = green;\n"
        + "  } else {\n" + "    ncolor.g = 1.0 - green;\n" + "  }\n"
        + "  ncolor.b = color.b * 0.5 + 0.25;\n"
        + "  gl_FragColor = vec4(ncolor.rgb, color.a);\n" + "}\n";

    /**
     * Initialize Effect
     */
    public CrossProcessEffect() {
        super(OpenGlUtils.DEFAULT_VERTEX_SHADER, FRAGMENT_SHADER);
    }
}
