package me.jessyan.mvparms.wanandroid.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.Map;

import io.reactivex.Observable;
import me.jessyan.mvparms.wanandroid.mvp.model.entity.BaseResponse;
import me.jessyan.mvparms.wanandroid.mvp.model.entity.Login;


public interface LoginContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        //登录
        Observable<BaseResponse<Login>> login(Map<String, String> map);

        //登出
        Observable<BaseResponse> logout();

        //注册
        Observable<BaseResponse<Login>> register(Map<String, String> map);

    }
}
