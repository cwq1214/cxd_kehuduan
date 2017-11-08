package com.cxd.khd.view.activity;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.cxd.khd.R;
import com.cxd.khd.api.BeanCallback;
import com.cxd.khd.api.Http;
import com.cxd.khd.entity.BaseJson;
import com.cxd.khd.entity.HomeBannerResult;
import com.cxd.khd.entity.SKHomeMsgBoxResult;
import com.cxd.khd.util.Cache;
import com.cxd.khd.util.IntentHelper;
import com.cxd.khd.util.T;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by chenweiqi on 2017/5/31.
 */

public class MainActivity extends BaseActivity {


    CustomMsgReceiver talkReceiver;
    @BindView(R.id.view_banner)
    BGABanner viewBanner;
    @BindView(R.id.img_recive1)
    ImageView imgSend;
    @BindView(R.id.text_one)
    TextView textOne;
    @BindView(R.id.layout_one)
    RelativeLayout layoutOne;
    @BindView(R.id.img_msgBox)
    ImageView imgMsgBox;
    @BindView(R.id.layout_order)
    RelativeLayout layoutOrder;
    @BindView(R.id.img_personalCenter)
    ImageView imgPersonalCenter;
    @BindView(R.id.layout_msgBox)
    RelativeLayout layoutMsgBox;
    @BindView(R.id.img_order)
    ImageView imgOrder;
    @BindView(R.id.layout_personalCenter)
    RelativeLayout layoutPersonalCenter;
    @BindView(R.id.layout_grid)
    GridLayout gridLayout;
    @BindView(R.id.layout_root)
    LinearLayout linearLayout;
    @BindView(R.id.text_msgCount)
    TextView textMsgCount;

    int loginType;

    public static int msgCount;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        hideBackBtn();
        textTitle.setText("城乡递");

        loginType = Cache.getInstance().getLoginType();
        talkReceiver = new CustomMsgReceiver();
        IntentFilter intentFilter = new IntentFilter(JPushInterface.ACTION_MESSAGE_RECEIVED);
        intentFilter.addCategory(getPackageName());
        registerReceiver(talkReceiver, intentFilter);

        if (Cache.getInstance().getLoginType() == 1) {//sk
            imgSend.setImageDrawable(getResources().getDrawable(R.mipmap.home_icon_jijian_n));
            textOne.setText("寄件");
        } else if (Cache.getInstance().getLoginType() == 2) {//bk
            imgSend.setImageDrawable(getResources().getDrawable(R.mipmap.home_icon_piliang_n));
            textOne.setText("批量下单");
        }
        getBanner();

        gridLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(linearLayout.getWidth(),linearLayout.getHeight()-viewBanner.getHeight());
                gridLayout.setLayoutParams(params);
                gridLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean exit = getIntent().getBooleanExtra(IntentHelper.KEY_EXIT,false);
        if (exit){
            finish();
            return;
        }

        if (loginType != Cache.getInstance().getLoginType()) {
            if (Cache.getInstance().getLoginType() == 1) {//sk
                imgSend.setImageDrawable(getResources().getDrawable(R.mipmap.home_icon_jijian_n));
                textOne.setText("寄件");
            } else if (Cache.getInstance().getLoginType() == 2) {//bk
                imgSend.setImageDrawable(getResources().getDrawable(R.mipmap.home_icon_piliang_n));
                textOne.setText("批量下单");
            }
            getBanner();
        }


        setMsgCount(msgCount);

    }

    private void setMsgCount(int msgCount){
        if (msgCount==0){
            textMsgCount.setVisibility(View.GONE);
        }else {
            textMsgCount.setVisibility(View.VISIBLE);
            if (msgCount>99){
                textMsgCount.setText("99+");
            }else {
                textMsgCount.setText(msgCount + "");
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(talkReceiver);
    }

    @OnClick(R.id.layout_one)
    public void onOneViewClick(){
        if (Cache.getInstance().getLoginType()==1){
            IntentHelper.openSKSendActivity(getContext());
        }else if (Cache.getInstance().getLoginType()==2){
            IntentHelper.openBKSendOrderActivity(getContext());
        }
    }

    @OnClick(R.id.layout_order)
    public void onOrderClick(){
        if (Cache.getInstance().getLoginType()==1){
            IntentHelper.openSKOrderListActivity(getContext());
        }else if (Cache.getInstance().getLoginType()==2){
            IntentHelper.openBKPackageAndOrderListActivity(getContext());
        }
    }
    @OnClick(R.id.layout_msgBox)
    public void onMsgBoxClick(){
        IntentHelper.openMessageBox(getContext());
    }

    @OnClick(R.id.layout_personalCenter)
    public void onPersonalCenterClick(){
//        if (Cache.getInstance().getLoginType()==1){
            IntentHelper.openSKPersonalCenter(getContext());
//        }else if (Cache.getInstance().getLoginType()==2){
//
//        }
    }


    private void getBanner(){
        BeanCallback callback = new BeanCallback<BaseJson<HomeBannerResult>>(getContext()) {
            @Override
            public void onResponse(BaseJson<HomeBannerResult> response, int id) {
                super.onResponse(response,id);
                T.showShort(getContext(),response.forUser);
                if (response.ret) {
                    setView(response.data);
                } else {

                }
            }
        };
        if (Cache.getInstance().getLoginType()==1){
            Http.getSKBanner(getContext(),callback);
        }else if (Cache.getInstance().getLoginType()==2){
            Http.getBKBanner(getContext(),callback);
        }

    }
    private void setView(HomeBannerResult result) {

        viewBanner.setPageChangeDuration(Integer.parseInt(result.stayTime) * 1000);

        List<View> imageViews = new ArrayList<>();

        for (int i = 0; i < result.marquee.size(); i++) {

            ImageView imageView = new ImageView(getContext());
            Glide.with(getContext()).load(result.marquee.get(i).img).bitmapTransform(new CenterCrop(getContext())).into(imageView);
            final String url = result.marquee.get(i).link;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(url))
                        IntentHelper.openSystemBrowser(getContext(),url);
                }
            });

            imageViews.add(imageView);
        }

        viewBanner.setData(imageViews);
    }


    public class CustomMsgReceiver extends BroadcastReceiver {
        private static final String TAG = "CustomMsgReceiver";

        private NotificationManager nm;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (null == nm) {
                nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            }

            Bundle bundle = intent.getExtras();
            Log.d(TAG, "onReceive - " + intent.getAction() + ", extras: " + Arrays.toString(bundle.keySet().toArray()));
            if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Log.d(TAG, "接受到推送下来的自定义消息");
                // Push Talk messages are push down by custom message format
                processCustomMessage(context, bundle);
            }
        }

        private void processCustomMessage(Context context, Bundle bundle) {
            String title = bundle.getString(JPushInterface.EXTRA_TITLE);
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);

        }
    }

    private void getMessage(){
        if (Cache.getInstance().getLoginType()==1){
            Http.getSKHomeMessage(getContext(), new BeanCallback<BaseJson<SKHomeMsgBoxResult>>(getContext()) {
                @Override
                public void onResult(boolean success, BaseJson<SKHomeMsgBoxResult> response, Exception e) {
                    super.onResult(success, response, e);
                    if (success){
                        if (response.ret){
                            if (response.data!=null&& !TextUtils.isEmpty(response.data.countOrder)){
                                MainActivity.msgCount = Integer.valueOf(response.data.countOrder);
                                setMsgCount(msgCount);
                            }
                        }
                    }
                }
            });
        }else if (Cache.getInstance().getLoginType()==2){
            Http.getBKMessage(getContext(), new BeanCallback<BaseJson<SKHomeMsgBoxResult>>(getContext()) {
                @Override
                public void onResult(boolean success, BaseJson<SKHomeMsgBoxResult> response, Exception e) {
                    super.onResult(success, response, e);
                    if (success){
                        if (response.ret){
                            if (response.data!=null&& !TextUtils.isEmpty(response.data.countOrder)){
                                MainActivity.msgCount = Integer.valueOf(response.data.countOrder);
                                setMsgCount(msgCount);
                            }
                        }
                    }

                }
            });
        }
    }

}
