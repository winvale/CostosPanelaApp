package com.example.tesis;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SignupFragment extends Fragment  implements View.OnClickListener {


    EditText nombre,cedula,correo,pass;
    Button rgbutton;
    float v=0;
    daoUsuario daou;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signup, container, false);

        daou = new daoUsuario(getActivity());

        nombre = root.findViewById(R.id.rg_nombre);
        cedula = root.findViewById(R.id.rg_cedula);
        correo = root.findViewById(R.id.rg_correo);
        pass = root.findViewById(R.id.rg_password);
        rgbutton = root.findViewById(R.id.registrar);
        rgbutton.setOnClickListener(this);

        nombre.setTranslationX(800);
        cedula.setTranslationX(800);
        correo.setTranslationX(800);
        pass.setTranslationX(800);
        rgbutton.setTranslationX(800);

        nombre.setAlpha(v);
        cedula.setAlpha(v);
        rgbutton.setAlpha(v);
        correo.setAlpha(v);
        pass.setAlpha(v);

        nombre.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(200).start();
        cedula.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        rgbutton.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(200).start();
        pass.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        correo.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(200).start();


        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registrar:

                Usuario u= new Usuario();
                u.setNombre(nombre.getText().toString());
                u.setCc(cedula.getText().toString());
                u.setCorreo(correo.getText().toString());
                u.setPassword(pass.getText().toString());
                 if(!u.isNull()){
                     Toast.makeText(getActivity(),"ERROR: Campos vacios",Toast.LENGTH_LONG).show();
                 }else if(daou.insertarUsuario(u)){
                     Toast.makeText(getActivity(),"Datos registrados",Toast.LENGTH_SHORT).show();
                     nombre.setText("");
                     cedula.setText("");
                     correo.setText("");
                     pass.setText("");
                 }



                break;
        }
    }

}