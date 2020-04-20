package com.food.domain.service;

import java.util.List;

import com.food.domain.filter.VendaDiariaFilter;
import com.food.domain.model.dto.VendaDiaria;

public interface VendaQueryService {
	
	List<VendaDiaria> consultaVendasDiarias(VendaDiariaFilter filter, String timeOffset);

}
