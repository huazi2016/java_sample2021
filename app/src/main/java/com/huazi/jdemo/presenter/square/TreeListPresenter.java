package com.huazi.jdemo.presenter.square;

import com.huazi.jdemo.base.presenter.BasePresenter;
import com.huazi.jdemo.bean.collect.Collect;
import com.huazi.jdemo.bean.db.Article;
import com.huazi.jdemo.contract.square.Contract;
import com.huazi.jdemo.model.TreeListModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/09
 * Time: 11:45
 */
public class TreeListPresenter extends BasePresenter<Contract.ITreeListView> implements Contract.ITreeListPresenter {
    Contract.ITreeListModel iTreeListModel;
    public TreeListPresenter() {
        iTreeListModel = new TreeListModel();
    }
    @Override
    public void loadTreeList(int pageNum, int cid) {
        if (isViewAttached() && pageNum == 0) {
            getView().onLoading();
        }
        iTreeListModel.loadTreeList(pageNum, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Article>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Article> articleList) {
                        if (isViewAttached()) {
                            getView().onLoadTreeList(articleList);
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

    @Override
    public void refreshTreeList(int pageNum, int cid) {
        iTreeListModel.refreshTreeList(pageNum, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Article>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Article> articleList) {
                        if (isViewAttached()) {
                            getView().onRefreshTreeList(articleList);
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

    @Override
    public void collect(int articleId) {
        iTreeListModel.collect(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Collect>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Collect collect) {
                        if (isViewAttached()) {
                            getView().onCollect(collect, articleId);
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

    @Override
    public void unCollect(int articleId) {
        iTreeListModel.unCollect(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Collect>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Collect collect) {
                        if (isViewAttached()) {
                            getView().onUnCollect(collect, articleId);
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
