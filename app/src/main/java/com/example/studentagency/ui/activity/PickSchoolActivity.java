package com.example.studentagency.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.studentagency.R;
import com.example.studentagency.ui.adapter.PickSchoolActivityRecyclerViewAdapter;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;


public class PickSchoolActivity extends BaseActivity {

    private static final String TAG = "PickSchoolActivity";
    private Toolbar toolbar;
    private MaterialSearchView searchView;

    private List<String> schoolBeansData;

    private PickSchoolActivityRecyclerViewAdapter adapter;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_school);

        initViews();

        setSearchViewDataList();

        initRecyclerView();

        setSearchViewListen();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        searchView = findViewById(R.id.searchView);
    }

    private void setSearchViewDataList() {
        schoolBeansData = new ArrayList<>();
        schoolBeansData.add("广东外语外贸大学");
        schoolBeansData.add("中山大学");
        schoolBeansData.add("北京大学");
        schoolBeansData.add("清华大学");
        schoolBeansData.add("山东大学");
        schoolBeansData.add("复旦大学");
        schoolBeansData.add("上海交通大学");
        schoolBeansData.add("上海理工大学");
        schoolBeansData.add("同济大学");
        schoolBeansData.add("广东美术学院");
        schoolBeansData.add("华南理工大学");
        schoolBeansData.add("中国海洋大学");
        schoolBeansData.add("广东大学");
        schoolBeansData.add("广东工业大学");
        schoolBeansData.add("天津大学");
        schoolBeansData.add("北京航天航空大学");
        schoolBeansData.add("重庆邮电大学");
        schoolBeansData.add("电子科技大学");
        schoolBeansData.add("武汉理工大学");
        schoolBeansData.add("华南农业大学");
        schoolBeansData.add("南京农业大学");
        schoolBeansData.add("中国人民大学");
//        schoolBeansData.add("abcd");
//        schoolBeansData.add("ef");
//        schoolBeansData.add("yui");
//        schoolBeansData.add("iop");
//        schoolBeansData.add("bcd");
//        schoolBeansData.add("a");
//        schoolBeansData.add("rrtye");
//        schoolBeansData.add("fncv");
//        schoolBeansData.add("qwe");
//        schoolBeansData.add("sdv");
//        schoolBeansData.add("arfu");
//        schoolBeansData.add("chjy");
//        schoolBeansData.add("iuyovn");
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.mRecyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);

        adapter = new PickSchoolActivityRecyclerViewAdapter(schoolBeansData);
        adapter.setOnClickItemListener(new PickSchoolActivityRecyclerViewAdapter.OnClickItemListener() {
            @Override
            public void clickItem(String schoolName) {
                Intent intent = new Intent();
                intent.putExtra("schoolName",schoolName);
                setResult(3,intent);
                finish();
            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    private void setSearchViewListen() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i(TAG, "onQueryTextChange: ");
                //过滤输入的内容
                filter(newText);
                return true;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });
    }

    private void filter(String inputString) {
        try {
            List<String> filterDataList = new ArrayList<>();
            //当输入框的内容为空时显示全部列表
            if (TextUtils.isEmpty(inputString)) {
                filterDataList = schoolBeansData;
            } else {
                //清空
                filterDataList.clear();
                //遍历列表
                for (String schoolName : schoolBeansData) {
                    if (schoolName.contains(inputString)) {
                        filterDataList.add(schoolName);
                    }
                }
            }
            adapter.update(filterDataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search_item);
        searchView.setMenuItem(menuItem);

        return true;
    }
}
