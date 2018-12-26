package me.jessyan.mvparms.wanandroid.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.mvparms.wanandroid.di.module.LoginModule;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.mvparms.wanandroid.mvp.ui.activity.LoginActivity;
import me.jessyan.mvparms.wanandroid.mvp.ui.activity.RegisterActivity;

@ActivityScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent {
    void inject(LoginActivity activity);
    void inject(RegisterActivity activity);
}