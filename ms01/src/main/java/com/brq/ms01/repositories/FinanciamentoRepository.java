package com.brq.ms01.repositories;

import com.brq.ms01.models.FinanciamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface FinanciamentoRepository extends JpaRepository<FinanciamentoModel, Integer> {
}
