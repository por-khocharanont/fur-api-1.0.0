package th.cu.thesis.fur.api.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import th.cu.thesis.fur.api.model.JasperModelInput;
import th.cu.thesis.fur.api.service.JasperReportService;
import th.cu.thesis.fur.api.service.exception.ServiceException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Service
public class JasperReportServiceImpl implements JasperReportService {

	@Autowired
	ComboPooledDataSource dataSource;
	
	@Value("${path.file.report}")
	String pathJrxml;
	
	@Override
	public void exportXls(JasperPrint jp, ByteArrayOutputStream baos)throws ServiceException {
				// Create a JRXlsExporter instance
				try {
					JRXlsExporter exporter = new JRXlsExporter();
					exporter.setExporterInput(new SimpleExporterInput(jp));
					exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
					SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
					configuration.setOnePagePerSheet(true);
					configuration.setDetectCellType(true);
					configuration.setCollapseRowSpan(false);
					configuration.setOnePagePerSheet(false);
					configuration.setRemoveEmptySpaceBetweenRows(true);
					configuration.setWhitePageBackground(false);
					exporter.setConfiguration(configuration);
					// Excel specific parameters
					exporter.exportReport();
				} catch (Exception e) {
					e.printStackTrace();
					throw ServiceException.get500SystemError(e);
				}
	}

	@Override
	public void exportPdf(JasperPrint jp, ByteArrayOutputStream baos)throws ServiceException {
				// Create a JRXlsExporter instance
				JRPdfExporter exporter = new JRPdfExporter();
				try {
					exporter.setExporterInput(new SimpleExporterInput(jp));
					exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
					exporter.exportReport();
				} catch (Exception e) {
					e.printStackTrace();
					throw ServiceException.get500SystemError(e);
				}		
	}

	@Override
	public byte[] downloadExcel(JasperModelInput jasperModelInput,String fileNameTemplate) throws ServiceException {
		try{		
//					long startDownload = System.currentTimeMillis();
//					long start = 0;
//					long end = 0;
					File template = new File(pathJrxml+fileNameTemplate);
					// 2.  Retrieve template
					InputStream reportStream = new FileInputStream(template);
					 
					// 3. Convert template to JasperDesign
					JasperDesign jd = JRXmlLoader.load(reportStream);
					JRDesignQuery query = new JRDesignQuery();
					query.setText(jasperModelInput.getSqlStatement());
					jd.setQuery(query);
					// 4. Compile design to JasperReport
					JasperReport jr = JasperCompileManager.compileReport(jd);
					// 5. Create the JasperPrint object
					// Make sure to pass the JasperReport, report parameters, and data source
					jasperModelInput.getMapVariables().put("SQL_STATEMENT", jasperModelInput.getSqlStatement());
					Map<String,Object> params= jasperModelInput.getMapVariables();
//					start = System.currentTimeMillis();
					JasperPrint jp = JasperFillManager.fillReport(jr, params, dataSource.getConnection());
//					end = System.currentTimeMillis();
//					System.out.println("res time fill Report : "+ (end - start));
					
					// 6. Create an output byte stream where data will be written
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					exportXls(jp,baos);
					
//					long endDownload = System.currentTimeMillis();
//					System.out.println("res time download : "+ (endDownload - startDownload));
					return baos.toByteArray();
					
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
					 
	}
	

}
