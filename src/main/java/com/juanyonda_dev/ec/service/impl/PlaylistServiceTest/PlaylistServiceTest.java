package com.juanyonda_dev.ec.service.impl.PlaylistServiceTest;


import com.juanyonda_dev.ec.model.dao.PlaylistDao;
import com.juanyonda_dev.ec.model.dto.PlaylistDto;
import com.juanyonda_dev.ec.model.entity.Playlist;
import com.juanyonda_dev.ec.model.entity.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaylistServiceTest {

    @InjectMocks
    private PlaylistImpl playlistService;

    @Mock
    private PlaylistDao playlistDao;

    private PlaylistDto playlistDto;
    private Playlist playlist;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        List<Song> songs = new ArrayList<>();
        songs.add(Song.builder()
                .title("Song 1")
                .artist("Artist 1")
                .year(2021)
                .genre("Pop")
                .build());

        playlist = Playlist.builder()
                .id(1)
                .name("Test Playlist")
                .description("Description")
                .created_at(new Date())
                .updated_at(new Date())
                .songs(songs)
                .build();

        playlistDto = PlaylistDto.builder()
                .name("Test Playlist")
                .description("Description")
                .build();
    }

    @Test
    void testSavePlaylist() {
        when(playlistDao.save(any(Playlist.class))).thenReturn(playlist);

        Playlist savedPlaylist = playlistService.save(playlistDto);

        assertNotNull(savedPlaylist);
        assertEquals("Test Playlist", savedPlaylist.getName());
        verify(playlistDao, times(1)).save(any(Playlist.class));
    }

    @Test
    void testFindById() {
        when(playlistDao.findById(1)).thenReturn(Optional.of(playlist));

        Playlist foundPlaylist = playlistService.findById(1);

        assertNotNull(foundPlaylist);
        assertEquals("Test Playlist", foundPlaylist.getName());
        verify(playlistDao, times(1)).findById(1);
    }

    @Test
    void testFindByName() {
        when(playlistDao.findByName("Test Playlist")).thenReturn(Optional.of(playlist));

        Playlist foundPlaylist = playlistService.findByName("Test Playlist");

        assertNotNull(foundPlaylist);
        assertEquals("Test Playlist", foundPlaylist.getName());
        verify(playlistDao, times(1)).findByName("Test Playlist");
    }

    @Test
    void testDeletePlaylist() {
        doNothing().when(playlistDao).delete(playlist);

        playlistService.delete(playlist);

        verify(playlistDao, times(1)).delete(playlist);
    }
}