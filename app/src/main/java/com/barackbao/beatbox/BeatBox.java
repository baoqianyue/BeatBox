package com.barackbao.beatbox;

import android.content.Context;
import android.content.res.AssetManager;
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

    private List<Sound> mSounds = new ArrayList<>();

    private AssetManager mAssets;


    /**
     * 访问Assets需要AssetsManager类，需要从Context中获取
     *
     * @param context
     */
    public BeatBox(Context context) {
        //这里不管使用的是哪个context,访问到的都是同一套assets资源
        mAssets = context.getAssets();
        loadSounds();
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
            String assetPath = SOUNDS_FOLDER + "/" + filename;
            Sound sound = new Sound(assetPath);
            mSounds.add(sound);
        }

    }

    public List<Sound> getSounds() {
        return mSounds;
    }


}
