package com.project.board.service;

import com.project.board.domain.User;
import com.project.board.dto.UserDTO;
import com.project.board.enumeration.UserRoleCode;
import com.project.board.mapper.UserMapper;
import com.project.board.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void initialize() {
        List<User> userList = new ArrayList<>();
        List<UserDTO> userDtoList = new ArrayList<>();
        int seq = 3;
        for(int i=0;i<seq;i++){
            // Setup Member
            User member = User.builder()
                    .userId((long)(i+1))
                    .loginId("testMember"+i+1)
                    .password("password000"+i+1)
                    .nickName("Member"+i+1)
                    .userRoleCode(UserRoleCode.MEB)
                    .activeYn('Y')
                    .createDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();
            userList.add(member);
            // Setup Guest
            User guest = User.builder()
                    .userId((long)(seq+i+1))
                    .loginId(null)
                    .password(null)
                    .nickName("Guest_0517248"+i)
                    .userRoleCode(UserRoleCode.GST)
                    .activeYn('Y')
                    .createDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();
            userList.add(guest);
            // Setup Member MemberDto
            UserDTO memberDto = UserMapper.toDTO(member);
            userDtoList.add(memberDto);
            // Setup Guest
            UserDTO guestDto = UserMapper.toDTO(guest);
            userDtoList.add(guestDto);
        }
    }

    @Test
    void findAllUsers() {
    }

    @Test
    @DisplayName("ID로 User 조회 - 성공")
    void successFindUserById() {
        // given
        User member = User.builder()
                .userId(1L)
                .loginId("testMember1")
                .password("password0001")
                .nickName("Member1")
                .userRoleCode(UserRoleCode.MEB)
                .activeYn('Y')
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        when(userRepository.findById(1L)).thenReturn(member);
        // when
        UserDTO foundUser = userService.findUserById(1L);
        // then
        assertNotNull(foundUser);
        assertEquals(member.getLoginId(), foundUser.getLoginId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void signInUser() {
    }

    @Test
    void signUpUser() {
    }

}