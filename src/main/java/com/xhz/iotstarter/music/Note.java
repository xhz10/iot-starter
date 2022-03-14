package com.xhz.iotstarter.music;

import lombok.Builder;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/3/14 12:52 PM
 */
@Builder
public class Note {

    private MusicBasicNote note;

    private int oneset;

    public Note(MusicBasicNote note, int oneset) {
        this.note = note;
        this.oneset = oneset;
    }

    public Note() {
    }

    public MusicBasicNote getNote() {
        return note;
    }

    public void setNote(MusicBasicNote note) {
        this.note = note;
    }

    public int getOneset() {
        return oneset;
    }

    public void setOneset(int oneset) {
        this.oneset = oneset;
    }
}
