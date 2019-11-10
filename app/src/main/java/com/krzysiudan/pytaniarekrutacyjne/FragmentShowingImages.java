package com.krzysiudan.pytaniarekrutacyjne;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class FragmentShowingImages extends DialogFragment {

    private ImageView imageView;

    public FragmentShowingImages(){

    }

    public static FragmentShowingImages newInstance(byte[] image){
        FragmentShowingImages frag = new FragmentShowingImages();
        Bundle args = new Bundle();
        args.putByteArray("BYTEARRAY",image);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_showing_images, container);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.imageView);
        byte[] image = getArguments().getByteArray("BYTEARRAY");
        Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.dialog_theme);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getDialog()==null){
            return;
        }
        int dialogWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        int dialogHeight = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setLayout(dialogWidth,dialogHeight);
    }
}
