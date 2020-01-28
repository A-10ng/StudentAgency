package com.example.studentagency.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studentagency.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/11/02
 * desc:
 */
public class PersonFragment extends Fragment {
    //根视图
    private View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_person,container,false);
        return root;
    }
}
