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
import com.pangeranmw.expertvalorant.R
import com.pangeranmw.expertvalorant.di.FavoriteModuleDepedencies
import com.pangeranmw.expertvalorant.favorite.databinding.FragmentFavoriteBinding
import com.pangeranmw.expertvalorant.home.MainActivity
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : Fragment() {
    @Inject
    lateinit var factory: ViewModelFactory

    private val favoriteViewModel: FavoriteViewModel by activityViewModels {
        factory
    }

    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
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
        val mainActivity = requireActivity() as MainActivity
        mainActivity.setNavViewListener(onNavigate={navCon,itemId->
            if(itemId == R.id.nav_home){
                navCon.navigate(com.pangeranmw.expertvalorant.favorite.R.id.action_nav_favorites_to_nav_home)
            }
        }, onChecked = {navView->
            navView.setCheckedItem(com.pangeranmw.expertvalorant.favorite.R.id.nav_favorites)
            navView.setCheckedItem(R.id.navigation_favorite_graph)
        })

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