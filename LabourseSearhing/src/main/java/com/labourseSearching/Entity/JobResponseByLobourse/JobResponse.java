package com.labourseSearching.Entity.JobResponseByLobourse;

import com.labourseSearching.Entity.JobPost.JobPost;
import com.labourseSearching.Entity.Users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class JobResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    private JobPost jobPost;

    @ManyToOne
    private User labour;

    private String name;
    private String mobile;

    private LocalDateTime respondedAt;

}