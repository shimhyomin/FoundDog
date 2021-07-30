package com.example.founddog.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.founddog.R
import com.example.founddog.model.PostDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_lost.*
import kotlinx.android.synthetic.main.item_posting_recyclerview.*
import kotlinx.android.synthetic.main.item_posting_recyclerview.view.*

class LostFragment : Fragment() {
    companion object {
        val TAG: String = "LostFragment"
    }

    var firestore: FirebaseFirestore? = null
    var uid: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firestore = FirebaseFirestore.getInstance()
        uid = FirebaseAuth.getInstance().currentUser?.uid

        Log.d(TAG, "before recyclerview")

        recyclerview.adapter = RecyclerViewAdapter()
        recyclerview.layoutManager = LinearLayoutManager(activity)

        Log.d(TAG, "after recyclerview")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_lost, container, false)


//        return inflater.inflate(R.layout.fragment_lost, container, false)
        return view
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var postDTO: ArrayList<PostDTO> = arrayListOf()

        init {
            Log.d(TAG, "RecyclerViewAdapter init")
            firestore?.collection("PostLost")?.orderBy("timestamp", Query.Direction.DESCENDING)
                ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    postDTO.clear()

                    if(querySnapshot == null) return@addSnapshotListener

                    for(snapshot in querySnapshot.documents){
                        var item = snapshot.toObject(PostDTO::class.java)
                        postDTO.add(item!!)
                    }
                    notifyDataSetChanged()
                }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.item_posting_recyclerview, parent, false)

            Log.d(TAG, "onCreateViewHolder")

            return ViewHolder(view)
        }

        inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view)

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewholder = (holder as ViewHolder).itemView

            var img: ImageView = viewholder.findViewById(R.id.img_recycle)

            Log.d(TAG, "onBindViewHolder")
            Log.d(TAG, "imgUrl : " + postDTO[position].imgUrl)
            Log.d(TAG, "img_recyclerview : " + img_recycle)
            Log.d(TAG, "img : " + img)

            Glide.with(holder.itemView.context).load(postDTO[position].imgUrl).into(img)
            viewholder.text_recycle.text = postDTO[position].title

            holder.itemView.setOnClickListener {
                startActivity(Intent(context, DetailActivity::class.java))
            }
        }

        override fun getItemCount(): Int = postDTO.size
    }

}