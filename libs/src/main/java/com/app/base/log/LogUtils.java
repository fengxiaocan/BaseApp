package com.app.base.log;

import android.text.TextUtils;
import android.util.Log;

import com.app.tool.Tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type Log utils.
 *
 * @name： FingerprintLoader
 * @package： com.evil.com.dgtle.baselib.log
 * @author: Noah.冯 QQ:1066537317
 * @time: 10 :46
 * @version: 1.1
 * @desc： 日志操作工具类
 */
public class LogUtils {

    public static final int MAX_LOG_CONTENT_SIZE = 1024 * 2;
    /**
     * The constant FORMAT_TYPE.
     */
    public static final String FORMAT_TYPE = "yyyy.MM.dd_HH:mm:ss.SSS";

    /**
     * The constant LOG_LOCATION.
     */
    public static final String LOG_LOCATION = ">>>>>>>>>>>>>位于%1$s类中的第%2$d行的%3$s方法<<<<<<<<<<<<<";
    /**
     * The constant LOG_FILE_PATH.
     */
    //日志文件保存路径
    static String LOG_FILE_PATH = Tools.App.getExternalCacheDir("log").getAbsolutePath();
    //是否开启代码定位功能
    private static boolean openLocation = false;
    //是否打开打印LOG功能
    private static boolean openLog = Tools.App.isAppDebug();
    //是否打开写入LOG文件功能
    private static boolean openWrite = false;
    //是否开启在线缓存LOG日志功能
    private static boolean openCache = false;
    //是否屏蔽Verbose级别的日志
    private static boolean shieldVerbose = false;
    //是否屏蔽Debug级别的日志
    private static boolean shieldDebug = false;
    //是否屏蔽Info级别的日志
    private static boolean shieldInfo = false;
    //是否屏蔽Warn级别的日志
    private static boolean shieldWarn = false;
    //是否屏蔽Error级别的日志
    private static boolean shieldError = false;

    private static SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_TYPE);

    private LogUtils() {
    }

    /**
     * Sets max pool size.设置最大池子数量
     *
     * @param maxPoolSize the max pool size
     */
    public static void setMaxPoolSize(int maxPoolSize) {
        LogPool.getInstance().setMaxPoolSize(maxPoolSize);
    }

    /**
     * Sets max pool size.设置最大缓存数量
     *
     * @param maxCacheSize the max pool size
     */
    public static void setMaxCacheSize(int maxCacheSize) {
        LogCache.getInstance().setMaxCacheSize(maxCacheSize);
    }

    /**
     * 设置日志文件路径
     *
     * @param logFilePath the com.dgtle.baselib.log file path
     */
    public static void setLogFilePath(String logFilePath) {
        LOG_FILE_PATH = logFilePath;
    }

    /**
     * Open 打开日志定位的功能
     */
    public static void openLocation() {
        openLocation = true;
    }

    /**
     * Open verbose.
     */
    public static void openVerbose() {
        shieldVerbose = false;
    }

    /**
     * Close verbose.
     */
    public static void closeVerbose() {
        shieldVerbose = true;
    }

    /**
     * Open debug.
     */
    public static void openDebug() {
        shieldDebug = false;
    }

    /**
     * Close debug.
     */
    public static void closeDebug() {
        shieldDebug = true;
    }

    /**
     * Open info.
     */
    public static void openInfo() {
        shieldInfo = false;
    }

    /**
     * Close info.
     */
    public static void closeInfo() {
        shieldInfo = true;
    }

    /**
     * Open warn.
     */
    public static void openWarn() {
        shieldWarn = false;
    }

    /**
     * Close warn.
     */
    public static void closeWarn() {
        shieldWarn = true;
    }

    /**
     * Open error.
     */
    public static void openError() {
        shieldError = false;
    }

    /**
     * Close error.
     */
    public static void closeError() {
        shieldError = true;
    }

    /**
     * 关闭日志定位的功能
     */
    public static void closeLocation() {
        openLocation = false;
    }

    /**
     * Open 打开打印日志的功能
     */
    public static void openLog() {
        openLog = true;
    }

    /**
     * 关闭打印日志的功能
     */
    public static void closeLog() {
        openLog = false;
    }

    /**
     * 关闭写日志到文件中的功能
     */
    public static void closeWrite() {
        openWrite = false;
    }

    /**
     * 打开写日志到文件中的功能
     */
    public static void openWrite() {
        openWrite = true;
    }

    /**
     * 关闭日志缓存
     */
    public static void closeCache() {
        openCache = false;
    }

    /**
     * 打开日志缓存
     */
    public static void openCache() {
        openCache = true;
    }

    /**
     * Flush.释放所有的日志文件,保存到文件夹中
     */
    public static void flush() {
        if (openWrite) {
            LogPool.getInstance().flush();
        }
    }

    /**
     * 清理缓冲池
     */
    public static void clearPool() {
        LogPool.getInstance().clear();
    }

    /**
     * 清理缓冲池
     */
    public static void clearCache() {
        LogCache.getInstance().clear();
    }

    /**
     * V.
     *
     * @param TAG the tag
     * @param msg the msg
     */
    public static void v(String TAG, String msg) {
        println(Log.VERBOSE, TAG, msg);
    }

    /**
     * D.
     *
     * @param TAG the tag
     * @param msg the msg
     */
    public static void d(String TAG, String msg) {
        println(Log.DEBUG, TAG, msg);
    }

    /**
     * .
     *
     * @param TAG the tag
     * @param msg the msg
     */
    public static void i(String TAG, String msg) {
        println(Log.INFO, TAG, msg);
    }

    /**
     * W.
     *
     * @param TAG the tag
     * @param msg the msg
     */
    public static void w(String TAG, String msg) {
        println(Log.WARN, TAG, msg);
    }

    /**
     * E.
     *
     * @param TAG the tag
     * @param msg the msg
     */
    public static void e(String TAG, String msg) {
        println(Log.ERROR, TAG, msg);
    }

    /**
     * E.
     *
     * @param TAG the tag
     * @param msg the msg
     */
    public static void println(int priority, String TAG, String msg) {
        if (shieldError) {
            return;
        }
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        String className = "";
        String mothodName = "";
        int line = 0;
        String location = "";
        if (openLocation) {
            className = Tools.StackTrace.getCurrentClassName();
            mothodName = Tools.StackTrace.getCurrentMethodName();
            line = Tools.StackTrace.getCurrentLineNumber();
            location = String.format(LOG_LOCATION, className, line, mothodName);
        }
        if (openLog) {
            if (openLocation) {
                String message = location;
                while (message.length() > MAX_LOG_CONTENT_SIZE) {
                    Log.println(priority, TAG, message.substring(0, MAX_LOG_CONTENT_SIZE));
                    message = message.substring(MAX_LOG_CONTENT_SIZE);
                }
                Log.println(priority, TAG, message);
            }
            String message = msg;
            while (message.length() > MAX_LOG_CONTENT_SIZE) {
                Log.println(priority, TAG, message.substring(0, MAX_LOG_CONTENT_SIZE));
                message = message.substring(MAX_LOG_CONTENT_SIZE);
            }
            Log.println(priority, TAG, message);
        }
        if (openWrite) {
            if (openLocation) {
                LogPool.getInstance().add(priority, TAG, location);
            }
            LogPool.getInstance().add(priority, TAG, msg);
        }
        if (openCache) {
            if (openLocation) {
                LogCache.getInstance().add(priority, TAG, location);
            }
            LogCache.getInstance().add(priority, TAG, msg);
        }
    }

    public static void logVLocation(String TAG, Object msg) {
        logLocation(Log.VERBOSE, TAG, msg);
    }

    public static void logDLocation(String TAG, Object msg) {
        logLocation(Log.DEBUG, TAG, msg);
    }

    public static void logILocation(String TAG, Object msg) {
        logLocation(Log.INFO, TAG, msg);
    }

    public static void logWLocation(String TAG, Object msg) {
        logLocation(Log.WARN, TAG, msg);
    }

    public static void logELocation(String TAG, Object msg) {
        logLocation(Log.ERROR, TAG, msg);
    }

    public static void logLocation(int priority, String TAG, Object msg) {
        Log.println(priority, TAG, "   ");
        Log.println(priority, TAG, "   ");
        Log.println(priority, TAG, "   ");
        Log.println(priority, TAG, String.valueOf(msg));
        StackTraceElement[] stackTrace = Tools.StackTrace.getStackTrace();
        for (StackTraceElement element : stackTrace) {
            Log.println(priority, TAG, new StringBuilder("所在类:").append(element.getClassName()).append(";所在方法:").append(
                    element.getMethodName()).append(";所在行数").append(element.getLineNumber()).toString());
        }
        Log.println(priority, TAG, "  ");
        Log.println(priority, TAG, "  ");
        Log.println(priority, TAG, "  ");
    }

    public static void w(String TAG, Object msg) {
        w(TAG, String.valueOf(msg));
    }

    public static void i(String TAG, Object msg) {
        i(TAG, String.valueOf(msg));
    }

    public static void d(String TAG, Object msg) {
        d(TAG, String.valueOf(msg));
    }

    public static void e(String TAG, Object msg) {
        e(TAG, String.valueOf(msg));
    }

    public static void v(String TAG, Object msg) {
        v(TAG, String.valueOf(msg));
    }

    /**
     * 格式化时间
     *
     * @param time the time
     * @return string string
     */
    public static String formatTime(long time) {
        return formatter.format(new Date(time));
    }
}
