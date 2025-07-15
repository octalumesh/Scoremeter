package com.cricbuzzplus.liveline.livedata.ui.activity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cricbuzzplus.liveline.R;
import com.cricbuzzplus.liveline.utils.Constants;


public class WebPDFActivity extends AppCompatActivity {

    WebView browser;
    Context context;
    AppCompatActivity activity;
    SwipeRefreshLayout refresh;
    //FloatingActionButton download;
    String currentUrl = "";
    String pdf="";
   // ValueCallback<Uri[]> mfilePathCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.web_url_activity);
        pdf = getIntent().getStringExtra("pdf"); //.getStringExtra("pdf");
       // pdf = getIntent().getExtras().getString("pdf"); //.getStringExtra("pdf");
        Log.e("docurl",""+pdf);
        browser = (WebView) findViewById(R.id.browser);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
       // download = (FloatingActionButton) findViewById(R.id.download);
        WebSettings settings = browser.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //settings.setAppCacheEnabled(false);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        browser.setWebViewClient(new AppWebViewClient());
        browser.loadUrl(pdf);
       // browser.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdf);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                browser.loadUrl(currentUrl);
            }
        });
        refresh.setRefreshing(true);

     //   browser.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdf);

/*        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storagePermission();
            }
        });*/

        /*browser.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mfilePathCallback=filePathCallback;
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
            }
        });
        mfilePathCallback=new ValueCallback<Uri[]>() {
            @Override
            public void onReceiveValue(Uri[] uris) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
               // intent.setType("star/star");
                startActivityForResult(intent, 101);
            }
        };*/

    }


    void startServices(){
        // start downliadoing from here

        Uri uri= Uri.parse(pdf);
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);

// set title and description
        request.setTitle("Data Download");
        request.setDescription("Android Data download using DownloadManager.");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

//set the local destination for download file to a path within the application's external files directory
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"Egf"+System.currentTimeMillis()+".pdf");
        request.setMimeType("*/*");
        downloadManager.enqueue(request);
    }



    /////////////////// Permissions /////////////////////

    void storagePermission() {
        int requestcode = Constants.storagePermissionCode_download;

        int myAPI = Build.VERSION.SDK_INT;
        if (myAPI >= 23) {
            int readPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int writePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (readPermission == PackageManager.PERMISSION_GRANTED && writePermission == PackageManager.PERMISSION_GRANTED) {
                startServices();

            } else {

                /*if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {}*/

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestcode);
            }
        } else {
            startServices();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Constants.storagePermissionCode_download) {
            boolean fileReadpermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean fileWritepermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            if (grantResults.length > 0 && fileReadpermission && fileWritepermission) {
                startServices();
            } else {
                Toast.makeText(activity, "Approve permissions to start Download", Toast.LENGTH_LONG).show();
            }
        }
    }





    @Override
    public void onBackPressed() {
        if (browser.canGoBack()) {
            browser.goBack();
        } else {
            super.onBackPressed();
        }
    }

    class AppWebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            //loadImage.show();
            refresh.setRefreshing(true);
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            //
            //
            //browser.clearCache(true);
//            loadImage.hide();
            refresh.setRefreshing(false);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, final String url) {
           /* if(Uri.parse(url).getHost().length() == 0) {
                return false;
            }*/
            currentUrl = url;
            browser.loadUrl(url);
            return true;
        }

    }
}
