package com.project.board.repository;

import com.project.board.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRepository {

    List<User> findAll();
    User findById(Long userId);
    String findByLoginId(String loginId);
    User findByLoginIdPassword(String loginId, String password);

    User findByNickName(String nickName);

    void insert(User user);
}
