package com.gzeinnumer.multipermition2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.gzeinnumer.gzndirectory.helper.FGPermission;
import com.gzeinnumer.gzndirectory.helper.model.PermissionsResult;

import java.util.List;

import rebus.permissionutils.PermissionEnum;
import rebus.permissionutils.PermissionManager;

public class PermissionActivity extends AppCompatActivity {

    private static final String TAG = "PermissionActivity";

    PermissionEnum[] permissions = new PermissionEnum[]{
            PermissionEnum.WRITE_EXTERNAL_STORAGE,
            PermissionEnum.READ_EXTERNAL_STORAGE,
            PermissionEnum.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        FGPermission.checkPermissions(this, permissions);

        checkPermissions();
    }

    private void checkPermissions() {
        boolean isAllGranted = FGPermission.getPermissionResult(this, permissions);

        if (isAllGranted){
            onSuccessCheckPermitions();
        } else {
            Toast.makeText(this, "Permission Requared", Toast.LENGTH_SHORT).show();
        }

        FGPermission.getPermissionResult(this, permissions, new FGPermission.CallBackPermission() {
            @Override
            public void result(boolean isAllGranted, List<PermissionsResult> listPermissions) {
                Log.d(TAG, "result: "+isAllGranted);

                for (int i = 0; i < listPermissions.size(); i++) {
                    Log.d(TAG, "result: "+listPermissions.toString());
                }
                if (isAllGranted){
                    onSuccessCheckPermitions();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Requared", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onSuccessCheckPermitions() {
        Log.d(TAG, "onSuccessCheckPermitions: S");
        startActivity(new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handleResult(this, requestCode, permissions, grantResults);

        checkPermissions();
    }
}