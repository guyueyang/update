package android_app.app_update;

import android.content.Context;
import android.os.Bundle;

import com.andcup.android.frame.datalayer.CallBack;
import com.andcup.android.frame.datalayer.Repository;
import com.andcup.android.frame.datalayer.RepositoryFactory;
import com.andcup.android.frame.datalayer.job.JobCaller;
import com.andcup.android.frame.view.navigator.DialogFragmentNavigator;
import com.andcup.common.AndroidUtils;
import com.andcup.common.DeviceHelper;
import com.andcup.common.MD5;
import com.andcup.lib.download.DownloadManager;

import android_app.app_update.config.Platform;
import android_app.app_update.config.base.ConfigureProvider;
import android_app.app_update.intercepts.HeaderIntercept;
import android_app.app_update.intercepts.LoggerIntercept;
import android_app.app_update.key.KeyValue;
import android_app.app_update.model.UpdateEntity;
import android_app.app_update.model.base.BaseEntity;
import android_app.app_update.task.UpdateApi;
import android_app.app_update.task.UpdateTask;
import android_app.app_update.view.UpdateFragment;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/25.
 */
public class Update {

    private static Update INST;

    private Context       mContext;
    private Repository<?> mRepository;
    private UpdateEntity mUpdateEntity;
    private String  mAppId;
    private String  mAppKey;
    private String  mChanel;

    protected JobCaller mJobCaller = new JobCaller();

    public interface UpdateListener{
        public void onComplete(UpdateEntity response);
        public void onError(Throwable e);
    }

    private Update(Context context){
        mContext = context;

        ConfigureProvider.init(context);
        // init repository
        RepositoryFactory.RETROFIT.withUrl(Platform.getInstance().getUrl());
        // init intercept
        RepositoryFactory.RETROFIT.addInterceptor(new HeaderIntercept());
        // log handler
        LoggerIntercept loggerIntercept = new LoggerIntercept();
        loggerIntercept.setLevel(LoggerIntercept.Level.BODY);
        RepositoryFactory.RETROFIT.addInterceptor(loggerIntercept);

        mRepository = RepositoryFactory.RETROFIT.create(UpdateApi.class);
        //
        initDownloadModule();
    }

    private void initDownloadModule(){
        //init download module.
        try{
            DownloadManager.init(getContext(), AndroidUtils.getDownloadDir(getContext()));
            DownloadManager.getInstance().setDownloadOnlyWifi(false);
            DownloadManager.getInstance().setDefaultRepositoryHandler(new TaskRepository());
        }catch (Exception e){

        }
    }

    public static Update getInstance(){
        return INST;
    }

    public void checkUpdate(UpdateListener listener){
        mJobCaller.call(new UpdateTask(), new CallBack<BaseEntity<UpdateEntity>>() {
            @Override
            public void onSuccess(BaseEntity<UpdateEntity> updateEntityBaseEntity) {
                mUpdateEntity = updateEntityBaseEntity.body();
                if( null != listener){
                    listener.onComplete(mUpdateEntity);
                }
            }

            @Override
            public void onError(Throwable e) {
                if( null != listener){
                    listener.onError(e);
                }
            }

            @Override
            public void onFinish() {

            }
        });
    }

    public void show(android.support.v4.app.FragmentManager fm){
        DialogFragmentNavigator nav = new DialogFragmentNavigator(fm);
        Bundle bundle = new Bundle();
        bundle.putSerializable(KeyValue.CHECK_UPDATE, mUpdateEntity);
        nav.to(UpdateFragment.class).with(bundle).go();
    }

    public static void init(Context context){
        if( null == INST){
            INST = new Update(context);
        }
    }

    public Context getContext(){
        return mContext;
    }

    public Repository<?> getRepository() {
        return mRepository;
    }

    public void setAppId(String appId){
        mAppId = appId;
    }

    public void setAppKey(String appKey){
        mAppKey = appKey;
    }

    public void setChannel(String channel){
        mChanel = channel;
    }

    public String getAppId(){
        return mAppId;
    }

    public String getAppKey(){
        return mAppKey;
    }

    public String getChannel(){
        return mChanel;
    }

    public String getUniqueId(){
        return MD5.toMd5(DeviceHelper.getUniqueId() + DeviceHelper.getIMEI(mContext));
    }
}
