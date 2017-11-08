package com.cxd.khd.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cxd.khd.R;
import com.cxd.khd.adapter.FragmentViewPagerAdapter;
import com.cxd.khd.view.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chenweiqi on 2017/6/21.
 */

public class BKPackListFragment extends BaseFragment {
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    @BindView(R.id.view_tabLayout)
    TabLayout viewTabLayout;


    FragmentViewPagerAdapter adapter;
    @Override
    public int getLayoutId() {
        return R.layout.layout_tablayout_viewpager;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager.setAdapter(adapter = new FragmentViewPagerAdapter(getChildFragmentManager()));
        adapter.setFragments(getFragments());
        adapter.setTitles(Arrays.asList(new String[]{"发往中转中心","到达中转中心"}));
        adapter.notifyDataSetChanged();
        viewTabLayout.setupWithViewPager(viewPager);

    }


    private List<Fragment> getFragments(){
        List<Fragment> fragments = new ArrayList<>();


        BKPackAndOrderListFragment doing = new BKPackAndOrderListFragment();
        doing.type = BKPackAndOrderListFragment.PACKAGE_DOING;
        doing.title = "发往中转中心";
        fragments.add(doing);

        BKPackAndOrderListFragment finish = new BKPackAndOrderListFragment();
        finish.type = BKPackAndOrderListFragment.PACKAGE_FINISH;
        finish.title = "到达中转中心";
        fragments.add(finish);

        return fragments;
    }

}
