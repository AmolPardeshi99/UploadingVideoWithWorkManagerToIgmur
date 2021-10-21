package com.example.uploadingvideowithworkmanager

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.work.*
import androidx.work.Data
import com.example.uploadingvideowithworkmanager.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var videoPath:String
    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var mediaController:MediaController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        mediaController = MediaController(this)
        mediaController.setAnchorView(activityMainBinding.videoView)

        activityMainBinding.btnSelectFromGallery.setOnClickListener {
            if (permissionGivenOrNot()) openGalary()
            else ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }

        activityMainBinding.btnUpload.setOnClickListener {
            if (videoPath!=null){
                uploadVideo(videoPath)
            }
        }

    }

    private fun uploadVideo(file: String) {
//        val progressDialog = ProgressDialog(this)
//        progressDialog.setMessage("Uploading the Video")
//        progressDialog.show()

        val constraints:Constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val oneTimeRequest = OneTimeWorkRequestBuilder<VideoWorker>().setConstraints(constraints)
        val data = Data.Builder()
        data.putString("filePath",videoPath)
        oneTimeRequest.setInputData(data.build())
        WorkManager.getInstance(this).beginWith(oneTimeRequest.build()).enqueue()

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeRequest.build().id).observe(this,{
            it?.let {
                CoroutineScope(Dispatchers.Main).launch {
                    activityMainBinding.tvWorkStatus.text = "Work Status- ${it.state.name}"
                }
            }
        })
    }

    private fun openGalary() {
        val intent = Intent(Intent.ACTION_PICK,MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        launchGallery.launch(intent)
    }


    private val launchGallery = registerForActivityResult(
        StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.data != null) {
            val selectedVideo: Uri? = result.data?.data
            activityMainBinding.videoView.setMediaController(mediaController)
            activityMainBinding.videoView.setVideoURI(selectedVideo)
            selectedVideo?.let { getVideoPathFromUri(it) }
        }
    }


    private fun getVideoPathFromUri(selectedVideo: Uri) {
        val filePath = arrayOf(MediaStore.Images.Media.DATA)
        val c = contentResolver.query(
            selectedVideo, filePath,
            null, null, null
        )
        c!!.moveToFirst()
        val columnIndex = c.getColumnIndex(filePath[0])
        videoPath = c.getString(columnIndex)
    }

    private fun permissionGivenOrNot(): Boolean {
        return ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==1){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
                openGalary()
            else
                Toast.makeText(this, "Permission Denied! Please allow to upload the Video", Toast.LENGTH_SHORT).show()
        }
    }
}