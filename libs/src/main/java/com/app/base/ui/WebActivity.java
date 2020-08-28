package com.app.base.ui;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;


/**
 * The type Web activity.
 *
 * @项目名： Mibokids
 * @包名： com.mibokids.app.activity
 * @创建者: feng
 * @时间: 9 :37
 * @描述： 自带浏览器
 */
public abstract class WebActivity extends AppActivity{

    /**
     * The M webview.
     */
    public WebView mWebview;
    /**
     * The M web settings.
     */
    public WebSettings mWebSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(createContentView());
        mWebview = findWebView();
        initData();
    }

    /**
     * Create content view int.设置内容view
     *
     * @return the int
     */
    public abstract @LayoutRes
    int createContentView();

    /**
     * Find web view web view.设置浏览器
     *
     * @return the web view
     */
    public abstract WebView findWebView();

    /**
     * Init data.
     */
    public void initData(){
        //1.首先在WebView初始化时添加如下代码
        mWebview.getSettings().setLoadsImagesAutomatically(true);
        mWebview.setWebViewClient(new MyWebViewClient());
        mWebview.setWebChromeClient(new WebChromeClient());
        mWebview.setDownloadListener(new MyDownloadListenter());
        mWebSettings = mWebview.getSettings();
        //支持js
        mWebSettings.setJavaScriptEnabled(true);

        //设置默认编码
        mWebSettings.setDefaultTextEncodingName("utf-8");

        //将图片调整到适合webview的大小
        mWebSettings.setUseWideViewPort(false);

        //支持缩放
        mWebSettings.setSupportZoom(true);

        //支持内容重新布局
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        //设置可以访问文件
        mWebSettings.setAllowFileAccess(true);

        //当webview调用requestFocus时为webview设置节点
        mWebSettings.setNeedInitialFocus(true);

        //设置支持缩放
        mWebSettings.setBuiltInZoomControls(true);

        //支持通过JS打开新窗口
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        //缩放至屏幕的大小
        mWebSettings.setLoadWithOverviewMode(true);

        //支持自动加载图片
        mWebSettings.setLoadsImagesAutomatically(true);
    }

    /**
     * Load url.加载url
     *
     * @param url the url
     */
    public void loadUrl(String url){
        if(! url.startsWith("http")){
            url = "http://" + url;
        }
        //加载地址
        mWebview.loadUrl(url);
    }

    /**
     * 加载html格式的文本
     */
    public void loadHtmlText(String text){
        if(text == null){
            return;
        }
        //加载地址
        mWebview.loadData(text,"text/html; charset=UTF-8",null);
    }

    /**
     * Go back.返回上一个网址
     */
    public void goBack(){
        if(mWebview != null){
            if(mWebview.canGoBack()){
                mWebview.goBack();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        //其中webView.canGoBack()在webView含有一个可后退的浏览记录时返回true
        if((keyCode == KeyEvent.KEYCODE_BACK) && mWebview.canGoBack()){
            mWebview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    /**
     * The type My web view client.
     */
    public class MyWebViewClient extends WebViewClient {

//        @Override
//        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
//            handler.cancel();  // 不接受信任所有网站的证书
//        }

        @Override
        public void onPageFinished(WebView view, String url){
            if(! mWebview.getSettings().getLoadsImagesAutomatically()){
                mWebview.getSettings().setLoadsImagesAutomatically(true);
            }
        }
    }

    /**
     * 通常webview渲染的界面中含有可以下载文件的链接，点击该链接后，应该开始执行下载的操作并保存文件到本地中。
     */
    public class MyDownloadListenter implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength)
        {
            //下载任务...，主要有两种方式
            //1.自定义下载任务
            //2.调用系统的download的模块
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }
    }
}
