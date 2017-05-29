/*
 * Copyright (C) 2016 Park, Woocheol
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.venditz.webviewdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Developed by Park, Woocheol
 * Email: admin@mrparkwc.com
 * GitHub: https://github.com/ParkWoocheol
 */
public abstract class PermissionCheckerActivity extends AppCompatActivity {

    private PermissionUtils mPermissionUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPermissionUtils = new PermissionUtils(this);
    }

    public boolean isValidPermission(String permission) {
        return mPermissionUtils.isValidPermission(permission);
    }

    public boolean isValidWindowAlertPermission(){
        return mPermissionUtils.isValidWindowAlertPermission();
    }

    /**
     * @param permissions Application Uses Permissions.
     * @return isValid Granted Application Uses Permissions.
     */
    public boolean checkPermissions(String... permissions) {
        return mPermissionUtils.checkPermissions(permissions);
    }

    /**
     * @return isValid Granted Window Alert Permission.
     */
    public boolean checkWindowAlertPermission() {
        return mPermissionUtils.checkWindowAlertPermission();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case PermissionUtils.APP_PERMISSION_SETTING_REQUEST:
                requestPermissionSettingResponse();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtils.OVERLAY_PERMISSION_REQUEST) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    requestPermissionsResult(false);
                    return;
                }
            }
            requestPermissionsResult(true);
        }
    }

    public void startApplicationPermissionSettings() {
        mPermissionUtils.startApplicationPermissionSettings();
    }

    public abstract void requestPermissionsResult(boolean result);

    public abstract void requestPermissionSettingResponse();

    public void showUserManualPermissionDialog(String title, String message, String positiveButtonMessage, String negativeButtonMessage
            , DialogInterface.OnClickListener positiveOnClickListener, DialogInterface.OnClickListener negativeOnClickListener) {
        mPermissionUtils.showUserManualPermissionDialog(title, message, positiveButtonMessage, negativeButtonMessage, positiveOnClickListener, negativeOnClickListener);
    }
}