package com.example.chapter3.homework;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

public class PlaceholderFragment extends Fragment {
    private View view;
    MyAdapter new_adapter;
    private LottieAnimationView lv;
    private ArrayAdapter<item> adapterItems;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create arraylist from item fixtures
        ArrayList<item> items = item.getItems();
        adapterItems = new ArrayAdapter<item>(getActivity(),
                R.layout.item_in_list, items);
        new_adapter = new MyAdapter(getActivity(),items);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件
        view = inflater.inflate(R.layout.fragment_placeholder, container, false);
        lv = view.findViewById(R.id.animation_view);
        listView = view.findViewById(R.id.list);

        listView.setAdapter(new_adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(lv,"alpha",1.0f,0.0f);
                animator1.setDuration(500);
                animator1.setInterpolator(new LinearInterpolator());

                listView.setVisibility(View.VISIBLE);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(listView,"alpha",0.0f,1.0f);
                animator2.setDuration(500);
                animator2.setInterpolator(new LinearInterpolator());

//                AnimatorSet animatorSet = new AnimatorSet();
//                animatorSet.playTogether(animator1,animator2);
//                animatorSet.start();
                animator1.start();
                animator2.start();
            }
        }, 5000);
    }
}
