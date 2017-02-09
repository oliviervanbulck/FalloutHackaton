package com.oliviervanbulck.fallouthackaton.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.oliviervanbulck.fallouthackaton.R;
import com.oliviervanbulck.fallouthackaton.application.FalloutApplication;
import com.oliviervanbulck.fallouthackaton.model.Login;
import com.oliviervanbulck.fallouthackaton.service.FalloutService;

import java.io.UnsupportedEncodingException;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;
import retrofit.Response;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.email) EditText email;
    @Bind(R.id.password) EditText password;
    @Bind(R.id.email_sign_in_button) Button sign_in;
    @Bind(R.id.email_register_button) Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(R.string.action_sign_in);
        ButterKnife.bind(this);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sEmail = email.getText().toString();
                String sPass = password.getText().toString();
                String auth = sEmail + ":" + sPass;
                byte[] byteArray = new byte[0];
                try {
                    byteArray = auth.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                auth = Base64.encodeToString(byteArray, Base64.URL_SAFE | Base64.NO_WRAP);
                Call<Login> call = ((FalloutApplication) getApplication()).getService().login("Basic " + auth);
                //Call<User> call = apiService.getUser(username);
                call.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Response<Login> response, Retrofit retrofit) {
                        int statusCode = response.code();
                        if(statusCode == 201) {
                            Login gegevens = response.body();
                            ((FalloutApplication)getApplication()).setSession(gegevens.getSession());
                            ((FalloutApplication)getApplication()).setCoins(gegevens.getMyCoins());
                            Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Context context = getApplicationContext();
                            CharSequence text = "Er ging iets fout tijdens het inloggen...";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        // Log error here since request failed
                        System.out.println("Error: " + t.getMessage());
                    }
                });

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
