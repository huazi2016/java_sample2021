package com.huazi.jdemo.contract.rank;

import com.huazi.jdemo.base.interfaces.IBaseView;
import com.huazi.jdemo.bean.db.Rank;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/13
 * Time: 10:24
 */
public class Contract {

    public interface IRankModel {
        /**
         * 加载积分排行
         * @param pageNum
         * @return
         */
        Observable<List<Rank>> loadRankData(int pageNum);

        /**
         * 刷新积分排行列表
         * @param pageNum
         * @return
         */
        Observable<List<Rank>> refreshRankData(int pageNum);
    }

    public interface IRankView extends IBaseView {
        /**
         * 加载积分排行
         * @param rankList
         */
        void onLoadRankData(List<Rank> rankList);

        /**
         * 刷新积分排行
         * @param rankList
         */
        void onRefreshRankData(List<Rank> rankList);
    }

    public interface IRankPResenter {

        /**
         * 加载积分排行
         * @param pageNum
         */
        void loadRankData(int pageNum);

        /**
         * 刷新积分排行
         * @param pageNum
         */
        void refreshRankData(int pageNum);
    }
}
