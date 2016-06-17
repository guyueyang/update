package android_app.app_update.config;

import android.content.Context;

import com.fasterxml.jackson.databind.JavaType;

import java.util.ArrayList;
import java.util.List;

import android_app.app_update.config.base.ConfigureProvider;
import android_app.app_update.config.model.PlatformEntity;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/31.
 */
public class Platform extends ConfigureProvider implements ConfigureProvider.Provider {

    List<PlatformEntity> mPlatformList;

    protected Platform() {
    }

    static Platform PROVIDER;

    public static Platform gen() {
        if (null == PROVIDER) {
            PROVIDER = new Platform();
        }
        return PROVIDER;
    }
    
    public static Platform getInstance() {
        return PROVIDER;
    }

    @Override
    public void build(Context context) {
        JavaType javaType = getCollectionType(ArrayList.class, PlatformEntity.class);
        mPlatformList = build(context, "update.json", javaType);
    }

    public String getUrl() {
        for(PlatformEntity platform: mPlatformList){
            if(platform.isEnable()) {
                return platform.getUrl();
            }
        }
        return null;
    }

    public boolean isDebug() {
        for(PlatformEntity platform: mPlatformList){
             if(platform.isEnable()){
                 return platform.isIsDebug();
             }
        }
        return false;
    }
}
