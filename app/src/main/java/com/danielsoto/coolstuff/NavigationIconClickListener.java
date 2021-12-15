package com.danielsoto.coolstuff;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.animation.Interpolator;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.util.DisplayMetrics;
import androidx.annotation.Nullable;

public class NavigationIconClickListener implements View.OnClickListener {
    private final AnimatorSet animatorSet = new AnimatorSet();
    private Context context;
    private View sheet;
    private Interpolator interpolator;
    private int height;
    private boolean backdropShown = false;
    private Drawable openIcon;
    private Drawable closeIcon;

    NavigationIconClickListener(Context context, View sheet) { this(context, sheet, null);};

    NavigationIconClickListener(Context context, View sheet, @Nullable Interpolator interpolator) {
        this(context, sheet, interpolator, null, null);
    }

    NavigationIconClickListener(
            Context context, View sheet, @Nullable Interpolator interpolator,
            @Nullable Drawable openIcon, @Nullable Drawable closeIcon){
        this.context = context;
        this.sheet = sheet;
        this.interpolator = interpolator;
        this.openIcon = openIcon;
        this.closeIcon = closeIcon;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
    }
    @Override
    public void onClick(View view) {
        backdropShown = !backdropShown;

        animatorSet.removeAllListeners();
        animatorSet.end();
        animatorSet.cancel();

        updateIcon(view);
        final int translateY = height - context.getResources().getDimensionPixelSize(R.dimen.product_grid_reveal_height);

        ObjectAnimator animator = ObjectAnimator.ofFloat(sheet, "translationY", backdropShown ? translateY : 0);
        animator.setDuration(500);
        if (interpolator != null) {
            animator.setInterpolator(interpolator);
        }
        animatorSet.play(animator);
        animator.start();

    }

    private  void  updateIcon(View view) {
        if(openIcon != null && closeIcon != null) {
            if(!(view instanceof ImageView)) {
                throw new IllegalArgumentException("updateIcon must be called in an Image");
            }
            if(backdropShown) {
                ((ImageView) view).setImageDrawable(closeIcon);
            } else {
                ((ImageView) view).setImageDrawable(openIcon);
            }
        }
    }
}
