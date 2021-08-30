package io.github.paulushcgcj.paulushcgcjv0.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class Pessoa {
    private Long id;
    private String nome;
    private String email;
    private String password;
}
