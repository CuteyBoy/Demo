package com.lsj.demo.drawables;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import com.lsj.demo.CxtUtils;

/**
 * 创建时间 2019/1/3
 * 描述     文件资源的builder
 */
public class ResourceBuilder implements DrawableBuilder {
    private int mResId;

    /**
     * @param resId 文件资源id
     */
    public ResourceBuilder(@DrawableRes int resId) {
        if (resId == 0) {
            throw new IllegalArgumentException("Id can not be 0");
        }
        this.mResId = resId;
    }

    @Override
    public Drawable build() {
        return CxtUtils.getDrawable(mResId);
    }
}
