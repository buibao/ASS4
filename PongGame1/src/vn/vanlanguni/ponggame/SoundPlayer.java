package vn.vanlanguni.ponggame;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author OS
 */
public class SoundPlayer {

    private Clip clip;

    public SoundPlayer(File path) {
        try {
            AudioInputStream ais;
            ais = AudioSystem.getAudioInputStream(path);
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
            clip = AudioSystem.getClip();
            clip.open(dais);
        } catch (Exception e) {
        }
    }

    public void play() {

        clip.setFramePosition(0);
        clip.start();
    }

    public void playMusic() {

        clip.setFramePosition(0);
        clip.loop(1000000000);
    }

    public void stop() {
        if (clip.isRunning()) {
            clip.stop();

        }
    }

    public void close() {
        clip.close();
    }
}
