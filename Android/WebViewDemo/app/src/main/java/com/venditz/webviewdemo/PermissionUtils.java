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

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;

/**
 * Developed by Park, Woocheol
 * Email: admin@mrparkwc.com
 * GitHub: https://github.com/ParkWoocheol
 */
public class PermissionUtils {

    private Context mContext;
    private Activity mActivity;
    private Fragment mFragment;

    public static final int OVERLAY_PERMISSION_REQUEST = 100;
    public static final int APP_PERMISSION_SETTING_REQUEST = 200;


    private ArrayList<String> mPermissions = new ArrayList<>();

    private AlertDialog mPermissionRequestDialog;


    public PermissionUtils(Fragment fragment, Context context) {
        mFragment = fragment;
        mContext = context;
    }

    public PermissionUtils(Activity activity) {
        mActivity = activity;
        mContext = activity;
    }

    public PermissionUtils(Context context) {
        mContext = context;
    }

    public boolean isValidPermission(String permission) {
        if (isValidVersionCode()) {
            return mContext.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    public boolean isValidWindowAlertPermission() {
        if (isValidVersionCode()) {
            return Settings.canDrawOverlays(mContext);
        } else {
            return true;
        }
    }

    public boolean checkPermissions(String... permissions) {
        if (isValidVersionCode()) {
            mPermissions = new ArrayList<>();
            for (String permission : permissions) {
                if (mContext.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    mPermissions.add(permission);
                }
            }
            if (mPermissions.size() != 0) {
                if (mActivity != null) {
                    mActivity.requestPermissions(mPermissions.toArray(new String[mPermissions.size()]), OVERLAY_PERMISSION_REQUEST);
                } else {
                    mFragment.requestPermissions(mPermissions.toArray(new String[mPermissions.size()]), OVERLAY_PERMISSION_REQUEST);
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    public boolean checkWindowAlertPermission() {
        if (isValidVersionCode()) {
            if (!Settings.canDrawOverlays(mContext)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + mContext.getPackageName()));
                if (mActivity != null) {
                    mActivity.startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST);
                } else {
                    mFragment.startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST);
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    public boolean isValidVersionCode() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * User Manual Permission Agree. Start Application Settings.
     */
    public void startApplicationPermissionSettings() {
        if (mActivity != null) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + mContext.getPackageName()));
            mActivity.startActivityForResult(intent, APP_PERMISSION_SETTING_REQUEST);
        } else {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + mContext.getPackageName()));
            mFragment.startActivityForResult(intent, APP_PERMISSION_SETTING_REQUEST);
        }
    }

    public void showUserManualPermissionDialog(String title, String message, String positiveButtonMessage, String negativeButtonMessage
            , DialogInterface.OnClickListener positiveOnClickListener, DialogInterface.OnClickListener negativeOnClickListener) {
        AlertDialog.Builder permissionReuqestDialogBuilder;
        if (mActivity != null) {
            permissionReuqestDialogBuilder = new AlertDialog.Builder(mActivity);
        } else {
            permissionReuqestDialogBuilder = new AlertDialog.Builder(mContext);
        }
        permissionReuqestDialogBuilder.setTitle(title);
        permissionReuqestDialogBuilder.setMessage(message);
        permissionReuqestDialogBuilder.setCancelable(false);
        permissionReuqestDialogBuilder.setPositiveButton(positiveButtonMessage, positiveOnClickListener);
        permissionReuqestDialogBuilder.setNegativeButton(negativeButtonMessage, negativeOnClickListener);
        mPermissionRequestDialog = permissionReuqestDialogBuilder.create();
        mPermissionRequestDialog.show();
    }

}