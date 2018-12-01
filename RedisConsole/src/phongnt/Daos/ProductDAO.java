/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phongnt.Daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import phongnt.model.Product;
import utils.ConnectionUtil;

/**
 *
 * @author PhongNT
 */
public class ProductDAO {
    public List<Product> getAll() throws Exception {
        List<Product> products = null;
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = ConnectionUtil.getConnection();
            String sql = "Select * From Product";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                float price = rs.getFloat("price");
                Product pro = new Product(id, name, price);
                if(products == null) {
                    products = new ArrayList<>();
                }
                products.add(pro);
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
            if(ps != null) {
                ps.close();
            }
            if(cn != null) {
                cn.close();
            }
        }

        return products;
    }
}
