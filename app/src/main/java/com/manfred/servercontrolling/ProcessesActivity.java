package com.manfred.servercontrolling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processes);
        fillExpandibleList();
    }
    public void fillExpandibleList() {

        List<Map<String, String>> interestsList = new ArrayList<Map<String,String>>();

        interestsList.add(createInterest("interest", "Sports"));
        interestsList.add(createInterest("interest", "Video Games"));
        interestsList.add(createInterest("interest", "Studying"));
        interestsList.add(createInterest("interest", "Volunteering"));

        SimpleAdapter simpleAdpterForListView =
                new SimpleAdapter(this, interestsList, android.R.layout.simple_list_item_checked,
                        new String[] {"interest"}, new int[] {android.R.id.text1});

        ListView lv = (ListView) findViewById(R.id.listViewProc);

        lv.setAdapter(simpleAdpterForListView);

    }
    private HashMap<String, String> createInterest(String key, String name) {
        HashMap<String, String> interest = new HashMap<String, String>();
        interest.put(key, name);
        return interest;

    }
}
