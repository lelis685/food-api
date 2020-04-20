package com.food.infrastructure.service.report;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.domain.filter.VendaDiariaFilter;
import com.food.domain.model.dto.VendaDiaria;
import com.food.domain.service.VendaQueryService;
import com.food.domain.service.VendaReportService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class VendaReportServiceImpl implements VendaReportService {

	@Autowired
	private VendaQueryService vendaQueryService;

	@Override
	public byte[] consultaVendasDiarias(VendaDiariaFilter filter, String timeOffset) {

		try {

			InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));

			List<VendaDiaria> vendasDiarias = vendaQueryService.consultaVendasDiarias(filter, timeOffset);
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(vendasDiarias);

			JasperPrint jasperPrint;
			jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);

			return JasperExportManager.exportReportToPdf(jasperPrint);
		
		} catch (Exception e) {
			throw new ReportException("Não foi possível emitir relatório de vendas diárias", e);
		}

	}

}
