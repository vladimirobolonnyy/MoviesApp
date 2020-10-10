package ru.padawans.moviesapp

import android.content.Intent
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
import ru.padawans.moviesapp.api.model.AuthenticationResponse
import ru.padawans.moviesapp.api.model.LoginResponse
import ru.padawans.moviesapp.api.model.TokenResponse
import ru.padawans.moviesapp.api.model.User
import ru.padawans.moviesapp.ui.MainActivity


class LoginActivity : AppCompatActivity() {

    private var values :String = ""
    private var flag :Boolean = false
    private var name :String = ""
    public lateinit var sessionManagerUserToken: SessionManager_UserToken
    public lateinit var sessionManagerSessionId: SessionManager_SessionID
    public lateinit var USER_TOKEN: String
    public lateinit var SESSION_ID: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.editTextTextPersonName)
        val pass = findViewById<EditText>(R.id.editTextTextPassword)

        getToken()
        
        sessionManagerUserToken = SessionManager_UserToken(this)
        sessionManagerSessionId = SessionManager_SessionID(this)

        name = username.text.toString()

        val button_guest_session = findViewById<Button>(R.id.guest_button)
        button_guest_session?.setOnClickListener()
        {
            getSession()
            Toast.makeText(this,USER_TOKEN + "   " +sessionManagerSessionId.fetchSessionID().toString(), Toast.LENGTH_LONG).show()

        }

        val button_user_signIN = findViewById<Button>(R.id.sign_up)
        button_user_signIN?.setOnClickListener()
        {
            //Toast.makeText(this, values, Toast.LENGTH_LONG).show()
            signIN(username.text.toString(),pass.text.toString(),USER_TOKEN)
            }
    }


    private fun getSession()
    {
        val retIn =
            RetrofitHTTPconnection.getRetrofitInstance().create(UserGuestInterface::class.java)

        retIn.getSession(BuildConfig.API_KEY)
            .enqueue(object : Callback<AuthenticationResponse> {

                override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                    Log.v("SESSION", "Session Failed");
                }

                override fun onResponse(
                    call: Call<AuthenticationResponse>,
                    response: Response<AuthenticationResponse>
                ) {
                    if (response.isSuccessful) {
                        val v= response.body()?.session_id.toString()
                        sessionManagerSessionId.saveAuthSessionId(v)
                        Toast.makeText(this@LoginActivity,USER_TOKEN + "   " +sessionManagerSessionId.fetchSessionID().toString(), Toast.LENGTH_LONG).show()
                        Log.v("SESSION ID", v);
                        val intent = Intent(this@LoginActivity,MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Please enter your username and password",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.v("WRONG RESPONSE", response.code().toString());
                        Log.v("WRONG RESPONSE", response.body().toString());
                        Log.v("API", BuildConfig.API_KEY.toString());
                    }
                }
            })
    }


    private fun signIN(username: String, password: String, request_token: String) {

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
                        val intent = Intent(this@LoginActivity,MainActivity::class.java)
                        startActivity(intent)
                        flag=true
                        Log.v("RESPONSE REG", "Registration Passed");
                        Log.v("RESPONSE REG", response.body().toString());
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Please enter your username and password",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.v("WRONG RESPONSE", response.code().toString());
                        Log.v("WRONG RESPONSE", response.body().toString());
                        Log.v("API", BuildConfig.API_KEY.toString());
                    }
                }
            })
    }

    public fun getToken()
    {

        val retIn =
            RetrofitHTTPconnection.getRetrofitInstance().create(UserLoginInterface::class.java)

        retIn.getResponse(BuildConfig.API_KEY).enqueue(object :  retrofit2.Callback<TokenResponse> {

            override fun onResponse(
                call: retrofit2.Call<TokenResponse>,
                response: Response<TokenResponse>
            ) {
                if (response.code() == 200) {
                    values = response.body()?.convert()?.request_token.toString()
                    sessionManagerUserToken.saveAuthToken(values)
                    //Log.v("RESPONSE TOKEN", values);
                    USER_TOKEN = sessionManagerUserToken.fetchAuthToken().toString()

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


















