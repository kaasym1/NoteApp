package com.example.noteapp.fragments.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.noteapp.R
import com.example.noteapp.adapter.OnBoardViewPagerAdapter
import com.example.noteapp.databinding.FragmentOnBoardBinding

class OnBoardFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListener()
        setVisible()
    }

    private fun setVisible() = with(binding) {
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        tvSkip.isVisible = true
                        btnGetStarted.isVisible = false
                    }

                    1 -> {
                        tvSkip.isVisible = true
                        btnGetStarted.isVisible = false
                    }

                    2 -> {
                        tvSkip.isVisible = false
                        btnGetStarted.isVisible = true
                    }
                }
                super.onPageSelected(position)
            }
        })

    }

    private fun initialize() {
        binding.viewPager2.adapter = OnBoardViewPagerAdapter(this@OnBoardFragment)
        binding.ci.setViewPager(binding.viewPager2)
    }

    private fun setupListener() = with(binding) {
        tvSkip.setOnClickListener {
            if (viewPager2.currentItem < 3) {
                viewPager2.setCurrentItem(viewPager2.currentItem + 2, true)
            }
        }
        btnGetStarted.setOnClickListener {
            if (viewPager2.currentItem == 2) {
                findNavController().navigate(R.id.noteFragment)
            }
        }
    }
}