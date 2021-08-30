package io.github.paulushcgcj.paulushcgcjv0.endpoints;

import io.github.paulushcgcj.paulushcgcjv0.models.Pagina;
import io.github.paulushcgcj.paulushcgcjv0.models.Pessoa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pessoas")
@Slf4j
public class PessoasEndpoint {

    private Map<Long, Pessoa> pessoasMap = new HashMap<>();
    private final AtomicLong idGen = new AtomicLong(1L);

    @GetMapping
    public ResponseEntity<Pagina<Pessoa>> listarPessoas(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {

        List<Pessoa> pessoaPage = pessoasMap
                .values()
                .stream()
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());

        log.info("Listando {} de {}, com pagina {} tamanho {}",pessoaPage.size(), pessoasMap.size(),page,size);

        return
                ResponseEntity
                        .ok(
                                Pagina
                                        .<Pessoa>builder()
                                        .page(page)
                                        .size(pessoaPage.size())
                                        .total(pessoasMap.size())
                                        .items(pessoaPage)
                                        .build()
                        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> getPessoa(@PathVariable Long id) {

        if (pessoasMap.containsKey(id))
            return ResponseEntity.ok(pessoasMap.get(id));
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> addPessoa(@RequestBody Pessoa pessoa) {
        long currentId = idGen.getAndIncrement();
        pessoasMap.put(currentId, pessoa.withId(currentId));
        log.info("Adicionando nova entrada {}", pessoa);
        return
                ResponseEntity
                        .created(UriComponentsBuilder.fromPath("/api/pessoas/{id}").build(currentId))
                        .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pagina<Pessoa>> updatePessoa(
            @PathVariable Long id,
            @RequestBody Pessoa pessoa
    ) {
        if (!pessoasMap.containsKey(id))
            return ResponseEntity.notFound().build();
        pessoasMap.put(id, pessoa.withId(id));
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pagina<Pessoa>> listarPessoas(@PathVariable Long id) {
        if (!pessoasMap.containsKey(id))
            return ResponseEntity.notFound().build();
        pessoasMap.remove(id);
        return ResponseEntity.noContent().build();
    }
}
