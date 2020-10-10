package ru.padawans.moviesapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.padawans.moviesapp.api.*
import ru.padawans.moviesapp.api.SessionManager.Companion.USER_TOKEN
import ru.padawans.moviesapp.api.repository.TMDB_create_session
import ru.padawans.moviesapp.api.repository.TokenResponse


class LoginActivity : AppCompatActivity() {

    public var values :String = ""
    public var token :String = ""
    public lateinit var sessionManager: SessionManager
    public lateinit var USER_TOKEN: String
    private lateinit var session :TMDB_create_session

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        getToken()
        //values = session.userApi.toString()
        //token = session.userApi.getResponse(BuildConfig.API_KEY).toString()


        val username = findViewById<EditText>(R.id.editTextTextPersonName)
        val pass = findViewById<EditText>(R.id.editTextTextPassword)


        val button_guest_session = findViewById<Button>(R.id.guest_button)
        button_guest_session?.setOnClickListener()
        {
            //Toast.makeText(this, values, Toast.LENGTH_LONG).show()
            Toast.makeText(this,USER_TOKEN, Toast.LENGTH_LONG).show()

        }

        val button_guest_signUP = findViewById<Button>(R.id.sign_up)
        button_guest_signUP?.setOnClickListener()
        {
            //Toast.makeText(this, values, Toast.LENGTH_LONG).show()
            Toast.makeText(this,username.text.toString()+" - " +USER_TOKEN+" - " +pass.text.toString(), Toast.LENGTH_LONG).show()
            signUp(username.text.toString(),pass.text.toString(),USER_TOKEN)}

    }

    private fun signUp(username: String, password: String, request_token: String) {
        getToken()
        val retIn =
            RetrofitHTTPconnection.getRetrofitInstance().create(UserLoginInterface::class.java)
        val signInInfo = User(username, password, request_token)

        retIn.registerUser(BuildConfig.API_KEY, signInInfo)
            .enqueue(object : Callback<LoginResponse> {

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.v("RESPONSE REG", "Registration Failed");
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.v("RESPONSE REG", "Registration Passed");
                        Log.v("RESPONSE REG", response.body().toString());
                    } else {
                        Log.v("WRONG RESPONSE", response.code().toString());
                        Log.v("WRONG RESPONSE", response.body().toString());
                        Log.v("API", BuildConfig.API_KEY.toString());
                    }
                }
            })
    }

    public fun getToken()
    {
        sessionManager = SessionManager(this)
        val retIn =
            RetrofitHTTPconnection.getRetrofitInstance().create(UserGuestInterface::class.java)

        retIn.getResponse(BuildConfig.API_KEY).enqueue(object :  retrofit2.Callback<TokenResponse> {

            override fun onResponse(
                call: retrofit2.Call<TokenResponse>,
                response: Response<TokenResponse>
            ) {
                if (response.code() == 200) {
                    values = response.body()?.convert()?.request_token.toString()
                    sessionManager.saveAuthToken(values)
                    Log.v("RESPONSE TOKEN", values);
                    USER_TOKEN = sessionManager.fetchAuthToken().toString()
                    Log.d("DDDDDDDDDDDDDDDDDD", USER_TOKEN);
                    Log.v("API", BuildConfig.API_KEY.toString());
                }
            }

            override fun onFailure(call: retrofit2.Call<TokenResponse>, t: Throwable) {
                Toast.makeText(
                    this@LoginActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }}
        )
    }
}


















