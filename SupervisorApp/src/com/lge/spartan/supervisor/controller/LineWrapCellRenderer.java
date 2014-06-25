package com.lge.spartan.supervisor.controller;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

public class LineWrapCellRenderer extends JTextArea implements TableCellRenderer {

    @Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
    	this.setText((String)value);
        this.setWrapStyleWord(true);            
        this.setLineWrap(true);

        return this;
	}	
}
