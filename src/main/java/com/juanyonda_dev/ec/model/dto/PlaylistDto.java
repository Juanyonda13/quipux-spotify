package com.juanyonda_dev.ec.model.dto;

import java.util.Date;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@Builder
public class PlaylistDto implements Serializable {

    private Integer id;
    private String name;
    private String description;
    private Date created_at;
    private Date updated_at;
    private List<SongDto> songs;
}