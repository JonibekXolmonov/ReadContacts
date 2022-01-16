package com.example.readcontacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.readcontacts.R
import com.example.readcontacts.model.Contact

class ContactAdapter(private val contactList: ArrayList<Contact>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.contact_layout, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val contact = contactList[position]
        if (holder is ContactViewHolder) {
            holder.contactName.text = contact.contactName
            holder.phoneNumber.text = contact.phoneNumber
        }
    }

    override fun getItemCount(): Int {
        return contactList.size
    }
}

class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var contactName: TextView = view.findViewById(R.id.tv_contact_name)
    var phoneNumber: TextView = view.findViewById(R.id.tv_phone_number)
}