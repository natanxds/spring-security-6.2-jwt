package org.codewithnatan.securityjwt.controller;

import lombok.RequiredArgsConstructor;
import org.codewithnatan.securityjwt.model.ApplicationUser;
import org.codewithnatan.securityjwt.model.AuthenticationDTO;
import org.codewithnatan.securityjwt.model.LoginResponseDTO;
import org.codewithnatan.securityjwt.model.RegisterDTO;
import org.codewithnatan.securityjwt.repository.ApplicationUserRepository;
import org.codewithnatan.securityjwt.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO authenticationDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((ApplicationUser) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO registerDTO) {
        if (applicationUserRepository.findByUsername(registerDTO.username()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        ApplicationUser applicationUser = new ApplicationUser(registerDTO.username(), encryptedPassword, registerDTO.role());
        this.applicationUserRepository.save(applicationUser);

        return ResponseEntity.ok().build();
    }
}
