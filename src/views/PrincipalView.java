package views;

import model.entities.Producto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalView extends JFrame {

    public PrincipalView() throws HeadlessException {
        setTitle("Tienda");
        setSize(640, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem itemProductos = new JMenuItem("Producto");

        itemProductos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getContentPane().getComponents().length > 0){
                    getContentPane().remove(0);
                }
                ProductoView productoView = new ProductoView();
                add(productoView, BorderLayout.CENTER);
                productoView.revalidate();

            }
        });

        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(itemProductos);

        menu.addSeparator();
        menu.add(itemSalir);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        setVisible(true);
    }

}
