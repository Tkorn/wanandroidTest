package me.jessyan.mvparms.wanandroid;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class TestUtils {

    public static void initRxJava() {

        RxJavaPlugins.setInitIoSchedulerHandler(schedulerCallable -> Schedulers.single());
        RxAndroidPlugins.setMainThreadSchedulerHandler(scheduler -> Schedulers.single());

//        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
//            @Override
//            public Scheduler getIOScheduler() {
//                return Schedulers.immediate();
//            }
//        });
//        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
//            @Override
//            public Scheduler getMainThreadScheduler() {
//                return Schedulers.immediate();
//            }
//        });
    }
}
