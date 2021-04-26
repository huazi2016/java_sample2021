package com.huazi.jdemo.contract.me;

import com.huazi.jdemo.base.interfaces.IBaseView;
import com.huazi.jdemo.bean.me.IntegralData;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2019/12/31
 * Time: 14:15
 */
public class Contract {

    public interface IMeModel {

        /**
         * 加载积分数据
         * @return
         */
        Observable<IntegralData> loadIntegralData();

        /**
         * 刷新积分数据
         * @return
         */
        Observable<IntegralData> refreshIntegralData();

    }

    public interface IMeView extends IBaseView {

        void onLoadIntegralData(IntegralData integral);

        void onRefreshIntegralData(IntegralData integral);
    }

    public interface IMePresenter {
        void loadIntegralData();

        void refreshIntegralData();
    }
}
