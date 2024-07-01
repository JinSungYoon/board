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
import org.springframework.dao.DuplicateKeyException;
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
                .salt("sffs13osz043opq9")
                .nickName("User1")
                .userRoleCode(UserRoleCode.MEB)
                .activeYn('Y')
                .deleteYn('N')
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        userRepository.insert(user);

    }

    @Test
    @DisplayName("ID로 회원 조회 - 성공")
    public void successFindById_success() {
        // given
        // when
        User foundUser = userRepository.findById(1L);
        // then
        assertNotNull(foundUser);
        assertEquals("testUser1", foundUser.getLoginId());
        assertEquals("password0001" ,foundUser.getPassword());
        assertNull(foundUser.getSalt(),"Salt data is secret data");
        assertEquals("User1",foundUser.getNickName());
        assertEquals(UserRoleCode.MEB,foundUser.getUserRoleCode());
        assertEquals('Y',foundUser.getActiveYn());
        assertEquals('N',foundUser.getDeleteYn());
    }

    @Test
    @DisplayName("ID로 회원 조회 - 실패")
    public void failFindById_fail() {
        // given
        // when
        User foundUser = userRepository.findById(2L);
        // then
        assertNull(foundUser);
    }

    @Test
    @DisplayName("Check All User")
    public void countAllUser(){
        // given
        User newMember = new User(User.memberBuilder("member2","member0000","osela31dif3298hc","member2"));
        userRepository.insert(newMember);
        User newGuest = new User(User.guestBuilder("guest1"));
        userRepository.insert(newGuest);
        // when
        List<User> foundUsers = userRepository.findAll();
        assertEquals(3,foundUsers.size());
    }

    @Test
    @DisplayName("Login ID로 User 찾기 - 성공")
    public void successFindByLoginId_success(){
        // given
        User newMember = new User(User.memberBuilder("member2","member0000","osela31dif3298hc","member2"));
        userRepository.insert(newMember);
        // when
        User foundUser = userRepository.findByLoginId("member2");
        // then
        assertNotNull(foundUser);
        assertEquals("member2", foundUser.getLoginId());
        assertEquals("member0000" ,foundUser.getPassword());
        assertNull(foundUser.getSalt(),"Salt data is secret data");
        assertEquals("member2",foundUser.getNickName());
        assertEquals(UserRoleCode.MEB,foundUser.getUserRoleCode());
        assertEquals('Y',foundUser.getActiveYn());
        assertEquals('N',foundUser.getDeleteYn());
    }

    @Test
    @DisplayName("Login ID로 User 찾기 - 실패")
    public void failFindByLoginId_fail(){
        // given
        User newMember = new User(User.memberBuilder("member2","member0000","osela31dif3298hc","member2"));
        userRepository.insert(newMember);
        User newGuest = new User(User.guestBuilder("guest1"));
        userRepository.insert(newGuest);
        // when
        User user = userRepository.findByLoginId("testGuest");
        // then
        assertNull(user,"Expected null when testGuest is not exist");
    }

    @Test
    @DisplayName("Nickname으로 User 찾기 - 성공")
    public void findByNickName_success(){
        // given
        User newMember = new User(User.memberBuilder("member2","member0000","osela31dif3298hc","member2"));
        userRepository.insert(newMember);
        // when
        User foundUser = userRepository.findByNickName("member2");
        // then
        assertNotNull(foundUser);
        assertEquals("member2",foundUser.getLoginId());
        assertEquals("member0000",foundUser.getPassword());
        assertEquals("member2",foundUser.getNickName());
        assertNull(foundUser.getSalt(),"Salt data is secret data");
        assertEquals(UserRoleCode.MEB,foundUser.getUserRoleCode());
        assertEquals('Y',foundUser.getActiveYn());
        assertEquals('N',foundUser.getDeleteYn());
    }

    @Test
    @DisplayName("Nickname으로 User 찾기 - 실패")
    public void failFindByNickName_fail(){
        // given
        User newMember = new User(User.memberBuilder("member2","member0000","osela31dif3298hc","member2"));
        userRepository.insert(newMember);
        // when
        User foundUser = userRepository.findByNickName("member99");
        // then
        assertNull(foundUser,"Expected is null because member99 user is not exist");
    }

    @Test
    @DisplayName("User 생성 - 성공")
    public void createUser_successCrateUser() {
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

    @Test
    @DisplayName("User 생성 - 실패[중복된 login ID]")
    public void createUser_failDuplicatedLoginId(){
        // given
        User existingUser = new User(User.memberBuilder("member1","member1111","cnqlask3dn5fs23a","MEMBER1"));
        userRepository.insert(existingUser);
        User newUser = new User(User.memberBuilder("member1","member1110","cnqlask3dn5fs23a","member1"));
        // when
        Exception exception = assertThrows(DuplicateKeyException.class,()->{
            userRepository.insert(newUser);
        });
        // then
        String expectedMessage = "Duplicate";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("User 생성 - 실패[중복된 login ID]")
    public void createUser_failDuplicatedNickName(){
        // given
        User existingUser = new User(User.memberBuilder("member1","member1111","cnqlask3dn5fs23a","MEMBER1"));
        userRepository.insert(existingUser);
        User newUser = new User(User.memberBuilder("member2","member1110","cnqlask3dn5fs23a","MEMBER1"));
        // when
        Exception exception = assertThrows(DuplicateKeyException.class,()->{
            userRepository.insert(newUser);
        });
        // then
        String expectedMessage = "Duplicate";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}