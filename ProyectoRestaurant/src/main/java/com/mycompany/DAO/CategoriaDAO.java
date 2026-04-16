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

public class CategoriaDAO {
    public List<Categoria> listar(){
        String sql = "SELECT * FROM categorias";
        List<Categoria> lista = new ArrayList<>();
        
        try(Connection con = Conexion.getConnection();
                PreparedStatement st = con.prepareStatement(sql);
                ResultSet rs = st.executeQuery()){
            
            while(rs.next()){
                Categoria c = new Categoria();
                c.setId(rs.getInt("id_categoria"));
                c.setNombre(rs.getString("nombre"));
                lista.add(c);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return lista;
    }
    
    public boolean insertar(Categoria categoria){
        String sql = "INSERT INTO categorias (nombre) VALUES(?)";
        
        try(Connection con = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setString(1, categoria.getNombre());
            return ps.executeUpdate() > 0;
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;     
    }
    
    public Categoria bucarPorId(int id){
        String sql ="SELECT * FROM categorias WHERE id_categoria = ?";
        Categoria categoria = null;
        
        try(Connection con = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);){
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
              categoria = new Categoria();
              categoria.setId(id);
              categoria.setNombre(rs.getString("nombre"));
              
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return categoria;
    }
    
    public boolean actualizar(Categoria c){
        String sql = "UPDATE categorias SET nombre = ? WHERE id_categoria = ?";
        
        try(Connection con = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setString(1, c.getNombre());
            ps.setInt(2, c.getId());
            
            return ps.executeUpdate() > 0;
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean eliminar(int id) {
        String sql = "DELETE FROM categorias WHERE id_categoria = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
