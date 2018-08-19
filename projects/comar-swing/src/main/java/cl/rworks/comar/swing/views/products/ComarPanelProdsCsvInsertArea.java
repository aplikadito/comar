/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.products;

import cl.rworks.comar.core.util.ComarCharset;
import cl.rworks.comar.swing.main.ComarSystem;
import cl.rworks.comar.swing.model.ComarCategory;
import cl.rworks.comar.swing.model.ComarControllerException;
import cl.rworks.comar.swing.model.ComarProduct;
import cl.rworks.comar.swing.util.ComarLookup;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarPanelOptionsArea;
import cl.rworks.comar.swing.util.ComarPanelView;
import cl.rworks.comar.swing.util.ComarUtils;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.filechooser.WebFileChooser;
import com.alee.laf.label.WebLabel;
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

    public static final String CSVUPDATE = "CSVUPDATE";
    private final ComarPanelProdsCsvInsertController controller = new ComarPanelProdsCsvInsertController();
    //
    private WebTextField textFile;
    private WebComboBox comboCharset;
    //
    private File selectedFile = null;

    public ComarPanelProdsCsvInsertArea() {
        super("Agregar Productos via CSV");
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

        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Object[] result = controller.load(selectedFile, (ComarCharset) comboCharset.getSelectedItem());
            List<ComarCategory> cats = (List<ComarCategory>) result[0];
            List<ComarProduct> prods = (List<ComarProduct>) result[1];

            String msg = "Proceso finalizado correctamente\n";
            msg += "Nro Categorias agregadas: '" + cats.size() + "'\n";
            msg += "Nro Productos agregados: '" + prods.size() + "'\n";

            ComarSystem.getInstance().getLookup().fire(ComarLookup.PRODUCT_CSVUPDATE, null);
            ComarUtils.showInfo(this, msg);
        } catch (ComarControllerException ex) {
            ex.printStackTrace();
            ComarUtils.showWarn(this, ex.getMessage());
        } finally {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

    }

}
