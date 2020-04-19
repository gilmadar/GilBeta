package com.example.gilbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.gilbeta.FBref.refAuth;
import static com.example.gilbeta.FBref.refUsers;

public class AuthenticationActivity extends AppCompatActivity {

    TextView tVtitle, tVregister;
    EditText eTname, eTphone, eTemail, eTpass;
    CheckBox cBstayconnect;
    Button btn;

    String name, phone, email, password, uid;
    User userdb;
    Boolean stayConnect, registered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        tVtitle=(TextView) findViewById(R.id.tVtitle);
        eTname=(EditText)findViewById(R.id.eTname);
        eTemail=(EditText)findViewById(R.id.eTemail);
        eTpass=(EditText)findViewById(R.id.eTpass);
        eTphone=(EditText)findViewById(R.id.eTphone);
        cBstayconnect=(CheckBox)findViewById(R.id.cBstayconnect);
        tVregister=(TextView) findViewById(R.id.tVregister);
        btn=(Button)findViewById(R.id.btn);

        stayConnect=false;
        registered=true;

        regoption();
    }

    protected void onStart() {
        super.onStart();
        SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
        Boolean isChecked=settings.getBoolean("stayConnect",false);
        Intent si = new Intent(AuthenticationActivity.this,choose.class);
        si.putExtra("newuser",false);
        if (refAuth.getCurrentUser()!=null && isChecked) {
            stayConnect=true;
            //startActivity(si);
        }


    }

    protected void onPause() {
        super.onPause();
        if (stayConnect) finish();
    }

    private void regoption() {
        SpannableString ss = new SpannableString("Don't have an account?  Register here!");
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                tVtitle.setText("Register");
                eTname.setVisibility(View.VISIBLE);
                eTphone.setVisibility(View.VISIBLE);
                btn.setText("Register");
                registered=false;
                logoption();
            }
        };
        ss.setSpan(span, 24, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tVregister.setText(ss);
        tVregister.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void logoption() {
        SpannableString ss = new SpannableString("Already have an account?  Login here!");
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                tVtitle.setText("Login");
                eTname.setVisibility(View.INVISIBLE);
                eTphone.setVisibility(View.INVISIBLE);
                btn.setText("Login");
                registered=true;
                regoption();
            }
        };
        ss.setSpan(span, 26, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tVregister.setText(ss);
        tVregister.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void logorreg(View view) {
        if (registered) {
            if(!TextUtils.isEmpty(eTemail.getText().toString()) && !TextUtils.isEmpty(eTpass.getText().toString())) {
                email = eTemail.getText().toString();
                password = eTpass.getText().toString();
                final ProgressDialog pd=ProgressDialog.show(this,"Login","Connecting...",true);
                refAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pd.dismiss();
                                if (task.isSuccessful()) {
                                    SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
                                    SharedPreferences.Editor editor=settings.edit();
                                    editor.putBoolean("stayConnect",cBstayconnect.isChecked());
                                    editor.commit();
                                    Log.d("MainActivity", "signinUserWithEmail:success");
                                    Toast.makeText(AuthenticationActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                                    //ניסיון
                                    Intent si = new Intent(AuthenticationActivity.this,choose.class);
                                    si.putExtra("newuser",false);
                                    startActivity(si);
                                } else {
                                    Log.d("MainActivity", "signinUserWithEmail:fail");
                                    Toast.makeText(AuthenticationActivity.this, "e-mail or password are wrong!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
            else{
                Toast.makeText(this, "You have to fill all the fields ", Toast.LENGTH_SHORT).show();
            }

        } else {
            if(!TextUtils.isEmpty(eTemail.getText().toString()) && !TextUtils.isEmpty(eTpass.getText().toString()) &&
                    !TextUtils.isEmpty(eTphone.getText().toString()) && !TextUtils.isEmpty(eTname.getText().toString())) {
                name = eTname.getText().toString();
                phone = eTphone.getText().toString();
                email = eTemail.getText().toString();
                password = eTpass.getText().toString();

                final ProgressDialog pd = ProgressDialog.show(this, "Register", "Registering...", true);
                refAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pd.dismiss();
                                if (task.isSuccessful()) {
                                    SharedPreferences settings = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putBoolean("stayConnect", cBstayconnect.isChecked());
                                    editor.commit();
                                    Log.d("MainActivity", "createUserWithEmail:success");
                                    FirebaseUser user = refAuth.getCurrentUser();
                                    uid = user.getUid();
                                    userdb = new User(name, email, phone, uid);
                                    refUsers.child(uid).setValue(userdb);
                                    Toast.makeText(AuthenticationActivity.this, "Successful registration", Toast.LENGTH_LONG).show();
                                    Intent si = new Intent(AuthenticationActivity.this, choose.class);
                                    si.putExtra("newuser", true);
                                    startActivity(si);
                                } else {
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                        Toast.makeText(AuthenticationActivity.this, "User with e-mail already exist!", Toast.LENGTH_LONG).show();
                                    else {
                                        Log.w("MainActivity", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(AuthenticationActivity.this, "User create failed.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
            }
            else {
                Toast.makeText(this, "You have to fill all the fields ", Toast.LENGTH_SHORT).show();

            }
        }
    }


}
