package com.example.android_test_practice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.android_test_practice.databinding.FragmentMainBinding


class MainFragment : Fragment(R.layout.fragment_main) {

    private var binding: FragmentMainBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind(view)
        binding?.textView?.text = "Hello Binding"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.unbind()
    }

}