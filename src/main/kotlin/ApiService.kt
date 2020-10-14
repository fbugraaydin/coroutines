package com.ashgem.coroutines

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/users")
    fun getUserListWithCallBack(): Call<JsonObject>

    @GET("api/users")
    suspend fun getUserListWithCoroutine(): JsonObject
}