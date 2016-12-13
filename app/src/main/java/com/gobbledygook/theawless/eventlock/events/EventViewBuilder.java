package com.gobbledygook.theawless.eventlock.events;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

class EventViewBuilder {
    private final Context gismoContext;
    RelativeLayout fullContainerRelativeLayout;
    private LinearLayout textContainerLinearLayout;

    EventViewBuilder(Context gismoContext) {
        this.gismoContext = gismoContext;
    }

    void setUpFullContainerRelativeLayout(int left, int above, int right, int below) {
        fullContainerRelativeLayout = new RelativeLayout(gismoContext);
        fullContainerRelativeLayout.setLayoutParams(new GridLayout.LayoutParams());
        fullContainerRelativeLayout.getLayoutParams().width = GridLayout.LayoutParams.MATCH_PARENT;
        fullContainerRelativeLayout.getLayoutParams().height = GridLayout.LayoutParams.WRAP_CONTENT;
        ((GridLayout.LayoutParams) fullContainerRelativeLayout.getLayoutParams()).setMargins(left, above, right, below);
    }

    void setUpTextContainerRelativeLayout(String position) {
        textContainerLinearLayout = new LinearLayout(gismoContext);
        textContainerLinearLayout.setOrientation(LinearLayout.VERTICAL);
        fullContainerRelativeLayout.addView(textContainerLinearLayout);
        textContainerLinearLayout.getLayoutParams().height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        textContainerLinearLayout.getLayoutParams().width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        ((RelativeLayout.LayoutParams) textContainerLinearLayout.getLayoutParams()).addRule(RelativeLayout.CENTER_VERTICAL);
        switch (position) {
            case "left": {
                ((RelativeLayout.LayoutParams) textContainerLinearLayout.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                break;
            }
            case "center": {
                ((RelativeLayout.LayoutParams) textContainerLinearLayout.getLayoutParams()).addRule(RelativeLayout.CENTER_HORIZONTAL);
                break;
            }
            case "right": {
                ((RelativeLayout.LayoutParams) textContainerLinearLayout.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                break;
            }
        }
    }

    void setUpTitleTextView(int left, int above, int right, int below, int size, String alignment) {
        TextView titleTextView = new TextView(gismoContext);
        textContainerLinearLayout.addView(titleTextView);
        titleTextView.setTag(EventViewBuildDirector.ItemTag.Title);
        titleTextView.setTextSize(size);
        titleTextView.setPadding(left, above, right, below);
        titleTextView.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
        titleTextView.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
        switch (alignment) {
            case "left": {
                titleTextView.setGravity(Gravity.LEFT);
                break;
            }
            case "right": {
                titleTextView.setGravity(Gravity.RIGHT);
                break;
            }
        }
    }

    void setUpTimeTextView(int left, int above, int right, int below, int size, String alignment) {
        TextView timeTextView = new TextView(gismoContext);
        textContainerLinearLayout.addView(timeTextView);
        timeTextView.setTag(EventViewBuildDirector.ItemTag.Time);
        timeTextView.setTextSize(size);
        timeTextView.setPadding(left, above, right, below);
        timeTextView.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
        timeTextView.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
        Log.v("VIEW", alignment);
        switch (alignment) {
            case "left": {
                timeTextView.setGravity(Gravity.LEFT);
                break;
            }
            case "right": {
                timeTextView.setGravity(Gravity.RIGHT);
                break;
            }
        }
    }

    void setUpColorImageView(int left, int above, int right, int below, String type, int height, int width, String alignment, boolean stick) {
        ImageView colorImageView = new ImageView(gismoContext);
        fullContainerRelativeLayout.addView(colorImageView);
        colorImageView.setTag(EventViewBuildDirector.ItemTag.Image);
        GradientDrawable outlineDrawable = new GradientDrawable();
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        if (type.equals("oval")) {
            shapeDrawable.setShape(new OvalShape());
            outlineDrawable.setShape(GradientDrawable.OVAL);
        } else {
            shapeDrawable.setShape(new RectShape());
            outlineDrawable.setShape(GradientDrawable.RECTANGLE);
        }
        shapeDrawable.setIntrinsicHeight(height);
        shapeDrawable.setIntrinsicWidth(width);
        colorImageView.setImageDrawable(new LayerDrawable(new Drawable[]{shapeDrawable, outlineDrawable}));
        colorImageView.getLayoutParams().height = FrameLayout.LayoutParams.WRAP_CONTENT;
        colorImageView.getLayoutParams().width = FrameLayout.LayoutParams.WRAP_CONTENT;
        colorImageView.setPadding(left, above, right, below);
        ((RelativeLayout.LayoutParams) colorImageView.getLayoutParams()).addRule(RelativeLayout.CENTER_VERTICAL);
        if (!stick) {
            switch (alignment) {
                case "left": {
                    ((RelativeLayout.LayoutParams) colorImageView.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    break;
                }
                case "right": {
                    ((RelativeLayout.LayoutParams) colorImageView.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    break;
                }
            }
        } else {
            ((RelativeLayout.LayoutParams) textContainerLinearLayout.getLayoutParams()).removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
            ((RelativeLayout.LayoutParams) textContainerLinearLayout.getLayoutParams()).removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            colorImageView.setId(View.generateViewId());
            switch (alignment) {
                case "left": {
                    ((RelativeLayout.LayoutParams) colorImageView.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    ((RelativeLayout.LayoutParams) textContainerLinearLayout.getLayoutParams()).addRule(RelativeLayout.RIGHT_OF, colorImageView.getId());
                    break;
                }
                case "right": {
                    ((RelativeLayout.LayoutParams) colorImageView.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    ((RelativeLayout.LayoutParams) textContainerLinearLayout.getLayoutParams()).addRule(RelativeLayout.LEFT_OF, colorImageView.getId());
                    break;
                }
            }
        }
    }
}