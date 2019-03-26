package kotlin_crud_online.models

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.erg.geryakbar.kotlin_crud_online.R
import kotlin_crud_online.api.Signup_Request
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class SignupActivity : AppCompatActivity(), View.OnClickListener {

    val URL : String ="https://wtfforum.satmaxt.xyz/andro/signup.php"
    lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_signup)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        btn_Signin.setOnClickListener(this)
        btn_Signup.setOnClickListener(this)
        progressDialog = ProgressDialog(this)
    }

    fun signUp(){
        var username : String = et_Username.text.toString().trim()
        if(TextUtils.isEmpty(username)){
            et_Username.setError("Please, insert an username!")
            return
        }

        var email : String = et_Email.text.toString().trim()
        if(TextUtils.isEmpty(email)){
            et_Email.setError("Please, insert an email1")
            return
        }

        var password : String = et_Password.text.toString().trim()
        if(TextUtils.isEmpty(password)){
            et_Password.setError("Please, insert a password!")
            return
        }

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        var joindate : String = sdf.format(Date())

        progressDialog.setTitle("Singing Up....")
        progressDialog.show()

        val responseListener = Response.Listener<String> { response ->
            try {
                val jsresponse = JSONObject(response)
                val success = jsresponse.getBoolean("success")
                if (success) {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext,"Signup Successfull! Please Login",Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, SigninActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)

                } else {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext,jsresponse.getString("data"),Toast.LENGTH_SHORT).show()

                }
            } catch (a: JSONException) {
                a.printStackTrace()
            }
        }
        //Konek ke Databases
        val signup_Request = Signup_Request(username, password, joindate,email, responseListener)
        val requestQueue = Volley.newRequestQueue(applicationContext)
        requestQueue.add(signup_Request)
    }

    override fun onClick(v: View?) {
        if(v == btn_Signin){
            val intent = Intent(applicationContext,SigninActivity::class.java )
            startActivity(intent)
            finish()
        }else if(v == btn_Signup){
            signUp()
        }
    }

}
