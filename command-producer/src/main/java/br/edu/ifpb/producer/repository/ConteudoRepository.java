package br.edu.ifpb.producer.repository;

import br.edu.ifpb.producer.domain.Conteudo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConteudoRepository extends JpaRepository<Conteudo, Long> {

    public Optional<Conteudo> findByCodigoReserva(@Param("codigoReserva") String codigoReserva);

}
