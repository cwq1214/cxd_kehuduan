package com.cxd.khd.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cxd.khd.R;
import com.cxd.khd.adapter.FragmentViewPagerAdapter;
import com.cxd.khd.view.fragment.BKOrderListFragment;
import com.cxd.khd.view.fragment.BKPackListFragment;
import com.cxd.khd.view.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/6/21.
 */

public class BKOrderListActivity extends BaseActivity {
    @BindView(R.id.rbtn_pickup)
    RadioButton rbtnPickup;
    @BindView(R.id.rbtn_send)
    RadioButton rbtnSend;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;

    FragmentViewPagerAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_bk_order_list;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseActionBarVisible(false);
        viewPager.setAdapter(adapter = new FragmentViewPagerAdapter(getSupportFragmentManager()));
        adapter.setFragments(getFragments());
        adapter.notifyDataSetChanged();


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbtn_pickup){
                    viewPager.setCurrentItem(0,false);
                }else if (checkedId == R.id.rbtn_send){
                    viewPager.setCurrentItem(1,false);
                }
            }
        });
    }



    @OnClick(R.id.btn_back2)
    @Override
    public void onBtnBackClick() {
        super.onBtnBackClick();
    }


    private List getFragments(){
        List<Fragment> fragments = new ArrayList<>();

        BKPackListFragment packListFragment = new BKPackListFragment();
        fragments.add(packListFragment);

        BKOrderListFragment orderListFragment = new BKOrderListFragment();
        fragments.add(orderListFragment);

        return fragments;
    }


}
