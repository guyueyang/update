package android_app.app_update.task;

import com.andcup.android.frame.datalayer.exception.JobException;
import com.andcup.common.DeviceHelper;

import java.io.IOException;

import android_app.app_update.Update;
import android_app.app_update.model.UpdateEntity;
import android_app.app_update.model.base.BaseEntity;
import android_app.app_update.task.base.BaseTask;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/28.
 */
public class UpdateTask extends BaseTask<BaseEntity<UpdateEntity>> {

    @Override
    public BaseEntity start() throws IOException {
        String uuid   = Update.getInstance().getUniqueId();
        String appid  = Update.getInstance().getAppId();
        String appKey = Update.getInstance().getAppKey();
        String channel= Update.getInstance().getChannel();
        int version= DeviceHelper.getVerCode(Update.getInstance().getContext());
        int time    = getTime();
        String sign=md5(uuid+appid+version+time+appKey);
        return apis().getUpdateTask(uuid,appid,version,time,channel,sign).execute().body();
    }

    @Override
    public void finish(BaseEntity<UpdateEntity> listEntity) throws JobException {
    }
}

