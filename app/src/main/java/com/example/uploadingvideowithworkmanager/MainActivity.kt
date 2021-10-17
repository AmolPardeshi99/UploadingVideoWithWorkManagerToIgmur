package com.example.uploadingvideowithworkmanager

import android.Manifest
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.uploadingvideowithworkmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var videoPath:String
    lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        buttonClickListner()
        val oneTimeRequest:OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<VideoWorker>()
                .build()
        WorkManager.getInstance(this).enqueue(oneTimeRequest)

        activityMainBinding.btnSelectFromGallery.setOnClickListener {
            if (permissionGivenOrNot()) openGalary()
            else ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }

        activityMainBinding.btnUpload.setOnClickListener { uploadVideo() }


    }

    private fun uploadVideo() {
        val progressDialog = ProgressDialog(this);
        progressDialog.setMessage("Uploading the Video")
        progressDialog.show()


    }

    private fun openGalary() {
        TODO("Not yet implemented")
    }

    private fun permissionGivenOrNot(): Boolean {
        TODO("Not yet implemented")
    }

    private fun buttonClickListner() {


    }
}