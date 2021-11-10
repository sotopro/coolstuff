package com.danielsoto.coolstuff;

import  androidx.fragment.app.Fragment;

/*
* esto es un host típico para una actividad que se pueda mostrar con un fragmento.
* los eventos de navegación
* */
public  interface NavigationHost {
    /*
    * disparador de navegación de un fragmento especifico y adicionalmente puede que ir hacia atras
    *  puedo hacer que este stack sea reversible
    * */
    void navigateTo(Fragment fragment, boolean addToBackstack);
}