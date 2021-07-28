package com.example.founddog.firestore

import android.app.Activity
import android.net.Uri
import android.util.Log
import com.example.founddog.model.PostDTO
import com.example.founddog.utils.Constants
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.text.SimpleDateFormat
import java.util.*

class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun getCurrentUid() : String {
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUid = ""
        if(currentUser != null){
            currentUid = currentUser.uid
        }

        return currentUid
    }

    fun postContent(type: Int, postDTO: PostDTO, photoUri: Uri) {
        //todo
    }
}