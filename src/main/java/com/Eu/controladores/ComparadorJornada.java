package com.Eu.controladores;

import java.util.Comparator;

import com.Eu.model.Jornada;

public class ComparadorJornada implements Comparator<Jornada> {
    public int compare(Jornada j1,Jornada j2) {
        return j1.getInicioJornada().compareTo(j2.getInicioJornada());
    }
}
