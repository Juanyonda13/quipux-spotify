package com.juanyonda_dev.ec;

import com.juanyonda_dev.ec.model.dto.PlaylistDto;
import com.juanyonda_dev.ec.model.dto.SongDto;
import com.juanyonda_dev.ec.model.entity.Playlist;
import com.juanyonda_dev.ec.model.entity.Song;
import com.juanyonda_dev.ec.service.IPlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class QuipuxSpotifyApplicationTests {

	@Mock
	private IPlaylistService playlistService;

	@InjectMocks
	private PlaylistDto playlistDto;

	private Playlist playlist;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		playlist = Playlist.builder()
				.id(1)
				.name("Test Playlist")
				.description("A test playlist")
				.created_at(new Date())
				.updated_at(new Date())
				.build();

		playlistDto = PlaylistDto.builder()
				.name("Test Playlist")
				.description("A test playlist")
				.build();
	}

	@Test
	void testCreatePlaylistWithSongs() {
		List<SongDto> canciones = new ArrayList<>();
		canciones.add(SongDto.builder()
				.title("Song 1")
				.artist("Artist 1")
				.year(2021)
				.genre("Pop")
				.build());
		canciones.add(SongDto.builder()
				.title("Song 2")
				.artist("Artist 2")
				.year(2020)
				.genre("Rock")
				.build());

		PlaylistDto playlistDto = PlaylistDto.builder()
				.name("Lista 1")
				.description("Lista de canciones de Spotify")
				.songs(canciones)
				.build();

		Playlist playlist = Playlist.builder()
				.id(1)
				.name("Lista 1")
				.description("Lista de canciones de Spotify")
				.songs(List.of(
						Song.builder()
								.title("Song 1")
								.artist("Artist 1")
								.year(2021)
								.genre("Pop")
								.build(),
						Song.builder()
								.title("Song 2")
								.artist("Artist 2")
								.year(2020)
								.genre("Rock")
								.build()
				))
				.build();

		when(playlistService.save(any(PlaylistDto.class))).thenReturn(playlist);

		Playlist savedPlaylist = playlistService.save(playlistDto);

		assertNotNull(savedPlaylist);
		assertEquals("Lista 1", savedPlaylist.getName());
		assertEquals("Lista de canciones de Spotify", savedPlaylist.getDescription());
		assertEquals(2, savedPlaylist.getSongs().size());
		assertEquals("Song 1", savedPlaylist.getSongs().get(0).getTitle());
		assertEquals("Song 2", savedPlaylist.getSongs().get(1).getTitle());

		verify(playlistService, times(1)).save(any(PlaylistDto.class));
	}

	@Test
	void testSavePlaylist() {
		when(playlistService.save(any(PlaylistDto.class))).thenReturn(playlist);

		Playlist savedPlaylist = playlistService.save(playlistDto);

		assertNotNull(savedPlaylist);
		assertEquals("Test Playlist", savedPlaylist.getName());
		verify(playlistService, times(1)).save(any(PlaylistDto.class));
	}

	@Test
	void testFindById() {
		when(playlistService.findById(1)).thenReturn(playlist);

		Playlist foundPlaylist = playlistService.findById(1);

		assertNotNull(foundPlaylist);
		assertEquals(1, foundPlaylist.getId());
		verify(playlistService, times(1)).findById(1);
	}

	@Test
	void testFindByName() {
		when(playlistService.findByName("Test Playlist")).thenReturn(playlist);

		Playlist foundPlaylist = playlistService.findByName("Test Playlist");

		assertNotNull(foundPlaylist);
		assertEquals("Test Playlist", foundPlaylist.getName());
		verify(playlistService, times(1)).findByName("Test Playlist");
	}

	@Test
	void testDeletePlaylist() {
		doNothing().when(playlistService).delete(playlist);

		playlistService.delete(playlist);

		verify(playlistService, times(1)).delete(playlist);
	}



	@Test
	void testCreatePlaylist() {
		when(playlistService.save(any(PlaylistDto.class))).thenReturn(playlist);

		Playlist savedPlaylist = playlistService.save(playlistDto);

		assertNotNull(savedPlaylist);
		assertEquals("Test Playlist", savedPlaylist.getName());
		verify(playlistService, times(1)).save(any(PlaylistDto.class));
	}

	@Test
	void testShowByName() {
		when(playlistService.findByName("Test Playlist")).thenReturn(playlist);

		Playlist foundPlaylist = playlistService.findByName("Test Playlist");

		assertNotNull(foundPlaylist);
		assertEquals("Test Playlist", foundPlaylist.getName());
		verify(playlistService, times(1)).findByName("Test Playlist");
	}

	@Test
	void testDeleteByName() {
		when(playlistService.findByName("Test Playlist")).thenReturn(playlist);

		doNothing().when(playlistService).delete(playlist);

		Playlist foundPlaylist = playlistService.findByName("Test Playlist");
		playlistService.delete(foundPlaylist);

		verify(playlistService, times(1)).findByName("Test Playlist");

		verify(playlistService, times(1)).delete(playlist);
	}

	@Test
	void contextLoads() {
		//assertNotNull(playlistService);
	}
}
