package kotlin_crud_online.api

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import java.util.HashMap

class Profile_Request(username: String, listener: Response.Listener<String>) :
    StringRequest(Request.Method.POST, SIGNIN_REQUEST_URL, listener, null) {
    private val params: MutableMap<String, String>

    init {
        params = HashMap()
        params["username"] = username
    }

    public override fun getParams(): Map<String, String> {
        return params
    }

    companion object {
        private val SIGNIN_REQUEST_URL = "https://wtfforum.satmaxt.xyz/andro/profile.php"
    }
}