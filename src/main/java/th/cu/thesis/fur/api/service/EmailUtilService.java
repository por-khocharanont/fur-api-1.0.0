package th.cu.thesis.fur.api.service;

import java.util.List;
import java.util.Set;

import javax.mail.internet.InternetAddress;

import org.thymeleaf.context.Context;

import th.cu.thesis.fur.api.service.exception.ServiceException;

public interface EmailUtilService {
	InternetAddress[] getRecipients(List<String> recipients) throws ServiceException;
	InternetAddress[] getRecipients(Set<String> recipients) throws ServiceException;
//	void send(String to, String subject, String templateEmail) throws ServiceException;
	void send(String to, String subject, String templateEmail,Context data) ;
//	void send(List<String> to, String subject, String templateEmail) throws ServiceException;
	void send(List<String> to, String subject, String templateEmail,Context data) ;
	void send(Set<String> to, String subject, String templateEmail,Context data) ;
//	void sendCc(String to, String cc ,String subject, String templateEmail) throws ServiceException;
//	void sendCc(String to, String cc ,String subject, String templateEmail,Context data) throws ServiceException;
//	void sendCc(List<String> to, String cc ,String subject, String templateEmail) throws ServiceException;
//	void sendCc(List<String> to, String cc ,String subject, String templateEmail,Context data) throws ServiceException;
//	void sendCc(String to, List<String> cc ,String subject, String templateEmail) throws ServiceException;
//	void sendCc(String to, List<String> cc ,String subject, String templateEmail,Context data) throws ServiceException;
//	void sendCc(List<String> to, List<String> cc, String subject, String templateEmail) throws ServiceException;
//	void sendCc(List<String> to, List<String> cc, String subject, String templateEmail,Context data) throws ServiceException;
}
