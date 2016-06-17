package android_app.app_update.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/6.
 */
public class UpdateEntity implements Serializable {

    @JsonProperty("upgrade")
    int mUpgrade;
    @JsonProperty("enforce_upgrade")
    int mEnforceUpgrade;
    @JsonProperty("upgradelog")
    String mUpgradeLog;
    @JsonProperty("package_version_code")
    String mPackageVersionCode;
    @JsonProperty("package_md5")
    String mPackageMD5;
    @JsonProperty("package_url")
    String mPackageUrl;
    @JsonProperty("package_size")
    String mPackageSize;

    public boolean isUpgrade() {
        return mUpgrade == 1;
    }

    public boolean isForceUpgrade() {
        return mEnforceUpgrade == 1;
    }

    public String getUpgradeLog() {
        return mUpgradeLog;
    }

    public String getPackageVersionCode() {
        return mPackageVersionCode;
    }

    public String getPackageMD5() {
        return mPackageMD5;
    }

    public String getPackageUrl() {
        return mPackageUrl;
    }

    public String getPackageSize() {
        return mPackageSize;
    }
}
