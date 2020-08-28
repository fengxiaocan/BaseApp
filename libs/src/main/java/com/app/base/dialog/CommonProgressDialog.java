package com.app.base.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

import com.app.base.R;
import com.app.tool.Tools;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

/**
 * Created: AriesHoo on 2017-01-19 14:16
 * Function: 自定义AlertDialog 弹出提示框
 * Desc:
 */

public class CommonProgressDialog extends Dialog {
	public static final int MATCH_PARENT = WindowManager.LayoutParams.MATCH_PARENT;
	public static final int WRAP_CONTENT = WindowManager.LayoutParams.WRAP_CONTENT;
	LinearLayout layoutContent;
	TextView mTitle;
	TextView mMessage;
	ProgressBar mProgressBar;
	private int width = WindowManager.LayoutParams.WRAP_CONTENT;
	private int height = WindowManager.LayoutParams.WRAP_CONTENT;

	private CommonProgressDialog(Context context) {
		super(context);
	}

	private CommonProgressDialog(
			Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	private CommonProgressDialog(Context context, int themeResId) {
		super(context, themeResId);
	}

	public static Builder builderTemp1(Context context) {
		return new Builder(context, R.layout.dialog_progress_template1);
	}

	public static Builder builderTemp2(Context context) {
		return new Builder(context, R.layout.dialog_progress_template2)
				.layout(MATCH_PARENT, WRAP_CONTENT);
	}

	public static Builder builderTemp1(Context context, int themeResId) {
		return new Builder(context, R.layout.dialog_progress_template1, themeResId);
	}

	public static Builder builderTemp2(Context context, int themeResId) {
		return new Builder(context, R.layout.dialog_progress_template2, themeResId)
				.layout(MATCH_PARENT, WRAP_CONTENT);
	}

	public void setTitle(int title) {
		if (mTitle != null) {
			mTitle.setText(title);
		}
	}

	public void setMessage(int title) {
		if (mMessage != null) {
			mMessage.setText(title);
		}
	}

	public void setTitle(CharSequence title) {
		if (mTitle != null) {
			mTitle.setText(title);
		}
	}

	public void setMessage(CharSequence title) {
		if (mMessage != null) {
			mMessage.setText(title);
		}
	}

	private void findViewById() {
		mTitle = findViewById(R.id.tv_title);
		mMessage = findViewById(R.id.tv_message);
		mProgressBar = findViewById(R.id.loading_progress);
		layoutContent = findViewById(R.id.layout_content);
	}

	@Override
	public void show() {
		super.show();
		Window window = getWindow();
		window.setLayout(width, height);
	}

	public static class Builder {
		private CommonProgressDialog dialog;

		private Builder(Context context, int layout) {
			dialog = new CommonProgressDialog(context);
			dialog.setContentView(layout);
			dialog.findViewById();
		}

		private Builder(Context context, int layout, int themeResId) {
			dialog = new CommonProgressDialog(context, themeResId);
			dialog.setContentView(layout);
			dialog.findViewById();
		}


		@Deprecated
		public Builder findViewById(IFindViewCallback callback) {
			callback.findView(dialog);
			return this;
		}

		/**
		 * 设置窗口透明度
		 *
		 * @param alpha the alpha
		 * @return ios dialog
		 */
		public Builder setAlpha(float alpha) {
			Window window = dialog.getWindow();
			WindowManager.LayoutParams params = window.getAttributes();
			params.alpha = alpha;
			window.setAttributes(params);
			return this;
		}

		/**
		 * 设置背景黑暗度
		 *
		 * @param dimAmount the dim amount
		 * @return ios dialog
		 */
		public Builder setDimAmount(float dimAmount) {
			Window window = dialog.getWindow();
			window.setDimAmount(dimAmount);
			return this;
		}

		public Builder layout(int width, int height) {
			dialog.width = width;
			dialog.height = height;
			return this;
		}

		public Builder background(Drawable drawable) {
			Window window = dialog.getWindow();
			window.setBackgroundDrawable(drawable);
			return this;
		}

		public Builder background(@DrawableRes int resId) {
			Window window = dialog.getWindow();
			window.setBackgroundDrawable(dialog.getContext().getResources().getDrawable(resId));
			return this;
		}

		public Builder gravity(int gravity) {
			Window window = dialog.getWindow();
			window.setGravity(gravity);
			return this;
		}

		public Builder matchWidth() {
			dialog.width = Tools.Screen.getScreenWidth();
			background(null);
			return this;
		}

		public Builder padding(int left, int top, int right, int bottom) {
			Window window = dialog.getWindow();
			window.getDecorView().setPadding(left, top, right, bottom);
			return this;
		}


		public Builder matchHeight() {
			dialog.height = Tools.Screen.getScreenHeight();
			background(null);
			return this;
		}

		public Builder matchScreen() {
			dialog.width = Tools.Screen.getScreenWidth();
			dialog.height = Tools.Screen.getScreenHeight();
			background(null);
			return this;
		}

		public Builder progressStyle(Drawable drawable) {
			dialog.mProgressBar.setIndeterminateDrawable(drawable);
			return this;
		}

		/**
		 * 是否设置点击dialog区域外，dialog消失
		 *
		 * @param cancel the cancel
		 */
		public Builder setCanceledOnTouchOutside(boolean cancel) {
			dialog.setCanceledOnTouchOutside(cancel);
			return this;
		}

		public Builder contentPadding(int left, int top, int right, int bottom) {
			if (dialog.layoutContent != null) {
				dialog.layoutContent.setPadding(Tools.Size.dp2px(dialog.getContext(), left),
						Tools.Size.dp2px(dialog.getContext(), top),
						Tools.Size.dp2px(dialog.getContext(), right),
						Tools.Size.dp2px(dialog.getContext(), bottom));
			}
			return this;
		}

		public Builder messagePadding(int left, int top, int right, int bottom) {
			if (dialog.mMessage != null) {
				dialog.mMessage.setPadding(Tools.Size.dp2px(dialog.getContext(), left),
						Tools.Size.dp2px(dialog.getContext(), top),
						Tools.Size.dp2px(dialog.getContext(), right),
						Tools.Size.dp2px(dialog.getContext(), bottom));
			}
			return this;
		}

		public Builder contentPadding(int padding) {
			if (dialog.layoutContent != null) {
				padding = Tools.Size.dp2px(dialog.getContext(), padding);
				dialog.layoutContent.setPadding(padding, padding, padding, padding);
			}
			return this;
		}

		public Builder messagePadding(int padding) {
			if (dialog.mMessage != null) {
				padding = Tools.Size.dp2px(dialog.getContext(), padding);
				dialog.mMessage.setPadding(padding, padding, padding, padding);
			}
			return this;
		}

		public Builder contentMargin(int margin) {
			if (dialog.layoutContent != null) {
				margin = Tools.Size.dp2px(dialog.getContext(), margin);
				ViewGroup.LayoutParams params = dialog.layoutContent.getLayoutParams();
				if (params instanceof ViewGroup.MarginLayoutParams) {
					((ViewGroup.MarginLayoutParams) params).setMargins(margin, margin, margin, margin);
				}
			}
			return this;
		}

		public Builder titlePadding(int padding) {
			if (dialog.mTitle != null) {
				padding = Tools.Size.dp2px(dialog.getContext(), padding);
				dialog.mTitle.setPadding(padding, padding, padding, padding);
			}
			return this;
		}

		public Builder titleMargin(int margin) {
			if (dialog.mTitle != null) {
				margin = Tools.Size.dp2px(dialog.getContext(), margin);
				ViewGroup.LayoutParams params = dialog.mTitle.getLayoutParams();
				if (params instanceof ViewGroup.MarginLayoutParams) {
					((ViewGroup.MarginLayoutParams) params).setMargins(margin, margin, margin, margin);
				}
			}
			return this;
		}

		public Builder messageMargin(int margin) {
			if (dialog.mMessage != null) {
				margin = Tools.Size.dp2px(dialog.getContext(), margin);
				ViewGroup.LayoutParams params = dialog.mMessage.getLayoutParams();
				if (params instanceof ViewGroup.MarginLayoutParams) {
					((ViewGroup.MarginLayoutParams) params).setMargins(margin, margin, margin, margin);
				}
			}
			return this;
		}

		public Builder messageMargin(int left, int top, int right, int bottom) {
			if (dialog.mMessage != null) {
				ViewGroup.LayoutParams params = dialog.mMessage.getLayoutParams();
				if (params instanceof ViewGroup.MarginLayoutParams) {
					((ViewGroup.MarginLayoutParams) params)
							.setMargins(Tools.Size.dp2px(dialog.getContext(), left),
									Tools.Size.dp2px(dialog.getContext(), top),
									Tools.Size.dp2px(dialog.getContext(), right),
									Tools.Size.dp2px(dialog.getContext(), bottom));
				}
			}
			return this;
		}

		public Builder contentMargin(int left, int top, int right, int bottom) {
			if (dialog.layoutContent != null) {
				ViewGroup.LayoutParams params = dialog.layoutContent.getLayoutParams();
				if (params instanceof ViewGroup.MarginLayoutParams) {
					((ViewGroup.MarginLayoutParams) params)
							.setMargins(Tools.Size.dp2px(dialog.getContext(), left),
									Tools.Size.dp2px(dialog.getContext(), top),
									Tools.Size.dp2px(dialog.getContext(), right),
									Tools.Size.dp2px(dialog.getContext(), bottom));
				}
			}
			return this;
		}


		public Builder setTitle(CharSequence title) {
			if (dialog.mTitle != null) {
				dialog.mTitle.setVisibility(View.VISIBLE);
				dialog.mTitle.setText(title);
			}
			return this;
		}

		public Builder setTitleGra(int gravity) {
			if (dialog.mTitle != null) {
				dialog.mTitle.setGravity(gravity);
			}
			return this;
		}

		/**
		 * Set title ios dialog.
		 *
		 * @param title the title
		 */
		public Builder setTitle(int title) {
			if (dialog.mTitle != null) {
				dialog.mTitle.setVisibility(View.VISIBLE);
				dialog.mTitle.setText(title);
			}
			return this;
		}


		/**
		 * 设置标题
		 *
		 * @param title the title
		 * @return ios dialog
		 */
		public Builder setMessage(CharSequence title) {
			if (dialog.mMessage != null) {
				dialog.mMessage.setText(title);
			}
			return this;
		}

		/**
		 * Set title ios dialog.
		 *
		 * @param title the title
		 */
		public Builder setMessage(int title) {
			if (dialog.mMessage != null) {
				dialog.mMessage.setVisibility(View.VISIBLE);
				dialog.mMessage.setText(title);
			}
			return this;
		}

		public Builder messageTextSize(int size) {
			if (dialog.mMessage != null) {
				dialog.mMessage
						.setTextSize(COMPLEX_UNIT_PX, Tools.Size.dp2px(dialog.getContext(), size));
			}
			return this;
		}

		public Builder messageTextColor(int color) {
			if (dialog.mMessage != null) {
				dialog.mMessage.setTextColor(color);
			}
			return this;
		}

		public Builder titleTextSize(int size) {
			if (dialog.mTitle != null) {
				dialog.mTitle
						.setTextSize(COMPLEX_UNIT_PX, Tools.Size.dp2px(dialog.getContext(), size));
			}
			return this;
		}

		public Builder titleTextColor(int color) {
			if (dialog.mTitle != null) {
				dialog.mTitle.setTextColor(color);
			}
			return this;
		}


		public Builder titleTextColor(ColorStateList color) {
			if (dialog.mTitle != null) {
				dialog.mTitle.setTextColor(color);
			}
			return this;
		}


		/**
		 * 设置返回键弹框是否可消失
		 *
		 * @param cancel the cancel
		 * @return ios dialog
		 */
		public Builder setCancelable(boolean cancel) {
			dialog.setCancelable(cancel);
			return this;
		}

		/**
		 * Set on key listener ios dialog.
		 *
		 * @param onKeyListener the on key listener
		 */
		public Builder setOnKeyListener(
				OnKeyListener onKeyListener) {
			dialog.setOnKeyListener(onKeyListener);
			return this;
		}

		/**
		 * Set on dismiss listener ios dialog.
		 *
		 * @param onDismissListener the on dismiss listener
		 */
		public Builder setOnDismissListener(
				OnDismissListener onDismissListener) {
			dialog.setOnDismissListener(onDismissListener);
			return this;
		}

		/**
		 * Show.
		 */
		public CommonProgressDialog show() {
			dialog.show();
			return dialog;
		}

		/**
		 * Show.
		 */
		public CommonProgressDialog create() {
			return dialog;
		}

		/**
		 * Dismiss.
		 */
		public CommonProgressDialog dismiss() {
			dialog.dismiss();
			return dialog;
		}
	}
}
