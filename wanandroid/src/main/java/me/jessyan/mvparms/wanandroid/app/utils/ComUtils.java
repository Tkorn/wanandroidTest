package me.jessyan.mvparms.wanandroid.app.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;

import com.jess.arms.utils.ArmsUtils;

import java.util.HashMap;

public class ComUtils {


    public static void changeStatusBarTextColor(Context context, boolean isBlack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isBlack) {
                //设置状态栏黑色字体
                ((Activity)context).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                //恢复状态栏白色字体
                ((Activity)context).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }


    public static HashMap<String, String> getMd5Str(String[] keyArray, String[] valueArray) {
        HashMap<String, String> map = new HashMap<>();
        StringBuffer sb = new StringBuffer();
        if (keyArray.length != valueArray.length) {
            ArmsUtils.snackbarText("key value长度不对应");
        } else {
            for (int i = 0; i < keyArray.length; i++) {
                map.put(keyArray[i], valueArray[i]);
            }
            for (int i = 0; i < valueArray.length; i++) {
                sb.append(valueArray[i]);
            }
            map.put("secret", MD5Utils.getMd5(sb.toString()));
            return map;
        }
        return null;
    }

}
