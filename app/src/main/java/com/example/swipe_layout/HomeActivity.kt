package com.example.swipe_layout

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import com.example.project_contact.Adapters.UserAdapter
import com.example.project_contact.DataBase.AppDataBase
import com.example.project_contact.DataBase.UserData
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_add_users.*

class HomeActivity : AppCompatActivity() {

    private lateinit var appDataBase: AppDataBase
    private lateinit var userList: ArrayList<UserData>
    private lateinit var userAdapters: UserAdapter

    companion object {
        var editBool: Boolean = false
        var id: Int = 0
        var names: String = ""
        var phones: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        appDataBase = AppDataBase.getInstance(this)


        loadList()
        loadAdapter()


        btnAddUsers.setOnClickListener {
            coustumDialog()
        }

    }

    private fun loadList() {
        userList = ArrayList()
        val alluser = appDataBase.userDao().getAllUser()

        alluser.forEach {
            userList.add(
                UserData(
                    id = it.id,
                    user_name = it.user_name,
                    user_Phone_number = it.user_Phone_number,
                    firstAt = it.firstAt

                )
            )
        }
        if (userList.isEmpty()) {
            empty_icon.visibility = View.VISIBLE
        } else {
            empty_icon.visibility = View.GONE
        }


    }

    private fun loadAdapter() {
        userAdapters = UserAdapter(userList, { userData, position ->

            appDataBase.userDao().deletUser(userData)

            userList.removeAt(position)
            userAdapters.notifyItemRemoved(position)
            userAdapters.notifyItemRangeChanged(position, userList.size - position)

            if (userList.isEmpty()) {
                empty_icon.visibility = View.VISIBLE
            } else {
                empty_icon.visibility = View.GONE
            }

        }, { userData, position ->
            editBool = true
            id = userData.id

            names = userData.user_name
            phones = userData.user_Phone_number

            coustumDialog()

        }, { userData, position ->
            val intent = Intent(Intent.ACTION_DIAL)
            intent.setData(Uri.parse("tel: ${userData.user_Phone_number}"))
            startActivity(intent)
        }, { userData, position ->
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.setData(Uri.parse("smsto: ${userData.user_Phone_number}"))
            startActivity(intent)
        })
        reyclerVIew.adapter = userAdapters
    }

    private fun coustumDialog() {
        val dialog = Dialog(this)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.item_add_users)

        dialog.btnCancelDialog.setOnClickListener {
            editBool = false
            dialog.Conosle_name.text = "Add User"
            dialog.dismiss()
        }

        if (editBool) {
            dialog.editName.setText(names)
            dialog.editPhone.setText(phones)
            dialog.Conosle_name.text = "Edit User"
        }

        dialog.btnSaveUser.setOnClickListener {
            val user_name = dialog.editName.text
            val user_phone = dialog.editPhone.text



            if (editBool) {
                if (dialog.editName.text.isNotEmpty() && dialog.editPhone.text.isNotEmpty()) {
                    val firstAt = user_name.substring(0, 1)
                    val obj = UserData(
                        id = id,
                        user_name.toString(),
                        user_phone.toString(),
                        firstAt.toString()
                    )
                    appDataBase.userDao().editUser(obj)

                    dialog.editName.text.clear()
                    dialog.editPhone.text.clear()

                    loadList()
                    loadAdapter()
                } else {
                    Toast.makeText(
                        this@HomeActivity,
                        "Iltmos name va phone to`ldiring",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                editBool = false
                dialog.Conosle_name.text = "Add User"
            } else {
                if (dialog.editName.text.isNotEmpty() && dialog.editPhone.text.isNotEmpty()) {
                    val firstAt = user_name.substring(0, 1)
                    val obj = UserData(
                        0, user_name.toString(),
                        user_phone.toString(),
                        firstAt
                    )
                    appDataBase.userDao().addUser(obj)


                    dialog.editName.text.clear()
                    dialog.editPhone.text.clear()

                    loadList()
                    loadAdapter()
                } else {
                    Toast.makeText(
                        this@HomeActivity,
                        "Iltmos name va phone to`ldiring",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
            dialog.dismiss()
        }

        dialog.setCancelable(false)
        dialog.show()

    }
}