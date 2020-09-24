package com.test.imagesearch.repositories.network;



import com.test.imagesearch.models.Results;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    @Headers({"Authorization: Client-ID 137cda6b5008a7c"})
    @GET("search/{page}")
    Call<Results> getData( @Path("page") String page,@Query("q") String searchText);
}
