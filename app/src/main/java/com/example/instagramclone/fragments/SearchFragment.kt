package com.example.instagramclone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.instagramclone.adapters.GridPostAdapter
import com.example.instagramclone.data.model.Post
import com.example.instagramclone.databinding.FragmentSearchBinding
import com.google.firebase.firestore.FirebaseFirestore

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: GridPostAdapter
    private val postList = mutableListOf<Post>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        adapter = GridPostAdapter(postList)
        binding.recyclerSearch.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerSearch.adapter = adapter

        loadAllPosts()
        return binding.root
    }

    private fun loadAllPosts() {
        FirebaseFirestore.getInstance().collection("posts")
            .get()
            .addOnSuccessListener { documents ->
                postList.clear()
                val posts = documents.map { doc ->
                    Post(
                        imageUrl = doc.getString("imageUrl") ?: "",
                        userId = doc.getString("userId") ?: ""
                    )
                }

                adapter.notifyDataSetChanged()
            }
    }
}
