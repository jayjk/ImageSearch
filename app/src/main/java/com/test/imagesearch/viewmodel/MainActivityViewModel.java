package com.test.imagesearch.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.test.imagesearch.models.MasterData;
import com.test.imagesearch.repositories.data.DataRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<MasterData>> mMasterData;

    private MutableLiveData<List<MasterData>> mSearchData;

    private DataRepository mRepo;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();

    public void init(Context applicationContext){
        if(mMasterData != null){
            return;
        }
        mRepo = DataRepository.getInstance(applicationContext);
        mMasterData = new MutableLiveData<>();
    }


    public LiveData<List<MasterData>> getData(){

        return mMasterData;
    }



    public LiveData<List<MasterData>> getMasterData(int pageNumber, String searchText){

        mMasterData = mRepo.getMasterDatas(pageNumber,searchText);

        return mMasterData;
    }


    public LiveData<List<MasterData>> getSingdleData(String searchText){

        mSearchData = mRepo.getSingleData(searchText);

        return mSearchData;
    }
}