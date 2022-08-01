package com.better.easypermission

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

