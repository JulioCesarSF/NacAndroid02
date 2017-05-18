package br.com.fiap.taxi.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by logonrm on 18/05/2017.
 */

public final class ArquivoDB {

    private SharedPreferences sp;

    public void gravarChaves(Context context, String prefName, HashMap<String, String> map) {
        sp = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            editor.putString(entry.getKey(), entry.getValue());
        }
        editor.commit();
    }

    public String retornarValor(Context context, String prefName, String key) {
        sp = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public boolean verificarChave(Context context, String prefName, String key) {
        sp = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sp.contains(key);
    }


}
