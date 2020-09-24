package com.test.imagesearch.repositories.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.test.imagesearch.models.MasterData;
import com.test.imagesearch.models.Results;
import com.test.imagesearch.repositories.database.DatabaseClient;
import com.test.imagesearch.repositories.network.ApiInterface;
import com.test.imagesearch.repositories.network.ApiManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {

    private static DataRepository instance;
    private List<MasterData> dataSet = new ArrayList<>();
    private static Context context;

    private ApiInterface api;

    public static DataRepository getInstance(Context applicationContext){
        context = applicationContext;
        if(instance == null){
            instance = new DataRepository();
        }
        return instance;
    }

    public  DataRepository(){
        api = ApiManager.getApiInterface();
    }


    public MutableLiveData<List<MasterData>> getMasterDatas(int pageNumber, String searchText) {

        final MutableLiveData<List<MasterData>> data = new MutableLiveData<>();


        try {

            api.getData(String.valueOf(pageNumber),searchText)
                    .enqueue(new Callback<Results>() {

                                 @Override
                                 public void onResponse(Call<Results> call, Response<Results> response) {
                                     try {
                                         if (response.isSuccessful()) {

                                             if (pageNumber==1)
                                                dataSet.clear();


                                             dataSet.addAll(response.body().getResults());
                                              new insertData(response.body().getResults()).execute();
                                             data.setValue(dataSet);
                                         }
                                     } catch (Exception e) {
                                         e.printStackTrace();
                                         data.setValue(null);
                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<Results> call, Throwable t) {
                                     t.printStackTrace();
                                     data.setValue(null);
                                 }
                             }
                    );



        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }



    public MutableLiveData<List<MasterData>> getSingleData(String searchText) {

        final MutableLiveData<List<MasterData>> data = new MutableLiveData<>();


        try {
            data.setValue(new getSingleDataDB(searchText).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return data;
    }

    class getSingleDataDB extends AsyncTask<Void, Void, List<MasterData>> {

        String local_imgID;
        public getSingleDataDB(String imgID) {
            local_imgID = imgID;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<MasterData> doInBackground(Void... voids) {


            List<MasterData> taskList = DatabaseClient
                    .getInstance(context)
                    .getAppDatabase()
                    .dataDao()
                    .getSingleData(local_imgID);



            return taskList;
        }

        @Override
        protected void onPostExecute(List<MasterData> data) {
            super.onPostExecute(data);
        }
    }


    class insertData extends AsyncTask<Void, Void, List<MasterData>> {

        List<MasterData> response;
        public insertData(List<MasterData> response1) {
            response = response1;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<MasterData> doInBackground(Void... voids) {

            DatabaseClient.getInstance(context).getAppDatabase()
                    .dataDao()
                    .insert(response);

            return null;
        }

        @Override
        protected void onPostExecute(List<MasterData> aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}