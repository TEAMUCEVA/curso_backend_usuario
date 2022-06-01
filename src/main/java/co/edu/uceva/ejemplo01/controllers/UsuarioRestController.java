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

    @GetMapping("/hola")
    public String saludar(@RequestParam(value = "nombre", defaultValue="Mundo") String nombre){
        return String.format("Hola %s", nombre);
    }

    @GetMapping("/usuarios")
    public List<Usuario> index(){
        return usuarioService.findAll();
    }

    @GetMapping("/usuarios/{id}")
    public Usuario buscarUsuario(@PathVariable Long id){
        return usuarioService.findById(id);
    }

    @PostMapping("/usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario crear(@RequestBody Usuario usuario){
        usuario.setCreadoEn(new Date());
        this.usuarioService.save(usuario);

        return usuario;
    }

    @DeleteMapping("/usuarios/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrarUsuario(@PathVariable Long id){
        Usuario usuario = usuarioService.findById(id);
        usuarioService.delete(usuario);
    }

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

