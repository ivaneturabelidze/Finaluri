package com.example.finaaluri

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Notes(
    val title: String = "",
    val status: String = ""
)

class notesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var rcNotes: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        rcNotes = findViewById(R.id.notesRCView)

        val store = Firebase.firestore
        val notesCollectionQuery = store.collection("test")
        val options = FirestoreRecyclerOptions.Builder<Notes>().setLifecycleOwner(this).setQuery(notesCollectionQuery, Notes::class.java).build()
        val adapter = object: FirestoreRecyclerAdapter<Notes, notesViewHolder>(options){
            override fun onBindViewHolder(holder: notesViewHolder, position: Int, model: Notes) {
                val txtViewTitle = holder.itemView.findViewById<TextView>(android.R.id.text1)
                val txtViewStatus = holder.itemView.findViewById<TextView>(android.R.id.text2)

                txtViewTitle.text = model.title
                txtViewStatus.text = model.status
                holder.itemView.setOnClickListener {

                }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notesViewHolder {
                val view = LayoutInflater.from(this@MainActivity).inflate(android.R.layout.simple_list_item_2, parent, false)
                return notesViewHolder(view)
            }
        }
        rcNotes.adapter = adapter
        rcNotes.layoutManager = LinearLayoutManager(this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       // return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logOutButton){
            auth.signOut()
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}