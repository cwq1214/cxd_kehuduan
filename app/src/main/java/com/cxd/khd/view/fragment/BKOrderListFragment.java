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

public class BKOrderListFragment extends BaseFragment {
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    @BindView(R.id.view_tabLayout)
    TabLayout viewTabLayout;


    FragmentViewPagerAdapter adapter;
    public String packageNo;
    @Override
    public int getLayoutId() {
        return R.layout.layout_tablayout_viewpager;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager.setAdapter(adapter = new FragmentViewPagerAdapter(getChildFragmentManager()));
        adapter.setFragments(getFragments());
        adapter.setTitles(Arrays.asList(new String[]{"待派件","派件中","已完成","派件失败"}));
        adapter.notifyDataSetChanged();
        viewTabLayout.setupWithViewPager(viewPager);

    }


    private List<Fragment> getFragments(){
        List<Fragment> fragments = new ArrayList<>();
        BKPackAndOrderListFragment ready = new BKPackAndOrderListFragment();
        ready.type = BKPackAndOrderListFragment.ORDER_READY;
        ready.packageNo = packageNo;
        ready.title = "待派件";
        fragments.add(ready);

        BKPackAndOrderListFragment doing = new BKPackAndOrderListFragment();
        doing.type = BKPackAndOrderListFragment.ORDER_DOING;
        doing.packageNo = packageNo;
        doing.title = "派件中";
        fragments.add(doing);


        BKPackAndOrderListFragment finish = new BKPackAndOrderListFragment();
        finish.type = BKPackAndOrderListFragment.ORDER_FINISH;
        finish.packageNo = packageNo;
        finish.title = "已完成";
        fragments.add(finish);

        BKPackAndOrderListFragment failed = new BKPackAndOrderListFragment();
        failed.type = BKPackAndOrderListFragment.ORDER_FAILED;
        failed.packageNo = packageNo;
        failed.title = "派件失败";
        fragments.add(failed);


        return fragments;
    }



}
