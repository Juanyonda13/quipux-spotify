package com.juanyonda_dev.ec.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "songs")
public class Song {
    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name= "title")
    private String title;

    @Column(name= "artist")
    private String artist;

    @Column(name= "year")
    private Integer year;

    @Column(name= "genre")
    private String genre;

    @CreationTimestamp
    @Column(name= "created_at")
    private Date created_at;

    @UpdateTimestamp
    @Column(name= "updated_at")
    private Date updated_at;

    @ManyToOne
    @JoinColumn(name = "playlist_id", nullable = false)
    @JsonBackReference
    private Playlist playlist;
}
