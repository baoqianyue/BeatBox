package com.barackbao.beatbox;

/**
 * Created by 鲍骞月 on 2017/5/1.
 */

public class Sound {
    private String mAssetPath;
    private String mName;
    private Integer mSoundId;


    public Integer getSoundId() {
        return mSoundId;
    }

    public void setSoundId(Integer soundId) {
        mSoundId = soundId;
    }

    public Sound(String assetPath){
        mAssetPath = assetPath;
        String [] components = assetPath.split("/");
        String fileName = components[components.length - 1];
        mName = fileName.replace(".wav","");
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public String getName() {
        return mName;
    }

}
