package com.app.base.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class ProgressDialogHelper {
    private static final Map<String, SoftReference<ProgressDialogHelper>> helperHashMap = new HashMap<>();;

    private CommonProgressDialog progressDialog;
    private String mHelperName;

    private ProgressDialogHelper(Context context) {
        this.mHelperName = context.toString();
        progressDialog = CommonProgressDialog.builderTemp1(context).create();
    }

    /**
     * 初始化Activity
     */
    public static ProgressDialogHelper with(@NonNull AppCompatActivity activity) {
        final String key = activity.toString();
        SoftReference<ProgressDialogHelper> reference = helperHashMap.get(key);
        ProgressDialogHelper helper = null;
        if (reference != null){
            helper =reference.get();
        }
        if (helper == null) {
            helper = new ProgressDialogHelper(activity);
            helperHashMap.put(key, new SoftReference<>(helper));
            activity.getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    destroy(key);
                }
            });
        }
        return helper;
    }

    public static ProgressDialogHelper with(@NonNull Context context) {
        final String key = context.toString();
        SoftReference<ProgressDialogHelper> reference = helperHashMap.get(key);
        ProgressDialogHelper helper = null;
        if (reference != null){
            helper =reference.get();
        }
        if (helper == null) {
            helper = new ProgressDialogHelper(context);
            helperHashMap.put(key, new SoftReference<>(helper));
            if (context instanceof AppCompatActivity) {
                ((AppCompatActivity) context).getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
                    if (event == Lifecycle.Event.ON_DESTROY) {
                        destroy(key);
                    }
                });
            }
        }
        return helper;
    }

    public static ProgressDialogHelper with(@NonNull Fragment fragment) {
        final String key = fragment.toString();
        SoftReference<ProgressDialogHelper> reference = helperHashMap.get(key);
        ProgressDialogHelper helper = null;
        if (reference != null){
            helper =reference.get();
        }
        if (helper == null) {
            helper = new ProgressDialogHelper(fragment.getContext());
            helperHashMap.put(key, new SoftReference<>(helper));
            fragment.getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    destroy(key);
                }
            });
        }
        return helper;
    }

    public static void destroy(Context context) {
        if (helperHashMap != null) {
            String key = context.toString();
            SoftReference<ProgressDialogHelper> reference = helperHashMap.get(key);
            ProgressDialogHelper helper = null;
            if (reference != null){
                helper =reference.get();
                if (helper != null) {
                    helper.destroy();
                }
            }
        }
    }

    private static void destroy(String key) {
        if (helperHashMap != null) {
            SoftReference<ProgressDialogHelper> reference = helperHashMap.get(key);
            ProgressDialogHelper helper = null;
            if (reference != null){
                helper =reference.get();
                if (helper != null) {
                    helper.destroy();
                }
            }
        }
    }

    public static void destroy(Activity context) {
        if (helperHashMap != null) {
            String key = context.toString();
            SoftReference<ProgressDialogHelper> reference = helperHashMap.get(key);
            ProgressDialogHelper helper = null;
            if (reference != null){
                helper =reference.get();
                if (helper != null) {
                    helper.destroy();
                }
            }
        }
    }

    public static void destroy(Fragment context) {
        if (helperHashMap != null) {
            String key = context.toString();
            SoftReference<ProgressDialogHelper> reference = helperHashMap.get(key);
            ProgressDialogHelper helper = null;
            if (reference != null){
                helper =reference.get();
                if (helper != null) {
                    helper.destroy();
                }
            }
        }
    }

    public void destroy() {
        if (helperHashMap != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                helperHashMap.remove(mHelperName);
                progressDialog = null;
            }
        }
    }

    public void show() {
        progressDialog.show();
    }

    public ProgressDialogHelper title(CharSequence title) {
        progressDialog.setTitle(title);
        return this;
    }

    public ProgressDialogHelper gravity(int gravity) {
        Window window = progressDialog.getWindow();
        window.setGravity(gravity);
        return this;
    }

    public ProgressDialogHelper cancelable(boolean isCancelable) {
        progressDialog.setCancelable(isCancelable);
        progressDialog.setCanceledOnTouchOutside(isCancelable);
        return this;
    }

    public ProgressDialogHelper message(CharSequence title) {
        progressDialog.setMessage(title);
        return this;
    }

    public ProgressDialogHelper progressColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressDialog.mProgressBar.setIndeterminateTintList(ColorStateList.valueOf(color));
        }
        return this;
    }

    public ProgressDialogHelper progressColor(ColorStateList color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressDialog.mProgressBar.setIndeterminateTintList(color);
        }
        return this;
    }

    public ProgressDialogHelper layoutColor(int color) {
        progressDialog.layoutContent.setBackgroundColor(color);
        return this;
    }

    public ProgressDialogHelper dismissListener(final OnDialogDismissListener listener) {
        progressDialog.setOnDismissListener(dialog -> listener.dismiss());
        progressDialog.setOnCancelListener(dialog -> listener.dismiss());
        return this;
    }

    public void dismiss() {
        progressDialog.dismiss();
    }

    public interface OnDialogDismissListener {
        void dismiss();
    }
}
