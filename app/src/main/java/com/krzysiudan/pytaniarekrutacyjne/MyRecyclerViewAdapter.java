package com.krzysiudan.pytaniarekrutacyjne;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> arrayListCategories;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    DatabaseAccess databaseAccess ;


    MyRecyclerViewAdapter(Context context, ArrayList<String> categories) {
        this.mInflater = LayoutInflater.from(context);
        this.arrayListCategories = categories;
        databaseAccess =DatabaseAccess.getInstance(context);
    }

    @NonNull
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.textViewCategoryName.setText(arrayListCategories.get(position));
        databaseAccess.open();
        String numbers = databaseAccess.getNumberOfQuestionsAnsweredCorrectlyInCategory(arrayListCategories.get(position))+"/"+databaseAccess.getNumberOfQuestionsInCategory(arrayListCategories.get(position));
        holder.textViewAnswersCount.setText(numbers);
        databaseAccess.close();
    }

    @Override
    public int getItemCount() {
        return arrayListCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textViewCategoryName;
        public TextView textViewAnswersCount;

        Typeface tf_regular;

        ViewHolder(View itemView) {
            super(itemView);

            textViewCategoryName = itemView.findViewById(R.id.category_name);
            textViewAnswersCount = itemView.findViewById(R.id.answers_count);
            tf_regular = ResourcesCompat.getFont(itemView.getContext(),R.font.chunk_five_regular);
            this.textViewAnswersCount.setTypeface(tf_regular);
            this.textViewCategoryName.setTypeface(tf_regular);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return arrayListCategories.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }





}
