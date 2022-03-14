package com.xhz.iotstarter.music;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/3/14 12:57 PM
 */
public class MusicCollection {

    private static List<Note> happySpeak= new ArrayList();

    private static int oneSet = 100;

    static {
        /**
         * 欢乐颂在此
         */
        happySpeak.add(Note.builder().note(MusicBasicNote.MI).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.MI).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.FA).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.SO).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.SO).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.FA).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.MI).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.RE).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.DO).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.DO).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.RE).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.MI).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.MI).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.RE).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.RE).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.MI).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.MI).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.FA).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.SO).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.SO).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.FA).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.MI).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.RE).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.DO).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.DO).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.RE).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.MI).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.RE).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.DO).oneset(oneSet).build());
        happySpeak.add(Note.builder().note(MusicBasicNote.DO).oneset(oneSet).build());
    }

    public static List<Note> getHappySpeak() {
        return happySpeak;
    }
}
