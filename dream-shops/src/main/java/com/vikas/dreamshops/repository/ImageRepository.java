package com.vikas.dreamshops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vikas.dreamshops.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
