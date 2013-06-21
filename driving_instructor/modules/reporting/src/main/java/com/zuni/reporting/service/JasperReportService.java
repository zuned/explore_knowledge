package com.zuni.reporting.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.xpath.XPathExpressionException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.zuni.reporting.core.Report;
import com.zuni.reporting.core.ReportService;
import com.zuni.reporting.request.JasperReport;
import com.zuni.reporting.utils.JRJsonExporter;
import com.zuni.reporting.utils.ReportUtils;
import com.zuni.reporting.utils.XMLUtils;

@Service("jasperReportService")
public class JasperReportService implements ReportService {

	private static Logger logger = LoggerFactory.getLogger(JasperReportService.class);

	private RestTemplate restTemplate;
	private String reportLoc;
	private String reportPath;
	private String reportDownloadUrl;
	private String loginUser;
	private String loginPassword;
	private String loginUrl;
	private String loginPort;
	private ReportUtils reportUtils;

	public ReportUtils getReportUtils() {
		return reportUtils;
	}

	public void setReportUtils(ReportUtils reportUtils) {
		this.reportUtils = reportUtils;
	}

	public String getReportLoc() {
		return reportLoc;
	}

	public void setReportLoc(String reportLoc) {
		this.reportLoc = reportLoc;
	}

	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}

	public String getReportDownloadUrl() {
		return reportDownloadUrl;
	}

	public void setReportDownloadUrl(String reportDownloadUrl) {
		this.reportDownloadUrl = reportDownloadUrl;
	}

	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLoginPort() {
		return loginPort;
	}

	public void setLoginPort(String loginPort) {
		this.loginPort = loginPort;
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Report initReport(HttpServletRequest request) {
		Report report = new JasperReport();
		String type = request.getParameter("format");
		if (type == null || type.trim().equals("")) {
			type = "HTML";
		}
		report.setType(type);
		report.setReportName(request.getParameter("reportName"));
		report.setParameters((HashMap<String, Object>) request.getAttribute("reportingParametersMap"));
		String pageNo = request.getParameter("page_Num");
		if (pageNo == null || pageNo.trim().equals("")) {
			report.setPageNumber(0);
		} else {
			report.setPageNumber(Integer.parseInt(pageNo));
		}
		return report;
	}

	@Override
	public String createReport(Report report) throws Exception {
		StringBuffer a = new StringBuffer();
		ResponseEntity<byte[]> outReport = outputReport(report.getReportName(), report.getType(), report.getParameters());
		if (outReport != null) {
			JRExporter jrExporter = new JRJsonExporter();
			InputStream is = new ByteArrayInputStream(outReport.getBody());
			JasperPrint print = null;
			print = (JasperPrint) JRLoader.loadObject(is);
			jrExporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, a);
			jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			jrExporter.setParameter(JRExporterParameter.PAGE_INDEX, report.getPageNumber());
			try {
				jrExporter.exportReport();
			} catch (JRException e) {
				// TODO Auto-generated catch block
				logger.error("Exception: ", e);
			}
		}
		return a.toString();
	}

	@Override
	public List<String> getReportsList() {
		// TODO Auto-generated method stub
		setCredentials(loginUser, loginPassword, loginUrl, Integer.parseInt(loginPort));
		try {
			return returnResourceList(reportLoc);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			logger.error("Exception: ", e);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			logger.error("Exception: ", e);
		}
		return null;
	}

	@Override
	public List<String> getReportsList(String repositoryPath) {
		try {
			return returnResourceList(repositoryPath);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			logger.error("Exception: ", e);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			logger.error("Exception: ", e);
		}
		return null;
	}

	public void setCredentials(String username, String password, String url, Integer port) {
		DefaultHttpClient client = new DefaultHttpClient();
		Credentials defaultcreds = new UsernamePasswordCredentials(username, password);
		client.getCredentialsProvider().setCredentials(new AuthScope(url, port, AuthScope.ANY_REALM), defaultcreds);
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
	}

	public ArrayList<String> returnResourceList(String path) throws RestClientException, XPathExpressionException {
		String reportListXML = restTemplate.getForEntity(path, String.class).getBody();
		NodeList nodes = XMLUtils.evalXpath(reportListXML, "resourceDescriptors/resourceDescriptor");
		ArrayList<String> reportList = new ArrayList<String>();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node instanceof Element) {
				reportList.add(((Element) node).getAttribute("name"));
			}
		}
		return reportList;
	}

	public void addParameter(Document doc, String attribute, String textValue) {
		Element rootElement = doc.getDocumentElement();
		Element parameter = doc.createElement("parameter");
		parameter.setAttribute("name", attribute);
		parameter.appendChild(doc.createTextNode(textValue));
		rootElement.appendChild(parameter);
	}

	public ResponseEntity<byte[]> outputReport(String reportName, String format, Map<String, Object> map) throws Exception {
		setCredentials(loginUser, loginPassword, loginUrl, Integer.parseInt(loginPort));
		ResponseEntity<String> getReportResponse = this.getReport(reportPath, reportName);
		Document doc = XMLUtils.convertToDoc(getReportResponse.getBody());
		for (String key : map.keySet()) {
			this.addParameter(doc, key, (String) map.get(key));
		}
		String updatedXML = XMLUtils.transformToString(doc);
		HttpEntity<String> requestEntity = new HttpEntity<String>(updatedXML, null);
		if (format == null || format.trim().equals("")) {
			format = "HTML";
		}

		ResponseEntity<String> runReportResponse = null;
		if (format.equals("HTML")) {
			format = "JRPRINT";
		}
		runReportResponse = this.runReport(reportDownloadUrl + "?RUN_OUTPUT_FORMAT=" + format, requestEntity);
		String uuid = this.getreportUuid(runReportResponse);
		ResponseEntity<byte[]> outReport = null;
		String numPages = this.getreportNumPages(runReportResponse);
		if (numPages != null && !numPages.trim().equals("") && Integer.parseInt(numPages) > 0) {
			if (format.equals("JRPRINT")) {
				outReport = this.downloadReport(reportDownloadUrl + "/", uuid, "jasperPrint");
			} else {
				outReport = this.downloadReport(reportDownloadUrl + "/", uuid, "report");
			}
		}
		return outReport;
	}

	public ResponseEntity<byte[]> outputDefaultReport(String reportName, Map<String, Object> parameters) throws Exception {
		return outputReportPage(reportName, "1", parameters);
	}

	public ResponseEntity<byte[]> outputReportPage(String reportName, String pageNo, Map<String, Object> parameters) throws Exception {
		return outputReport(reportName, "HTML", parameters);
	}

	public ResponseEntity<String> getReport(String path, String reportName) {
		return restTemplate.getForEntity(path + "/" + reportName, String.class);
	}

	public ResponseEntity<String> runReport(String path, HttpEntity<String> requestEntity) {
		return restTemplate.exchange(path, HttpMethod.PUT, requestEntity, String.class);
	}

	public ResponseEntity<String> runReportPage(String path, HttpEntity<String> requestEntity, int pageNo) {
		return restTemplate.exchange(path + "&PAGE=" + pageNo, HttpMethod.PUT, requestEntity, String.class);
	}

	public ResponseEntity<byte[]> downloadReport(String path, String uuid, String fileType) {
		return restTemplate.getForEntity(path + uuid + "?file=" + fileType, byte[].class);
	}

	public String getreportUuid(ResponseEntity<String> reportResponse) throws DOMException, XPathExpressionException {

		return XMLUtils.evalXpath(reportResponse.getBody(), "report/uuid").item(0).getTextContent();
	}

	public String getreportNumPages(ResponseEntity<String> reportResponse) throws DOMException, XPathExpressionException {

		return XMLUtils.evalXpath(reportResponse.getBody(), "report/totalPages").item(0).getTextContent();
	}

	public void exportReport(JRExporter jrExporter, String fileName, byte[] body) throws JRException {
		InputStream is = new ByteArrayInputStream(body);
		JasperPrint print = (JasperPrint) JRLoader.loadObject(is);
		jrExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, fileName);
		jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, print);

		jrExporter.exportReport();
	}

	//public ResponseEntity<byte[]> outputReport(String reportName,String format,int pageNo,Map parameters,HttpServletResponse response) throws Exception
	public HashMap<String, ArrayList<String>> getInputControls(ArrayList<String> reportList) throws XPathExpressionException {
		HashMap<String, ArrayList<String>> inputMap = new HashMap<String, ArrayList<String>>();
		for (String reportName : reportList) {
			ArrayList<String> paramList = new ArrayList<String>();
			ResponseEntity<String> reportDesc = this.getReport(reportPath, reportName);
			NodeList nodes = XMLUtils.evalXpath(reportDesc.getBody(), "resourceDescriptor/resourceDescriptor");
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node instanceof Element) {
					if (((Element) node).getAttribute("wsType").equals("inputControl")) {
						paramList.add(((Element) node).getAttribute("name"));
					}
				}
			}
			inputMap.put(reportName, paramList);
		}
		return inputMap;
	}

	@Override
	public ResponseEntity<byte[]> export(Report report, HttpHeaders responseHeaders) {
		ResponseEntity<byte[]> outReport = null;
		try {
			outReport = outputReport(report.getReportName(), report.getType(), report.getParameters());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception: ", e);
		}
		responseHeaders.set("content-type", reportUtils.getOutputTypeMap().get(report.getType()));
		String fileName = report.getReportName() + reportUtils.getDateFormat().format(new Date()) + "." + report.getType();
		responseHeaders.set("Content-disposition", "attachment; filename=" + fileName);
		return outReport;
	}

	@Override
	public <T> String createReport(Report reportRequest, List<T> list, Long totalRecords) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> ResponseEntity<byte[]> export(Report report, HttpHeaders responseHeaders, List<T> list) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
