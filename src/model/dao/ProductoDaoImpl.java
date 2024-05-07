package model.dao;

import dataSource.DataSource;
import model.entities.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDaoImpl implements ProductoDao {
    private Connection connection;

    public ProductoDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Producto> findAll() {

        List<Producto> productos = new ArrayList<Producto>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select * from producto order by codigo";
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();

            while(rs.next()){
                Producto p = new Producto();
                p.setCodigo(rs.getInt("codigo"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setNumStock(rs.getInt("numStock"));

                productos.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSource.closeStatement(ps);
            DataSource.closeResultSet(rs);
        }
        return productos;
    }



    @Override
    public Producto findById(int codigo) {

        Producto p =null;

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select * from producto where codigo = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, codigo);

            rs = ps.executeQuery();

            if(rs.next()){
                p = new Producto();
                p.setCodigo(rs.getInt("codigo"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setNumStock(rs.getInt("numStock"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSource.closeStatement(ps);
            DataSource.closeResultSet(rs);
        }
        return p;
    }

    @Override
    public Producto save(Producto p) {

        PreparedStatement ps = null;

        try {
            String sql = "insert into producto (codigo, descripcion, precio, numStock) " +
                    " values (?, ?, ?,?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, p.getCodigo());
            ps.setString(2, p.getDescripcion());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getNumStock());

            int fila = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSource.closeStatement(ps);
        }
        return p;
    }

    @Override
    public void update(Producto p) {

        PreparedStatement ps = null;

        try {
            String sql = "update producto set descripcion = ?, precio = ?, numStock = ? where codigo = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, p.getDescripcion());
            ps.setDouble(2, p.getPrecio());
            ps.setInt(3, p.getNumStock());
            ps.setInt(4, p.getCodigo());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSource.closeStatement(ps);
        }
    }

    @Override
    public void delete(Producto p) {
        PreparedStatement ps = null;

        try {
            String sql = "delete from producto where codigo = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, p.getCodigo());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSource.closeStatement(ps);
        }
    }
}
