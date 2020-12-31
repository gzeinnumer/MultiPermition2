<p align="center">
  <img src="https://bordencom.com/wp-content/uploads/2016/03/Do-You-Have-Permission.png" width="400"/>
</p>

<h1 align="center">
    MultiPermission V2 Wiht Rebus Permission
</h1>

**Example Multi Check Permissions.** Request Permissions at same time, I recommend to use this step on your `First Activity`, now i use it on `PermissionActivity` :



#### Step 1.
Add maven `jitpack.io` and `dependencies` in `build.gradle (Project)` :
```gradle
// build.gradle project
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}

// build.gradle app/module
dependencies {
  ...
  implementation 'com.github.gzeinnumer:MyLibDirectory:1.5.3'
}
```

#### Step 2.
**Manifest.** add permission ke file manifest. I recommend to add `requestLegacyExternalStorage=true` if you using Android 10.

```xml
<manifest >

    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

    <application
        android:requestLegacyExternalStorage="true">

    </application>

</manifest>
```

#
#### Step 3.
Add array permission that you need :

**First Activity.** put request permission at your first activity, now i use it on `PermissionActivity`.

```java
public class PermissionActivity extends AppCompatActivity {

    PermissionEnum[] permissions = new PermissionEnum[]{
            PermissionEnum.WRITE_EXTERNAL_STORAGE,
            PermissionEnum.READ_EXTERNAL_STORAGE
    };

    ...

}
```

#
#### Step 4.
Add function `checkPermissions` to check permission on app, if permission not granted than popup will show and ask to `Allow`. Please click `allow` :

```java
public class PermissionActivity extends AppCompatActivity {

    ...

    //TYPE 1
    private void checkPermissions() {
        boolean isAllGranted = FGPermission.getPermissionResult(this, permissions);

        if (isAllGranted){
            onSuccessCheckPermitions();
        } else {
            Toast.makeText(this, "Permission Required", Toast.LENGTH_SHORT).show();
        }
    }

    //TYPE 2 if you want to know which permission that Granted or Denied use
    private void checkPermissions2() {
        FGPermission.getPermissionResult(this, permissions, new FGPermission.CallBackPermission() {
            @Override
            public void result(boolean isAllGranted, List<PermissionsResult> listPermissions) {
                Log.d(TAG, "result: "+isAllGranted);
                for (int i = 0; i < list.size(); i++) {
                    Log.d(TAG, "result: "+list.toString());
                }
                if (isAllGranted){
                    onSuccessCheckPermitions();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    ...

}
```

#
#### Step 5.
You can check all permition are granted or not with function `onRequestPermissionsResult`, if granted you can run your code :

```java
public class PermissionActivity extends AppCompatActivity {

    ...

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handleResult(this, requestCode, permissions, grantResults);

        checkPermissions();
    }

    ...

}
```

#
#### Step 6.
Add action to function `onSuccessCheckPermitions` :

```java
public class PermissionActivity extends AppCompatActivity {

    ...

    private void onSuccessCheckPermitions() {

        startActivity(new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

    }

}
```

**notes.**
  - I always use this method to get permition for all Libray that i made.
  - You can modif body of function `onSuccessCheckPermitions`.

#
#### Step 7.
Add function `checkPermissions` in `onCreate` to check permission everytime app running :

```java
public class PermissionActivity extends AppCompatActivity {

    ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ...

        FGPermission.checkPermissions(this, permissions);

        checkPermissions();
    }

    ...

}
```

#
#### Step 8.
Fullcode will be like this :

```java
public class PermissionActivity extends AppCompatActivity {

    private static final String TAG = "PermissionActivity";

    PermissionEnum[] permissions = new PermissionEnum[]{
            PermissionEnum.WRITE_EXTERNAL_STORAGE,
            PermissionEnum.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        FGPermission.checkPermissions(this, permissions);

        checkPermissions();
    }

    //TYPE 1
    private void checkPermissions() {
        boolean isAllGranted = FGPermission.getPermissionResult(this, permissions);

        if (isAllGranted){
            onSuccessCheckPermitions();
        } else {
            Toast.makeText(this, "Permission Required", Toast.LENGTH_SHORT).show();
        }
    }

    //TYPE 2
    private void checkPermissions2() {
        FGPermission.getPermissionResult(this, permissions, new FGPermission.CallBackPermission() {
            @Override
            public void result(boolean isAllGranted, List<PermissionsResult> list) {
                Log.d(TAG, "result: "+isAllGranted);
                for (int i = 0; i < list.size(); i++) {
                    Log.d(TAG, "result: "+list.toString());
                }
                if (isAllGranted){
                    onSuccessCheckPermitions();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onSuccessCheckPermitions() {
        Log.d(TAG, "onSuccessCheckPermitions: S");
        startActivity(new Intent(getApplicationContext(), PermissionActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handleResult(this, requestCode, permissions, grantResults);

        checkPermissions();
    }
}
```

#
#### Step 9.
Preview :
|![](https://github.com/gzeinnumer/MultiPermition/blob/master/assets/example1.jpg)|![](https://github.com/gzeinnumer/MultiPermition/blob/master/assets/example2.jpg)|![](https://github.com/gzeinnumer/MultiPermition/blob/master/assets/example3.jpg)|
|--|--|--|
|Request Permission 1 |Request Permission 2 |If all permission granted Toast `AllGranted` will show |

#
#### FullCode

[Java](https://github.com/gzeinnumer/MultiPermition2/blob/master/app/src/main/java/com/gzeinnumer/multipermition2/PermissionActivity.java)
& [Manifest](https://github.com/gzeinnumer/MultiPermition2/blob/master/app/src/main/AndroidManifest.xml)

---

```
Copyright 2020 M. Fadli Zein
```
