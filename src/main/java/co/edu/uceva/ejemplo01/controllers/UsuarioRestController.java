package co.edu.uceva.ejemplo01.controllers;

import co.edu.uceva.ejemplo01.model.entities.Usuario;
import co.edu.uceva.ejemplo01.model.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.persistence.PrePersist;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @GetMapping("/usuarios/page/{page}")
    public Page<Usuario> index(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        return usuarioService.findAll(pageable);
    }

    /**
     * Metodo para buscar un usuario
     * @param id Id del usuario a buscar
     * @return El objeto usuario encontrado
     */
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> buscarUsuario(@PathVariable Long id){
        Usuario usuario = null;
        Map<String, Object> response = new HashMap<>();

        try {
            usuario = usuarioService.findById(id);
        } catch(DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(usuario == null) {
            response.put("mensaje", "El usuario id: ".concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }

    /**
     * Metodo para crear un usuario
     * @param usuario El usuario a persistir
     * @return El usuario creado
     */
    @PostMapping("/usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result){

        Usuario usuarioNew = null;
        Map<String, Object> response = new HashMap<>();
        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            usuarioNew = usuarioService.save(usuario);
        } catch(DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Usuario creado exitosamente.");
        response.put("usuario", usuarioNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    /**
     * Metodo que borra un usuario
     * @param id Identificador del usuario
     */
    @DeleteMapping("/usuarios/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> borrarUsuario(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try {
            usuarioService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el usuario de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El usuario eliminado con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    /**
     * Metodo para actualizar un usuario
     * @param usuario Usuario modificado a persistir
     * @param id Id del usuario a persistir
     * @return El usuario modificado
     */
    @PutMapping("/usuarios/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> actualizarUsuario(@Valid @RequestBody Usuario usuario,  BindingResult result, @PathVariable Long id){
        Usuario usuarioActual = usuarioService.findById(id);
        Usuario usuarioUpdated = null;
        Map<String, Object> response = new HashMap<>();
        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if (usuarioActual == null) {
            response.put("mensaje", "Error: no se pudo editar, el usuario con Id: "
                    .concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            usuarioActual.setApellido(usuario.getApellido());
            usuarioActual.setNombre(usuario.getNombre());
            usuarioActual.setEmail(usuario.getEmail());
            usuarioActual.setCreadoEn(usuario.getCreadoEn());
            usuarioUpdated = usuarioService.save(usuarioActual);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el usuario en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El usuario ha sido actualizado con éxito!");
        response.put("usuario", usuarioUpdated);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}

