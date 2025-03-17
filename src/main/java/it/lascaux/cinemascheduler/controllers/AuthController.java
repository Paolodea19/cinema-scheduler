package it.lascaux.cinemascheduler.controllers;

import it.lascaux.cinemascheduler.models.User;
import it.lascaux.cinemascheduler.services.FilmService;
import it.lascaux.cinemascheduler.services.JwtService;
import it.lascaux.cinemascheduler.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;
    private final FilmService filmService;
    private final PasswordEncoder passwordEncoder; // Aggiungi il PasswordEncoder


    public AuthController(JwtService jwtService, UserService userService, FilmService filmService, PasswordEncoder passwordEncoder ) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.filmService = filmService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        if (userService.findByUsername(username).isPresent()) {
            return "Errore: Username già in uso!";
        }
        userService.registerUser(username, password);
        return "Registrazione avvenuta con successo!";
    }

  /*  @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        Optional<User> userOptional = userService.findByUsername(username);

        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
            String token = jwtService.generateToken(username);
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenziali non valide");
    }*/

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Credenziali non valide"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Credenziali non valide");
        }

        String token = jwtService.generateToken(username);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token mancante o formato errato.");
        }

        token = token.substring(7); // Rimuovi il "Bearer " dal token

        if (jwtService.isTokenExpired(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token scaduto.");
        }

        String username = jwtService.extractUsername(token);
        return ResponseEntity.ok("Token valido per l'utente: " + username);
    }
}
