package com.project.board.repository;

import com.project.board.domain.User;
import com.project.board.enumeration.UserRoleCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User user = User.builder()
                .userId(1L)
                .loginId("testUser1")
                .password("password0001")
                .nickName("User1")
                .userRoleCode(UserRoleCode.MEB)
                .activeYn('Y')
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        userRepository.insert(user);

    }

    @Test
    @DisplayName("ID로 회원 조회 - 성공")
    public void successFindById() {
        // given
        // when
        User foundUser = userRepository.findById(1L);
        // then
        assertNotNull(foundUser);
        assertEquals("testUser1", foundUser.getLoginId());
        assertNull(null, foundUser.getPassword());
        assertEquals("User1",foundUser.getNickName());
        assertEquals(UserRoleCode.MEB,foundUser.getUserRoleCode());
        assertEquals('Y',foundUser.getActiveYn());
    }

    @Test
    @DisplayName("ID로 회원 조회 - 실패")
    public void failFindById() {
        // given
        // when
        User foundUser = userRepository.findById(2L);
        // then
        assertNull(foundUser);
    }

    @Test
    @DisplayName("Login ID로 User 찾기 - 성공")
    public void successFindByLoginId(){
        // given
        // when
        String loginId = userRepository.findByLoginId("testUser1");
        // then
        assertNotNull(loginId);
    }

    @Test
    @DisplayName("Login ID로 User 찾기 - 실패")
    public void failFindByLoginId(){
        // given
        // when
        String loginId = userRepository.findByLoginId("testGuest");
        // then
        assertNull(loginId);
    }

    @Test
    @DisplayName("Login Id & Password User 찾기 - 성공")
    public void successFindByLoginIdPassword() {
        // given
        // when
        User foundUser = userRepository.findByLoginIdPassword("testUser1", "password0001");
        //then
        assertNotNull(foundUser);
        assertEquals("testUser1", foundUser.getLoginId());
        assertNull(foundUser.getPassword());
    }

    @Test
    @DisplayName("Login Id & Password User 찾기 - 실패")
    public void failFindByLoginIdPassword() {
        // given
        // when
        User foundUser = userRepository.findByLoginIdPassword("testUser2", "password0001");
        // then
        assertNull(foundUser);
    }

    @Test
    @DisplayName("Nickname으로 User 찾기 - 성공")
    public void successFindByNickName(){
        // given
        User newMember = new User(User.memberBuilder("member2","member0000","member2"));
        userRepository.insert(newMember);
        // when
        User foundUser = userRepository.findByNickName("member2");
        // then
        assertNotNull(foundUser);
        assertEquals("member2",foundUser.getLoginId());
        assertNull(foundUser.getPassword());
        assertEquals(UserRoleCode.MEB,foundUser.getUserRoleCode());
        assertEquals('Y',foundUser.getActiveYn());
    }

    @Test
    @DisplayName("Nickname으로 User 찾기 - 실패")
    public void failFindByNickName(){
        // given
        User newMember = new User(User.memberBuilder("member2","member0000","member2"));
        userRepository.insert(newMember);
        // when
        User foundUser = userRepository.findByNickName("member99");
        // then
        assertNull(foundUser);
    }

    @Test
    @DisplayName("User 생성 - 성공")
    public void testInsert() {
        // given
        User newGuest = new User(User.guestBuilder("newGuest2"));
        userRepository.insert(newGuest);
        // when
        List<User> allUsers = userRepository.findAll();
        User foundUser = userRepository.findByNickName("newGuest2");
        // then
        assertEquals(2,allUsers.size(),"2명의 User가 조회될 것으로 예상됩니다.");
        assertNotNull(foundUser);
        assertTrue(foundUser.getLoginId().contains("Guest"),"'Guest'가 포함되어 있습니다.");
        assertNull(foundUser.getPassword());
        assertEquals(UserRoleCode.GST,foundUser.getUserRoleCode());
        assertEquals('Y',foundUser.getActiveYn());

    }

}