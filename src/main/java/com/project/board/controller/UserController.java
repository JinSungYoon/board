package com.project.board.controller;

import com.project.board.dto.UserDTO;
import com.project.board.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * Description : ID로 User 조회
     *
     * @ param long userId
     * @ return UserDTO
     * @ exception UserNotFoundException, UserValidationException
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findUserById(id));
    }

    /**
     * Description : ID로 User 조회
     *
     * @ HTTP Method Code : POST
     * @ param UserDTO
     * @ param BindingResult bindingResult
     * @ return ResponseEntity<?>
     * @ exception
     */
    @PostMapping("/signUp")
    public ResponseEntity<?> signUpUser(@Valid @RequestBody UserDTO userDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        userService.signUpUser(userDto);
        return ResponseEntity.ok().build();
    }

    /**
     * Description : 로그인
     *
     * @ param UserDTO userDto
     * @ return ResponseEntity<String>
     * @ exception
     */
    @PostMapping("/signIn")
    public ResponseEntity<String> signInUser(@RequestBody UserDTO userDto){
        userService.signInUser(userDto);
        return ResponseEntity.ok().build();
    }

}
