package android_app.app_update.intercepts;

import android.os.Build;
import android.text.TextUtils;
import com.andcup.common.DeviceHelper;

import java.io.IOException;

import android_app.app_update.Update;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/19.
 */
public class HeaderIntercept implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder request = chain.request().newBuilder();
        // android type.
        request.addHeader("device_type", "1");
        // mac
        String mac  = DeviceHelper.getMacAddress(Update.getInstance().getContext());
        if(TextUtils.isEmpty(mac)){
            mac = "mac is null";
        }
        request.addHeader("mac", mac);
        //imei
        String imei = DeviceHelper.getIMEI(Update.getInstance().getContext());
        if(TextUtils.isEmpty(imei)){
            imei = "imei is null";
        }
        request.addHeader("imei", imei);
        // uuid
        String uuid = Update.getInstance().getUniqueId();
        request.addHeader("uuid",    uuid);
        //imsi
        String imsi = DeviceHelper.getIMSI(Update.getInstance().getContext());
        if(TextUtils.isEmpty(imsi)){
            imsi = "imsi is null";
        }
        request.addHeader("imsi", imsi);

        request.addHeader("channel", "qudao");
        request.addHeader("version", DeviceHelper.getVerName(Update.getInstance().getContext()));
        request.addHeader("build_version", String.valueOf(DeviceHelper.getVerCode(Update.getInstance().getContext())));
        request.addHeader("os_version", Build.VERSION.RELEASE);
        request.addHeader("device_model", Build.MODEL);
        return chain.proceed(request.build());
    }
}
