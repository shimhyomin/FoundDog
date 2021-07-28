package com.example.founddog

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.founddog.firestore.FirestoreClass
import com.example.founddog.model.PostDTO
import com.example.founddog.navigation.FoundFragment
import com.example.founddog.navigation.LostFragment
import com.example.founddog.utils.Constants
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_upload.*
import java.text.SimpleDateFormat
import java.util.*

class UploadActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        val TAG: String = "UploadActivity"
        const val PICK_IMAGE_FROM_ALBUM = 0
        const val REQUEST_PERMISSION = 1
    }

    var photoUri: Uri? = null
    var type: Int = Constants.CONTENT_TYPE_LOST

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        if (!checkPermission()) {
            requestPermission()
        }

        img_clear.setOnClickListener(this)
        img_content.setOnClickListener(this)
        btn_submit.setOnClickListener(this)

        radiogroup_found_lost.setOnCheckedChangeListener { group, checkedId ->
            Log.d(TAG, "radio button click")
            when (checkedId) {
                R.id.radio_lost -> type = Constants.CONTENT_TYPE_LOST
                R.id.radio_found -> type = Constants.CONTENT_TYPE_FOUND
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_clear -> {
                finish()
            }
            R.id.img_content -> {
                // 앨범
                var photoPickIntent = Intent(Intent.ACTION_PICK)
                photoPickIntent.type = "image/*"
                startActivityForResult(photoPickIntent, PICK_IMAGE_FROM_ALBUM)
            }
            R.id.btn_submit -> {
                if (validateUpload() && FirestoreClass().getCurrentUid() != "") {
                    contentUpload()
                }
            }
        }
    }

    private fun validateUpload(): Boolean {
        if (edt_title.text.toString() == "") {
            Toast.makeText(this, getString(R.string.error_fill_title), Toast.LENGTH_SHORT).show()
            return false
        } else if (photoUri == null) {
            Toast.makeText(this, getString(R.string.error_fill_image), Toast.LENGTH_SHORT).show()
            return false

        } else if (edt_content.text.toString() == "") {
            Toast.makeText(this, getString(R.string.error_fill_content), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun contentUpload() {
        var storage = FirebaseStorage.getInstance()
        var firestore = FirebaseFirestore.getInstance()
        var auth = FirebaseAuth.getInstance()
        // Make filename -> 중복 없게 만들기 위해 현재 시간
        var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imageFileName = "IMAGE_" + timestamp + "_png"

        var storageRef = storage?.reference?.child("images")?.child(imageFileName)

        // FileUpload
        // 1. Promise method
        storageRef.putFile(photoUri!!).continueWithTask { task: Task<UploadTask.TaskSnapshot> ->
            return@continueWithTask storageRef.downloadUrl
        }?.addOnSuccessListener { uri ->
            var postDTO = PostDTO()

            // Insert downloadUri of image
            postDTO.imgUrl = uri.toString()

            // Insert uid of user
            postDTO.uid = auth.currentUser?.uid

            // Insert title of content
            postDTO.title = edt_title.text.toString()

            // Insert content of content
            postDTO.content = edt_content.text.toString()

            // Insert timestamp
            postDTO.timestamp = System.currentTimeMillis()

            Log.d(TAG, "type : " + type)
            when (type) {
                Constants.CONTENT_TYPE_LOST -> {
                    firestore.collection("PostLost").document().set(postDTO)
                    setResult(Activity.RESULT_OK)
                    finish()
                }
                Constants.CONTENT_TYPE_FOUND -> {
                    firestore.collection("PostFound").document().set(postDTO)
                    setResult(Activity.RESULT_OK)
                    finish()
                }
                else -> {
                    Log.d(TAG, "fail to save content")
                }
            }
        }.addOnFailureListener { e ->
            Log.i(TAG, "error : " + e)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_FROM_ALBUM) {
            if (resultCode == Activity.RESULT_OK) {
                photoUri = data?.data
                img_content.setImageURI(photoUri)
            } else {
                finish()
            }
        }
    }

    // 카메라 권한 요청
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
            REQUEST_PERMISSION
        )
    }

    // 카메라 권한 체크
    private fun checkPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED)
    }

    // 권한요청 결과
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0] + "success")
        } else {
            Log.d(TAG, "fail")
        }
    }
}