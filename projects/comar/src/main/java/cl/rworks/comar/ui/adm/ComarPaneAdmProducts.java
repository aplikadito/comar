/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui.adm;

import cl.rworks.comar.core.model.ComarContext;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.ui.ComarGuiUtils;
import cl.rworks.comar.ui.ComarIconLoader;
import cl.rworks.comar.ui.ComarPaneSectionTitle;
import cl.rworks.comar.ui.ComarValidationException;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rgonzalez
 */
public class ComarPaneAdmProducts extends BorderPane {

    private static final Logger LOG = LoggerFactory.getLogger(ComarPaneAdmProducts.class);
    //
    private static final String DB_ERROR = "Error en la base de datos";
    //
    private ComarContext context;
    //
    private PaneMain paneMain;
    private PaneAdd paneAdd;
    private PaneEdit paneEdit;

    public ComarPaneAdmProducts() {
        paneMain = new PaneMain();
        paneAdd = new PaneAdd();
        paneEdit = new PaneEdit();
    }

    public final void show(Node node) {
//        Node pane = this.map.get(id);
        setCenter(node);
    }

    public final void init(ComarContext context) {
        this.context = context;

//        this.paneMain.setContext(context);
        this.paneMain.updateForm();
        this.show(paneMain);
    }

    public final void goBack() {
        show(paneMain);
    }

    private class PaneMain extends BorderPane {

        private TableView<ComarProduct> table;

        public PaneMain() {
            setTop(new ComarPaneSectionTitle("PRODUCTOS"));

            BorderPane paneTable = new BorderPane();
            paneTable.setCenter(buildTable());

            FlowPane paneSearch = new FlowPane(new Label("Buscar"), new TextField());
            paneSearch.getStyleClass().addAll("comar-flowpane");
            paneTable.setBottom(paneSearch);

            setCenter(paneTable);

            Button buttonAdd = new Button("Agregar");
            buttonAdd.setOnMouseClicked(e -> addProduct());

            Button buttonEdit = new Button("Editar");
            buttonEdit.setOnMouseClicked(e -> editProduct());

            Button buttonDelete = new Button("Eliminar");
            buttonDelete.setOnMouseClicked(e -> deleteProduct());

            FlowPane paneButtons = new FlowPane(buttonAdd, buttonEdit, buttonDelete);
            paneButtons.setAlignment(Pos.CENTER);
            paneButtons.getStyleClass().addAll("comar-flowpane");
            setBottom(paneButtons);
        }

        private TableView buildTable() {
            this.table = new TableView();

            TableColumn<ComarProduct, String> col = new TableColumn("Codigo");
            col.setMinWidth(150);
            col.setCellValueFactory(new PropertyValueFactory("code"));
            table.getColumns().add(col);

            col = new TableColumn("Nombre");
            col.setMinWidth(150);
            col.setCellValueFactory(new PropertyValueFactory("name"));
            table.getColumns().add(col);

            col = new TableColumn("Categoria");
            col.setMinWidth(150);
            col.setCellValueFactory(e -> {
                ComarProduct product = e.getValue();
                String str = product.getCategory() != null ? product.getCategory().getName() : "---";
                return new SimpleStringProperty(str);
            });
            table.getColumns().add(col);

            col = new TableColumn("Unidad");
            col.setMinWidth(80);
            col.setCellValueFactory(e -> {
                ComarProduct product = e.getValue();
                String str = product.getUnit().getName();
                return new SimpleStringProperty(str);
            });
            table.getColumns().add(col);

            table.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2) {
                    editProduct();
                }
            });
            return table;
        }

        private void addProduct() {
            try {
                ComarProduct product = context.getService().getServiceProduct().create();
                paneAdd.setProduct(product);
                paneAdd.updateForm();
                show(paneAdd);
            } catch (ComarServiceException ex) {
                ex.printStackTrace();
            }
        }

        private void editProduct() {
            ComarProduct product = table.getSelectionModel().getSelectedItem();
            if (product == null) {
                ComarGuiUtils.showInfo("Seleccione un producto de la tabla");
                return;
            }

            paneEdit.setProduct(product);
            paneEdit.updateForm();
            show(paneEdit);
        }

        private void deleteProduct() {
            ComarProduct product = table.getSelectionModel().getSelectedItem();
            if (product == null) {
                ComarGuiUtils.showInfo("Seleccione un producto de la tabla");
                return;
            }

        }

        private void updateForm() {
            if (context == null) {
                return;
            }

            try {
                List<ComarProduct> products = context.getService().getServiceProduct().getAll();
                table.setItems(FXCollections.observableArrayList(products));
            } catch (ComarServiceException ex) {
                ex.printStackTrace();
            }

        }
    }

    private class PaneTop extends ComarPaneSectionTitle {

        public PaneTop(String title) {
            super(title);
            Label buttonBack = ComarIconLoader.getIcon(ComarIconLoader.BACK, 16);
            buttonBack.getStyleClass().add("comar-link");
            buttonBack.setOnMouseClicked(e -> goBack());
            getPaneTitle().setRight(buttonBack);
        }

    }

    class PaneAdd extends BorderPane {

        private ProductForm productForm;
        private ComarProduct product;

        public PaneAdd() {
            setTop(new PaneTop("PRODUCTOS > AGREGAR"));
            setCenter(buildForm());
        }

        private Node buildForm() {
            BorderPane pane = new BorderPane();
            this.productForm = new ProductForm("Agregar");
            this.productForm.getButtonAction().setOnMouseClicked(e -> add());
            pane.setCenter(productForm);
            return pane;
        }

        private void setProduct(ComarProduct product) {
            this.product = product;
        }

        private void updateForm() {
            if (product == null) {
                return;
            }

            productForm.setProduct(product);
            productForm.updateForm();
        }

        private void add() {
            try {
                validateAndUpdate();
                
                context.getService().getServiceProduct().insert(product);
                ComarGuiUtils.showInfo("Producto agregado");

                paneMain.updateForm();
                show(paneMain);
            } catch (ComarValidationException ex) {
                ComarGuiUtils.showWarn(ex.getMessage());
            } catch (ComarServiceException ex) {
                LOG.warn(DB_ERROR, ex);
                ComarGuiUtils.showWarn(DB_ERROR);
            }
        }

        private void validateAndUpdate() throws ComarValidationException {
            try {
                String code = productForm.getTextCode().getText();
                if (code == null || code.isEmpty()) {
                    throw new ComarValidationException("Codigo nulo o vacio");
                }

                String name = productForm.getTextName().getText();
                if (name == null || name.isEmpty()) {
                    throw new ComarValidationException("Nombre nulo o vacio");
                }

                if (context.getService().getServiceProduct().existsCode(code)) {
                    throw new ComarValidationException("El codigo ya existe");
                }
                
                product.setCode(code);
                product.setName(name);

            } catch (ComarServiceException ex) {
                LOG.warn(DB_ERROR, ex);
                throw new ComarValidationException(DB_ERROR, ex);
            }
        }
    }

    private class PaneEdit extends BorderPane {

        private ProductForm productForm;
        private ComarProduct product;

        public PaneEdit() {
            setTop(new PaneTop("PRODUCTOS > EDITAR"));
            setCenter(buildForm());
        }

        private Node buildForm() {
            BorderPane pane = new BorderPane();
            productForm = new ProductForm("Editar");
            productForm.getButtonAction().setOnMouseClicked(e -> edit());
            productForm.getTextCode().setDisable(true);
            productForm.getTextCode().setEditable(false);

            pane.setCenter(productForm);
            return pane;
        }

        private void setProduct(ComarProduct product) {
            this.product = product;
        }

        private void updateForm() {
            if (product == null) {
                return;
            }

            productForm.setProduct(product);
            productForm.updateForm();
        }

        private void edit() {
            try {
                validate();
                context.getService().getServiceProduct().update(product);
                ComarGuiUtils.showInfo("Producto actualizado");
            } catch (ComarValidationException ex) {
                ComarGuiUtils.showWarn(ex.getMessage());
            } catch (ComarServiceException ex) {
                LOG.error("Error BD", ex);
                ComarGuiUtils.showWarn(ex.getMessage());
            }
        }

        public void validate() throws ComarValidationException {
            String name = productForm.getTextName().getText();
            if (name == null || name.isEmpty()) {
                throw new ComarValidationException("Nombre nulo o vacio");
            }
        }
    }

    private class ProductForm extends GridPane {

        private String actionName;
        //
        private TextField textCode;
        private TextField textName;
        private Button buttonAction;
        private Button buttonGoBack;
        //
        private ComarProduct product;

        public ProductForm(String actionName) {
            setHgap(10);
            setVgap(10);
            setPadding(new Insets(25, 25, 25, 25));

            int row = 0;
            Label userName = new Label("Codigo");
            add(userName, 0, row);

            textCode = new TextField();
            textCode.setMinWidth(400);
            add(textCode, 1, row);

            row++;
            Label pw = new Label("Nombre");
            add(pw, 0, row);

            textName = new TextField();
            add(textName, 1, row);

            row++;

            buttonAction = new Button(actionName);
            buttonGoBack = new Button("Volver");
            buttonGoBack.setOnMouseClicked(e -> goBack());

            FlowPane flpane = new FlowPane(buttonAction, buttonGoBack);
            flpane.getStyleClass().add("comar-flowpane");
            flpane.setAlignment(Pos.CENTER);
            add(flpane, 0, row, 2, 1);
        }

        public TextField getTextCode() {
            return textCode;
        }

        public TextField getTextName() {
            return textName;
        }

        public Button getButtonAction() {
            return buttonAction;
        }

        public void setProduct(ComarProduct product) {
            this.product = product;
        }

        public void updateForm() {
            if (product == null) {
                return;
            }

            textCode.setText(product.getCode());
            textName.setText(product.getName());
        }

    }

}
