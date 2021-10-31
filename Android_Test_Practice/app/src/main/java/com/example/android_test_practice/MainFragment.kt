package com.example.android_test_practice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.android_test_practice.databinding.FragmentMainBinding
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        private val TAG = MainFragment::class.simpleName
        private const val API_BASE_URL = "https://raw.githubusercontent.com/"
    }

    private var binding: FragmentMainBinding? = null
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    private val api = retrofit.create(MastodonApi::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind(view)
        binding?.button?.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val dogList = api.fetchPublicTimeline()
                Log.d(TAG, dogList.toString())
                showDogList(dogList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.unbind()
    }

    private suspend fun showDogList(
        dogList: List<DogBreed>
    ) = withContext(Dispatchers.Main) {
        val binding = binding ?: return@withContext
        val dogName = dogList.map { it.dogBreed }
        binding.button.text = dogName.joinToString("\n")
    }

}