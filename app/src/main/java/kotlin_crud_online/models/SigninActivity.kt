package kotlin_crud_online.models

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.erg.geryakbar.kotlin_crud_online.R
import kotlin_crud_online.api.Signin_Request
import kotlin_crud_online.utils.Session
import kotlinx.android.synthetic.main.activity_signin.*
import org.json.JSONException
import org.json.JSONObject

class SigninActivity : AppCompatActivity(),View.OnClickListener {

    lateinit var session : Session
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_signin)
        session = Session(applicationContext)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        progressDialog = ProgressDialog(this)
        btn_Signup.setOnClickListener(this)
        btn_Signin.setOnClickListener(this)
    }

    fun signin(){
        var username : String = et_Username.text.toString().trim()
        if(TextUtils.isEmpty(username)){
            et_Username.setError("Username cannot be empty!")
            return
        }

        var password : String = et_Password.text.toString().trim()
        if(TextUtils.isEmpty(password)){
            et_Password.setError("Password cannot be empty!")
            return
        }

        progressDialog.setTitle("Signing in, please wait...")
        progressDialog.show()

        val response = Response.Listener<String> { response ->
            try {
                val jsonObject = JSONObject(response)
                val success = jsonObject.getBoolean("success")
                if (success) {
                    progressDialog.dismiss()
                    //Mengambil variabel dari Databases untuk dijadikan Session
                    session.createLoginSession(username)
                    val intent = Intent(applicationContext, MenuActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, jsonObject.getString("data"), Toast.LENGTH_SHORT).show()
                }
            } catch (a: JSONException) {
                a.printStackTrace()
            }
        }
        //Konek ke Databases
        val login_request = Signin_Request(username, password, response)
        val queue = Volley.newRequestQueue(applicationContext)
        queue.add(login_request)

    }

    override fun onClick(v: View?) {
        if( v == btn_Signup){
            val intent = Intent(applicationContext, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }else if(v == btn_Signin){
            signin()
        }
    }
}
