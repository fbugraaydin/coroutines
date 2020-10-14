package com.ashgem.coroutines

import android.widget.TextView
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Retrofit {

    companion object{
        fun retrofit(retrofitTextView:TextView) {

            retrofitWithCallBack(retrofitTextView)
            retrofitWithCoroutine(retrofitTextView)

        }

        private fun retrofitWithCoroutine(textView:TextView) {
            GlobalScope.launch {
                val userListCoroutined = RetrofitClient.getClient()?.create(ApiService::class.java)?.getUserListWithCoroutine()
                withContext(Dispatchers.Main){
                    textView.text = "Value with Coroutine 1"
                }
                val userListCoroutined2 = RetrofitClient.getClient()?.create(ApiService::class.java)?.getUserListWithCoroutine()
                withContext(Dispatchers.Main){
                    textView.text = "Value with Coroutine 2"
                }
            }
        }

        private fun retrofitWithCallBack(textView:TextView) {
            val userListCallBacked = RetrofitClient.getClient()?.create(ApiService::class.java)
                ?.getUserListWithCallBack()

            userListCallBacked?.enqueue(object : Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                }

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    textView.text = "Value with call back 1"
                    userListCallBacked?.clone().enqueue(object: Callback<JsonObject> {
                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                        }

                        override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                            textView.text = "Value with call back 2"
                        }

                    })

                }

            })
        }
    }
}