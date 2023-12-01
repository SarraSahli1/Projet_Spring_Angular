package com.example.springbootesprit.controller;

import com.example.springbootesprit.config.JwtService;
import com.example.springbootesprit.entities.EnumRole;
import com.example.springbootesprit.entities.User;
import com.example.springbootesprit.exception.RoleNotFoundException;
import com.example.springbootesprit.repositories.UserRepository;
import com.example.springbootesprit.service.AuthenticationService;
import com.example.springbootesprit.service.EnumRoleUtils;
import com.example.springbootesprit.service.UserServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private static final Logger log = LoggerFactory.getLogger(User.class);

    @Autowired
    private UserRepository userRepository;
    private final AuthenticationService service;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserServiceImp userServiceImp;
@Autowired
JwtService jwtService ;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest signUpRequest) {
        if (userRepository.existsByFirstname(signUpRequest.getFirstname())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: firstname is already taken!"));
        }
        if (userRepository.existsByLastname(signUpRequest.getLastname())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: lastname is already taken!"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User();
        user.setFirstname(signUpRequest.getFirstname());
        user.setLastname(signUpRequest.getLastname());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        EnumRole userRole;

        if (signUpRequest.getRoles() == null) {
            // Utilisation d'un rôle par défaut (ROLE_UNIVERSITE)
            userRole = EnumRoleUtils.findByName(EnumRole.ROLE_UNIVERSITE.name())
                    .orElseThrow(() -> new RoleNotFoundException("Default Role not found."));
        } else {
            // Utilisation du rôle fourni
            userRole = EnumRoleUtils.findByName(signUpRequest.getRoles().name())
                    .orElseThrow(() -> new RoleNotFoundException("Role not found."));
        }

        user.setRole(userRole); // Attribution du rôle à l'utilisateur

        userRepository.save(user);

        // Generate JWT token
        String jwtToken = jwtService.generateToken(user); // Use appropriate UserDetails if needed

        // Return JWT token in the response
        return ResponseEntity.ok(new AuthenticationResponse(jwtToken,userRole));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));

    }

//1.forget password button takes u here
    @PostMapping("/password-reset-request")
     public String resetPasswordRequest(@RequestBody PasswordResetRequest passwordResetRequest,
                                        final HttpServletRequest request) throws MessagingException, jakarta.mail.MessagingException, UnsupportedEncodingException {
         Optional<User> user = userServiceImp.findByEmail(passwordResetRequest.getEmail());
         String passwordResetUrl = "";
        if (user.isPresent()){
            String passwordResetToken = UUID.randomUUID().toString();
            userServiceImp.createPasswordResetTokenForUser(user.get(), passwordResetToken);
            passwordResetUrl = passswordResetEmailLink(user.get(), applicationUrl(request), passwordResetToken);
        }
        return passwordResetUrl;
     }


    private String passswordResetEmailLink(User user, String applicationUrl, String passwordResetToken) throws MessagingException, jakarta.mail.MessagingException, UnsupportedEncodingException {
        String url = applicationUrl+"/api/v1/auth/reset-password?token="+ passwordResetToken;
        userServiceImp.sendPasswordResetVerificationEmail(url,user);
        log.info("Click the link to reset ur password  : {}", url);
        return url;
    }

    //2. the link takes u here
    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody PasswordResetRequest passwordResetRequest,
                                @RequestParam("token") String passwordResetToken){
        String tokenValidationResult = userServiceImp.validatePasswordResetToken(passwordResetToken);
        if(!tokenValidationResult.equalsIgnoreCase("valid")){
            return "Invalid password reset token";
        }
        User user=userServiceImp.findUserByPasswordToken(passwordResetToken);
        if (user != null){
            userServiceImp.resetUserPassword(user, passwordResetRequest.getNewPassword());
            return "Password has been reset successfully";
        }
        return "Invalid Password reset token ";
    }
    public String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"
                +request.getServerPort()+request.getContextPath();
    }

}
