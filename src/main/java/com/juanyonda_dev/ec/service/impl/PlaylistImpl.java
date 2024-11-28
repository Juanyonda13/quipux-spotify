package com.juanyonda_dev.ec.service.impl;

import com.juanyonda_dev.ec.model.dao.PlaylistDao;
import com.juanyonda_dev.ec.model.dto.PlaylistDto;
import com.juanyonda_dev.ec.model.entity.Playlist;
import com.juanyonda_dev.ec.model.entity.Song;
import com.juanyonda_dev.ec.service.IPlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaylistImpl implements IPlaylistService {

    @Autowired
    private PlaylistDao playlistDao;

    @Transactional
    @Override
    public Playlist save(PlaylistDto playlistDto) {
        Playlist playlist = Playlist.builder()
                .id(playlistDto.getId())
                .name(playlistDto.getName())
                .description(playlistDto.getDescription())
                .build();

        if (playlistDto.getSongs() != null) {
            List<Song> songs = playlistDto.getSongs().stream()
                    .map(songDto -> Song.builder()
                            .title(songDto.getTitle())
                            .artist(songDto.getArtist())
                            .year(songDto.getYear())
                            .genre(songDto.getGenre())
                            .playlist(playlist)
                            .build())
                    .collect(Collectors.toList());
            playlist.setSongs(songs);
        }

        return playlistDao.save(playlist);
    }

    @Transactional(readOnly = true)
    @Override
    public Playlist findById(Integer id) {
        return playlistDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Playlist playlistDto) {
        playlistDao.delete(playlistDto);
    }

    @Transactional
    @Override
    public void deleteByName(String name) {
        playlistDao.deleteByName(name);
    }

    @Override
    public boolean existsById(Integer id) {
        return playlistDao.existsById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Playlist findByName(String name) {
        Optional<Playlist> playlistOptional = playlistDao.findByName(name);
        if (playlistOptional.isEmpty()) {
            throw new IllegalArgumentException("Playlist not found with name: " + name);
        }
        return playlistOptional.get();
    }

    @Override
    public List<Playlist> listAlll() {
        return (List<Playlist>) playlistDao.findAll();
    }
}