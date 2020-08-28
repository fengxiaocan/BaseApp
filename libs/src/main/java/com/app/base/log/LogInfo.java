package com.app.base.log;

import android.util.Log;

import static com.app.base.log.LogUtils.formatTime;

/**
 * @name： FingerprintLoader
 * @package： com.evil.com.dgtle.baselib.log
 * @author: Noah.冯 QQ:1066537317
 * @time: 12:23
 * @version: 1.1
 * @desc： TODO
 */

public class LogInfo {

    int type;
    String TAG;
    String log;
    String time;

    public LogInfo() {
        this.time = formatTime(System.currentTimeMillis());
    }

    public LogInfo(int type, String TAG, String log) {
        this.type = type;
        this.TAG = TAG;
        this.log = log;
        this.time = formatTime(System.currentTimeMillis());
    }


    public String getType() {
        switch (type) {
            case Log.VERBOSE:
                return "V";
            case Log.DEBUG:
                return "D";
            case Log.INFO:
                return "I";
            case Log.WARN:
                return "W";
            case Log.ERROR:
                return "E";
        }
        return "V";
    }

    @Override
    public String toString() {
        return new StringBuffer(time).append(" ").append(TAG).append(" ").append(getType()).append(": ").append(log).toString();
    }

    public String toStrings() {
        return new StringBuffer(time).append(" ").append(TAG).append(": ").append(log).toString();
    }
}
