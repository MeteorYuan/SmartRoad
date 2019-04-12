package com.hfxy.video.po;

public class VidData {
    private int id;
    private String name;
    private String VideoPath;
    private int VideoLength;
    private int states;


    @Override
    public String toString() {
        return "VidData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", VideoPath='" + VideoPath + '\'' +
                ", VideoLength=" + VideoLength +
                ", states=" + states +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoPath() {
        return VideoPath;
    }

    public void setVideoPath(String videoPath) {
        VideoPath = videoPath;
    }

    public int getVideoLength() {
        return VideoLength;
    }

    public void setVideoLength(int videoLength) {
        VideoLength = videoLength;
    }

    public int getstates() {
        return states;
    }

    public void setstates(int states) {
        this.states = states;
    }
}
