package com.example.ziv.zhujiandemo.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ziv.zhujiandemo.Models.Recipe;
import com.example.ziv.zhujiandemo.R;
import com.example.ziv.zhujiandemo.Tools.GetImageByUrl;
import com.example.ziv.zhujiandemo.Tools.SpUtils;

import java.util.ArrayList;
import java.util.List;

public class KitchenImgPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<Recipe> mList;


    public KitchenImgPagerAdapter(Context mContext, List<Recipe> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void changeAdapterList(List<Recipe> mList) {
        this.mList = mList;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = inflater.inflate(R.layout.kitchen_slide_item, null);

        ImageView slideImg = slideLayout.findViewById(R.id.kitchen_slide_img);
        TextView slideText = slideLayout.findViewById(R.id.kitchen_slide_title);

        GetImageByUrl getImageByUrl = new GetImageByUrl();
        getImageByUrl.setImage(slideImg, mList.get(position).getImage());
        slideText.setText(mList.get(position).getTitle());

        container.addView(slideLayout);
        return slideLayout;
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        } else {
            return mList.size();
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
