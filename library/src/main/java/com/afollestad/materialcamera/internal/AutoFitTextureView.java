/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.afollestad.materialcamera.internal;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.TextureView;

/**
 * A {@link TextureView} that can be adjusted to a specified aspect ratio.
 */
class AutoFitTextureView extends TextureView {

    private int mTextureWidth = 0;
    private int mTextureHeight = 0;
    private Point mDisplaySize;

    public AutoFitTextureView(Context context) {
        this(context, null);
    }

    public AutoFitTextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoFitTextureView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Sets the aspect ratio for this view. The size of the view will be measured based on the ratio
     * calculated from the parameters. Note that the actual sizes of parameters don't matter, that
     * is, calling setAspectRatio(2, 3) and setAspectRatio(4, 6) make the same result.
     *
     * @param width  Relative horizontal size
     * @param height Relative vertical size
     */
    public void setTextureSize(int width, int height,Point size) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        mTextureWidth = width;
        mTextureHeight = height;
        mDisplaySize = size;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (0 == mTextureWidth || 0 ==mTextureHeight) {
            setMeasuredDimension(width, height);
        } else {
            if(mDisplaySize!=null) {
                float scaleFactor = (mDisplaySize.x * 1.0f) / mTextureWidth > (mDisplaySize.y * 1.0f) / mTextureHeight ?
                        (mDisplaySize.x * 1.0f) / mTextureWidth : (mDisplaySize.y * 1.0f) / mTextureHeight;
                mTextureWidth = (int) (mTextureWidth * scaleFactor);
                mTextureHeight = (int) (mTextureHeight*scaleFactor);
                setMeasuredDimension(mTextureWidth, mTextureHeight);
            }else{
                if (width < height * mTextureWidth / mTextureHeight) {
                    setMeasuredDimension(width, width * mTextureHeight / mTextureWidth);
                } else {
                    setMeasuredDimension(width * mTextureHeight / mTextureWidth, height);
                }
            }

        }
    }

}
