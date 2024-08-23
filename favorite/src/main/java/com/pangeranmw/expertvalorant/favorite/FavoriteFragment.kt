package com.pangeranmw.expertvalorant.favorite

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pangeranmw.core.ui.AgentAdapter
import com.pangeranmw.expertvalorant.di.FavoriteModuleDepedencies
import com.pangeranmw.expertvalorant.favorite.databinding.FragmentFavoriteBinding
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : Fragment() {
    @Inject
    lateinit var factory: ViewModelFactory

    private val favoriteViewModel: FavoriteViewModel by activityViewModels {
        factory
    }

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFavoriteComponent.builder()
            .context(context)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    context.applicationContext,
                    FavoriteModuleDepedencies::class.java
                )
            )
            .build()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val agentAdapter = AgentAdapter{agent->
            val action = FavoriteFragmentDirections.actionNavFavoritesToNavDetail(agent)
            findNavController().navigate(action)
        }
        binding.rvAgent.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = agentAdapter
        }

        favoriteViewModel.favorite.observe(viewLifecycleOwner) { favoriteData ->
            agentAdapter.submitList(favoriteData)
            binding.error.root.visibility = if (favoriteData.isNotEmpty()) View.GONE else View.VISIBLE
        }
    }
}