package com.better.easypermission

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Description:
 * Created by huoweian on 2022/8/1.
 */

typealias ActivityCallback = ((data:Intent) -> Unit)
typealias PermissionCallback = (Boolean, List<String>) -> Unit
class InvisibleFragment : Fragment() {

    private var mCallback: ((Boolean, List<String>) -> Unit)? = null

    fun request(cb: PermissionCallback,vararg permissons: String){
        mCallback = cb
        requestPermissions(permissons,1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1){
            var deniedPermissions = ArrayList<String>()
            for((index,value) in grantResults.withIndex()){
                if(value == PackageManager.PERMISSION_DENIED){
                    deniedPermissions.add(permissions[index])
                }
            }
            mCallback?.let {
                it(deniedPermissions.isEmpty(),deniedPermissions)
            }
        }
    }


    private var mActivityCallback: ActivityCallback? = null
    fun startActivityForResult(intent: Intent,cb: ActivityCallback){
        mActivityCallback = cb
        startActivityForResult(intent,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && resultCode == RESULT_OK){
            mActivityCallback?.let {
                if(data != null){
                    it(data)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

