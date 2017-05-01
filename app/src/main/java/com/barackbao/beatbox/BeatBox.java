package com.barackbao.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 鲍骞月 on 2017/4/30.
 */

public class BeatBox {
    private static final String TAG = "BeatBox";
    private static final String SOUNDS_FOLDER = "sample_sounds";
    private static final int MAX_SOUNDS = 5;

    private List<Sound> mSounds = new ArrayList<>();

    private AssetManager mAssets;
    private SoundPool mSoundPool;


    /**
     * 访问Assets需要AssetsManager类，需要从Context中获取
     *
     * @param context
     */
    public BeatBox(Context context) {
        //这里不管使用的是哪个context,访问到的都是同一套assets资源
        mAssets = context.getAssets();

        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        //AudioManager中STREAM_MUSIC使用的是同音乐和游戏一样的音量控制，不影响铃声音量

        loadSounds();

    }

    public void play(Sound sound) {
        Integer soundId = sound.getSoundId();
        if (soundId == null) {
            return;
        }
        mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void release() {
        mSoundPool.release();
    }

    public List<Sound> getSounds() {
        return mSounds;
    }

    private void loadSounds() {
        String[] soundNames;

        try {
            soundNames = mAssets.list(SOUNDS_FOLDER);
            Log.i(TAG, "Found" + soundNames.length + " sounds");
        } catch (IOException ex) {
            Log.e(TAG, "Could not list assets", ex);
            return;
        }

        for (String filename : soundNames) {
            try {
                String assetPath = SOUNDS_FOLDER + "/" + filename;
                Sound sound = new Sound(assetPath);
                load(sound);
                mSounds.add(sound);
            } catch (IOException e) {
                Log.e(TAG, "Cound not load sound" + filename, e);
            }
        }

    }

    /**
     * 使用SoundPool加载音频有快速响应的特点，但是需要预先加载，这里需要使用到
     * 这些音频各自的id
     * 此方法用来加载
     *
     * @param sound
     * @throws IOException
     */
    private void load(Sound sound) throws IOException {
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(afd, 1);
        sound.setSoundId(soundId);
    }

}
