package android_app.app_update.config.base;

import android.content.Context;

import com.andcup.common.IOUtils;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android_app.app_update.config.Platform;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/29.
 */
public abstract class ConfigureProvider {

    public interface Provider{
        void build(Context context);
    }

    public static void init(Context context){
        Platform.gen().build(context);
    }

    protected ConfigureProvider(){

    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    protected <T> List<T> build(Context context, String file, JavaType javaType){
        try {
            InputStream inputStream = context.getAssets().open(file);
            String pc = IOUtils.readToString(inputStream);
            ObjectMapper objectMapper = new ObjectMapper();
            List<T> list = objectMapper.readValue(pc, javaType);
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }


    protected <T> T build(Context context, String file, Class<T> tClass){
        try {
            InputStream inputStream = context.getAssets().open(file);
            String pc = IOUtils.readToString(inputStream);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(pc, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

}
