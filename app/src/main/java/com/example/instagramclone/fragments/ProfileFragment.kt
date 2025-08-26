import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclone.R
import com.example.instagramclone.adapters.GridPostAdapter
import com.example.instagramclone.data.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ProfileFragment : Fragment() {

    private lateinit var recyclerProfile: RecyclerView
    private lateinit var adapter: GridPostAdapter
    private val postList = mutableListOf<Post>()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        recyclerProfile = view.findViewById(R.id.recyclerProfilePosts)
        recyclerProfile.layoutManager = GridLayoutManager(requireContext(), 3)
        adapter = GridPostAdapter(postList)
        recyclerProfile.adapter = adapter

        fetchUserPosts()

        return view
    }

    private fun fetchUserPosts() {
        FirebaseFirestore.getInstance().collection("posts")
            .whereEqualTo("userId", userId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null) return@addSnapshotListener
                postList.clear()
                for (doc in snapshots!!) {
                    val post = doc.toObject(Post::class.java)
                    postList.add(post)
                }
                adapter.notifyDataSetChanged()
            }
    }
}
