package com.juanyonda_dev.ec.model.dao;

import com.juanyonda_dev.ec.model.entity.Playlist;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlaylistDao extends JpaRepository<Playlist,Integer> {
    Optional<Playlist> findByName(String Name);
    void deleteByName(String name);
}