package com.raj.task.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.raj.task.R
import com.raj.task.data.model.CallLogObject
import com.raj.task.databinding.ActivityMainBinding
import com.raj.task.ui.adapter.CallLogAdapter
import com.raj.task.ui.viewmodel.MainActivityViewModel
import com.raj.task.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels()

    private var permissionsRequired = arrayOf(
        Manifest.permission.READ_CALL_LOG
    )
    private val callPermissionCheckVar = 123
    private lateinit var runTimePermissionSettingResultLauncher: ActivityResultLauncher<Intent>

    @Inject
    lateinit var callLogAdapter: CallLogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addDataToAdapter(ArrayList())
        binding.recyclerViewCallLog.adapter = callLogAdapter
        requestRuntimePermission()

        runTimePermissionSettingResultLauncher =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            )
            {
                if (checkPermissions())
                    viewModel.fetchCallLogFromDB(this)
            }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.readCallLog().collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.materialProgressBar.visibility = View.GONE
                        it.data?.let { callLogArrayList ->
                            Log.e("size", callLogArrayList.size.toString())
                            addDataToAdapter(callLogArrayList)
                        }
                    }
                    Status.LOADING -> {
                        if (it.data != null)
                            binding.materialProgressBar.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        binding.materialProgressBar.visibility = View.GONE
                        Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.INTERNET -> {

                    }
                }
            }
        }
    }

    private fun addDataToAdapter(callLogObjectArrayList: ArrayList<CallLogObject>) {
        callLogAdapter.addData(callLogObjectArrayList, this)
        callLogAdapter.notifyDataSetChanged()
    }

    private fun requestRuntimePermission() {
        ActivityCompat.requestPermissions(
            this,
            permissionsRequired,
            callPermissionCheckVar
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        @NonNull permissions: Array<String>,
        @NonNull grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == callPermissionCheckVar) {
            if (checkPermissions()) {
                viewModel.fetchCallLogFromDB(this)
            } else {
                val alertDialog = AlertDialog.Builder(this).create()
                alertDialog.setIcon(R.mipmap.ic_launcher)
                alertDialog.setTitle("Alert!!!")
                alertDialog.setMessage("Call permission is required to read your call history. Enable it to read call logs\n\nThank you")
                alertDialog.setButton(
                    AlertDialog.BUTTON_POSITIVE, resources.getString(R.string.proceed)
                ) { _, _ ->
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    runTimePermissionSettingResultLauncher.launch(intent)
                }
                alertDialog.setButton(
                    AlertDialog.BUTTON_NEGATIVE, resources.getString(R.string.close)
                ) { _, _ ->
                    finish()
                }
                alertDialog.show()
            }
        }
    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CALL_LOG
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }
}