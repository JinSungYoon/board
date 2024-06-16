package com.project.board.service;

import com.project.board.domain.User;
import com.project.board.dto.UserDTO;
import com.project.board.enumeration.UserRoleCode;
import com.project.board.exception.*;
import com.project.board.mapper.UserMapper;
import com.project.board.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Description : ID로 User 조회
     *
     * @ param long userId
     * @ return UserDTO
     * @ exception UserNotFoundException, UserValidationException
     */
    public UserDTO findUserById(Long userId){
        User user = userRepository.findById(userId);
        userValidation(user);
        return UserMapper.toDTO(user);
    }

    /**
     * Description : User 로그인
     *
     * @ param UserDTO userDto
     * @ return UserDTO
     * @ exception MissingLoginDataException, UserNotFoundException, UserValidationException
     */
    public UserDTO signInUser(UserDTO userDto){
        validateParameter(userDto);
        User user = userRepository.findByLoginIdPassword(userDto.getLoginId(), userDto.getPassword());
        userValidation(user);
        return UserMapper.toDTO(user);
    }
    /**
     * Description : User 회원 가입
     *
     * @ param UserDTO userDto
     * @ return void
     * @ exception MissingLoginDataException, UserNotFoundException, UserValidationException, DuplicateLoginDataException, PasswordValidationException
     */
    public void signUpUser(UserDTO userDto) {
        checkUserConstraint(userDto);
        userDto = mappingUserCode(userDto);
        User user = UserMapper.toDomain(userDto);
        userRepository.insert(user);
    }

    /**
     * Description : User 회원 Role Code 매핑
     *
     * @ param UserDTO userDto
     * @ return UserDTO
     * @ exception
     */
    private UserDTO mappingUserCode(UserDTO userDto){
        if(userDto.getLoginId() == null){
            userDto.setUserRoleCode(UserRoleCode.GST);
        }else{
            userDto.setUserRoleCode(UserRoleCode.MEB);
        }
        return userDto;
    }

    /**
     * Description : User Log in Parameter 검증 로직
     *
     * @ param UserDTO userDto
     * @ return void
     * @ exception MissingLoginDataException
     */
    private void validateParameter(UserDTO userDto) {
        if(userDto.getLoginId().isEmpty()){
            throw new MissingLoginDataException("Login ID is required");
        }
        if(userDto.getPassword().isEmpty()){
            throw new MissingLoginDataException("Password is required");
        }
    }

    /**
     * Description : 조회된 User에 대한 검증 로직
     *
     * @ param User user
     * @ return void
     * @ exception UserNotFoundException, UserValidationException
     */
    private void userValidation(User user){
        if(user == null){
            throw new UserNotFoundException("User does not exist");
        }
        if('N' == user.getActiveYn()){
            throw new UserValidationException("User is inactive, please activate user before login");
        }
    }

    /**
     * Description : 회원가입시 User 제약사항 체크 로직
     *
     * @ param UserDTO userDto
     * @ return void
     * @ exception UserNotFoundException, UserValidationException
     */
    private void checkUserConstraint(UserDTO userDto) {
        validateParameter(userDto);

        // password
        if(userDto.getPassword().contains(userDto.getLoginId())){
            throw new PasswordValidationException("Password can not include login id information");
        }

        // loginId
        String loginId = userRepository.findByLoginId(userDto.getLoginId());
        if(loginId != null){
            throw new DuplicateLoginDataException(userDto.getLoginId()+"is already exists");
        }

    }
}