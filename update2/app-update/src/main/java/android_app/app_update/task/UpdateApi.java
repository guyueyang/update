package android_app.app_update.task;


import android_app.app_update.model.UpdateEntity;
import android_app.app_update.model.base.BaseEntity;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/25.
 */
public interface UpdateApi {

    @FormUrlEncoded
    @POST("/api/update/check")
    Call<BaseEntity<UpdateEntity>> getUpdateTask(
            @Field(Fields.uuid) String uuid,
            @Field(Fields.appId) String pid,
            @Field(Fields.versioncode) int versioncode,
            @Field(Fields.timestamp) int timestamp,
            @Field(Fields.channel) String channel,
            @Field(Fields.sign) String sign
    );
}
