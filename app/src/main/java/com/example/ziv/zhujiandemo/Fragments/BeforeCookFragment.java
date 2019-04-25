package com.example.ziv.zhujiandemo.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ziv.zhujiandemo.Models.Recipe;
import com.example.ziv.zhujiandemo.R;
import com.example.ziv.zhujiandemo.Tools.SpUtils;

import java.util.ArrayList;
import java.util.List;

public class BeforeCookFragment extends Fragment {
    View view;

    public BeforeCookFragment() {

    }

    private TextView warnTV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_before_cook, container, false);
        List<Recipe> recipes = SpUtils.getObject(getActivity(), Recipe.class);
        warnTV = view.findViewById(R.id.before_cook_warn_text);
        if (recipes.size() != 0) {
            setScrollViewContent(recipes);
        } else {
            warnTV.setVisibility(View.VISIBLE);
        }

        return view;


    }

    private void setScrollViewContent(List<Recipe> recipes) {
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.before_cook_detail_content);
        for (int i = 0; i < recipes.size(); i++) {
            View titleView = View.inflate(getActivity(), R.layout.before_cook_recipe_title, null);
            ((TextView) titleView.findViewById(R.id.recipe_name)).setText(recipes.get(i).getTitle());
            layout.addView(titleView);

            final ArrayList<String> materialList = recipes.get(i).getMaterial();
            for (int j = 0; j < materialList.size(); j++) {
                String[] split = materialList.get(j).split(":");
                View view = View.inflate(getActivity(), R.layout.before_cook_matrial_row_item, null);
                ((TextView) view.findViewById(R.id.item_name)).setText(split[0]);
                ((TextView) view.findViewById(R.id.item_count)).setText(split[1]);
                //动态添加 子View
                layout.addView(view);
            }
        }

    }
}
