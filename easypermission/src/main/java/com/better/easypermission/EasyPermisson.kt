package com.better.easypermission

import androidx.fragment.app.FragmentActivity

/**
 * Description:
 * Created by huoweian on 2022/8/1.
 */
object EasyPermisson {

    const val TAG: String ="InvisibleFragment"

    /**
     * TODO
     *
     * @param activity
     * @param permissions
     * @param cb  kotlin转换为java后，该回调对象如果是activity实现，那么该回调对象是作为activity的内部类对象，会持有Activity对象的引用
     */
    fun request(activity: FragmentActivity, vararg permissions: String, cb: PermissionCallback){
        val fragmentManager = activity.supportFragmentManager
        val existedFragment = fragmentManager.findFragmentByTag(TAG)
        val fragment = if(existedFragment == null){
            val invisibleFragment = InvisibleFragment()
            //Calls {@link #add(int, Fragment, String)} with a 0 containerViewId.
            fragmentManager.beginTransaction().add(invisibleFragment,TAG).commitNow()
            invisibleFragment
        }else{
            existedFragment as InvisibleFragment
        }

        fragment.request(cb, *permissions)
    }
}