/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.products;

import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.swing.model.ComarCategory;
import cl.rworks.comar.swing.model.ComarControllerException;
import cl.rworks.comar.swing.model.ComarProduct;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarPanelOptionsArea;
import cl.rworks.comar.swing.util.ComarPanelView;
import cl.rworks.comar.swing.util.ComarUtils;
import com.alee.laf.button.WebButton;
import com.alee.laf.filechooser.WebFileChooser;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.text.WebTextField;
import java.awt.BorderLayout;
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

        try {
            List<ComarProduct> csvProducts = controller.readCsvFile(selectedFile);
            Object[] lists = controller.checkCsvProducts(csvProducts);
            List<ComarProduct> existsYes = (List<ComarProduct>) lists[0];
            List<ComarProduct> existsNo = (List<ComarProduct>) lists[1];

            boolean go = false;
            if (existsYes.size() > 0) {
                int r = ComarUtils.showYesNo(this, "Algunos productos del archivo ya existen y no seran ingresados. Desea continuar?", "Aviso");
                if (r == WebOptionPane.YES_OPTION) {
                    go = true;
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
                    ComarCategory category = (ComarCategory) response;
                    controller.insertProducts(existsNo, category);
                    ComarUtils.showInfo(this, "Productos ingresados correctamente");
                } catch (ComarControllerException ex) {
                    ex.printStackTrace();
                    ComarUtils.showWarn(this, ex.getMessage());
                }
            }

        } catch (ComarControllerException ex) {
            ComarUtils.showWarn(this, ex.getMessage());
        }

    }

}
