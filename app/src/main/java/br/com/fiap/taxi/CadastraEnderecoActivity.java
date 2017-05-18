package br.com.fiap.taxi;

import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;
import java.util.HashSet;

import br.com.fiap.taxi.taxi.R;
import br.com.fiap.taxi.util.ArquivoDB;

public class CadastraEnderecoActivity extends AppCompatActivity {

    private EditText edtCidade, edtBairro, edtEndereco, edtComplemento;
    private RadioGroup rgTipo;
    private HashMap<String, String> map;
    private ArquivoDB arquivoDB;
    private final String ARQUIVO = "cadastro";

    //lista de chaves
    private String[] chaves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_endereco);

        initViews();
        initArquivoDB();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarEndereco();
    }

    private void initArquivoDB() {
        map = new HashMap<>();
        arquivoDB = new ArquivoDB();

        //lista de chaves
        chaves = getResources().getStringArray(R.array.chaves);
    }

    private void initViews() {
        edtCidade = (EditText) findViewById(R.id.edtCidade);
        edtBairro = (EditText) findViewById(R.id.edtBairro);
        edtEndereco = (EditText) findViewById(R.id.edtEndereco);
        edtComplemento = (EditText) findViewById(R.id.edtComplemento);
        rgTipo = (RadioGroup) findViewById(R.id.rgTipo);
    }

    private boolean capturarDados() {
        if (TextUtils.isEmpty(edtCidade.getText().toString())
                && TextUtils.isEmpty(edtBairro.getText().toString())
                && TextUtils.isEmpty(edtEndereco.getText().toString())
                && TextUtils.isEmpty(edtComplemento.getText().toString())
                && rgTipo.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, R.string.cadastre_tudo, Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            map.put(chaves[0], edtCidade.getText().toString());
            map.put(chaves[1], edtBairro.getText().toString());
            map.put(chaves[2], edtEndereco.getText().toString());
            map.put(chaves[3], edtComplemento.getText().toString());

            RadioButton rb = (RadioButton) findViewById(rgTipo.getCheckedRadioButtonId());
            map.put(chaves[4], rb.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void cadastrarEndereco(View v) {
        if (capturarDados()) {
            try {
                arquivoDB.gravarChaves(getApplicationContext(), ARQUIVO, map);
                Toast.makeText(this, R.string.sucesso, Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void carregarEndereco() {
        try {

            HashSet<Boolean> set = new HashSet<>();
            for (String chave : chaves) {
                if (arquivoDB.verificarChave(getApplication(), ARQUIVO, chave)) {
                    set.add(true);
                } else {
                    set.add(false);
                }
            }

           if (set.size() == 1 && set.contains(true)) {
                for (String chave : chaves) {
                    map.put(chave, arquivoDB.retornarValor(getApplicationContext(), ARQUIVO, chave));
                }

                edtCidade.setText(map.get(chaves[0]));
                edtBairro.setText(map.get(chaves[1]));
                edtEndereco.setText(map.get(chaves[2]));
                edtComplemento.setText(map.get(chaves[3]));
                for (int i = 0; i < rgTipo.getChildCount(); i++) {
                    if (rgTipo.getChildAt(i) instanceof RadioButton) {
                        RadioButton rb = (RadioButton) rgTipo.getChildAt(i);
                        if (rb.getText().toString().equals(map.get(chaves[4]))) {
                            rb.setChecked(true);
                        }
                    }
                }
                Toast.makeText(this, R.string.carregou, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
