package com.app.base.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.app.tool.Tools;

public class AlertDialogBuilder extends AlertDialog.Builder {
    public AlertDialogBuilder(@NonNull Context context) {
        super(context);
    }

    public AlertDialogBuilder(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static AlertDialogBuilder create(@NonNull Context context) {
        return new AlertDialogBuilder(context);
    }

    public static AlertDialogBuilder create(@NonNull Context context, int themeResId) {
        return new AlertDialogBuilder(context, themeResId);
    }

    @Override
    public AlertDialogBuilder setTitle(int titleId) {
        super.setTitle(titleId);
        return this;
    }

    @Override
    public AlertDialogBuilder setTitle(@Nullable CharSequence title) {
        super.setTitle(title);
        return this;
    }

    @Override
    public AlertDialogBuilder setCustomTitle(@Nullable View customTitleView) {
        super.setCustomTitle(customTitleView);
        return this;
    }

    @Override
    public AlertDialogBuilder setMessage(int messageId) {
        super.setMessage(messageId);
        return this;
    }

    @Override
    public AlertDialogBuilder setMessage(@Nullable CharSequence message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public AlertDialogBuilder setIcon(int iconId) {
        super.setIcon(iconId);
        return this;
    }

    @Override
    public AlertDialogBuilder setIcon(@Nullable Drawable icon) {
        super.setIcon(icon);
        return this;
    }

    @Override
    public AlertDialogBuilder setIconAttribute(int attrId) {
        super.setIconAttribute(attrId);
        return this;
    }

    @Override
    public AlertDialogBuilder setPositiveButton(int textId,
            DialogInterface.OnClickListener listener)
    {
        super.setPositiveButton(textId, listener);
        return this;
    }

    @Override
    public AlertDialogBuilder setPositiveButton(CharSequence text,
            DialogInterface.OnClickListener listener)
    {
        super.setPositiveButton(text, listener);
        return this;
    }

    @Override
    public AlertDialogBuilder setPositiveButtonIcon(Drawable icon) {
        super.setPositiveButtonIcon(icon);
        return this;
    }

    @Override
    public AlertDialogBuilder setNegativeButton(int textId,
            DialogInterface.OnClickListener listener)
    {
        super.setNegativeButton(textId, listener);
        return this;
    }

    @Override
    public AlertDialogBuilder setNegativeButton(CharSequence text,
            DialogInterface.OnClickListener listener)
    {
        super.setNegativeButton(text, listener);
        return this;
    }

    @Override
    public AlertDialogBuilder setNegativeButtonIcon(Drawable icon) {
        super.setNegativeButtonIcon(icon);
        return this;
    }

    @Override
    public AlertDialogBuilder setNeutralButton(int textId, DialogInterface.OnClickListener listener)
    {
        super.setNeutralButton(textId, listener);
        return this;
    }

    @Override
    public AlertDialogBuilder setNeutralButton(CharSequence text,
            DialogInterface.OnClickListener listener)
    {
        super.setNeutralButton(text, listener);
        return this;
    }

    @Override
    public AlertDialogBuilder setNeutralButtonIcon(Drawable icon) {
        super.setNeutralButtonIcon(icon);
        return this;
    }

    @Override
    public AlertDialogBuilder setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        return this;
    }

    @Override
    public AlertDialogBuilder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener)
    {
        super.setOnCancelListener(onCancelListener);
        return this;
    }

    @Override
    public AlertDialogBuilder setOnDismissListener(
            DialogInterface.OnDismissListener onDismissListener)
    {
        super.setOnDismissListener(onDismissListener);
        return this;
    }

    @Override
    public AlertDialogBuilder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        super.setOnKeyListener(onKeyListener);
        return this;
    }

    @Override
    public AlertDialogBuilder setItems(int itemsId, DialogInterface.OnClickListener listener) {
        super.setItems(itemsId, listener);
        return this;
    }

    @Override
    public AlertDialogBuilder setItems(CharSequence[] items,
            DialogInterface.OnClickListener listener)
    {
        super.setItems(items, listener);
        return this;
    }

    @Override
    public AlertDialogBuilder setAdapter(ListAdapter adapter,
            DialogInterface.OnClickListener listener)
    {
        super.setAdapter(adapter, listener);
        return this;
    }

    @Override
    public AlertDialogBuilder setCursor(Cursor cursor, DialogInterface.OnClickListener listener,
            String labelColumn)
    {
        super.setCursor(cursor, listener, labelColumn);
        return this;
    }

    @Override
    public AlertDialogBuilder setMultiChoiceItems(int itemsId, boolean[] checkedItems,
            DialogInterface.OnMultiChoiceClickListener listener)
    {
        super.setMultiChoiceItems(itemsId, checkedItems, listener);
        return this;
    }

    @Override
    public AlertDialogBuilder setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems,
            DialogInterface.OnMultiChoiceClickListener listener)
    {
        super.setMultiChoiceItems(items, checkedItems, listener);
        return this;
    }

    @Override
    public AlertDialogBuilder setMultiChoiceItems(Cursor cursor, String isCheckedColumn,
            String labelColumn, DialogInterface.OnMultiChoiceClickListener listener)
    {
        super.setMultiChoiceItems(cursor, isCheckedColumn, labelColumn, listener);
        return this;
    }

    @Override
    public AlertDialogBuilder setSingleChoiceItems(int itemsId, int checkedItem,
            DialogInterface.OnClickListener listener)
    {
        super.setSingleChoiceItems(itemsId, checkedItem, listener);
        return this;
    }

    @Override
    public AlertDialogBuilder setSingleChoiceItems(Cursor cursor, int checkedItem,
            String labelColumn, DialogInterface.OnClickListener listener)
    {
        super.setSingleChoiceItems(cursor, checkedItem, labelColumn, listener);
        return this;
    }

    @Override
    public AlertDialogBuilder setSingleChoiceItems(CharSequence[] items, int checkedItem,
            DialogInterface.OnClickListener listener)
    {
        super.setSingleChoiceItems(items, checkedItem, listener);
        return this;
    }

    @Override
    public AlertDialogBuilder setSingleChoiceItems(ListAdapter adapter, int checkedItem,
            DialogInterface.OnClickListener listener)
    {
        super.setSingleChoiceItems(adapter, checkedItem, listener);
        return this;
    }

    @Override
    public AlertDialogBuilder setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener)
    {
        super.setOnItemSelectedListener(listener);
        return this;
    }

    @Override
    public AlertDialogBuilder setView(int layoutResId) {
        super.setView(layoutResId);
        return this;
    }

    @Override
    public AlertDialogBuilder setView(View view) {
        super.setView(view);
        return this;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public AlertDialogBuilder setView(View view, int viewSpacingLeft, int viewSpacingTop,
            int viewSpacingRight, int viewSpacingBottom)
    {
        super.setView(view, viewSpacingLeft, viewSpacingTop, viewSpacingRight, viewSpacingBottom);
        return this;
    }

    @Override
    public AlertDialogBuilder setInverseBackgroundForced(boolean useInverseBackground) {
        super.setInverseBackgroundForced(useInverseBackground);
        return this;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public AlertDialogBuilder setRecycleOnMeasureEnabled(boolean enabled) {
        super.setRecycleOnMeasureEnabled(enabled);
        return this;
    }

    @Override
    public AlertDialog show() {
        AlertDialog dialog = super.show();
        //由于adapterutils方案导致alertDialog的宽度问题,所以在此处更改dialog的宽度
        dialog.getWindow().setLayout((int) (Tools.Screen.getScreenWidth() * 0.92f),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    public AlertDialog show(int width) {
        AlertDialog dialog = super.show();
        //由于adapterutils方案导致alertDialog的宽度问题,所以在此处更改dialog的宽度
        dialog.getWindow().setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        return dialog;
    }
}
