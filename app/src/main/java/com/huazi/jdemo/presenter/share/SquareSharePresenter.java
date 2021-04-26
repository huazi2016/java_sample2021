package com.huazi.jdemo.presenter.share;

import com.huazi.jdemo.base.presenter.BasePresenter;
import com.huazi.jdemo.bean.db.Article;
import com.huazi.jdemo.contract.squaresharearticle.Contract;
import com.huazi.jdemo.model.SquareShareModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/19
 * Time: 20:23
 */
public class SquareSharePresenter extends BasePresenter<Contract.IShareView> implements Contract.ISharePresenter {
    Contract.IShareModel iShareModel;
    public SquareSharePresenter() {
        iShareModel = new SquareShareModel();
    }
    @Override
    public void addArticle(String title, String link) {
        if(isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        iShareModel.addArticle(title, link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Article article) {
                        if (isViewAttached()) {
                            getView().onAddArticle(article);
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
