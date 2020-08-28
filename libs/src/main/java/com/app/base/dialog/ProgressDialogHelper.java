package com.app.base.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.HashMap;
import java.util.Map;

public class ProgressDialogHelper {
    private static Map<String, ProgressDialogHelper> helperHashMap;

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
        if (helperHashMap == null) {
            helperHashMap = new HashMap<>();
        }
        final String key = activity.toString();
        ProgressDialogHelper helper = helperHashMap.get(key);
        if (helper == null) {
            helper = new ProgressDialogHelper(activity);
            helperHashMap.put(key, helper);
            activity.getLifecycle().addObserver(new LifecycleEventObserver() {
                @Override
                public void onStateChanged(@NonNull LifecycleOwner source,
                                           @NonNull Lifecycle.Event event) {
                    if (event == Lifecycle.Event.ON_DESTROY) {
                        destroy(key);
                    }
                }
            });
        }
        return helper;
    }

    public static ProgressDialogHelper with(@NonNull Context context) {
        if (helperHashMap == null) {
            helperHashMap = new HashMap<>();
        }
        final String key = context.toString();
        ProgressDialogHelper helper = helperHashMap.get(key);
        if (helper == null) {
            helper = new ProgressDialogHelper(context);
            helperHashMap.put(key, helper);
            if (context instanceof AppCompatActivity) {
                ((AppCompatActivity) context).getLifecycle().addObserver(new LifecycleEventObserver() {
                    @Override
                    public void onStateChanged(@NonNull LifecycleOwner source,
                                               @NonNull Lifecycle.Event event) {
                        if (event == Lifecycle.Event.ON_DESTROY) {
                            destroy(key);
                        }
                    }
                });
            }
        }
        return helper;
    }

    public static ProgressDialogHelper with(@NonNull Fragment fragment) {
        if (helperHashMap == null) {
            helperHashMap = new HashMap<>();
        }
        final String key = fragment.toString();
        ProgressDialogHelper helper = helperHashMap.get(key);
        if (helper == null) {
            helper = new ProgressDialogHelper(fragment.getContext());
            helperHashMap.put(key, helper);
            fragment.getLifecycle().addObserver(new LifecycleEventObserver() {
                @Override
                public void onStateChanged(@NonNull LifecycleOwner source,
                                           @NonNull Lifecycle.Event event) {
                    if (event == Lifecycle.Event.ON_DESTROY) {
                        destroy(key);
                    }
                }
            });
        }
        return helper;
    }

    public static void destroy(Context context) {
        if (helperHashMap != null) {
            String key = context.toString();
            ProgressDialogHelper helper = helperHashMap.get(key);
            if (helper != null) {
                helper.destroy();
            }
            if (helperHashMap.isEmpty()) {
                helperHashMap = null;
            }
        }
    }

    private static void destroy(String key) {
        if (helperHashMap != null) {
            ProgressDialogHelper helper = helperHashMap.get(key);
            if (helper != null) {
                helper.destroy();
            }
            if (helperHashMap.isEmpty()) {
                helperHashMap = null;
            }
        }
    }

    public static void destroy(Activity context) {
        if (helperHashMap != null) {
            String key = context.toString();
            ProgressDialogHelper helper = helperHashMap.get(key);
            if (helper != null) {
                helper.destroy();
            }
            if (helperHashMap.isEmpty()) {
                helperHashMap = null;
            }
        }
    }

    public static void destroy(Fragment context) {
        if (helperHashMap != null) {
            String key = context.toString();
            ProgressDialogHelper helper = helperHashMap.get(key);
            if (helper != null) {
                helper.destroy();
            }
            if (helperHashMap.isEmpty()) {
                helperHashMap = null;
            }
        }
    }

    public void destroy() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
            helperHashMap.remove(mHelperName);
            progressDialog = null;
        }
        if (helperHashMap.isEmpty()) {
            helperHashMap = null;
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
        progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                listener.dismiss();
            }
        });
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                listener.dismiss();
            }
        });
        return this;
    }

    public void dismiss() {
        progressDialog.dismiss();
    }

    public interface OnDialogDismissListener {
        void dismiss();
    }
}
