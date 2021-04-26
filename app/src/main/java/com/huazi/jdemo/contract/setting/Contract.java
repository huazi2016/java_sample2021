package com.huazi.jdemo.contract.setting;

import com.huazi.jdemo.base.interfaces.IBaseView;
import com.huazi.jdemo.bean.me.LogoutData;

import io.reactivex.Observable;


/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/16
 * Time: 13:54
 */
public class Contract {
    public interface ILogoutModel {
        /**
         * 退出登录
         * @return
         */
        Observable<LogoutData> logout();
    }

    public interface ILogoutView extends IBaseView {
        void onLogout(LogoutData logoutData);
    }

    public interface ILogoutPresenter {
        void logout();
    }
}
