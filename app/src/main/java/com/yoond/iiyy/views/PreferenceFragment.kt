package com.yoond.iiyy.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceFragmentCompat
import com.yoond.iiyy.MainActivity
import com.yoond.iiyy.R

class PreferenceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_preference, container, false)
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.layout_pref, Preference())
            ?.commit()
        return root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarTitle(resources.getString(R.string.title_preference))
    }
    // TODO: fragment transaction 시 fade-in이 없어짐
}