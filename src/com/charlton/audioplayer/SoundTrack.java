package com.charlton.audioplayer;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.midi.*;
import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;

public class SoundTrack {

    private final File file;

    public SoundTrack(String filename) {
        ClassLoader cl = SoundTrack.class.getClassLoader();
        URL resource = cl.getResource(String.format("assets/tracks/%s", filename));
        System.out.println(resource);
        String file = resource.getFile();
        System.out.println(file);
        this.file = new File(file);
    }


    public void play() throws IOException, UnsupportedAudioFileException, InvalidMidiDataException, MidiUnavailableException, LineUnavailableException {
        AudioFormat playbackFormat = new AudioFormat(44100, 16, 1, true, false);
        AudioInputStream source = AudioSystem.getAudioInputStream(file);
        AudioFormat format = source.getFormat();
        System.out.println(format);
        source = AudioSystem.getAudioInputStream(format, source);
        DataLine.Info info = new DataLine.Info(Clip.class, format);
// create the line
        Clip clip = (Clip)AudioSystem.getLine(info);
// load the samples from the stream
        clip.open(source);
// begin playback of the sound clip
        clip.start();

    }

}
