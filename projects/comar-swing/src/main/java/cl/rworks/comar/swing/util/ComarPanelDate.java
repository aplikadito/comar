/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBoxCellRenderer;
import com.alee.laf.optionpane.WebOptionPane;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author aplik
 */
public class ComarPanelDate extends ComarPanel {

    private ComarComboBox comboDay;
    private ComarComboBox comboMonth;
    private ComarComboBox comboYear;

    public ComarPanelDate() {
        this("");
    }

    public ComarPanelDate(String title) {
        this(title, new FlowLayout(FlowLayout.CENTER));
    }

    public ComarPanelDate(String title, LayoutManager layout) {
        this.setLayout(layout);
        comboYear = new ComarComboBox();
        comboYear.addItem(-1);
        for (int i = 2000; i <= 2050; i++) {
            comboYear.addItem(i);
        }

        comboMonth = new ComarComboBox();
        comboMonth.addItem(-1);
        for (int i = 1; i <= 12; i++) {
            comboMonth.addItem(i);
        }

        comboDay = new ComarComboBox();
        comboDay.addItem(-1);
        for (int i = 1; i <= 31; i++) {
            comboDay.addItem(i);
        }

        comboYear.setRenderer(new WebComboBoxCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                int val = (Integer) value;
                String strVal = val == -1 ? "-" : Integer.toString(val);
                label.setText(strVal);
                return label;
            }
        });

        comboMonth.setRenderer(new WebComboBoxCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                int val = (Integer) value;

                String strVal = val == -1 ? "-" : LocalDate.of(2000, val, 1).format(DateTimeFormatter.ofPattern("MMMM"));
                label.setText(strVal);
                return label;
            }
        });

        comboDay.setRenderer(new WebComboBoxCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                int val = (Integer) value;
                String strVal = val == -1 ? "-" : Integer.toString(val);
                label.setText(strVal);
                return label;
            }
        });

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        this.comboYear.setSelectedItem(year);

        int month = now.getMonth().getValue();
        this.comboMonth.setSelectedItem(month);

        int day = now.getDayOfMonth();
        this.comboDay.setSelectedItem(day);

        add(new ComarLabel(title));
        add(comboYear);
        add(comboMonth);
        add(comboDay);
    }

    public int[] getValue() {
        int year = (Integer) this.comboYear.getSelectedItem();
        int month = (Integer) this.comboMonth.getSelectedItem();
        int day = (Integer) this.comboDay.getSelectedItem();
        return new int[]{year, month, day};
    }

    public LocalDate getDate() throws ComarValidationException {
        int[] value = getValue();

        boolean anyMatch = Arrays.stream(value).anyMatch(e -> e == -1);
        if (!anyMatch) {
            return LocalDate.of(value[0], value[1], value[2]);
        } else {
            throw new ComarValidationException("Fecha incorrecta");
        }
    }

    public void setDate(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        this.comboYear.setSelectedItem(year);
        this.comboMonth.setSelectedItem(month);
        this.comboDay.setSelectedItem(day);
    }

    public ComarComboBox getComboDay() {
        return comboDay;
    }

    public ComarComboBox getComboMonth() {
        return comboMonth;
    }

    public ComarComboBox getComboYear() {
        return comboYear;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                WebLookAndFeel.install();
                ComarPanelDate panelFilter = new ComarPanelDate();
                WebButton buttonSearch = new WebButton("Buscar");
                buttonSearch.addActionListener(e -> {
                    int[] value = panelFilter.getValue();
                    System.out.println(String.format("%s, %s, %s", value[0], value[1], value[2]));
                });

                ComarPanel panel = new ComarPanelFactory().flowLayoutCenter().add(panelFilter).add(buttonSearch).create();
                WebOptionPane.showMessageDialog(null, panel, "", WebOptionPane.PLAIN_MESSAGE);
            }

        });
    }

}
