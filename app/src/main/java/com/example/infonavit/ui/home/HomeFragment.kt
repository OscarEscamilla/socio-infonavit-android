package com.example.infonavit.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infonavit.data.models.benevits.*
import com.example.infonavit.data.models.wallets.WalletResponse
import com.example.infonavit.data.network.RemoteDataSource
import com.example.infonavit.repository.RepositoryImpl
import com.example.infonavit.ui.VMFactory

// Enter your package name here
import com.example.infonavit.databinding.FragmentHomeBinding
import com.example.infonavit.ui.adapters.WalletsAdapter
import com.example.infonavit.utils.UserPreferences
import com.example.infonavit.vo.Resource

class HomeFragment : Fragment() {

    private val  viewModel by viewModels<HomeViewModel> { VMFactory(RepositoryImpl(RemoteDataSource())) }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    private var responseWallet: WalletResponse? = null
    private var responseBenevits: BenevitsResponse? = null




    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root


        initData()
        setupObservers()

        Log.e("RESPONSES", responseBenevits.toString())
        Log.e("RESPONSES", responseWallet.toString())

        return view

    }



    fun initData(){
        val userPreferences = UserPreferences(requireContext())
        viewModel.getBenevits(userPreferences.getToken())
    }

    private fun setupObservers() {
        viewModel.walletResponse.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    responseWallet = result.data
                    // second observer after get responseWallet
                    viewModel.benevitsResponse.observe(viewLifecycleOwner, Observer { result ->
                        when (result) {
                            is Resource.Loading -> {
                            }
                            is Resource.Success -> {

                                responseBenevits = result.data
                                processData()

                            }
                            is Resource.Failure -> {
                                Toast.makeText(requireContext(), "Fallo al cargar wallets", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })

                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Fallo al cargar wallets", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }



    fun processData(){
        Log.e("BENEVITS_OBSERVER", responseBenevits.toString())
        Log.e("WALLET_OBSERVER", responseWallet.toString())

        var benevitsLocked: List<Locked>? = responseBenevits?.locked
        var benevitsUnlocked: List<Unlocked>? = responseBenevits?.unlocked


        var benevitItems = arrayListOf<BenevitItem>()

        for (e in benevitsLocked!!){
            benevitItems.add(BenevitItem(
                e.active,
                e.ally,
                e.description,
                e.expiration_date,
                e.has_coupons,
                e.id,
                e.instructions,
                e.name,
                e.primary_color,
                e.slug,
                e.territories,
                e.title,
                e.vector_file_name,
                e.vector_full_path,
                e.wallet,
                "locked"
            ))
        }

        for (e in benevitsUnlocked!!){
            benevitItems.add(BenevitItem(
                e.active,
                e.ally,
                e.description,
                e.expiration_date,
                e.has_coupons,
                e.id,
                e.instructions,
                e.name,
                e.primary_color,
                e.slug,
                e.territories,
                e.title,
                e.vector_file_name,
                e.vector_full_path,
                e.wallet,
                "unlocked"
            ))
        }

        binding.recyclerWallets.visibility = View.VISIBLE
        binding.skeleton.root.visibility = View.GONE
        binding.recyclerWallets.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerWallets.adapter = WalletsAdapter(requireContext(), responseWallet!!, benevitItems)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}