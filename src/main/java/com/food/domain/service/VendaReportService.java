package com.food.domain.service;

import com.food.domain.filter.VendaDiariaFilter;

public interface VendaReportService {
	
	byte[] consultaVendasDiarias(VendaDiariaFilter filter, String timeOffset);

}
