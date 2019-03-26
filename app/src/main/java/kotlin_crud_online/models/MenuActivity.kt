package kotlin_crud_online.models

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.erg.geryakbar.kotlin_crud_online.R
import kotlin_crud_online.fragment.fragment_category
import kotlin_crud_online.fragment.fragment_forum
import kotlin_crud_online.fragment.fragment_profile
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_menu)
        loadFragment(fragment = fragment_forum())
        navigation.setOnNavigationItemSelectedListener(this)
    }

    private fun loadFragment( fragment: Fragment): Boolean{
        if(fragment != null){
            supportFragmentManager.beginTransaction().replace(R.id.fl_container, fragment).commit()
            return true
        }
        return false
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        var fragment : Fragment = fragment_forum()
        when(p0.itemId){
            R.id.navigation_home -> fragment = fragment_forum()
            R.id.navigation_category -> fragment = fragment_category()
            R.id.navigation_profile -> fragment = fragment_profile()
        }
        return loadFragment(fragment)
    }
}
