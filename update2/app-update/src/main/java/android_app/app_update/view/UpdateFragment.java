package android_app.app_update.view;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andcup.android.frame.view.annotations.Restore;
import com.andcup.app.AbsDialogFragment;
import com.andcup.common.AndroidUtils;
import com.andcup.lib.download.DownloadListener;
import com.andcup.lib.download.DownloadManager;
import com.andcup.lib.download.ErrorType;
import com.andcup.lib.download.TaskCreator;
import com.andcup.lib.download.data.model.DownloadResource;
import com.andcup.lib.download.data.model.DownloadStatus;
import com.andcup.lib.download.data.model.DownloadTask;

import java.io.File;
import java.util.List;

import android_app.app_update.R;
import android_app.app_update.key.KeyValue;
import android_app.app_update.model.UpdateEntity;

/**
 * Created by Administrator on 2016/5/6.
 */
public class UpdateFragment extends AbsDialogFragment implements DownloadListener {

    @Restore(KeyValue.CHECK_UPDATE)
    UpdateEntity mUpateEntity;
    Button mBtnDownload;
    ProgressBar mPbUpdate;
    TextView mTvInfo;
    ImageButton mIBClose;

    DownloadTask mDownloadTask;
    String         mAppPath;
    String         mFormatDownload;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_update;
    }

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.ThemeUpdateDialog);
    }

    @Override
    protected void afterActivityCreate(Bundle afterActivityCreate){
        super.afterActivityCreate(afterActivityCreate);
        mAppPath = mUpateEntity.getPackageUrl();
        mFormatDownload = getString(R.string.task_download_progress);
        mBtnDownload = findViewById(R.id.tv_download);
        mPbUpdate    = findViewById(R.id.pb_update);
        mTvInfo = findViewById(R.id.tv_info);
        mIBClose=findViewById(R.id.ib_close);
        mTvInfo.setText(mUpateEntity.getUpgradeLog());
        if(mUpateEntity.isForceUpgrade()){
            getDialog().setCanceledOnTouchOutside(false);
            getDialog().setCancelable(false);
            mIBClose.setVisibility(View.GONE);
        }else{
            getDialog().setCanceledOnTouchOutside(true);
            mIBClose.setVisibility(View.VISIBLE);

        }

        mBtnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDownload();
            }
        });
        mIBClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }

    public void updateDownload(){
        //自动开始下载任务.
        DownloadManager.getInstance().addDownloadListener(this);
        mDownloadTask = DownloadHandler.getTask(mUpateEntity.getPackageUrl());
        if(null == mDownloadTask){
            startDownload(mAppPath);
            return;
        }

        //任务正在下载中.
        if(!mDownloadTask.isCompleted()){
            mBtnDownload.setText(String.format(mFormatDownload, mDownloadTask.getProgress()) + " % ");
            resumeDownloadIfNeed();
            return;
        }

        //任务已下载完成，但未安装
        if(mDownloadTask.isCompleted()){
            install();
        }
    }

    private void install(){
        File file = new File(mDownloadTask.getResources().get(0).getLocalPath());
        if(file.exists()){
            AndroidUtils.installApkWithPrompt(file, getActivity());
        }else{
            DownloadManager.getInstance().delete(mDownloadTask.getTaskId(), true);
        }
    }

    private void resumeDownloadIfNeed(){
        //判断是否正在下载
        if( null == mDownloadTask){
            return;
        }

        //下载过程中,文件被删除
        List<DownloadResource> list = mDownloadTask.getResources();
        if( null != list && list.size() > 0) {
            try{
                File file = new File(list.get(0).getLocalPath() + ".download");
                if (!file.exists()) {
                    DownloadManager.getInstance().delete(mDownloadTask.getTaskId(), true);
                    startDownload(mAppPath);
                    return;
                }
            }catch (Exception e){

            }
        }

        //开始下载
        if(mDownloadTask.isDownloading()){
            DownloadManager.getInstance().pause(mDownloadTask.getTaskId());
        }else{
            DownloadManager.getInstance().start(mDownloadTask.getTaskId());
        }
    }

    private void startDownload(String app){
        TaskCreator creator = TaskCreator.creator();
        creator.setTitle(app);
        creator.setDescription(app);
        creator.addResource(app, app);
        mDownloadTask = DownloadManager.getInstance().add(creator);
    }

    @Override
    public void onAdd(long l) {

    }

    @Override
    public void onStart(long l, int i) {
        if(null != mDownloadTask && mDownloadTask.getTaskId() == l){
            try{
                mBtnDownload.setText(String.format(mFormatDownload, i) + " % ");
            }catch (Exception e){

            }
        }
    }

    @Override
    public void onPrepared(long l) {

    }

    @Override
    public void onPause(long l, DownloadStatus downloadStatus) {
        if(null != mDownloadTask && mDownloadTask.getTaskId() == l){
            try {
                mBtnDownload.setText(getString(R.string.download_continue));
            }catch (Exception e){

            }
        }
    }

    @Override
    public void onProgress(long l, int i) {
        if(null != mDownloadTask && mDownloadTask.getTaskId() == l){
            try{
                mBtnDownload.setText(String.format(mFormatDownload, i) + " % ");
                mPbUpdate.setProgress(i);
            }catch (Exception e){

            }
        }
    }

    @Override
    public void onComplete(long l) {
        if(null != mDownloadTask && mDownloadTask.getTaskId() == l){
            try{
                mPbUpdate.setProgress(0);
                mBtnDownload.setText(getString(R.string.update_ok));
                install();
            }catch (Exception e){

            }
        }
    }

    @Override
    public void onError(long l, ErrorType errorType) {

    }

    @Override
    public void onWait(long l) {

    }

    @Override
    public void onSpeed(long l, long l1) {

    }

    @Override
    public void onDeleted(long l) {

    }
}
