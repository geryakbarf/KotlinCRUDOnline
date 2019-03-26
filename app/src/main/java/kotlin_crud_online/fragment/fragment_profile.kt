package kotlin_crud_online.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.erg.geryakbar.kotlin_crud_online.R
import kotlin_crud_online.api.Profile_Request
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.json.JSONArray
import org.json.JSONException

class fragment_profile : Fragment(), View.OnClickListener{

    lateinit var session: kotlin_crud_online.utils.Session
    lateinit var username : String
    lateinit var progressDialog : ProgressDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(R.layout.fragment_profile, fl_container, false)

        session = kotlin_crud_online.utils.Session(view.context)
        var user :HashMap<String, String> = session.getUserDetails()
        username = user.get(session.KEY_USERNAME)!!
        progressDialog = ProgressDialog(context)
        getProfile()

        return view
    }

    fun getProfile(){
        progressDialog.setTitle("Loading...")
        progressDialog.show()
        val listener = Response.Listener<String> { response ->
            try {
                progressDialog.dismiss()
                val array = JSONArray(response)
                for (i in 0 until array.length()) {
                    val ob = array.getJSONObject(i)
                    val image = ob.getString("image")
                    val status = ob.getString("status")
                    txt_Status.setText(status)
                    txt_Username.setText(username)
                  Glide.with(context).load(image).into(img_Profile)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        val akun_request = Profile_Request(username, listener)
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(akun_request)
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}