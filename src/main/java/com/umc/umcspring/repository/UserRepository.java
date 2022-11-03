package com.umc.umcspring.repository;

import com.umc.umcspring.model.Board;
import com.umc.umcspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
