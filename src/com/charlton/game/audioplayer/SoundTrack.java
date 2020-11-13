package com.charlton.game.audioplayer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
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

    public float getVolume(Clip clip) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    public void setVolume(Clip clip, float volume) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
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

        setVolume(clip, 0.05f);
        clip.start();


    }

}
