package com.pangeranmw.expertvalorant.detail

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.pangeranmw.core.domain.model.Agent
import com.pangeranmw.core.ui.AbilityAdapter
import com.pangeranmw.core.ui.SimillarAgentRoleAdapter
import com.pangeranmw.expertvalorant.R
import com.pangeranmw.expertvalorant.databinding.FragmentDetailBinding
import com.pangeranmw.expertvalorant.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailBinding
    private val detailViewModel: DetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val agent: Agent = args.agent
        val mainActivity = requireActivity() as MainActivity
        mainActivity.setNavViewListener()

        binding.llRole.setOnClickListener {
            createDialog(agent.role.displayName, agent.role.description)
        }
        binding.tvCodename.setOnClickListener {
            createDialog(agent.codeName, agent.description)
        }
        binding.tvAgentName.text = agent.name
        binding.tvRole.text = agent.role.displayName
        binding.tvCodename.text = agent.codeName
        Glide.with(requireContext()).load(agent.fullPortrait).into(binding.imgAgent)

        val colors = intArrayOf(
            Color.parseColor("#${agent.backgroundGradientColors[0]}"),
            Color.parseColor("#${agent.backgroundGradientColors[1]}"),
            Color.parseColor("#${agent.backgroundGradientColors[2]}"),
            Color.parseColor("#${agent.backgroundGradientColors[3]}")
        )
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.BOTTOM_TOP,
            colors
        )
        Glide.with(requireContext()).load(agent.role.displayIcon).into(binding.imgRole)
        Glide.with(requireContext()).load(agent.background).into(binding.imgBg)
        binding.imgBg.background = gradientDrawable

        val abilityAdapter = AbilityAdapter{ability->
            createDialog(ability.displayName, ability.description)
        }
        abilityAdapter.submitList(agent.abilities)
        binding.rvAbility.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = abilityAdapter
        }

        var isFavorite = agent.isFavorite
        detailViewModel.favorite.observe(viewLifecycleOwner){favAgent->
            isFavorite=favAgent.find { it.agentId == agent.agentId } != null
            setFavorite(isFavorite)
        }
        binding.btnFav.setOnClickListener {
            isFavorite = !isFavorite
            detailViewModel.setFavoriteAgent(agent, isFavorite)
        }
        val simillarAgentAdapter = SimillarAgentRoleAdapter{similarAgent->
            try {
                val action = DetailFragmentDirections.actionDetailFragmentSelf(similarAgent)
                findNavController().navigate(action)
            }catch (e: Exception){
                Toast.makeText(requireContext(), "Oops, Cannot open similar agent from favorite detail", Toast.LENGTH_SHORT).show()
            }
        }
        binding.rvSimilarRole.apply {
            layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
            adapter = simillarAgentAdapter
        }
        detailViewModel.agent(agent.role.displayName).observe(viewLifecycleOwner){ag->
            simillarAgentAdapter.submitList(ag.filter {it.agentId!=agent.agentId})
        }
    }
    private fun setFavorite(isFavorite: Boolean) {
        if (isFavorite) {
            binding.btnFav.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.baseline_favorite_24))
        }
        else {
            binding.btnFav.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.baseline_favorite_border_24))
        }
    }
    private fun createDialog(title: String, message: String){
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setNeutralButton("Close") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }
}