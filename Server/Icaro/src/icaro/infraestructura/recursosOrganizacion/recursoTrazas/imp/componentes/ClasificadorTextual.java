package icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;

public class ClasificadorTextual implements Serializable {

    private static final String rutaLogs = "./log/";
    private HashMap tablaEntidadesTrazables;
    private String nombreEntidad;
    private int versionArchivo;
    private String rutaFichero = "";
    private String nombreFicheroRegistroLogs;

    public ClasificadorTextual() {
        tablaEntidadesTrazables = new HashMap();
    }

    public void clasificaTraza(InfoTraza traza) {
        String mensajeAEscribir = traza.getNivel().toString() + " : " + traza.getEntidadEmisora() + " : " + traza.getMensaje();
        nombreEntidad = traza.getEntidadEmisora();
        if (!tablaEntidadesTrazables.containsKey(nombreEntidad)) {
            versionArchivo = getVersion(nombreEntidad);
            rutaFichero = getRuta(nombreEntidad);
            tablaEntidadesTrazables.put(nombreEntidad, rutaFichero);
        } else {
            rutaFichero = tablaEntidadesTrazables.get(nombreEntidad).toString();
        }
        escribeFichero(mensajeAEscribir, rutaFichero);
    }

    private String getRuta(String nombreAgente) {
        String rutaFichero = "";
        if (tablaEntidadesTrazables.containsKey(nombreEntidad)) {
            versionArchivo = getVersion(nombreAgente);
            rutaFichero = rutaLogs + nombreAgente + versionArchivo + ".log";
        } else {
            int versionArchivo = getVersion(nombreAgente);
            if (versionArchivo == -1) {
                rutaFichero = rutaLogs + nombreAgente + "0.log";
                actualizaRegistro(nombreAgente, 0);
            } else {
                versionArchivo++;
                rutaFichero = rutaLogs + nombreAgente + versionArchivo + ".log";
                actualizaRegistro(nombreAgente, versionArchivo);
            }
        }
        return rutaFichero;
    }

    private int getNumeroDigitos(int numero) {
        int numDigitos = 1;
        while (numero > 9) {
            numero = numero / 10;
            numDigitos++;
        }
        return numDigitos;
    }

    private void actualizaRegistro(String nombreAgente, int versionArchivo) {
        String rutaFichero = rutaLogs + nombreFicheroRegistroLogs;        // Sustituir por RandomAccessFile
        File archivo = new File(rutaFichero);
        boolean encontrado = false;
        try {
            if (!archivo.exists()) {
                PrintWriter writer = new PrintWriter(archivo);
            }

            BufferedReader bf = new BufferedReader(new FileReader(rutaFichero));
            String cadenaActual = "";
            String cadenaNueva = "";
            if (versionArchivo == 0) { //primera version del agente
                cadenaNueva += "0" + nombreAgente + "\n";
            } else {
                while (((cadenaActual = bf.readLine()) != null) && (!encontrado)) {
                    if (cadenaActual.contains(nombreAgente)) {
                        encontrado = true;
                        cadenaNueva += versionArchivo + cadenaActual.substring(getNumeroDigitos(versionArchivo - 1), cadenaActual.length()) + "\n";
                    }
                }
            }

            BufferedReader bf2 = new BufferedReader(new FileReader(rutaFichero));
            String contenidoFichero = cadenaNueva;

            while ((cadenaActual = bf2.readLine()) != null) {
                if (!cadenaActual.contains(nombreAgente)) {
                    contenidoFichero += cadenaActual + "\n";
                }
            }
            PrintWriter writer = new PrintWriter(archivo);
            writer.write(contenidoFichero);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getVersion(String nombreAgente) {
        String rutaFichero = rutaLogs + nombreFicheroRegistroLogs;
        File archivo = new File(rutaFichero);
        int version = -1;
        boolean encontrado = false;
        try {
            if (archivo.exists()) {
                BufferedReader bf = new BufferedReader(new FileReader(rutaFichero));
                String cadenaActual = "";
                String caracterActual = "";
                int numeroConstruido = 0;
                while (((cadenaActual = bf.readLine()) != null) && (!encontrado)) {
                    if (cadenaActual.contains(nombreAgente)) {
                        encontrado = true;
                        int indice = 0;
                        caracterActual = cadenaActual.substring(indice, indice + 1);
                        do {
                            numeroConstruido = numeroConstruido * 10 + (new Integer(caracterActual).intValue());
                            indice++;
                            caracterActual = cadenaActual.substring(indice, indice + 1);
                        } while (caracterActual.equals("0") || caracterActual.equals("1") || caracterActual.equals("2") || caracterActual.equals("3")
                                || caracterActual.equals("4") || caracterActual.equals("5") || caracterActual.equals("6") || caracterActual.equals("7")
                                || caracterActual.equals("8") || caracterActual.equals("9"));
                        version = numeroConstruido;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    private void escribeFichero(String mensaje, String rutaFichero) {
        File archivo = new File(rutaFichero);
        String contenidoAnterior = "";
        try {
            if (archivo.exists()) {
                BufferedReader bf = new BufferedReader(new FileReader(rutaFichero));
                String cadenaActual = "";
                while ((cadenaActual = bf.readLine()) != null) {
                    contenidoAnterior += cadenaActual;
                    contenidoAnterior += "\n";
                }
            }
            PrintWriter writer = new PrintWriter(archivo);
            writer.print(contenidoAnterior + mensaje);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
