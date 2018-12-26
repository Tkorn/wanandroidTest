package me.jessyan.mvparms.wanandroid.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;

import java.util.HashMap;

import me.jessyan.mvparms.wanandroid.app.utils.ComUtils;
import me.jessyan.mvparms.wanandroid.app.utils.Constants;
import me.jessyan.mvparms.wanandroid.app.utils.RxUtils;
import me.jessyan.mvparms.wanandroid.mvp.model.entity.BaseResponse;
import me.jessyan.mvparms.wanandroid.mvp.model.entity.Login;
import me.jessyan.mvparms.wanandroid.mvp.ui.activity.UserActivity;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.mvparms.wanandroid.mvp.contract.LoginContract;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }



    //登录
    public void login(String username, String password){
        RxUtils.apply(mModel.login(ComUtils.getMd5Str(new String[]{"username", "password"},
                new String[]{username, password})),mRootView,true)
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Login>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Login> loginBaseResponse) {
                        DataHelper.saveDeviceData(mApplication, Constants.SP_LOGIN,loginBaseResponse.getData());
                        ArmsUtils.startActivity(UserActivity.class);
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("t --> "+t.getMessage());
                        super.onError(t);
                    }
                });

    }

    //登出
    public void logout(){
        RxUtils.apply(mModel.logout(),mRootView,true)
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        DataHelper.saveDeviceData(mApplication, Constants.SP_LOGIN,null);
                        ArmsUtils.startActivity(Login.class);
                    }
                });


    }

    //注册
    public void register(String username, String password, String repassword){
        RxUtils.apply(mModel.login(ComUtils.getMd5Str(new String[]{"username", "password", "repassword"},
                new String[]{username, password, repassword})),mRootView,true)
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Login>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Login> loginBaseResponse) {
                        ArmsUtils.startActivity(Login.class);
                    }
                });
    }


}
