package co.edu.uceva.ejemplo01.model.dao;

import co.edu.uceva.ejemplo01.model.entities.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {
}
