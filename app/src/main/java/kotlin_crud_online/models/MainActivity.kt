package kotlin_crud_online.models

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.erg.geryakbar.kotlin_crud_online.R
import kotlin_crud_online.utils.Session
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var session: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_main)
        btn_Signup.setOnClickListener(this)
        btn_Signin.setOnClickListener(this)
        session = Session(applicationContext)
        session.checkLogin()
    }

    override fun onClick(v: View?) {
        if(v == btn_Signup){
            val intent = Intent(applicationContext,SignupActivity::class.java)
            startActivity(intent)
        }else if ( v == btn_Signin){
            val  intent = Intent(applicationContext,SigninActivity::class.java)
            startActivity(intent)
        }
    }
}
