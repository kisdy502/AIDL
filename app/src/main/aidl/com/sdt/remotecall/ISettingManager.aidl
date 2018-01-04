// ISettingManager.aidl
package com.sdt.remotecall;

import android.content.ComponentName;
// Declare any non-default types here with import statements

interface ISettingManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

            int getScreenTime();
            ComponentName getScreenSaverComponent();
            String getSetting(in String key);
            boolean setScreenComponents(in ComponentName component);
}
