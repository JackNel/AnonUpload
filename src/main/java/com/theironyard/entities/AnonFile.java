package com.theironyard.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Jack on 11/18/15.
 */
@Entity
@Table(name = "files")
public class AnonFile {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    int id;

    @Column(nullable = false)
    public String originalName;

    @Column(nullable = false)
    public String name;

    @Column
    public LocalDateTime uploadTime;

    @Column(nullable = false)
    public boolean isPerm;

    @Column
    public String comment;
}
