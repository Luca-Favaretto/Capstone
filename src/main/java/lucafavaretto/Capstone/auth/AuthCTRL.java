package lucafavaretto.Capstone.auth;


import lucafavaretto.Capstone.auth.authDTO.LoginRegisterDTO;
import lucafavaretto.Capstone.auth.authDTO.UserLoginDTO;
import lucafavaretto.Capstone.auth.user.User;
import lucafavaretto.Capstone.auth.user.UserDTO;
import lucafavaretto.Capstone.auth.user.UserSRV;
import lucafavaretto.Capstone.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthCTRL {
    @Autowired
    private AuthSRV authSRV;
    @Autowired
    private UserSRV userSRV;


    @PostMapping("/login")
    public LoginRegisterDTO login(@RequestBody @Validated UserLoginDTO payload, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return new LoginRegisterDTO(authSRV.authenticateUserAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody @Validated UserDTO userDTO, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }

        return this.userSRV.save(userDTO);
    }

}
