package co.edu.uceva.ejemplo01.model.services;

import co.edu.uceva.ejemplo01.model.entities.Usuario;

import java.util.List;

public interface IUsuarioService {
    List<Usuario> findAll();
    Usuario save(Usuario usuario);
    Usuario findById(Long id);
    void delete(Long id);
}
