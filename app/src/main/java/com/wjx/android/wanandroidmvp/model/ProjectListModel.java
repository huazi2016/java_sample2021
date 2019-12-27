package com.wjx.android.wanandroidmvp.model;

import com.wjx.android.wanandroidmvp.base.utils.ApiServer;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.project.ProjectListData;
import com.wjx.android.wanandroidmvp.bean.project.ProjectListDataNew;
import com.wjx.android.wanandroidmvp.contract.project.Contract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/27
 * Time: 17:44
 */
public class ProjectListModel implements Contract.IProjectListModel {

    List<ProjectListDataNew> mProjectBean = new ArrayList<>();

    @Override
    public Observable<List<ProjectListDataNew>> loadProjectList(int pageNum, int cid) {
        return getApiServer().loadProjectList(pageNum, cid).filter(a ->a.getErrorCode() == 0)
                .map(original -> {
                    original.getData().getDatas().stream().sorted((o1,o2) -> (int)(o2.getPublishTime() - o1.getPublishTime()))
                            .forEach(datas -> {
                                long count = mProjectBean.stream().filter(b -> b.id == datas.getId()).count();
                                if (count <= 0) {

                                    ProjectListDataNew projectListBean = new ProjectListDataNew();
                                    projectListBean.id = datas.getId();
                                    projectListBean.title = datas.getTitle();
                                    projectListBean.author = datas.getAuthor();
                                    projectListBean.niceDate = datas.getNiceDate();
                                    projectListBean.publishTime = datas.getPublishTime();
                                    projectListBean.chapterName = datas.getChapterName();
                                    projectListBean.superChapterName = datas.getSuperChapterName();
                                    projectListBean.collect = datas.isCollect();
                                    projectListBean.link = datas.getLink();
                                    projectListBean.desc = datas.getDesc();
                                    projectListBean.envelopePic = datas.getEnvelopePic();
                                    mProjectBean.add(projectListBean);
                                }
                            });

                    return mProjectBean;
                });
    }

    @Override
    public Observable<List<ProjectListDataNew>> refreshProjectList() {
        return getApiServer().refreshProjectList().filter(a ->a.getErrorCode() == 0)
                .map(original -> {
                    original.getData().getDatas().stream().sorted((o1, o2) -> (int) (o1.getPublishTime() - o2.getPublishTime()))
                            .forEach(datas -> {
                                //如果数据是新数据
                                long count = mProjectBean.stream().filter(b -> b.id == datas.getId()).count();
                                if (count <= 0) {
                                    ProjectListDataNew projectListBean = new ProjectListDataNew();
                                    projectListBean.id = datas.getId();
                                    projectListBean.title = datas.getTitle();
                                    projectListBean.author = datas.getAuthor();
                                    projectListBean.niceDate = datas.getNiceDate();
                                    projectListBean.publishTime = datas.getPublishTime();
                                    projectListBean.chapterName = datas.getChapterName();
                                    projectListBean.superChapterName = datas.getSuperChapterName();
                                    projectListBean.collect = datas.isCollect();
                                    projectListBean.link = datas.getLink();
                                    projectListBean.desc = datas.getDesc();
                                    projectListBean.envelopePic = datas.getEnvelopePic();
                                    mProjectBean.add(0, projectListBean);

                                }

                            });

                    return mProjectBean;
                });
    }

    /**
     * 获取请求对象
     * @return 当前的请求对象
     */
    private ApiServer getApiServer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiServer apiServer = retrofit.create(ApiServer.class);
        return apiServer;
    }
}
