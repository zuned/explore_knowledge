package com.zuni.serviceprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zuni.serviceprovider.domain.PostCode;

public interface PostCodeRepositroy extends JpaRepository<PostCode, String> {

}
