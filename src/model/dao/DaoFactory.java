package model.dao;

import dataSource.DataSource;

public class DaoFactory {
    public static ProductoDao createProductoDao(){
        return new ProductoDaoImpl(DataSource.getConnection());
    }


}
