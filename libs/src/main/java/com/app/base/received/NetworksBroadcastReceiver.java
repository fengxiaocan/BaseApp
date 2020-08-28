package com.app.base.received;

import android.annotation.SuppressLint;
import android.app.admin.NetworkEvent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.app.base.intface.*;
import com.app.tool.Tools;


/**
 * @name： BaseApp
 * @package： com.dgtle.baselib.base.received
 * @author: Noah.冯 QQ:1066537317
 * @time: 19:23
 * @version: 1.1
 * @desc： 网络状态变化监听器
 */

public class NetworksBroadcastReceiver extends BroadcastReceiver {
    //	private boolean isNoFirst = false;
    private static OnNetStatusChangeListener networkStatus;

    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent){
        // 如果相等的话就说明网络状态发生了变化
        if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
            if (networkStatus != null){
                networkStatus.onChange(Tools.Net.getActiveNetworkInfo().getType());
            }
        }
    }
}
