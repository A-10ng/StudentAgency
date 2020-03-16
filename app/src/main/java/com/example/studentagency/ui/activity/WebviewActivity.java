package com.example.studentagency.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.studentagency.R;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

public class WebviewActivity extends BaseActivity {

    private static final String TAG = "WebviewActivity";
    private WebView webView;
    private WebSettings webSettings;
    private String url;
    private ProgressBar progressBar;
    private WebviewHandler webviewHandler = new WebviewHandler(this);

    //用于设置webview的加载超时时间
    private long TIME_OUT = 5000;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        //拿到传过来的url
        url = getIntent().getStringExtra("url");
        Log.i(TAG, "传过来的url: "+url);

        webView = findViewById(R.id.webView);
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//让webview支持js脚本

        //设置可以支持缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);//设置出现缩放工具
        webSettings.setDisplayZoomControls(false);//设置缩放控件隐藏

        //设置自适应屏幕
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        //当需要从一个网页跳转到另一个网页时，目标网页仍然在当前webview中显示
        webView.setWebViewClient(new WebViewClient(){

            /**
             * 用于设置加载超时时间，主要思想是利用计时器
             * @param view
             * @param url
             * @param favicon
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                Log.i(TAG, "onPageStarted: 页面开始加载");
                final int curProgress = webView.getProgress();

                timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        //超时后，首先判断页面加载进度，超时并且进度小于100，就执行超时后的动作
                        if (curProgress < 100){
                            Log.i(TAG, "网页加载超时！");
                            webviewHandler.sendEmptyMessage(1);
                            timer.cancel();
                            timer.purge();
                        }
                    }
                };
                timer.schedule(timerTask,TIME_OUT,1);
            }

            //页面加载完成后，取消计时器
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                Log.i(TAG, "onPageFinished: 页面加载完成");
                if (null != timer){
                    timer.cancel();
                    timer.purge();
                }
            }
        });

        //将加载进度显示到progressBar上
        progressBar = findViewById(R.id.progressBar);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100){
                    progressBar.setVisibility(View.GONE);
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }
        });

        //webview加载传过来的url
        webView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()){
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (webView != null){
            webView.loadDataWithBaseURL(null,"","text/html","utf-8",null);
            webView.clearHistory();//清楚当前webview访问的历史记录
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    private void setErrorPage(){
        RelativeLayout layout = new RelativeLayout(this);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_webview_error,null);
        ImageView iv_error = view.findViewById(R.id.iv_loaderror);
        iv_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl(url);
            }
        });
        layout.addView(view);
        layout.setGravity(Gravity.CENTER);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);

        addContentView(layout,params);

        layout.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
    }

    private class WebviewHandler extends Handler{
        private WeakReference<Context> reference;

        public WebviewHandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            WebviewActivity webviewActivity = (WebviewActivity) reference.get();
            if (webviewActivity != null) {
                switch (msg.what) {
                    case 1:
                        //避免出现默认的错误界面
                        webView.loadUrl("");
                        //加载自定义的错误页面
                        setErrorPage();
                    break;
                }
            }
        }
    }
}
