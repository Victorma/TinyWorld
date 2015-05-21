package icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.gui;

import icaro.infraestructura.entidadesBasicas.comunicacion.EventoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.awt.Color;
import java.awt.Font;

public class PanelTrazasRecurso extends PanelTrazasAbstracto {

    private String nombreComponente; //identificacin de la ventana

    public PanelTrazasRecurso(String nombre, String contenido) {
        this.initComponents();
        this.nombreComponente = nombre;
        this.setTitle(nombreComponente);
        this.areaTrazas.setText(contenido);
        this.setResizable(true);
    }

    public String getIdentificador() {
        return nombreComponente;
    }

    @Override
    public void muestraInfoTraza(InfoTraza traza) {
        String nivel;
        //Color c = new Color(0);
        if (traza.getNivel() == InfoTraza.NivelTraza.debug) {
            nivel = "DEBUG";
            //c = Color.BLUE;
        } else if (traza.getNivel() == InfoTraza.NivelTraza.info) {
            nivel = "INFO";
            //c = Color.GREEN;
        } else if (traza.getNivel() == InfoTraza.NivelTraza.error) {
            nivel = "ERROR";
            //c = Color.ORANGE;
        } else { //fatal
            nivel = "FATAL";
            //c = Color.RED;
        }
        Font f = new Font("Trebuchet", Font.PLAIN, 12);
        areaTrazas.setFont(f);
        areaTrazas.setForeground(Color.BLUE);
        //Concateno el nuevo mensaje con el que habia antes

        areaTrazas.append(nivel + " : " + traza.getMensaje() + "\n");
        //si escribo null,borra lo anterior
    }

    @Override
    public void muestraEventoEnviado(EventoSimple m) {
        Font f = new Font("Trebuchet", Font.PLAIN, 12);
        areaTrazaEventos.setFont(f);
        areaTrazaEventos.setForeground(Color.BLUE);
        //Concateno el nuevo mensaje con el que habia antes
        Object contenido = m.getContenido();
        if (contenido != null) {
            areaTrazaEventos.append("Emisor : " + m.getOrigen() + ". Tipo Contenido: "
                    + m.getContenido().getClass().getSimpleName() + "" + ". Contenido : "
                    + contenido.toString() + "\n");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        areaTrazas = new java.awt.TextArea();
        jPanel3 = new javax.swing.JPanel();
        areaTrazaEventos = new java.awt.TextArea();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(areaTrazas, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(areaTrazas, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Traza General", jPanel1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(areaTrazaEventos, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(areaTrazaEventos, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Eventos emitidos", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.TextArea areaTrazaEventos;
    private java.awt.TextArea areaTrazas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

}
