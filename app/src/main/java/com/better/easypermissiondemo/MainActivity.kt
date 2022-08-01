package com.better.easypermissiondemo

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.better.easypermission.EasyActivity
import com.better.easypermission.EasyPermisson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        makeCallBtn.setOnClickListener {
            EasyPermisson.request(this, Manifest.permission.CALL_PHONE){ allGranted, deniedPermissions ->
                if(allGranted){
                    call()
                }else{
                    Toast.makeText(
                        this, "You denied $deniedPermissions",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        getPic.setOnClickListener {
            //打开相册
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"

            EasyPermisson.request(this,Manifest.permission.READ_EXTERNAL_STORAGE){granted,_ ->
                if(granted){
                    EasyActivity.startActivityForResult(this,intent){
                        Toast.makeText(
                            this, "data:${it.data}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }
    }

    private fun call() {
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:10086")
            startActivity(intent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }
}