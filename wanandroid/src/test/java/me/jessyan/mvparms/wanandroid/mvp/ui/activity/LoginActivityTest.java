package me.jessyan.mvparms.wanandroid.mvp.ui.activity;

import android.content.Intent;
import android.widget.EditText;

import com.google.gson.Gson;
import com.jess.arms.utils.ArmsUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowApplication;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.mvparms.wanandroid.R;
import me.jessyan.mvparms.wanandroid.di.component.DaggerLoginComponent;
import me.jessyan.mvparms.wanandroid.di.module.LoginModule;
import me.jessyan.mvparms.wanandroid.mvp.model.LoginModel;
import me.jessyan.mvparms.wanandroid.mvp.model.entity.BaseResponse;
import me.jessyan.mvparms.wanandroid.mvp.model.entity.Login;

import static org.junit.Assert.*;


@RunWith(RobolectricTestRunner.class)
public class LoginActivityTest {
    private final String LOGIN_JSON = "{\"chapterTops\":[],\"collectIds\":[7656,1905,7666,7679,7688,7556,3242,7654,7700,7697,7410,7661]," +
            "\"email\":\"\",\"icon\":\"\",\"id\":14540,\"password\":\"\",\"token\":\"\",\"type\":0,\"username\":\"12341234\"}";
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private static boolean hasInited = false;
    private LoginActivity loginActivity;
    @Mock
    LoginModel loginModel;
    private Gson gson;

    @Before
    public void setUp() throws Exception {
        //将rxjava 的异步操作转化为同步
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());

        //初始化Mockito
        MockitoAnnotations.initMocks(this);

        //获取测试的activity
        loginActivity = Robolectric.setupActivity(LoginActivity.class);

        gson = new Gson();

//        因为我们不需要它LoginModel中的方法返回真正的值，只是需要mock它的方法返回我们想要的值，所以用@Mock
//        而LoginModule我们是需要它提供真是的view所以我们用spy
        LoginModule loginModule = Mockito.spy(new LoginModule(loginActivity));
        Mockito.when(loginModule.provideLoginModel(Mockito.any(LoginModel.class)))
                .thenReturn(loginModel);

//        我们mock出的module需要注入到测试的activity中
        DaggerLoginComponent
                .builder()
                .appComponent(ArmsUtils.obtainAppComponentFromContext(loginActivity))
                .loginModule(loginModule)
                .build()
                .inject(loginActivity);
    }

    /**
     * 将登录的请求直接mock成我们想要的结果
     */
    @Test
    public void testLogin(){
        System.out.println("*********** testLogin 开始 *********");
        BaseResponse<Login> response = new BaseResponse<>();
        response.setCode("0");
        response.setData(gson.fromJson(LOGIN_JSON,Login.class));
        //将去服务器请求login数据的方法直接mock成我们想得到数据
        Mockito.when(loginModel.login(Mockito.anyMap()))
                .thenReturn(Observable.just(response));

        EditText userName = loginActivity.findViewById(R.id.account_edt);
        EditText password = loginActivity.findViewById(R.id.password_edt);

        userName.setText("12341234");
        password.setText("123456");

        //模拟点击 登录按钮
        loginActivity.findViewById(R.id.login_btn).performClick();

        //检查是否登录成功跳转到了UserActivity
        Intent intent = new Intent(loginActivity, UserActivity.class);
        Assert.assertEquals(intent.getComponent(),Shadows.shadowOf(loginActivity).getNextStartedActivity().getComponent());
        System.out.println("*********** testLogin 完成 *********");

    }

    @Test
    public void testToRegister(){
        System.out.println("*********** testToRegister 开始 *********");
        loginActivity.findViewById(R.id.register_tv).performClick();
        Intent intent = new Intent(loginActivity,RegisterActivity.class);
        Assert.assertEquals(intent.getComponent(),Shadows.shadowOf(loginActivity).getNextStartedActivity().getComponent());
        System.out.println("*********** testToRegister 完成 *********");
    }

}
