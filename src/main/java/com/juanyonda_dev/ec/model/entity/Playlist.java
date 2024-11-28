package com.juanyonda_dev.ec.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "playlists")
public class Playlist implements Serializable {
        @Id
        @Column(name= "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name= "name")
        private String name;

        @Column(name= "description")
        private String description;

        @CreationTimestamp
        @Column(name= "created_at")
        private Date created_at;

        @UpdateTimestamp
        @Column(name= "updated_at")
        private Date updated_at;

        @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonManagedReference
        private List<Song> songs;
}
