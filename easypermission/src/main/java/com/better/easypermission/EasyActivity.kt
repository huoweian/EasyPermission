package com.better.easypermission

import android.content.Intent
import androidx.fragment.app.FragmentActivity

/**
 * Description:
 * Created by huoweian on 2022/8/1.
 */
object EasyActivity {
    fun startActivityForResult(activity: FragmentActivity, intent: Intent, cb: ActivityCallback){
        val fragmentManager = activity.supportFragmentManager
        val existedFragment = fragmentManager.findFragmentByTag(EasyPermisson.TAG)
        val fragment = if(existedFragment == null){
            val invisibleFragment = InvisibleFragment()
            //Calls {@link #add(int, Fragment, String)} with a 0 containerViewId.
            fragmentManager.beginTransaction().add(invisibleFragment, EasyPermisson.TAG).commitNow()
            invisibleFragment
        }else{
            existedFragment as InvisibleFragment
        }

        fragment.startActivityForResult(intent, cb)
    }
}