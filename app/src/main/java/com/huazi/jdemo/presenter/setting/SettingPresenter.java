package com.huazi.jdemo.presenter.setting;

import com.huazi.jdemo.base.presenter.BasePresenter;
import com.huazi.jdemo.bean.me.LogoutData;
import com.huazi.jdemo.contract.setting.Contract;
import com.huazi.jdemo.model.SettingModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/16
 * Time: 13:57
 */
public class SettingPresenter extends BasePresenter<Contract.ILogoutView> implements Contract.ILogoutPresenter {

    Contract.ILogoutModel iLogoutModel;
    public SettingPresenter() {
        iLogoutModel = new SettingModel();
    }
    @Override
    public void logout() {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        iLogoutModel.logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LogoutData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(LogoutData logoutData) {
                        if (isViewAttached()) {
                            getView().onLogout(logoutData);
                            getView().onLoadSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (isViewAttached()) {
                            getView().onLoadFailed();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
