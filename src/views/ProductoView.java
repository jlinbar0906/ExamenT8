package views;

import model.entities.Producto;
import model.services.ProductoService;
import model.services.ProductoServiceImpl;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;
import java.awt.*;
import java.awt.event.*;


public class ProductoView extends JPanel {
    ProductoService service;
    JTable listadoTable;
    private JPanel formPanel;
    JTextField _codigo;
    JTextField _descripcion;
    JTextField _precio;
    JTextField _numStock;

    JButton addButton;
    JButton updateButton;
    JButton deleteButton;
    JButton clearButton;

    public ProductoView () {

        service = new ProductoServiceImpl();

        this.setLayout(new GridLayout(2,1));

        listadoTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(listadoTable);
        this.add(scrollPane, BorderLayout.CENTER);

        crearFormulario();

        this.add(formPanel, BorderLayout.SOUTH);
        formPanel.setVisible(true);

        listadoTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = listadoTable.getSelectedRow();
                if (selectedRow >= 0) {
                    showProductoForm(selectedRow);
                }
            }
        });

        showProductos();
        this.setVisible(true);
    }

    private void crearFormulario() {
        // Crear formulario
        formPanel = new JPanel(new FlowLayout());

        formPanel.setBorder(BorderFactory.createTitledBorder("Detalles del producto"));
        formPanel.add(new JLabel("Codigo:"));
        _codigo = new JTextField();
        _codigo.setPreferredSize(new Dimension(80, 30));
        formPanel.add(_codigo);

        formPanel.add(new JLabel("Descripcion:"));
        _descripcion = new JTextField();
        _descripcion.setPreferredSize(new Dimension(80, 30));
        formPanel.add(_descripcion);

        formPanel.add(new JLabel("Precio:"));
        _precio = new JTextField();
        _precio.setPreferredSize(new Dimension(80, 30));
        formPanel.add(_precio);

        formPanel.add(new JLabel("NumStock:"));
        _numStock = new JTextField();
        _numStock.setPreferredSize(new Dimension(80, 30));
        formPanel.add(_numStock);


        addButton = new JButton("Nuevo");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProducto();
            }
        });
        formPanel.add(addButton);

        updateButton = new JButton("Actualizar");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProducto();
            }
        });
        formPanel.add(updateButton);

        deleteButton = new JButton("Eliminar");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteproducto();
            }
        });
        formPanel.add(deleteButton);

        clearButton = new JButton("Limpiar");

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        formPanel.add(clearButton);
    }

    private void showProductos() {

        // Configurar modelo de datos
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Codigo");
        model.addColumn("Descripcion");
        model.addColumn("Precio");
        model.addColumn("NumStock");


        List<Producto> productos = service.findAll();

        for (Producto p : productos) {
            model.addRow(new Object[]{
                    p.getCodigo(),
                    p.getDescripcion(),
                    p.getPrecio(),
                    p.getNumStock()
            });
        }

        listadoTable.setModel(model);
        formPanel.setVisible(true);
    }

    private void showProductoForm(int rowIndex) {
        DefaultTableModel model = (DefaultTableModel) listadoTable.getModel();
        int codigo = (int) model.getValueAt(rowIndex, 0);

        Producto p = service.findById(codigo);

        _codigo.setText(String.valueOf(p.getCodigo()));
        _descripcion.setText(p.getDescripcion());
        _precio.setText(String.valueOf(p.getPrecio()));
        _numStock.setText(String.valueOf(p.getNumStock()));

    }

    private void addProducto() {

        int  codigo = Integer.parseInt(_codigo.getText());
        String descripcion = _descripcion.getText();
        double precio= Double.parseDouble(_precio.getText());
        int numStock= Integer.parseInt(_numStock.getText());


        Producto p = new Producto();

        p.setCodigo(codigo);
        p.setDescripcion(descripcion);
        p.setPrecio(precio);
        p.setNumStock(numStock);

        DefaultTableModel model = (DefaultTableModel) listadoTable.getModel();

        service.save(p);

        model.addRow(new Object[]{
                p.getCodigo(),
                p.getDescripcion(),
                p.getPrecio(),
                p.getNumStock()

        });

        clearForm();
    }

    private void updateProducto() {

        int  codigo = Integer.parseInt(_codigo.getText());
        String descripcion = _descripcion.getText();
        double precio= Double.parseDouble(_precio.getText());
        int numStock= Integer.parseInt(_numStock.getText());

        Producto p = new Producto();

        p.setCodigo(codigo);
        p.setDescripcion(descripcion);
        p.setPrecio(precio);
        p.setNumStock(numStock);

        service.update(p);
        showProductos();
    }

    private void deleteproducto() {
        int codigo= Integer.parseInt(_codigo.getText());
        Producto p = new Producto();
        p.setCodigo(codigo);
        service.delete(p);
        showProductos();
    }

    private void clearForm() {
        _codigo.setText("");
        _descripcion.setText("");
        _precio.setText("");
        _numStock.setText("");
    }

}
