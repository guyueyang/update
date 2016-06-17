package android_app.app_update.task.base;

import com.andcup.android.frame.datalayer.job.JobEntity;

import android_app.app_update.task.UpdateApi;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/28.
 */
public abstract class BaseTask<T extends JobEntity> extends Task<T> {

    protected UpdateApi apis(){
        return super.api();
    }
}
