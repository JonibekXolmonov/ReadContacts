package com.example.readcontacts.activity

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readcontacts.R
import com.example.readcontacts.adapter.ContactAdapter
import com.example.readcontacts.model.Contact

class MainActivity : AppCompatActivity() {

    private val contactList = ArrayList<Contact>()
    private val PERMISSIN_CODE = 11111
    lateinit var contactRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermissions()
    }

    private fun initViews() {
        contactRecyclerView = findViewById(R.id.rv_contacts)
        contactRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun refreshAdapter(contactList: java.util.ArrayList<Contact>) {
        val adapter = ContactAdapter(contactList)
        contactRecyclerView.adapter = adapter
    }


    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                Array(1) { Manifest.permission.READ_CONTACTS },
                PERMISSIN_CODE
            )
        } else {
            readContacts()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIN_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            readContacts()
    }

    private fun readContacts() {
        val contacts = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        while (contacts!!.moveToNext()) {
            val name =
                contacts.let { contacts.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) }
            val number =
                contacts.let { contacts.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)) }

            contactList.add(Contact(name, number))
        }

        initViews()
        refreshAdapter(contactList)
        contacts.close()
    }
}