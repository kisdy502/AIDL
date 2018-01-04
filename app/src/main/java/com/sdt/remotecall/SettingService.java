package com.sdt.remotecall;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2017/12/27.
 */

public class SettingService extends Service {

    SettingManager binder;

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new SettingManager();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private class SettingManager extends ISettingManager.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int getScreenTime() throws RemoteException {
            return 128;
        }

        @Override
        public ComponentName getScreenSaverComponent() throws RemoteException {
            return null;
        }

        @Override
        public String getSetting(String key) throws RemoteException {
            return null;
        }

        @Override
        public boolean setScreenComponents(ComponentName component) throws RemoteException {
            return false;
        }
    }
}
