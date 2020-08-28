package com.app.base.helper;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import com.app.tool.Tools;

import java.util.Locale;

/**
 * The type Language helper.多语言的工具类
 */
public final class LanguageHelper{

    /**
     * The constant LANGUAGE_NAME.
     */
    public static final String LANGUAGE_NAME = "Locale.ini";
    private static Locale sLocale;

    private LanguageHelper(){
    }

    /**
     * 多语言设置
     * 最好放在Activity
     *
     * @param language the language
     */
    public static void setLanguage(Locale language){
        if(language == null){
            language = Locale.getDefault();
        }
        sLocale = language;
        Resources resources = Tools.Resource.getResources();

        Configuration config = resources.getConfiguration();
        // 应用用户选择语言
        config.locale = language;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            //android7.1.1以上的适配
            // apply locale
            config.setLocale(language);
            Tools.getContext().createConfigurationContext(config);
        } else{
            // 应用用户选择语言
            DisplayMetrics dm = resources.getDisplayMetrics();
            resources.updateConfiguration(config,dm);
        }
        //持久化保存
        Tools.Objects.save(LANGUAGE_NAME,language);
    }

    /**
     * 获取上次保存的语言
     *
     * @return the locale
     */
    public static Locale getLastLanguage(){
        if(sLocale == null){
            sLocale = Tools.Objects.get(LANGUAGE_NAME);
        }
        if(sLocale == null){
            //获取系统首选语言
            sLocale = getSystemPreferredLanguage();
        }
        return sLocale;
    }

    /**
     * 获取系统首选语言
     *
     * @return Locale
     */
    public static Locale getSystemPreferredLanguage(){
        Locale locale;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            //android 7.1.1以上的有多个语言选择
            locale = LocaleList.getDefault().get(0);
        } else{
            locale = Locale.getDefault();
        }
        return locale;
    }
}



