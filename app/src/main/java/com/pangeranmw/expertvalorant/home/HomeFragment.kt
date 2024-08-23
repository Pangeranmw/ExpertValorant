package com.pangeranmw.expertvalorant.home

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity.INPUT_METHOD_SERVICE
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pangeranmw.core.data.Resource
import com.pangeranmw.core.ui.AgentAdapter
import com.pangeranmw.expertvalorant.R
import com.pangeranmw.expertvalorant.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()
    private val agentAdapter = AgentAdapter { agent ->
        val action = HomeFragmentDirections.actionMainFragmentToDetailFragment(agent)
        findNavController().navigate(action)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        binding.rvAgent.adapter = null
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = requireActivity() as MainActivity
        mainActivity.setNavViewListener()

        binding.rvAgent.apply {
            layoutManager = LinearLayoutManager(requireActivity(),
                LinearLayoutManager.HORIZONTAL, false)

            adapter = agentAdapter
        }
        mainViewModel.agent.observe(viewLifecycleOwner) { agent ->
            Log.d("TAG", "initSearch: $agent")
            if (agent != null) {
                binding.progressBar.isVisible = agent is Resource.Loading
                initError(agent is Resource.Error, agent.message ?: getString(R.string.error))
                if (agent is Resource.Success) agentAdapter.submitList(agent.data)
            }
        }
        initKeyboard()
        initSearch()
    }

    private fun clearFocusFromEditText() {
        binding.etSearch.clearFocus()
        val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }

    private fun initError(state: Boolean, message: String? = null){
        binding.error.root.isVisible = state
        binding.error.tvError.text = message
    }

    private fun initSearch(){
        val crossIcon = AppCompatResources.getDrawable(requireContext(),R.drawable.cross_valo)
        val lineIcon = AppCompatResources.getDrawable(requireContext(),R.drawable.line_valo)
        binding.tilSearch.setEndIconOnClickListener {
            if(binding.tilSearch.endIconDrawable == crossIcon) {
                binding.etSearch.setText(String())
                clearFocusFromEditText()
            }
        }
        binding.etSearch.doAfterTextChanged {s->
            lifecycleScope.launch {
                binding.tilSearch.endIconDrawable = if(s.toString().isNotBlank()) crossIcon else lineIcon
                mainViewModel.queryAgent.value = s.toString()
            }
        }

        mainViewModel.searchResult.observe(viewLifecycleOwner) { agent ->
            lifecycleScope.launch {
                agentAdapter.submitList(agent.first())
                initError(agent.first().isEmpty(), getString(R.string.empty))
            }
        }

    }

    private fun initKeyboard(){
        binding.svAgent.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            private var initialHeight = binding.svAgent.height
            override fun onGlobalLayout() {
                val r = Rect()
                if(_binding!=null){
                    binding.svAgent.getWindowVisibleDisplayFrame(r)
                    val screenHeight = binding.svAgent.height
                    if (initialHeight == screenHeight) {
                        clearFocusFromEditText()
                    }
                    if(initialHeight == 0) initialHeight = screenHeight
                }
            }
        })
    }
}