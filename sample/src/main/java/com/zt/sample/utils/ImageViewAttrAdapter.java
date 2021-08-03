package com.zt.sample.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

/**
 * <b>Title: </b>BindingAdapter注解更改/自定义imageview属性加载方式<br>
 * <b>Description: </b>无需手动调用以下函数
 *
 * @author ZT
 * <p>
 * 修订历史:
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2019/12/27&nbsp;&nbsp;&nbsp;&nbsp;ZT&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class ImageViewAttrAdapter {
    /**
     * <b>Title: </b>加载自定义类图片，加载前/图片地址为空/加载错误时显示的图片<br>
     * <b>Description: </b>此时 img_custom_url placeholder fallback error 4个属性必传否则看不到相应效果 ,
     * app:img_custom_url="@{xxx}"方式在ImageView控件里使用
     * app:placeholder="@{xxx}"方式在ImageView控件里使用
     * app:fallback="@{xxx}"方式在ImageView控件里使用
     * app:error="@{xxx}"方式在ImageView控件里使用
     *
     * @param imageView   图片控件本身
     * @param img_custom_url    图片地址
     * @param placeholder    图片加载前显示的图片
     * @param fallback     图片地址为空时显示的图片
     * @param error    图片加载失败显示的图片
     * @author ZT
     * <p>
     * 修订历史:
     * <ul>
     * <li>修改日期&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
     * <hr>
     * <li>20191227&nbsp;&nbsp;&nbsp;&nbsp;ZT&nbsp;&nbsp;&nbsp;&nbsp;创建方法</li>
     * </ul>
     */
//    @BindingAdapter({"img_custom_url", "placeholder","fallback", "error"})
//    public static void loadImage(ImageView imageView, String img_custom_url,@DrawableRes int placeholder,@DrawableRes int fallback, @DrawableRes int error) {
//        RequestOptions options = new RequestOptions()
//                .placeholder(placeholder)//图片加载出来前，显示的图片
//                .fallback(fallback) //url为空的时候,显示的图片
//                .error(error);//图片加载失败后，显示的图片
//        Glide.with(App.instance.getApplicationContext())
//                .load(img_custom_url)
//                .apply(options)
//                .centerCrop()
//                .into(imageView);
//    }
    /**
     * <b>Title: </b>加载正常类图片，加载前/图片地址为空/加载错误时显示的图片<br>
     * <b>Description: </b> app:img_normal_url="@{xxx}"方式在ImageView控件里使用
     * app:img_normal_url="@{xxx}"方式在ImageView控件里使用
     * @param imageView    图片控件本身
     * @param img_normal_url    地址
     * @author ZT
     * <p>
     * 修订历史:
     * <ul>
     * <li>修改日期&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
     * <hr>
     * <li>20191227&nbsp;&nbsp;&nbsp;&nbsp;ZT&nbsp;&nbsp;&nbsp;&nbsp;创建方法</li>
     * </ul>
     */
    @BindingAdapter({"img_normal_url"})
    public static void loadNormalImage(ImageView imageView,String img_normal_url) {
       ZTGlideUtils.loadNormalPicture(img_normal_url,imageView);
    }

    /**
     *  模糊图片,高斯模糊
     * app:img_blur_url="@{xxx}" 图片地址
     */
    @BindingAdapter({"img_blur_url"})
    public static void loadBlurImage(ImageView imageView,String img_blur_url) {
        //ZTGlideUtils.loadBlurPicture_Three(img_blur_url,imageView);
    }
    /**
     * <b>Title: </b>加载个人头像类图片，加载前/图片地址为空/加载错误时显示的图片<br>
     * <b>Description: </b>app:img_header_url="@{xxx}"方式在ImageView控件里使用
     * app:img_header_url="@{xxx}"方式在ImageView控件里使用
     * @param imageView    图片控件本身
     * @param img_header_url    地址
     * @author ZT
     * <p>
     * 修订历史:
     * <ul>
     * <li>修改日期&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
     * <hr>
     * <li>20191227&nbsp;&nbsp;&nbsp;&nbsp;ZT&nbsp;&nbsp;&nbsp;&nbsp;创建方法</li>
     * </ul>
     */
    @BindingAdapter({"img_header_url"})
    public static void loadHeadeImage(ImageView imageView,String img_header_url) {
        ZTGlideUtils.loadHeadeProtrait(img_header_url,imageView);
    }
    /**
     * <b>Title: </b>加载群头像类图片，加载前/图片地址为空/加载错误时显示的图片<br>
     * <b>Description: </b>app:img_group_url="@{xxx}"方式在ImageView控件里使用
     * app:img_group_url="@{xxx}"方式在ImageView控件里使用
     * @param imageView    图片控件本身
     * @param img_group_url    地址
     * @author ZT
     * <p>
     * 修订历史:
     * <ul>
     * <li>修改日期&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
     * <hr>
     * <li>20191227&nbsp;&nbsp;&nbsp;&nbsp;ZT&nbsp;&nbsp;&nbsp;&nbsp;创建方法</li>
     * </ul>
     */
    @BindingAdapter({"img_group_url"})
    public static void loadGroupImage(ImageView imageView,String img_group_url) {
        ZTGlideUtils.loadGroupHeadeProtrait(img_group_url,imageView);
    }
}
