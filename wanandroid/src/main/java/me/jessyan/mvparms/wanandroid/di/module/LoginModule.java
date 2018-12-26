package me.jessyan.mvparms.wanandroid.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import me.jessyan.mvparms.wanandroid.mvp.contract.LoginContract;
import me.jessyan.mvparms.wanandroid.mvp.model.LoginModel;


@Module
public class LoginModule {
    private LoginContract.View view;

    /**
     * 构建LoginModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public LoginModule(LoginContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public LoginContract.View provideLoginView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    public LoginContract.Model provideLoginModel(LoginModel model) {
        return model;
    }
}