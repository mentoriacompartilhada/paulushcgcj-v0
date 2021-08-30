package io.github.paulushcgcj.paulushcgcjv0.models;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class Pagina< T > {
    private int page;
    private int size;
    private long total;
    @Singular
    private List< T > items;
}
