package com.nightfarmer.sample;

import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.nightfarmer.androidcommon.app.AppInfo;
import com.nightfarmer.androidcommon.common.Alert;
import com.nightfarmer.androidcommon.data.LocalData;
import com.nightfarmer.androidcommon.device.DeviceInfo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Person person = new Person();
        person.age = 23333;
        person.name = "yoo";
        LocalData localData = new LocalData(this);
        localData.put(person);

        ArrayList<Person> persons = new ArrayList<>();

        person.name = "p0";
        localData.put(person);
        persons.add(person);

        person.name = "p1";
        localData.put("p1", person);
        persons.add(person);


        person.name = "p2";
        localData.put("p2", person);
        persons.add(person);

        localData.put(persons);

        Person person1 = localData.get(Person.class);
        Log.i("xx", "" + person1.name);
        person1 = localData.get("p1", Person.class);
        Log.i("xx", "" + person1.name);
        person1 = localData.get("p2", Person.class);
        Log.i("xx", "" + person1.name);

        ArrayList arrayList = localData.get(ArrayList.class);
        Log.i("xx", "" + arrayList.toString());


        Log.i("xxx", DeviceInfo.getBootTimeString());
        Log.i("xxx", DeviceInfo.getMacAddress(this));

        DeviceInfo.printDisplayInfo(this);
        DeviceInfo.printMemoryInfo(this);
        DeviceInfo.printSystemInfo();

        DeviceInfo.getIMEI(this);
        DeviceInfo.getIMSI(this);
        DeviceInfo.printTelephoneInfo(this);

        Log.i("xxx", DeviceInfo.getDataPath(this));
        String normalSDCardPath = DeviceInfo.getNormalSDCardPath();
        Log.i("xxx", normalSDCardPath);
        Log.i("xxx", DeviceInfo.getSDCardPath());

        Log.i("xxx", AppInfo.getPackageName(this));
        PackageInfo packageInfo = AppInfo.getPackageInfo(this);
        Log.i("xxx", packageInfo.versionName);
        Log.i("xxx", "" + packageInfo.versionCode);
        Log.i("xxx", "" + AppInfo.isRunningForeground(this));

//        AppInfo.shareToOtherApp(this, "xx", "yyy", "yoo");


        Alert.alert(this, "a", "b", new String[]{"x", "y", "z"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("xxx", "x");
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("xxx", "y");
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("xxx", "z");
            }
        });
    }
}
