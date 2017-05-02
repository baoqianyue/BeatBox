package com.barackbao.beatbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Created by 鲍骞月 on 2017/4/29.
 */

public class BeatBoxFragment extends Fragment {

    private BeatBox mBeatBox;

    public static BeatBoxFragment newInstance() {
        return new BeatBoxFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beat_box, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.
                fragment_beat_box_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //保留当前fragment
        setRetainInstance(true);

        mBeatBox = new BeatBox(getContext());
    }

    private class SoundViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        private Button mButton;
        private Sound mSound;

        public SoundViewHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.list_item_sound, container, false));

            mButton = (Button) itemView.findViewById(R.id.list_item_sound_button);
            mButton.setOnClickListener(this);
        }

        /**
         * 这里使用这个绑定方法会整合视图和数据的绑定过程，之后在Adapter中只需调用
         * 一下这个方法就能完成绑定任务
         *
         * @param sound
         */
        public void bindSound(Sound sound) {
            mSound = sound;
            mButton.setText(sound.getName());
        }

        @Override
        public void onClick(View v) {
            mBeatBox.play(mSound);
        }
    }

    private class SoundAdapter extends RecyclerView.Adapter<SoundViewHolder> {
        private List<Sound> mSounds;

        public SoundAdapter(List<Sound> sounds) {
            mSounds = sounds;
        }

        @Override
        public SoundViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            return new SoundViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(SoundViewHolder holder, int position) {
            Sound sound = mSounds.get(position);
            holder.bindSound(sound);
        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeatBox.release();
    }
}
