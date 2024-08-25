package com.sparta.msa_exam.auth.domain.repository;

import com.sparta.msa_exam.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
