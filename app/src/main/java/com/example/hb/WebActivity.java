package com.example.hb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.dd.processbutton.iml.ActionProcessButton;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Vic on 2016/4/21 0021.
 */
public class WebActivity extends BaseActivity {

    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.webView)
    com.tencent.smtt.sdk.WebView webView;
    @Bind(R.id.progress)
    ActionProcessButton progress;
    private ProgressGenerator generator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setSupportActionBar(toolBar);
        //设置toolbar后调用setDisplayHomeAsUpEnabled
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progress.setMode(ActionProcessButton.Mode.ENDLESS);
        generator = new ProgressGenerator(progress);

        webView.setWebViewClient(client);
        String url = getIntent().getStringExtra(AppConstant.INTENT_URL);
        webView.loadUrl(url);
        loading();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    private com.tencent.smtt.sdk.WebViewClient client = new com.tencent.smtt.sdk.WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView webView, String s) {
            webView.loadUrl(s);
            loading();
            return true;
        }

        @Override
        public void onPageFinished(com.tencent.smtt.sdk.WebView webView, String s) {
            super.onPageFinished(webView, s);
            toolBar.setTitle(webView.getTitle());
            loadFinish();
        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView != null && webView.canGoBack()) {
                webView.goBack();
                return true;
            } else
                return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void loading() {
        progress.setVisibility(View.VISIBLE);
        generator.start();
    }

    private void loadFinish() {
        progress.setProgress(100);
        progress.setVisibility(View.GONE);
    }
}
