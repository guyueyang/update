package android_app.app_update.view;

import android.text.TextUtils;

import com.andcup.lib.download.data.loader.DownloadTaskDao;
import com.andcup.lib.download.data.model.DownloadTask;

import java.util.List;

/**
 * Created by Amos on 2015/9/11.
 */
public class DownloadHandler {

    public static List<DownloadTask> getTasks(String description){
        if(TextUtils.isEmpty(description)){
            return null;
        }
        return DownloadTaskDao.getTasks("description" + " =?", description);
    }

    public static boolean isTaskExist(String appPath){
        List<DownloadTask> taskList = getTasks(appPath);
        if(taskList != null && taskList.size() > 0){
            return true;
        }
        return false;
    }

    public static DownloadTask getTask(String appPath){
        List<DownloadTask> taskList = getTasks(appPath);
        if(taskList != null && taskList.size() > 0){
            return taskList.get(0);
        }
        return null;
    }
}
