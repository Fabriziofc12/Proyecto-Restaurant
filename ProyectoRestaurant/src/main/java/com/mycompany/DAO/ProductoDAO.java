package com.mycompany.DAO;

import com.mycompany.config.Conexion;
import com.mycompany.model.Categoria;
import com.mycompany.model.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    public List<Producto> listarProductos(){
        String sql = "SELECT p.id_producto, p.nombre, p.descripcion, p.precio, p.imagen, c.id_categoria, c.nombre AS categoria_nombre "
                + "FROM productos p JOIN categorias c ON p.id_categoria = c.id_categoria";
        List<Producto> lista = new ArrayList<>();
        
        try(Connection connection = Conexion.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet= statement.executeQuery()){
            
            while(resultSet.next()){
                Categoria categoria = new Categoria();
                categoria.setId(resultSet.getInt("id_categoria"));
                categoria.setNombre(resultSet.getString("categoria_nombre"));
                
                Producto producto = new Producto();
                producto.setId(resultSet.getInt("id_producto"));
                producto.setNombre(resultSet.getString("nombre"));
                producto.setDescripcion(resultSet.getString("descripcion"));
                producto.setPrecio(resultSet.getDouble("precio"));
                producto.setImagen(resultSet.getString("imagen"));
                producto.setCategoria(categoria);
                
                lista.add(producto);
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return lista;
    }
    public Producto buscarPorId(int id){
        String sql = "SELECT p.id_producto, p.nombre, p.descripcion, p.precio, p.imagen, c.id_categoria, c.nombre AS categoria_nombre " +
                     "FROM productos p JOIN categorias c ON p.id_categoria = c.id_categoria WHERE p.id_producto = ?";
         Producto producto = null;
         
        try(Connection connection = Conexion.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                Categoria c = new Categoria();
                c.setId(resultSet.getInt("id_categoria"));
                c.setNombre(resultSet.getString("categoria_nombre"));

                producto = new Producto();
                producto.setId(resultSet.getInt("id_producto"));
                producto.setNombre(resultSet.getString("nombre"));
                producto.setDescripcion(resultSet.getString("descripcion"));
                producto.setPrecio(resultSet.getDouble("precio"));
                producto.setImagen(resultSet.getString("imagen"));
                producto.setCategoria(c);
                
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return producto;
    }
    public boolean insertar(Producto p) {
        String sql = "INSERT INTO productos(nombre, descripcion, imagen, precio, id_categoria) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setString(3, p.getImagen());
            ps.setDouble(4, p.getPrecio());
            ps.setInt(5, p.getCategoria().getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean actualizar(Producto p) {
        String sql = "UPDATE productos SET " +
                     "nombre = ?, descripcion = ?, imagen = ?, precio = ?, id_categoria = ? " +
                     "WHERE id_producto = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setString(3, p.getImagen());
            ps.setDouble(4, p.getPrecio());
            ps.setInt(5, p.getCategoria().getId());
            ps.setInt(6, p.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id_producto = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public List<Producto> listarPorCategoria(int id) {
        List<Producto> lista = new ArrayList<>();

        String sql = "SELECT " +
                     "p.id_producto, p.nombre, p.descripcion, p.precio, p.imagen, " +
                     "c.id_categoria, c.nombre AS categoria_nombre " +
                     "FROM productos p " +
                     "JOIN categorias c ON p.id_categoria = c.id_categoria " +
                     "WHERE c.id_categoria = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Categoria c = new Categoria();
                c.setId(rs.getInt("id_categoria"));
                c.setNombre(rs.getString("categoria_nombre"));

                Producto p = new Producto();
                p.setId(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setImagen(rs.getString("imagen"));
                p.setCategoria(c);

                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
}
