package me.jessyan.mvparms.wanandroid.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanks.htextview.fall.FallTextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.mvparms.wanandroid.R;
import me.jessyan.mvparms.wanandroid.mvp.model.entity.Login;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.timmer_tv)
    TextView timmerTv;
    @BindView(R.id.anim_tv1)
    FallTextView animTv1;
    @BindView(R.id.anim_tv2)
    FallTextView animTv2;


    private int count = 5;
    private Disposable disposable;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_splash;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ArmsUtils.statuInScreen(this);

        timmerTv.setOnClickListener(view -> {
            if (disposable != null) disposable.dispose();
            ArmsUtils.startActivity(LoginActivity.class);
        });

        Observable.interval(0,1, TimeUnit.SECONDS)
                .take(count+1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        timmerTv.setText(count + " 跳过");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        timmerTv.setText((count-aLong) + " 跳过");

                        if (aLong == 1){
                            animTv1.animateText("Talk is cheap.");
                        }else if (aLong == 2){
                            animTv2.animateText("Show me the code!");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        ArmsUtils.startActivity(LoginActivity.class);
                    }
                });


    }

}
