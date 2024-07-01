package com.project.board.repository;

import com.project.board.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRepository {

    List<User> findAll();
    User findById(Long userId);

    // 회원가입
    User findByLoginId(String loginId);

    // 회원 조회
    User findByNickName(String nickName);

    // 회원가입
    void insert(User user);
}
