package com.andre.otakufav_anime.ui.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.andre.otakufav_anime.viewModel.MainViewModel
import com.andre.otakufav_anime.utils.Utils
import com.andre.otakufav_anime.databinding.FragmentExploreCharakterBinding

class ExploreCharakterFragment : Fragment() {

    private lateinit var binding: FragmentExploreCharakterBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreCharakterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSpinner()

        binding.ivHeartIconCharakter.setOnClickListener {
            viewModel.updateIsLikedCharacter()
        }

        viewModel.currentCharacter.observe(viewLifecycleOwner) { character ->
            try {
                val newCharacter = character
                val newUrl = Utils.extractImageUrl(newCharacter.image)
                binding.tvTitle.text = newCharacter.name
                binding.ivCharakter.load(newUrl)

                binding.ivInfo.setOnClickListener {
                    val action =
                        ExploreCharakterFragmentDirections.actionExploreCharakterFragmentToDetailExploreCharakterFragment()
                    findNavController().navigate(action)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setUpSpinner() {
        val filterData = mutableListOf("Charakter", "Anime")

        binding.spinnerExploreCharkter.adapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            filterData
        )

        binding.spinnerExploreCharkter.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        1 -> {
                            val action =
                                ExploreCharakterFragmentDirections.actionExploreCharakterFragmentToExploreAnimeFragment()
                            findNavController().navigate(action)
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }
}