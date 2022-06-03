package co.edu.uceva.ejemplo01.model.services;

import co.edu.uceva.ejemplo01.model.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUsuarioService {
    List<Usuario> findAll();
    Usuario save(Usuario usuario);
    Usuario findById(Long id);
    Page<Usuario> findAll(Pageable pageable);
    void delete(Long id);
}
