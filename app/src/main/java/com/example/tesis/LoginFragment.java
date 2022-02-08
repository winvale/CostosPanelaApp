package com.example.tesis;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment implements View.OnClickListener {


    EditText correo,pass;
    Button isbutton;
    float v=0;
    daoUsuario dao;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        dao = new daoUsuario(getActivity());

        correo = root.findViewById(R.id.is_correo);
        pass = root.findViewById(R.id.is_pass);
        isbutton = root.findViewById(R.id.inicioSesion);
        isbutton.setOnClickListener(this);

        correo.setTranslationX(800);
        pass.setTranslationX(800);
        isbutton.setTranslationX(800);

        correo.setAlpha(v);
        pass.setAlpha(v);
        isbutton.setAlpha(v);

        correo.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(200).start();
        pass.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        isbutton.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(400).start();

        return root;
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.inicioSesion:
                String c = correo.getText().toString();
                String p = pass.getText().toString();
                if (c.equals("") && p.equals("")){
                    Toast.makeText(getActivity(),"ERROR: Campos vacios",Toast.LENGTH_SHORT).show();
                }else if(dao.login(c,p) == 1){
                    Usuario us = dao.getUsuario(c,p);


                    Toast.makeText(getActivity(),"Inicio exitoso",Toast.LENGTH_SHORT).show();
                    Intent form = new Intent(getActivity(), MainActivity.class);
                    form.putExtra("idU", us.getId());

                    startActivity(form);
                }else {
                    Toast.makeText(getActivity(),"Usuario y/o contraseña incorrectos",Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

}