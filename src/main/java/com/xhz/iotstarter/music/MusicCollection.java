package com.xhz.iotstarter.music;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/3/14 12:57 PM
 */
public class MusicCollection {

    private static List<Note> happySpeak = new ArrayList();

    private static List<Note> kanong = new ArrayList<>();

    private static int oneSet = 500;

    private static MusicBasicNote tune[] = {
            MusicBasicNote.NOTE_G5, MusicBasicNote.NOTE_E5, MusicBasicNote.NOTE_F5, MusicBasicNote.NOTE_G5,
            MusicBasicNote.NOTE_E5, MusicBasicNote.NOTE_F5, MusicBasicNote.NOTE_G5, MusicBasicNote.NOTE_G4,
            MusicBasicNote.NOTE_A4, MusicBasicNote.NOTE_B4,
            MusicBasicNote.NOTE_C5, MusicBasicNote.NOTE_D5, MusicBasicNote.NOTE_E5, MusicBasicNote.NOTE_F5,
            MusicBasicNote.NOTE_E5, MusicBasicNote.NOTE_C5, MusicBasicNote.NOTE_D5, MusicBasicNote.NOTE_E5,
            MusicBasicNote.NOTE_E4, MusicBasicNote.NOTE_F4,
            MusicBasicNote.NOTE_G4, MusicBasicNote.NOTE_A4, MusicBasicNote.NOTE_G4, MusicBasicNote.NOTE_F4,
            MusicBasicNote.NOTE_G4, MusicBasicNote.NOTE_E4, MusicBasicNote.NOTE_F4, MusicBasicNote.NOTE_G4,
            MusicBasicNote.NOTE_F4, MusicBasicNote.NOTE_A4, MusicBasicNote.NOTE_G4, MusicBasicNote.NOTE_F4,
            MusicBasicNote.NOTE_E4, MusicBasicNote.NOTE_D4,
            MusicBasicNote.NOTE_E4, MusicBasicNote.NOTE_D4, MusicBasicNote.NOTE_C4, MusicBasicNote.NOTE_D4,
            MusicBasicNote.NOTE_E4, MusicBasicNote.NOTE_F4, MusicBasicNote.NOTE_G4, MusicBasicNote.NOTE_A4,
            MusicBasicNote.NOTE_F4, MusicBasicNote.NOTE_A4, MusicBasicNote.NOTE_G4, MusicBasicNote.NOTE_A4,
            MusicBasicNote.NOTE_B4, MusicBasicNote.NOTE_C5,
            MusicBasicNote.NOTE_G4, MusicBasicNote.NOTE_A4, MusicBasicNote.NOTE_B4, MusicBasicNote.NOTE_C5,
            MusicBasicNote.NOTE_D5, MusicBasicNote.NOTE_E5, MusicBasicNote.NOTE_F5, MusicBasicNote.NOTE_G5
    };

    private static double durt[] = {
            1, 0.5, 0.5, 1, 0.5, 0.5,
            0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5,
            1, 0.5, 0.5, 1, 0.5, 0.5,
            0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5,
            1, 0.5, 0.5, 1, 0.5, 0.5,
            0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5,
            1, 0.5, 0.5, 1, 0.5, 0.5,
            0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5
    };

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

        /**
         * 卡农在此
         */
        for (int i = 0; i < tune.length; i++) {
            kanong.add(Note.builder()
                    .note(tune[i])
                    .oneset(oneSet * durt[i])
                    .build());
        }

    }

    public static List<Note> getHappySpeak() {
        return happySpeak;
    }

    public static List<Note> getKanong() {
        return kanong;
    }
}
