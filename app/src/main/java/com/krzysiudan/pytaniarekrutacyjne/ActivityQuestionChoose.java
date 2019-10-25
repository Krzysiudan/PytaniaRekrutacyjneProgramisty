package com.krzysiudan.pytaniarekrutacyjne;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class ActivityQuestionChoose extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_choose);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvNumbers);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        ArrayList<String> categories = databaseAccess.getCategories();
        databaseAccess.close();
        recyclerViewAdapter = new MyRecyclerViewAdapter(getApplicationContext(),categories);
        recyclerViewAdapter.setClickListener(this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(recyclerViewAdapter);

        for(String a:categories){
            Log.e("Activities","Category : "+ a);
        }


    }

    @Override
    public void onItemClick(View view, int position) {
        String selectedCategory = recyclerViewAdapter.getItem(position);
        Log.d("Activities","Clicked : " + selectedCategory);
        Intent intent = new Intent(ActivityQuestionChoose.this,ActivitySingleQuestion.class);
        intent.putExtra("Category",selectedCategory);
        startActivity(intent);

    }
}
