import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclone.R
import com.example.instagramclone.adapters.PostAdapter
import com.example.instagramclone.data.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FeedFragment : Fragment() {

    private lateinit var recyclerFeed: RecyclerView
    private lateinit var adapter: PostAdapter
    private val postList = mutableListOf<Post>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feed, container, false)

        recyclerFeed = view.findViewById(R.id.recyclerFeed)
        recyclerFeed.layoutManager = LinearLayoutManager(requireContext())
        adapter = PostAdapter(postList)
        recyclerFeed.adapter = adapter

        fetchPosts()

        return view
    }

    private fun fetchPosts() {
        FirebaseFirestore.getInstance().collection("posts")
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
