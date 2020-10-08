package ru.padawans.moviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import okhttp3.Call
import okhttp3.ResponseBody
import retrofit2.Response
import ru.padawans.moviesapp.api.LoginResponse
import ru.padawans.moviesapp.api.RetrofitHTTPconnection
import ru.padawans.moviesapp.api.User
import ru.padawans.moviesapp.api.UserLoginInterface
import javax.security.auth.callback.Callback


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }



//    private fun signin(username: String, password: String, request_token: String) {
//
//        val retIn =
//            RetrofitHTTPconnection.getRetrofitInstance().create(UserLoginInterface::class.java)
//        val signInInfo = User(username, password, request_token)
//
//        retIn.getToken(signInInfo).enqueue(object : retrofit2.Callback<LoginResponse> {
//            override fun onResponse(
//                call: retrofit2.Call<LoginResponse>,
//                response: Response<LoginResponse>
//            ) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//        })

    }












