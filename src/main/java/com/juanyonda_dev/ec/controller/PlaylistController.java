package com.juanyonda_dev.ec.controller;

import com.juanyonda_dev.ec.model.dto.PlaylistDto;
import com.juanyonda_dev.ec.model.dto.SongDto;
import com.juanyonda_dev.ec.model.entity.Playlist;
import com.juanyonda_dev.ec.model.payload.MessageResponse;
import com.juanyonda_dev.ec.service.IPlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1")
public class PlaylistController {
    @Autowired
    private IPlaylistService playlistService;

    @GetMapping("lists")
    public ResponseEntity<?> index(){
        List<Playlist> getList = playlistService.listAlll();
        if (getList == null) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message("Not register")
                            .object(null)
                            .build()
                    , HttpStatus.OK);
        }
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .message("")
                        .object(getList)
                        .build()
                , HttpStatus.OK);
    }

    @PostMapping("lists")
    public ResponseEntity<?> create(@RequestBody PlaylistDto playlistDto) {
        Playlist playlistSave = null;
        try {
            playlistSave = playlistService.save(playlistDto);

            playlistDto = PlaylistDto.builder()
                    .id(playlistSave.getId())
                    .name(playlistSave.getName())
                    .description(playlistSave.getDescription())
                    .songs(playlistSave.getSongs().stream()
                            .map(song -> SongDto.builder()
                                    .title(song.getTitle())
                                    .artist(song.getArtist())
                                    .year(song.getYear())
                                    .genre(song.getGenre())
                                    .build())
                            .collect(Collectors.toList()))
                    .build();

            return new ResponseEntity<>(MessageResponse.builder()
                    .message("save success")
                    .object(playlistDto)
                    .build(), HttpStatus.CREATED);

        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message(exDt.getMessage())
                            .object(null)
                            .build(),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("lists/{id}")
    public ResponseEntity<?> update(@RequestBody PlaylistDto playlistDto,@PathVariable Integer id){
        Playlist playlistUpdate = null;
        try{
            if(playlistService.existsById(id) ){
                playlistDto.setId(id);
                playlistUpdate= playlistService.save(playlistDto);
                playlistDto= PlaylistDto.builder()
                        .id(playlistUpdate.getId())
                        .name(playlistUpdate.getName())
                        .description(playlistUpdate.getDescription())
                        .created_at(playlistUpdate.getCreated_at())
                        .updated_at(playlistUpdate.getUpdated_at())
                        .build();
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("update success")
                        .object(playlistDto)
                        .build()
                        ,HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(
                        MessageResponse.builder()
                                .message("Not exist")
                                .object(null)
                                .build()
                        , HttpStatus.NOT_FOUND);
            }

        }catch(DataAccessException exDt){
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("lists/{name}")
    public ResponseEntity<?> deleteByName(@PathVariable String name) {
        Playlist playlist = playlistService.findByName(name);

        if (playlist == null) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message("Playlist not found")
                            .object(null)
                            .build(),
                    HttpStatus.NOT_FOUND
            );
        }

        playlistService.deleteByName(name);
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .message("Playlist deleted successfully")
                        .object(null)
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }

    @GetMapping("lists/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        Playlist playlist = playlistService.findByName(name);

        if (playlist == null) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message("Playlist not found")
                            .object(null)
                            .build(),
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(MessageResponse.builder()
                .message("success")
                .object(playlist)
                .build(), HttpStatus.OK);
    }
}