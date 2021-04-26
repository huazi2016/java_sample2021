package com.huazi.jdemo.presenter.square;

import com.huazi.jdemo.base.presenter.BasePresenter;
import com.huazi.jdemo.bean.square.NavigationData;
import com.huazi.jdemo.contract.square.Contract;
import com.huazi.jdemo.model.SquareModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2019/12/29
 * Time: 14:57
 */
public class SquarePresenter extends BasePresenter<Contract.ISquareView> implements Contract.ISquarePresenter {
    Contract.ISquareModel iSquareModel;

    public SquarePresenter() {
        iSquareModel = new SquareModel();
    }

    @Override
    public void loadNavigation() {
        iSquareModel.loadNavigation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NavigationData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(NavigationData navigationData) {
                        if (isViewAttached()) {
                            getView().loadNavigation(navigationData);
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
