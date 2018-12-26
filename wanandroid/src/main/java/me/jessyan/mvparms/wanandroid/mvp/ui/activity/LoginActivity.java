package me.jessyan.mvparms.wanandroid.mvp.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.mvparms.wanandroid.R;
import me.jessyan.mvparms.wanandroid.app.utils.DialogUtils;
import me.jessyan.mvparms.wanandroid.di.component.DaggerLoginComponent;
import me.jessyan.mvparms.wanandroid.di.module.LoginModule;
import me.jessyan.mvparms.wanandroid.mvp.contract.LoginContract;
import me.jessyan.mvparms.wanandroid.mvp.presenter.LoginPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.visitor_tv)
    TextView visitorTv;
    @BindView(R.id.account_edt)
    EditText accountEdt;
    @BindView(R.id.password_edt)
    EditText passwordEdt;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.register_tv)
    TextView registerTv;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ArmsUtils.statuInScreen(this);

    }

    @OnClick({R.id.login_btn,R.id.register_tv,R.id.visitor_tv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.login_btn:
                login();
                break;
            case R.id.register_tv:
                 ArmsUtils.startActivity(RegisterActivity.class);
                break;
            case R.id.visitor_tv:
                ArmsUtils.startActivity(UserActivity.class);
                break;
        }
    }

    private void login(){
        String name = accountEdt.getText().toString().trim();
        String password = passwordEdt.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)){
            ArmsUtils.snackbarText("用户名或密码不能为空");
            return;
        }
        mPresenter.login(name,password);
    }

    @Override
    public void showLoading() {
        DialogUtils.showLoading(this);
    }

    @Override
    public void hideLoading() {
        DialogUtils.hideLoading();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

}
