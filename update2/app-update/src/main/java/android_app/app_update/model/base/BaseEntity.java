package android_app.app_update.model.base;

import com.andcup.android.frame.datalayer.job.JobEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/28.
 */
public class BaseEntity<T> extends JobEntity<T> {

    @JsonProperty("status")
    int     mStatus;

    @JsonProperty("message")
    String  mMessage;

    @JsonProperty("data")
    T       mData;

    @Override
    public int getErrCode() {
        return mStatus;
    }

    @Override
    public String getMessage() {
        return mMessage;
    }

    @Override
    public void throwIfException() {
        if(mStatus < 0){
            throw new RuntimeException(mMessage);
        }
    }

    @Override
    public T body() {
        return mData;
    }
}
