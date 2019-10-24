package com.krzysiudan.pytaniarekrutacyjne;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class ActivityQuestionChoose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_choose);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvNumbers);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        ArrayList<String> categories = databaseAccess.getCategories();
        databaseAccess.close();
        MyRecyclerViewAdapter recyclerViewAdapter = new MyRecyclerViewAdapter(getApplicationContext(),categories);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(recyclerViewAdapter);

        for(String a:categories){
            Log.e("Activities","Category : "+ a);
        }


    }
}
