package com.cxd.khd.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cxd.khd.R;
import com.cxd.khd.adapter.FragmentViewPagerAdapter;
import com.cxd.khd.view.fragment.SKOrderListFragment;
import com.cxd.khd.view.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chenweiqi on 2017/6/15.
 */

public class SKOrderListActivity extends BaseActivity {
    @BindView(R.id.view_tabLayout)
    TabLayout viewTabLayout;
    @BindView(R.id.view_viewPager)
    NoScrollViewPager viewViewPager;


    FragmentViewPagerAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_sk_order_list;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        viewViewPager.setAdapter(adapter = new FragmentViewPagerAdapter(getSupportFragmentManager()));

        adapter.setFragments(getFragments());
        adapter.setTitles(Arrays.asList(new String[]{"进行中","已完成","已取消"}));
        adapter.notifyDataSetChanged();

        viewTabLayout.setupWithViewPager(viewViewPager);

    }



    private List<Fragment> getFragments(){
        List fragments = new ArrayList();

        SKOrderListFragment doing = new SKOrderListFragment();
        doing.type = SKOrderListFragment.TYPE_DOING;
        fragments.add(doing);

        SKOrderListFragment finish = new SKOrderListFragment();
        finish.type = SKOrderListFragment.TYPE_FINISH;
        fragments.add(finish);

        SKOrderListFragment cancel = new SKOrderListFragment();
        cancel.type = SKOrderListFragment.TYPE_CANCEL;
        fragments.add(cancel);

        return fragments;
    }
}
