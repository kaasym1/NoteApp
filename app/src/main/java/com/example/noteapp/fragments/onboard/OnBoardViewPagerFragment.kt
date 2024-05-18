package com.example.noteapp.fragments.onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentOnBoardViewPagerBinding

class OnBoardViewPagerFragment : Fragment() {
    private lateinit var binding: FragmentOnBoardViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() = with(binding) {
        when (requireArguments().getInt(ARG_ONBOARD_POSITION)) {
            0 -> {
                ivBoard.setAnimation(R.raw.note1)
                tvTitle.text = "Очень удобный функционал"
            }

            1 -> {
                tvTitle.text = "Быстрый, качественный продукт"
                ivBoard.setAnimation(R.raw.note2)
            }

            2 -> {
                tvTitle.text = "Куча функций и интересных фишек"
                ivBoard.setAnimation(R.raw.note3)
            }
        }
    }

    companion object {
        const val ARG_ONBOARD_POSITION = "onBoard"
    }
}



