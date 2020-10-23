package com.pk.ms.services.user;

import com.pk.ms.dao.user.IUserRepository;
import com.pk.ms.dao.user.RoleRepository;
import com.pk.ms.entities.user.MyScheduleUser;
import com.pk.ms.entities.user.Role;
import com.pk.ms.entities.user.UserRole;
import com.pk.ms.security.jwt.JwtUtils;
import com.pk.ms.security.request.LoginRequest;
import com.pk.ms.security.request.SignupRequest;
import com.pk.ms.security.response.JwtResponse;
import com.pk.ms.security.response.MessageResponse;
import com.pk.ms.security.services.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final PasswordEncoder encoder;

    private final IUserRepository userRepository;

    private final RoleRepository roleRepository;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtUtils jwtUtils, IUserRepository userRepository,
                       RoleRepository roleRepository, PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public JwtResponse authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = createUsernamePasswordAuthenticationToken(loginRequest);

        setAuthentication(authentication);

        String jwt = generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = createNamesOfRoles(userDetails);

        return  new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    public Authentication createUsernamePasswordAuthenticationToken(LoginRequest loginRequest){
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    }

    public void setAuthentication(Authentication authentication){
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public String generateJwtToken(Authentication authentication){
        return jwtUtils.generateJwtToken(authentication);
    }

    public List<String> createNamesOfRoles(UserDetails userDetails){

        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    public MessageResponse registerUser(SignupRequest signUpRequest) {

        validateRequest(signUpRequest);

        MyScheduleUser user = createNewUserAccount(signUpRequest);

        assignUserRole(user);

        userRepository.save(user);

        return new MessageResponse("User registered successfully!");
    }


    public void validateRequest(SignupRequest signUpRequest) {

        String nick = signUpRequest.getUsername();
        if (userRepository.existsByNick(nick)) {
            throw new IllegalArgumentException("Username is already existed");
        }

        String email = signUpRequest.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("E-mail is already existed");
        }
    }

    public MyScheduleUser createNewUserAccount(SignupRequest signUpRequest) {

        return new MyScheduleUser("need to add firstname " + signUpRequest.getUsername(),
                "need to add lastname",
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
    }

    public void assignUserRole(MyScheduleUser user){
        Set<Role> defaultRoles = new HashSet<>();
        defaultRoles.add(roleRepository.findByName(UserRole.ROLE_USER)
                .orElseThrow(() -> new IllegalStateException("Role USER")));
        user.setRoles(defaultRoles);
    }

}