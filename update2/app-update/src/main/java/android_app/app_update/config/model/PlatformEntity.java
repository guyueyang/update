package android_app.app_update.config.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/28.
 */
public class PlatformEntity {
    @JsonProperty("url")
    String      mUrl;
    @JsonProperty("enable")
    boolean     mIsEnable;
    @JsonProperty("debug")
    boolean     mIsDebug;

    public boolean isEnable() {
        return mIsEnable;
    }

    public boolean isIsDebug() {
        return mIsDebug;
    }

    public String getUrl() {
        return mUrl;
    }
}
