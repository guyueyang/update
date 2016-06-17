package android_app.update;

import com.andcup.android.frame.AndcupApplication;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import android_app.app_update.Update;
import android_app.app_update.model.UpdateEntity;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/22.
 */
public class CordovaApplication extends AndcupApplication {

    public static CordovaApplication CTX;


    @Override
    public void onCreate() {
        super.onCreate();
        CTX = this;
        Update.init(CTX);
        Update.getInstance().setAppId("5");
        Update.getInstance().setAppKey("aa35ae1370eb5a6161187913088aa24e");
    }

    public void checkUpdate(){
        Update.getInstance().checkUpdate(new Update.UpdateListener() {
            @Override
            public void onComplete(UpdateEntity response) {
                EventBus.getDefault().post(response);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

}
