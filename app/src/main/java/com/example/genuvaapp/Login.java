package com.example.genuvaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity implements View.OnClickListener {
    TextView signUp, forgtPassword;
    EditText email, password;
    Button signIn, signInGoogle, signInWithPhoneNumber;
    ProgressBar progressBar;

    EditText varificationCode, userNumber;
    Button sendVarificationCode, checkNumber;
    private String verificationID;


    public static String userID;

    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 1;

    private FirebaseAuth mAuth;

    private String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();

        signInGoogle = findViewById(R.id.login_google);
        progressBar=findViewById(R.id.progressBar);

        userNumber = findViewById(R.id.userNumberLogin);
        sendVarificationCode = findViewById(R.id.sendVerificationCode);
        varificationCode = findViewById(R.id.verificationCode);
        checkNumber = findViewById(R.id.checkVerificationCode);
        signInWithPhoneNumber = findViewById(R.id.login_number);

        signInWithPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager manager = (ConnectivityManager) getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    userNumber.setVisibility(View.VISIBLE);
                    sendVarificationCode.setVisibility(View.VISIBLE);

                    varificationCode.setVisibility(View.VISIBLE);
                    checkNumber.setVisibility(View.VISIBLE);

                    YoYo.with(Techniques.Shake)
                            .duration(300)
                            .repeat(1)
                            .playOn(findViewById(R.id.verificationCode));
                    YoYo.with(Techniques.Shake)
                            .duration(300)
                            .repeat(1)
                            .playOn(findViewById(R.id.checkVerificationCode));

                    YoYo.with(Techniques.Shake)
                            .duration(300)
                            .repeat(1)
                            .playOn(findViewById(R.id.userNumberLogin));
                    YoYo.with(Techniques.Shake)
                            .duration(300)
                            .repeat(1)
                            .playOn(findViewById(R.id.sendVerificationCode));


                    sendVarificationCode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SendVerificationCode();
                        }
                    });

                    checkNumber.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            String code = varificationCode.getText().toString().trim();
                            if (code.isEmpty() || code.length() < 6) {
                                varificationCode.setError("Invalid Verification code");
                                varificationCode.requestFocus();
                                return;
                            }

                            VerifyCode(code);
                        }
                    });
                }
                else{
                    Toast.makeText(Login.this, "Please Check your network connection", Toast.LENGTH_SHORT).show();
                }
            }

        });

        forgtPassword = findViewById(R.id.forgetPassword);
        forgtPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent restPassword = new Intent(Login.this, ForgetPassword.class);
                startActivity(restPassword);
            }
        });
        email = findViewById(R.id.email_loginPage);
        password = findViewById(R.id.password_loginPage);
        signIn = findViewById(R.id.login_btn_loginPage);
        signIn.setOnClickListener(this);
        signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(this);
        signInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager manager = (ConnectivityManager) getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
                if(activeNetwork!=null) {

                    signIn();
                }
                else{
                    Toast.makeText(Login.this, "Please Check your network Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void VerifyCode(String code) {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationID, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent login=new Intent(Login.this, Home_page.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Email", "phone");
                    login.putExtras(bundle);
                    login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(login);
                    finish();
                }
                else{
                    Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void SendVerificationCode() {
        progressBar.setVisibility(View.VISIBLE);
        String number = userNumber.getText().toString().trim();
        if (number.isEmpty() || number.length() < 11) {
            userNumber.setError("Please enter a valid number!");
            userNumber.requestFocus();
            return;
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallback
        );

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code=phoneAuthCredential.getSmsCode();
                    if(code!=null){
                        varificationCode.setText(code);
                        VerifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    verificationID = s;
                }
            };


    public void userLogin() {


        ConnectivityManager manager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        if (activeNetwork != null) {


            final String Email = email.getText().toString().trim();
            String Password = password.getText().toString().trim();
            if (TextUtils.isEmpty(Email)) {
                Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(Password)) {
                Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(Email, Password)

                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                userID = task.getResult().getUser().getUid();
                                Intent home = new Intent(Login.this, Home_page.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("Email", Email);
                                home.putExtras(bundle);
                                startActivity(home);
                                finish();
                            } else {
                                Toast.makeText(Login.this, "Check your Email or Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login_btn_loginPage) {
            userLogin();
        }
        if (view.getId() == R.id.signUp) {
            Intent register = new Intent(this, Register.class);
            startActivity(register);
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);


            updateUI(account);
        } catch (ApiException e) {

            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(Object o) {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            Intent login = new Intent(Login.this, Home_page.class);
            Bundle bundle = new Bundle();
            bundle.putString("Email", "@gmail.com");
            login.putExtras(bundle);
            startActivity(login);
            finish();
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
//            Intent login=new Intent(Login.this, Home_page.class);
//            Bundle bundle=new Bundle();
//            if(email.getText().toString().equals("kamal@yahoo.com")){
//
//                bundle.putString("Email", "kamal@yahoo.com");
//            }
//            else{
//                bundle.putString("Email", "@yahoo.com");
//            }
//            startActivity(login);
//            finish();
//        }
//    }
}
