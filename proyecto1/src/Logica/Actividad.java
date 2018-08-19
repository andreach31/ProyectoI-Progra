/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.util.ArrayList;

public class Actividad {

    private String id;
    private int duracion;
    private int x;
    private int y;

    int inicioCercano;
    int inicioLejano;
    int terminoLejano;
    int terminoCercano;

    public ArrayList<Actividad> predecesor = new ArrayList<Actividad>();
    public ArrayList<Actividad> sucesor = new ArrayList<Actividad>();
   
    
    public Actividad() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ArrayList<Actividad> getSucesor() {
        return sucesor;
    }

    public ArrayList<Actividad> getPredecesor() {
		return predecesor;
	}
   
	public void setPredecesor(Actividad predecesor) {
		this.predecesor.add(predecesor);
	}

	public void setInicioCercano(int inicioCercano) {
        this.inicioCercano = inicioCercano;
    }

    public void setInicioLejano(int inicioLejano) {
        this.inicioLejano = inicioLejano;
    }

    public void setTerminoLejano(int terminoLejano) {
        this.terminoLejano = terminoLejano;
    }

    public void setTerminoCercano(int terminoCercano) {
        this.terminoCercano = terminoCercano;
    }

    public void setSucesor(Actividad elemento) {
        this.sucesor.add(elemento);
    }

    public int getInicioCercano() {
        return inicioCercano;
    }

    public int getInicioLejano() {
        return inicioLejano;
    }

    public int getTerminoLejano() {
        return terminoLejano;
    }

    public int getTerminoCercano() {
        return terminoCercano;
    }

    public String imprimeListaSucesores() {
        String x = " ";
        
        if (sucesor != null) {
            for (Actividad string : sucesor) {
                x = x + string.getId() + " ";
            }
        } else {
            x = "Sin sucesor";
        }
        return x;
    }
    
    
    public String imprimeListaPredecesores() {
        String x = " ";
        
        if (sucesor != null) {
            for (Actividad string : predecesor) {
                x = x + string.getId() + " ";
            }
        } else {
            x = "Sin Predecesor";
        }
        return x;
    }

    @Override
    public String toString() {
        return "Actividad [id=" + id + ", duracion=" + duracion + ", x=" + x + ", y=" + y + ", sucesor=" + imprimeListaSucesores()+ ", predecesor=" + imprimeListaPredecesores()
                + "]";
    }

}
