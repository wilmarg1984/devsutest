package com.devsu.common.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.devsu.common.domain.Parameter;

public interface ParameterRepository extends JpaRepository<Parameter, Long> {

}
