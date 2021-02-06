package com.warpdev.giphytest;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.Set;

/**
 * 이미지들의 정보가 담겨있는 ArrayList와 RecyclerView를 연결하기위한 Adapter
 *
 * @author warpdev
 */
public class GifListAdapter extends PagedListAdapter<ImageData, RecyclerView.ViewHolder> {
    public static class giflist_ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public Switch mSwitch;

        public giflist_ViewHolder(View v) {
            super(v);
            mImageView = v.findViewById(R.id.gif_tile);
            mSwitch = v.findViewById(R.id.favor_switch);

        }
    }

    /** 미리보기 이미지를 위한 색상코드 */
    private static final String[] colors = {"#75FAA2", "#5ACAFA", "#8D41F6", "#ED706B", "#FDF276"};

    /** Favorite 리스트에 기록을 하기 위한 SharedPreferences Object */
    private SharedPreferences mSharedPreferences;

    /** color 를 결정하는 colors 배열에 대한 index역할 */
    private int mColorParam;

    /**
     * GifListAdapter 생성자
     *
     * @param sharedPreferences Favorite리스트 기록을 위한 SharedPreferences Object
     */
    public GifListAdapter(SharedPreferences sharedPreferences) {
        super(DIFF_CALLBACK);
        this.mSharedPreferences = sharedPreferences;
        this.mColorParam = 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gif_row_layout, parent, false);
        return new giflist_ViewHolder(v);
    }

    private static DiffUtil.ItemCallback<ImageData> DIFF_CALLBACK = new DiffUtil.ItemCallback<ImageData>() {
        @Override
        public boolean areItemsTheSame(@NonNull ImageData oldItem, @NonNull ImageData newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull ImageData oldItem, @NonNull ImageData newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    };

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        giflist_ViewHolder viewHolder = (giflist_ViewHolder) holder;


        //이미지의 width
        int w = getItem(position).getAboutGif().getWidth();

        //이미지의 height
        int h = getItem(position).getAboutGif().getHeight();

        /*
          로딩 전에 알록달록한 타일로 표시하기 위한 작업
          colors배열에 있는 색상 코드를 이용하여 이미지의 width와 height만큼의 drawable을 만든다.
         */
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.parseColor(colors[(mColorParam++) % 5]));
        Drawable drawable = new BitmapDrawable(null, bitmap);
        getItem(position).setFavorite(mSharedPreferences.contains(getItem(position).getId()));

        /*
          GifView에 이미지 로드
          placeholder      로드되는 동안 보여질 이미지 지정 옵션
          override         지정된 width와 height로 이미지를 리사이즈하는 옵션
         */
        Glide.with(viewHolder.mImageView).load(getItem(position).getAboutGif().getUrl()).placeholder(drawable).override(w, h).into(viewHolder.mImageView);

        //Favorite 버튼에 대한 클릭 리스너
        viewHolder.mSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {  //favorite 스위치 클릭할때

            //Favorite 리스트에 저장하기 위한 SharedPreferencesEditor
            SharedPreferences.Editor sharedPreferencesEditor = mSharedPreferences.edit();

            SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager();

            //현재 누른 Gif의 Favovite여부를 갱신
            ImageData imageData = getItem(position);
            getItem(position).setFavorite(isChecked);

            Set<String> idSets;

            if (isChecked) {
                //Favorite를 활성화 했을 때
                idSets = mSharedPreferences.getStringSet("favorlist", null);   //favorlist : favor누른것들의 id집합
                if (idSets != null) {
                    //Favorite 목록에 해당 Gif 고유 id를 추가한다.
                    idSets.add(imageData.getId());
                }

                sharedPreferenceManager.writeData(imageData.getId(), imageData.getAboutGif().getUrl());
                sharedPreferenceManager.writeData(imageData.getId() + "_w", imageData.getAboutGif().getWidth());
                sharedPreferenceManager.writeData(imageData.getId() + "_h", imageData.getAboutGif().getHeight());
                sharedPreferenceManager.commitData();
//                sharedPreferencesEditor.putString(imageData.getId(), imageData.getAboutGif().getUrl());    //id : url 저장
//                sharedPreferencesEditor.putInt(imageData.getId() + "_w", imageData.getAboutGif().getWidth());    // (id)_w : width 저장
//                sharedPreferencesEditor.putInt(imageData.getId() + "_h", imageData.getAboutGif().getHeight());   // (id)_h : height 저장
//                sharedPreferencesEditor.apply();
            } else {
                //Favorate를 비활성화 했을 때
                if (mSharedPreferences.contains(imageData.getId())) {

                    idSets = mSharedPreferences.getStringSet("favorlist", null);

                    if (idSets != null) {
                        idSets.remove(imageData.getId());
                        sharedPreferencesEditor.remove("favorlist");
                        sharedPreferencesEditor.putStringSet("favorlist", idSets);    //favorlist 갱신하기
                    }

                    sharedPreferencesEditor.remove(imageData.getId());
                    sharedPreferencesEditor.remove(imageData.getId() + "_w");
                    sharedPreferencesEditor.remove(imageData.getId() + "_h");
                    sharedPreferencesEditor.apply();
                }
            }
            sharedPreferencesEditor.commit();
        });

        //Switch의 체크 여부를 갱신
        viewHolder.mSwitch.setChecked(getItem(position).isFavorite());
    }
}
