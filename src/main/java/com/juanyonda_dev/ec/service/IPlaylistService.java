package com.juanyonda_dev.ec.service;

import com.juanyonda_dev.ec.model.dto.PlaylistDto;
import com.juanyonda_dev.ec.model.entity.Playlist;

import java.util.List;

public interface IPlaylistService {
    Playlist save(PlaylistDto playlist);

    Playlist findById(Integer id);

    void delete(Playlist playlist);

    void deleteByName(String name);

    Playlist findByName(String name);

    List<Playlist> listAlll();
    boolean existsById(Integer id);

}
