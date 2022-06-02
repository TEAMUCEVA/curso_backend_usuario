package co.edu.uceva.ejemplo01.controllers;

import co.edu.uceva.ejemplo01.model.entities.Usuario;
import co.edu.uceva.ejemplo01.model.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin({"http://localhost:4200", "http://localhost:8080"})
@RestController
@RequestMapping("/usuario_service")
public class UsuarioRestController {

    @Autowired
    private IUsuarioService usuarioService;

    /**
     * Endpoint de ejemplo que retorna un saludo
     * @param nombre Es el nombre del usuario
     * @return
     */
    @GetMapping("/hola")
    public String saludar(@RequestParam(value = "nombre", defaultValue="Mundo") String nombre){
        return String.format("Hola %s", nombre);
    }

    /**
     * Metodo que devuelve la lista de usuarios
     * @return Lista de usuarios
     */
    @GetMapping("/usuarios")
    public List<Usuario> index(){
        return usuarioService.findAll();
    }

    /**
     * Metodo para buscar un usuario
     * @param id Id del usuario a buscar
     * @return El objeto usuario encontrado
     */
    @GetMapping("/usuarios/{id}")
    public Usuario buscarUsuario(@PathVariable Long id){
        return usuarioService.findById(id);
    }

    /**
     * Metodo para crear un usuario
     * @param usuario El usuario a persistir
     * @return El usuario creado
     */
    @PostMapping("/usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario crear(@RequestBody Usuario usuario){
        usuario.setCreadoEn(new Date());
        this.usuarioService.save(usuario);
        return usuario;
    }

    /**
     * Metodo que borra un usuario
     * @param id Identificador del usuario
     */
    @DeleteMapping("/usuarios/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrarUsuario(@PathVariable Long id){
        Usuario usuario = usuarioService.findById(id);
        usuarioService.delete(usuario);
    }

    /**
     * Metodo para actualizar un usuario
     * @param usuario Usuario modificado a persistir
     * @param id Id del usuario a persistir
     * @return El usuario modificado
     */
    @PutMapping("/usuarios/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario actualizarUsuario(@RequestBody Usuario usuario, @PathVariable Long id){
        Usuario usuarioActual = usuarioService.findById(id);
        usuarioActual.setNombre(usuario.getNombre());
        usuarioActual.setApellido(usuario.getApellido());
        usuarioActual.setEmail(usuario.getEmail());
        usuarioActual.setCreadoEn(usuario.getCreadoEn());
        usuarioService.save(usuarioActual);
        return usuarioActual;
    }
}

