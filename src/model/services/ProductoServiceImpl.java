package model.services;

import model.dao.DaoFactory;
import model.dao.ProductoDao;
import model.entities.Producto;

import java.util.List;

public class ProductoServiceImpl implements ProductoService {
    private ProductoDao dao;

    public ProductoServiceImpl() {
        this.dao = DaoFactory.createProductoDao();
    }

    @Override
    public List<Producto> findAll() {
        return dao.findAll();
    }

    @Override
    public Producto findById(int codigo) {
        return dao.findById(codigo);
    }

    @Override
    public Producto save(Producto producto) {
        return dao.save(producto);
    }

    @Override
    public void update(Producto producto) {
        dao.update(producto);
    }

    @Override
    public void delete(Producto producto) {
        dao.delete(producto);
    }
}
