package com.qts.gopik_money.Utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class FileDownloader {

    public void downloadFile(String url, Activity mActivity) {
        Log.e("FilePath ", url);
        File direct = new File(Environment.getExternalStorageDirectory() + "/MyApp");
        if (direct.exists() && direct.isDirectory()) {

                String[] children = direct.list();
                for (int i = 0; i < children.length; i++) {
                    new File(direct, children[i]).delete();
                }

        }
        if (!direct.exists()) {
            direct.mkdirs();
        }
        DownloadManager mgr = (DownloadManager) mActivity.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadUri = Uri.parse(url);
        long time = System.currentTimeMillis();
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle("Invoice" + time)
                .setDescription("Saving Image")
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "invoiceFIle.jpg")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        mgr.enqueue(request);
    }
}
