package android_app.app_update.task.base;

import com.andcup.android.frame.datalayer.exception.JobException;
import com.andcup.android.frame.datalayer.job.Job;
import com.andcup.android.frame.datalayer.job.JobEntity;
import com.andcup.common.MD5;

import android_app.app_update.Update;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/25.
 */
public abstract class Task<T extends JobEntity> implements Job<T> {

    protected  <Api> Api api(){
        return (Api) Update.getInstance().getRepository().from();
    }

//    protected String getAppId(){
//        return Template.getInstance().getAppId();
//    }
//
//    protected String getAppKey(){
//        return Template.getInstance().getAppKey();
//    }

    protected int getTime(){
        return (int) (System.currentTimeMillis() / 1000);
    }

    protected String lastUpdate(){
        return "0";
    }

    protected String md5(String value){
        return MD5.toMd5(value);
    }

    protected String getUniqueId(){
        return Update.getInstance().getUniqueId();
    }

    @Override
    public void finish(T t) throws JobException {

    }
}
