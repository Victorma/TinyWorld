package icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.gui;

import icaro.infraestructura.entidadesBasicas.comunicacion.EventoSimple;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

public abstract class PanelTrazasAbstracto extends javax.swing.JFrame {

    public PanelTrazasAbstracto() {
        initComponents();
    }

    public void cierraVentana() {
        this.setVisible(false);
    }

    public void muestraInfoTraza(InfoTraza traza) {
    }

    public void muestraMensajeEnviado(MensajeSimple traza) {

    }

    public void muestraEventoEnviado(EventoSimple evento) {

    }

    public void muestraMensajeRecibido(MensajeSimple traza) {

    }

    public void muestraEventoRecibido(EventoSimple evento) {

    }

    public void muestraTrazaEjecucionReglas(String infoAtrazar) {

    }

    public void muestraTrazaActivacionReglas(String infoAtrazar) {

    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
