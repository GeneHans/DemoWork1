package com.example.demowork1.anim

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.demowork1.R
import com.google.android.material.tabs.TabLayout


class TestAnimActivity : AppCompatActivity() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager1: ViewPager
    private var listFragment = ArrayList<Fragment>()
    private var listTitle = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_anim)
        tabLayout = findViewById(R.id.anim_tab_layout1)

        viewPager1 = findViewById(R.id.anim_vp1)
        listFragment.add(SimpleAnimFragment.newInstance())
        listTitle.add("简单动画")
        listFragment.add(PropertyAnimFragment.newInstance())
        listTitle.add("属性动画")
        listFragment.add(SimpleAnimFragment.newInstance())
        listTitle.add("简单动画2")
        listFragment.add(PropertyAnimFragment.newInstance())
        listTitle.add("属性动画2")
        listFragment.add(SimpleAnimFragment.newInstance())
        listTitle.add("简单动画3")
        listFragment.add(PropertyAnimFragment.newInstance())
        listTitle.add("属性动画3")
        listFragment.add(SimpleAnimFragment.newInstance())
        listTitle.add("简单动画4")
        listFragment.add(PropertyAnimFragment.newInstance())
        listTitle.add("属性动画4")
        listFragment.add(SimpleAnimFragment.newInstance())
        listTitle.add("简单动画5")
        listFragment.add(PropertyAnimFragment.newInstance())
        listTitle.add("属性动画5")
        var adapter = MyAnimAdapter(supportFragmentManager, 0, listFragment, listTitle)
        viewPager1.adapter = adapter
        tabLayout.setupWithViewPager(viewPager1)
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        var count = tabLayout.tabCount
        var i = 0
        while (i < count) {
            tabLayout.getTabAt(i)?.customView = getTabTitle(i)
            i++
        }
    }

    private fun getTabTitle(position: Int): View? {
        var view = LayoutInflater.from(this).inflate(R.layout.anim_tab_layout, null)
        var tv: TextView = view.findViewById(R.id.tv_anim_tab)
        tv.text = listTitle[position]
        return view
    }

    class MyAnimAdapter(
        fm: FragmentManager,
        behavior: Int,
        var listData: List<Fragment>,
        var listTitle: List<String>
    ) :
        FragmentPagerAdapter(fm, behavior) {

        override fun getItem(position: Int): Fragment {
            return listData[position]
        }

        override fun getCount(): Int {
            return listData.size
        }

//        override fun getPageTitle(position: Int): CharSequence? {
//            return if (listTitle.size > position) {
//                listTitle[position]
//            } else {
//                super.getPageTitle(position)
//            }
//        }
    }
}