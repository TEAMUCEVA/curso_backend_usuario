package co.edu.uceva.ejemplo01.model.dao;

import co.edu.uceva.ejemplo01.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDao extends JpaRepository<Usuario, Long> {
}
