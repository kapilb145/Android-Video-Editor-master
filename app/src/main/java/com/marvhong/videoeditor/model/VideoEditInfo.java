package com.marvhong.videoeditor.model;

import java.io.Serializable;

/**
 * @author LLhon
 * @Project diaoyur_android
 * @Package com.kangoo.diaoyur.model
 * @Date 2018/4/21 10:59
 * @description
 */
public class VideoEditInfo implements Serializable {

    public String path; //Sd card path of the picture
    public long time;//The time of the video where the picture is  millisecond

    public VideoEditInfo() {
    }


    @Override
    public String toString() {
        return "VideoEditInfo{" +
            "path='" + path + '\'' +
            ", time='" + time + '\'' +
            '}';
    }
}
