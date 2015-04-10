package icaro.aplicaciones.recursos.interfazChatUsuario.imp.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class RendererTablaTexto extends JLabel implements TableCellRenderer {

	private static final long serialVersionUID = 1L;
	
	//Fuentes
	private Font fuenteTextoNormal = new Font("Lucida Sans Unicode",Font.PLAIN,11);
	
	//Colores
	private Color colorAmarillo = new Color(255, 255, 200);
	private Color colorNaranja = new Color(250, 240, 184);
	private Color colorGrisClaro = new Color(238, 238, 238);


	public RendererTablaTexto() {
		
		super();
		this.setOpaque(true);
		this.setFont(fuenteTextoNormal);
	}
	
	
	public Component getTableCellRendererComponent(JTable table, Object text, boolean isSelected,
			boolean hasFocus, int row, int column) {
		
		if (text instanceof String) this.setText((String)text);
		else if (text instanceof Integer) this.setText(String.valueOf(((Integer)text).intValue()));
		
		this.setSize(table.getColumnModel().getColumn(column).getWidth(),getPreferredSize().height);
		if (table.getRowHeight(row) != getPreferredSize().height)
			table.setRowHeight(row, getPreferredSize().height);
		
		if (isSelected) {
			this.setForeground(Color.WHITE);
			this.setBackground(Color.DARK_GRAY);
			this.setBorder(BorderFactory.createMatteBorder(2,2,2,2,colorGrisClaro));
		}
		else {
			if (row % 2 == 0) { //par
				this.setBackground(colorAmarillo);
				this.setBorder(BorderFactory.createMatteBorder(2,2,2,2,colorNaranja));
			}
			else { //impar
				this.setBackground(colorGrisClaro);
				this.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.WHITE));
			}
			this.setForeground(Color.BLACK);
		}
		
		return this;
	}
}
