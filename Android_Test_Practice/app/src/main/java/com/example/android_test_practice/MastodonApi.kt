package com.example.android_test_practice

import retrofit2.http.GET

interface MastodonApi {

    @GET("DevTides/DogsApi/master/dogs.json")
    suspend fun fetchPublicTimeline(
    ): List<DogBreed>

}