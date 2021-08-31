package com.ztind.sample.utils;

import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ztind.sample.App;
import com.ztind.sample.R;


/**
 * glide utils
 */
public class ZTGlideUtils {

    private ZTGlideUtils() {
    }

    static RequestOptions optionsHeadeProtrait = new RequestOptions()
            .placeholder(R.mipmap.ic_launcher_round)//图片加载出来前，显示的图片
            .fallback(R.mipmap.ic_launcher_round) //url为空的时候,显示的图片
            .error(R.mipmap.ic_launcher_round);//图片加载失败后，显示的图片

    static RequestOptions optionsGroupHeadeProtrait = new RequestOptions()
            .placeholder(R.mipmap.ic_launcher_round)//图片加载出来前，显示的图片
            .fallback(R.mipmap.ic_launcher_round) //url为空的时候,显示的图片
            .error(R.mipmap.ic_launcher_round);//图片加载失败后，显示的图片

    static RequestOptions optionsNormalImage = new RequestOptions()
            .placeholder(R.mipmap.ic_launcher_round)//图片加载出来前，显示的图片
            .fallback(R.mipmap.ic_launcher_round) //url为空的时候,显示的图片
            .error(R.mipmap.ic_launcher_round);//图片加载失败后，显示的图片

    /**
     * 加载显示普通头像类图片 加载失败vs预加载vs图片链接为null时显示的图片
     *
     * @param url       图片地址
     * @param imageView 继承于iamgeview的控件
     */
    public static void loadHeadeProtrait(String url, ImageView imageView) {
        Glide.with(App.instance.getApplicationContext())
                .load(url)
                .apply(optionsHeadeProtrait)
                .centerCrop()
                .into(imageView);
    }

    /**
     * 加载显示群头像类图片 加载失败vs预加载vs图片链接为null时显示的图片
     *
     * @param url       图片地址
     * @param imageView 继承于iamgeview的控件
     */
    public static void loadGroupHeadeProtrait(String url, ImageView imageView) {
        Glide.with(App.instance.getApplicationContext())
                .load(url)
                .apply(optionsGroupHeadeProtrait)
                .centerCrop()
                .into(imageView);
    }

    /**
     * 加载显示普通类的图片 加载失败vs预加载vs图片链接为null时显示的图片
     *
     * @param url       图片地址
     * @param imageView 继承于iamgeview的控件
     */
    public static void loadNormalPicture(String url, ImageView imageView) {
        Glide.with(App.instance.getApplicationContext())
                .load(url)
                .apply(optionsNormalImage)
                .centerCrop()
                .into(imageView);
    }

    /**
     * 自定义图片 加载失败vs预加载vs图片链接为null时显示的图片
     *
     * @param url       图片地址
     * @param imageView 继承于iamgeview的控件
     */
    public static void loadCustomPicture(String url, ImageView imageView, @DrawableRes int resourceId) {
        RequestOptions options = new RequestOptions()
                .placeholder(resourceId)//图片加载出来前，显示的图片
                .fallback(resourceId) //url为空的时候,显示的图片
                .error(resourceId);//图片加载失败后，显示的图片
        Glide.with(App.instance.getApplicationContext())
                .load(url)
                .apply(options)
                .centerCrop()
                .into(imageView);
    }

}
