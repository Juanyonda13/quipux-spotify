package com.juanyonda_dev.ec.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@Builder
public class SongDto implements Serializable {
    private String title;
    private String artist;
    private Integer year;
    private String genre;
}

