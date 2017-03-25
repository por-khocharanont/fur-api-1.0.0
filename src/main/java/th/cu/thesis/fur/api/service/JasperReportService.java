package th.cu.thesis.fur.api.service;

import java.io.ByteArrayOutputStream;

import net.sf.jasperreports.engine.JasperPrint;
import th.cu.thesis.fur.api.model.JasperModelInput;
import th.cu.thesis.fur.api.service.exception.ServiceException;




public interface JasperReportService {
	public void exportXls(JasperPrint jp, ByteArrayOutputStream baos) throws ServiceException;
	public void exportPdf(JasperPrint jp, ByteArrayOutputStream baos) throws ServiceException;
	public byte[] downloadExcel (JasperModelInput jasperModelInput,String fileNameTemplate) throws ServiceException;
}
