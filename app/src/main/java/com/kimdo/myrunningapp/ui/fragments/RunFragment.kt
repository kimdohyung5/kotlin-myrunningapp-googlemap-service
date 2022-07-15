package com.kimdo.myrunningapp.ui.fragments

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kimdo.myrunningapp.R
import com.kimdo.myrunningapp.adapters.RunAdapter
import com.kimdo.myrunningapp.databinding.FragmentRunBinding
import com.kimdo.myrunningapp.other.Constants.REQUEST_CODE_LOCATION_PERMISSION
import com.kimdo.myrunningapp.other.SortType
import com.kimdo.myrunningapp.other.TrackingUtility
import com.kimdo.myrunningapp.ui.viewmodels.MainViewModel
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RunFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private var _binding: FragmentRunBinding? =null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private lateinit var runAdapter: RunAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRunBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermit()
        setupRecyclerView()

        when(viewModel.sortType) {
            SortType.DATE -> binding.spFilter.setSelection(0)
            SortType.RUNNING_TIME -> binding.spFilter.setSelection(1)
            SortType.DISTANCE -> binding.spFilter.setSelection(2)
            SortType.AVG_SPEED -> binding.spFilter.setSelection(3)
            SortType.CALORIES_BURNED -> binding.spFilter.setSelection(4)
        }


        viewModel.runs.observe(viewLifecycleOwner) {
            runAdapter.submitList(it)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }
    }

    private fun setupRecyclerView() = binding.rvRuns.apply {
        runAdapter = RunAdapter()
        adapter = runAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    // 현재 권한 은 각각 전부 해야 한다.
    private fun requestPermit() {
        if(TrackingUtility.hasLocationPermission(requireContext())) {
            return
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {

        } else {
            EasyPermissions.requestPermissions(this,
                "kimdo 3: You need to accept location permissions to use this app.",
                REQUEST_CODE_LOCATION_PERMISSION
                ,Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(requireContext()).build().show()
        } else {
            requestPermit()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


}