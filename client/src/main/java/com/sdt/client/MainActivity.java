package com.sdt.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sdt.remotecall.ISettingManager;

public class MainActivity extends AppCompatActivity {
    private boolean isConnected = false;
    private ISettingManager iSettingManager;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iSettingManager = ISettingManager.Stub.asInterface(service);
            isConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnected = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void clickEvent(View view) {
        int id = view.getId();
        if (R.id.bindRemote == id) {
            Intent intent = new Intent();
            intent.setAction("com.sdt.remotecall.SettingService");
            intent.setPackage("com.sdt.remotecall");
            bindService(intent, connection, Context.BIND_AUTO_CREATE);

        } else if (R.id.unbindRemote == id) {

        } else if (R.id.callMethod1 == id) {
            if (isConnected) {
                try {
                    int time = iSettingManager.getScreenTime();
                    Toast.makeText(MainActivity.this, "time:" + time, Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else if (R.id.callMethod2 == id) {

        }
    }

    @Override
    protected void onDestroy() {
        if (connection != null)
            unbindService(connection);
        super.onDestroy();
    }
}
