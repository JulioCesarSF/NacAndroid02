package br.com.fiap.taxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.fiap.taxi.taxi.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chamarCadastro();
            }
        }, 2000);
    }

    private void chamarCadastro() {
        startActivity(new Intent(SplashActivity.this, CadastraEnderecoActivity.class));
        finish();
    }
}
