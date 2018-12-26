package me.jessyan.mvparms.wanandroid.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.mvparms.wanandroid.mvp.contract.LoginContract;
import me.jessyan.mvparms.wanandroid.mvp.model.api.service.UserService;
import me.jessyan.mvparms.wanandroid.mvp.model.entity.BaseResponse;
import me.jessyan.mvparms.wanandroid.mvp.model.entity.Login;


@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<BaseResponse<Login>> login(Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).login(map);
    }

    @Override
    public Observable<BaseResponse> logout() {
        return mRepositoryManager.obtainRetrofitService(UserService.class).logout();
    }

    @Override
    public Observable<BaseResponse<Login>> register(Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).register(map);
    }
}