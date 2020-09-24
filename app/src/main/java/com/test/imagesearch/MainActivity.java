package com.test.imagesearch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.test.imagesearch.adapter.ImageSearchAdapter;
import com.test.imagesearch.models.MasterData;
import com.test.imagesearch.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<MasterData> masterList = new ArrayList<>();

    private MainActivityViewModel mMainActivityViewModel;


    RecyclerView rv_img;
    ImageSearchAdapter imageSearchAdapter;

    EditText et_searchtxt;
    Button btn_search;

    private ProgressDialog dialog;
    Toolbar toolbar;
    ProgressBar proBar;

    boolean isScrolling = false;

    int currentItem,totalItems,scrollItems, pageNumber = 1;

    String searchValue="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rv_img = findViewById(R.id.rv_img);

        et_searchtxt= findViewById(R.id.et_searchtxt);
        btn_search= findViewById(R.id.btn_search);

        proBar = findViewById(R.id.proBar);

        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        proBar.setVisibility(View.GONE);

        dialog = new ProgressDialog(MainActivity.this);

        mMainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);


        mMainActivityViewModel.init(getApplicationContext());


        mMainActivityViewModel.getData().observe(MainActivity.this, new Observer<List<MasterData>>() {
            @Override
            public void onChanged(@Nullable List<MasterData> data) {
                if (data!=null)
                    masterList.addAll(data);

                imageSearchAdapter.notifyDataSetChanged();
            }
        });

        setupRecyclerView();


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_searchtxt.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"Enter Value to Search",Toast.LENGTH_SHORT).show();
                }else{
                    if (et_searchtxt.getText().toString().length()<3){
                        Toast.makeText(MainActivity.this,"Enter 3 Characters to search",Toast.LENGTH_SHORT).show();
                    }else{

                        dialog.setMessage("Fetching Images");
                        dialog.show();
                        pageNumber = 1;
                        searchValue = et_searchtxt.getText().toString();
                        mMainActivityViewModel.getMasterData(pageNumber,et_searchtxt.getText().toString()).observe(MainActivity.this, new Observer<List<MasterData>>() {
                            @Override
                            public void onChanged(@Nullable List<MasterData> data) {
                                dialog.dismiss();
                                masterList.clear();
                                if (data!=null)
                                    masterList.addAll(data);
                                else
                                    Toast.makeText(MainActivity.this,"Search Failed. Try Again",Toast.LENGTH_SHORT).show();

                                imageSearchAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        });

    }

    private void setupRecyclerView() {
        if (imageSearchAdapter == null) {
            imageSearchAdapter = new ImageSearchAdapter(MainActivity.this, masterList);
            rv_img.setLayoutManager(new GridLayoutManager(this,2));
            rv_img.setAdapter(imageSearchAdapter);
            rv_img.setItemAnimator(new DefaultItemAnimator());
            rv_img.setNestedScrollingEnabled(true);
        } else {
            imageSearchAdapter.notifyDataSetChanged();
        }


        if(getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            rv_img.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else{
            rv_img.setLayoutManager(new GridLayoutManager(this, 4));
        }

        rv_img.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrolling = true;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem = recyclerView.getLayoutManager().getChildCount();
                totalItems = recyclerView.getLayoutManager().getItemCount();
                scrollItems = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if(isScrolling && (currentItem + scrollItems == totalItems)){
                    pageNumber = pageNumber+1;

                    if (pageNumber<4){
                        proBar.setVisibility(View.VISIBLE);
                        mMainActivityViewModel.getMasterData(pageNumber,searchValue).observe(MainActivity.this, new Observer<List<MasterData>>() {
                            @Override
                            public void onChanged(@Nullable List<MasterData> data) {
                                proBar.setVisibility(View.GONE);
                                if (data!=null)
                                    masterList.addAll(data);
                                else
                                    Toast.makeText(MainActivity.this,"Search Failed. Try Again",Toast.LENGTH_SHORT).show();

                                imageSearchAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }

            }
        });

    }


}

