/*
    A basic App launcher for Android Things by Carl Whalley

    This class shows a list of Apps to be launched.
    If your app has no BACK button you'll not be able to get out of it, so a quick tip is to use
    ADB shell input keyevent 4 when it's needed.
 */
package com.otamate.android.things.athome;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Go fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        List<App> installedApps = getAllApplications(getApplicationContext(), false);

        for (App app: installedApps) {
            if (!app.isSystemApp()) {
                Log.d(TAG, "Installed package: " + app.getPackageName());
                Log.d(TAG, "Installed App    : " + app.getName());
            }
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AppAdapter(installedApps));
    }

    private List<App> getAllApplications(Context context, boolean includeSystemApps) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);

        List<App> installedApps = new ArrayList<>();

        for (PackageInfo pkgInfo : packages) {
            if (pkgInfo.versionName == null) {
                continue;
            }

            App newApp = new App();
            boolean isSystemApp = ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);

            newApp.setPackageName(pkgInfo.packageName);
            newApp.setName(pkgInfo.applicationInfo.loadLabel(packageManager).toString());
            newApp.setIcon(pkgInfo.applicationInfo.loadIcon(packageManager));
            newApp.setSystemApp(isSystemApp);

            if (includeSystemApps || !isSystemApp) {
                installedApps.add(newApp);
            }
        }

        return installedApps;
    }
}
