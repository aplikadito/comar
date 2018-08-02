/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.products;

import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.util.ComarCharset;
import cl.rworks.comar.swing.model.ComarCategory;
import cl.rworks.comar.swing.model.ComarControllerException;
import cl.rworks.comar.swing.model.ComarProduct;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarPanelOptionsArea;
import cl.rworks.comar.swing.util.ComarPanelView;
import cl.rworks.comar.swing.util.ComarUtils;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.filechooser.WebFileChooser;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.text.WebTextField;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.io.File;
import java.util.List;

/**
 *
 * @author aplik
 */
public class ComarPanelProdsCsvInsertArea extends ComarPanelView {

    private final ComarPanelProdsCsvInsertController controller = new ComarPanelProdsCsvInsertController();
    //
    private WebTextField textFile;
    private WebComboBox comboCharset;
    //
    private File selectedFile = null;

    public ComarPanelProdsCsvInsertArea() {
        super("Agregar via CSV");
        initComponents();
    }

    private void initComponents() {
        ComarPanel panelContent = new ComarPanel(new BorderLayout());
        getPanelContent().add(panelContent, BorderLayout.CENTER);

        ComarPanelOptionsArea options = new ComarPanelOptionsArea();
        options.addLeft(new WebLabel("Archivo"));
        options.addLeft(textFile = new WebTextField(50));
        options.addLeft(new WebButton("Buscar", e -> searchFile()));
        options.addLeft(new WebButton("Cargar", e -> loadFile()));
        options.addRight(new WebLabel("Codificacion"));
        options.addRight(comboCharset = new WebComboBox(ComarCharset.values()));

        panelContent.add(options, BorderLayout.NORTH);
    }

    private void searchFile() {
        File file = WebFileChooser.showOpenDialog();
        if (file != null) {
            this.selectedFile = file;
            this.textFile.setText(file.getAbsolutePath());
        }
    }

    private void loadFile() {
        if (selectedFile == null) {
            ComarUtils.showWarn(this, "Seleccione un archivo");
            return;
        }

        boolean go = false;
        Object[] lists = null;
        try {
            List<ComarProduct> csvProducts = controller.readCsvFile(selectedFile, (ComarCharset) comboCharset.getSelectedItem());
            lists = controller.checkCsvProducts(csvProducts);
            go = true;
        } catch (ComarControllerException ex) {
            go = false;
            ComarUtils.showWarn(this, ex.getMessage());
        }

        if (!go) {
            return;
        }

        go = false;
        List<ComarProduct> existsYes = (List<ComarProduct>) lists[0];
        List<ComarProduct> existsNo = (List<ComarProduct>) lists[1];
        if (existsYes.size() > 0) {
            int r = ComarUtils.showYesNo(this, "'" + existsYes.size() + "' productos del archivo ya existen y no seran ingresados.\n"
                    + "Se ingresaran '" + existsNo.size() + "' productos. \n"
                    + "Desea continuar?", "Aviso");
            if (r == WebOptionPane.YES_OPTION) {
                go = true;
            } else {
                // se podria generar un reporte que imprima los productos que ya existen
                // go = false;
            }
        } else {
            go = true;
        }

        if (!go) {
            return;
        }

        ComarCategory defaultCategory = null;
        ComarCategory[] values = controller.getCategories().toArray(new ComarCategory[]{});
        for (ComarCategory c : values) {
            if (c.getEntity().getNombre().equals(CategoriaEntity.DEFAULT_CATEGORY)) {
                defaultCategory = c;
            }
        }

        Object response = WebOptionPane.showInputDialog(this, "Categoria", "Seleccione una categoria", WebOptionPane.PLAIN_MESSAGE, null, values, defaultCategory);
        if (response != null) {
            try {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                ComarCategory category = (ComarCategory) response;
                controller.insertProducts(existsNo, category);
                ComarUtils.showInfo(this, "Productos ingresados correctamente");
            } catch (ComarControllerException ex) {
                ex.printStackTrace();
                ComarUtils.showWarn(this, ex.getMessage());
            } finally {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }

    }

}
