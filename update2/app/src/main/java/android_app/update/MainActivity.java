package android_app.update;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android_app.app_update.Update;
import android_app.app_update.model.UpdateEntity;

public class MainActivity extends Activity {

    Button mBtnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnUpdate=(Button)findViewById(R.id.btn_update);
        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update.getInstance().checkUpdate(new Update.UpdateListener() {
                    @Override
                    public void onComplete(UpdateEntity response) {
                        if(response.isUpgrade()){
//                            Update.getInstance().show(getFragmentManager());
                        }else{

                        }
                    }
                    @Override
                    public void onError(Throwable e) {

                    }
                });
            }
        });
    }


}
