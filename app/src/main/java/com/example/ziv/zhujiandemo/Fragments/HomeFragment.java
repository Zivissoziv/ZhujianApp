package com.example.ziv.zhujiandemo.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;



import com.example.ziv.zhujiandemo.Activities.RecipeDetailActivity;
import com.example.ziv.zhujiandemo.Adapters.RecommendRecipeAdapter;
import com.example.ziv.zhujiandemo.Adapters.SliderPagerAdapter;
import com.example.ziv.zhujiandemo.Helpers.RecipeItemClickListener;
import com.example.ziv.zhujiandemo.Models.Recipe;
import com.example.ziv.zhujiandemo.R;
import com.example.ziv.zhujiandemo.Tools.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 *
 * 默认首页
 * 主要包含RecyclerView（首页轮播图Slide）和ListView（首页滑动推荐栏Recommend）
 * 由于两个View的更新都涉及到使用JsonUtil联网读取
 * 所以需要开启两个子线程加两个Handler分别更新两个View
 */


public class HomeFragment extends Fragment implements RecipeItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    //从这里开始写
    private List<Recipe> lstSlides;//首页轮播图list
    private List<Recipe> lstRecommend;//今日推荐list
    private ViewPager sliderpager;
    private TabLayout indicator;
    private RecyclerView RecipesRV;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 初始化容器
        View fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        sliderpager = fragmentView.findViewById(R.id.slider_pager);
        indicator = fragmentView.findViewById(R.id.indicator);
        RecipesRV = fragmentView.findViewById(R.id.Rv_recipes);


        //开启一个线程读取服务器上的推荐json文件
        new Thread() {//创建子线程进行网络访问的操作
            public void run() {

                try {
                    lstRecommend= JsonUtil.readParse("https://zhujian-1253572416.cos.ap-guangzhou.myqcloud.com/recipejson/recipe.json");
                    for (int i = 0; i < lstRecommend.size(); i++) {
                        if(lstRecommend.get(i).isSlide()){
                            lstRecommend.remove(i);
                            i--;
                        }
                    }
                    Message message = new Message();
                    message.what = 1;
                    Recommendhandler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        //开启一个线程读取服务器上的滑动栏json文件
        new Thread() {//创建子线程进行网络访问的操作
            public void run() {

                try {
                    lstSlides= JsonUtil.readParse("https://zhujian-1253572416.cos.ap-guangzhou.myqcloud.com/recipejson/recipe.json");
                    for (int i = 0; i < lstSlides.size(); i++) {
                        if(lstSlides.get(i).isRecommend()){
                            lstSlides.remove(i);
                            i--;
                        }
                    }
                    Message message = new Message();
                    message.what = 1;
                    Slidehandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


        return fragmentView;
    }

    //handler配合子线程实时更新推荐栏UI
    @SuppressLint("HandlerLeak")
    public Handler Recommendhandler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    RecommendRecipeAdapter recommendRecipeAdapter = new RecommendRecipeAdapter(getActivity(), lstRecommend, HomeFragment.this);
                    RecipesRV.setAdapter(recommendRecipeAdapter);
                    RecipesRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    break;
            }

        }
    };

    //handler配合子线程实时更新滑动栏UI
    @SuppressLint("HandlerLeak")
    public Handler Slidehandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    SliderPagerAdapter adapter = new SliderPagerAdapter(getActivity(), lstSlides);
                    sliderpager.setAdapter(adapter);
                    //添加定时切换功能
                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {

                        @Override
                        public void run() {
                            if (getActivity() == null) return;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (sliderpager.getCurrentItem() < lstSlides.size() - 1) {
                                        sliderpager.setCurrentItem(sliderpager.getCurrentItem() + 1);
                                    } else {
                                        sliderpager.setCurrentItem(0);
                                    }
                                }
                            });
                        }
                    }, 4000, 6000);

                    indicator.setupWithViewPager(sliderpager, true);
                    break;
            }

        }
    };


    @Override
    public void onRecipeClick(Recipe recipe, ImageView recipeImageView) {
        Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);

        intent.putExtra("recipe",recipe);
        startActivity(intent);

//        Toast.makeText(getActivity(), "item clicked : " + recipe.getTitle(), Toast.LENGTH_LONG).show();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
