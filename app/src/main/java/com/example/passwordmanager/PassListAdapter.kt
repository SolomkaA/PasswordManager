package com.example.passwordmanager

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanager.dataB.Tables

class PassListAdapter(var passList: List<Tables.Passwords>) :
    RecyclerView.Adapter<PassListAdapter.PassViewHolder>() {


    class PassViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){


        var passVal: String = ""
        var id: Int? = null

        val nameOfPass : TextView = itemView.findViewById(R.id.passNameView)
        val textView = itemView.findViewById<TextView?>(R.id.passNameView).setOnClickListener {

            val intent = Intent( itemView.context, EditPasswordActivity :: class.java)
            itemView.context.startActivity(intent)
            intent.putExtra("id", id)


        }
        val copyButton = itemView.findViewById<ImageButton?>(R.id.copyButton).setOnClickListener {

            val clipboard = ContextCompat.getSystemService(it.context, ClipboardManager::class.java)
            clipboard?.setPrimaryClip(ClipData.newPlainText("", passVal))

            Toast.makeText(it.context, "${nameOfPass.text} Copied", Toast.LENGTH_SHORT).show()
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.passwords_list, parent, false)
        return PassViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PassViewHolder, position: Int) {

        val currentItem = passList[position]
        holder.nameOfPass.text = currentItem.passName
        holder.passVal = currentItem.passValue.toString()
        holder.id = currentItem.id

    }

    override fun getItemCount(): Int = passList.size

}