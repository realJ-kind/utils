package com.jiuwan.utils.ui;

import android.content.Context;
import android.net.CaptivePortal;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.jiuwan.utils.R;


public class CustomToolbarBehavior extends CoordinatorLayout.Behavior<ConstraintLayout> {
    public CustomToolbarBehavior() {
        super();
        //Used when the layout has a behavior attached via the Annotation;
    }

    public CustomToolbarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        //Used when the layout has a behavior attached via xml (Within the xml file e.g.
        //<app:layout_behavior=".link.to.your.behavior">
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull ConstraintLayout child, @NonNull View dependency) {
        return dependency instanceof AppBarLayout ;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull ConstraintLayout child, @NonNull View dependency) {
        child.post( ()->{
            AppBarLayout appBarLayout=(AppBarLayout) dependency;
            int max =Utils.dip2px(child.getContext(),100);
            int top = appBarLayout.getTop();
            Log.e("appbarlayout top", String.valueOf(top) );
            int scrolled=-top ;
            float percent=scrolled >= max? 1f  : scrolled<=0? 0f : ((float)scrolled) / max;
            int alpha = (int) (percent*255);
            ((TextView) (child.findViewById(R.id.tv_title))).setAlpha(percent);
            appBarLayout.findViewById(R.id.app_bar_content).getBackground().setAlpha(255-alpha);
            child.getBackground().setAlpha(alpha);
            new CollapsingToolbarLayout(parent.getContext());

        });

        return super.onDependentViewChanged(parent, child, dependency);

    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull ConstraintLayout child, int layoutDirection) {
        child.post(()->{parent.onLayoutChild(child,layoutDirection);});
        return true;//super.onLayoutChild(parent, child, layoutDirection);
    }
}