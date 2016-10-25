package com.nodhan.exp4;

import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> subjects;
    ArrayAdapter<String> arrayAdapter;
    final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";
    String linkHead = "https://www.tutorialspoint.com/";
    String linkTail = "/index.htm";
    String[] links;
    CustomTabsClient mCustomTabsClient;
    CustomTabsSession mCustomTabsSession;
    CustomTabsServiceConnection mCustomTabsServiceConnection;
    CustomTabsIntent mCustomTabsIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        subjects = new ArrayList<>();
        subjects.add("C");
        subjects.add("C++");
        subjects.add("Java");
        subjects.add("Android");
        subjects.add("Python");
        subjects.add("HTML");
        subjects.add("CSS");
        subjects.add("PHP");
        subjects.add("JavaScript");
        subjects.add("AngularJS");

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subjects);
        listView.setAdapter(arrayAdapter);

        links = new String[]{
                "cprogramming",
                "cplusplus",
                "java",
                "android",
                "python",
                "html",
                "css",
                "php",
                "javascript",
                "angularjs"
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mCustomTabsServiceConnection = new CustomTabsServiceConnection() {
                    @Override
                    public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                        mCustomTabsClient = customTabsClient;
                        mCustomTabsClient.warmup(0L);
                        mCustomTabsSession = mCustomTabsClient.newSession(null);
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        mCustomTabsClient = null;
                    }
                };

                Context context = view.getContext();

                CustomTabsClient.bindCustomTabsService(context, CUSTOM_TAB_PACKAGE_NAME, mCustomTabsServiceConnection);

                mCustomTabsIntent = new CustomTabsIntent.Builder(mCustomTabsSession)
                        .setShowTitle(true)
                        .setStartAnimations(context, android.R.anim.slide_out_right, android.R.anim.slide_in_left)
                        .setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .build();
                mCustomTabsIntent.launchUrl(context, Uri.parse(linkHead + links[position] + linkTail));
            }
        });

    }
}
