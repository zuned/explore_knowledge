package com.zuni.reporting.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JRStyledTextAttributeSelector;
import net.sf.jasperreports.engine.export.CutsInfo;
import net.sf.jasperreports.engine.export.ExporterNature;
import net.sf.jasperreports.engine.export.JRCsvExporterNature;
import net.sf.jasperreports.engine.export.JRExporterGridCell;
import net.sf.jasperreports.engine.export.JRGridLayout;
import net.sf.jasperreports.engine.fill.JRTemplatePrintText;
import net.sf.jasperreports.engine.type.BandTypeEnum;
import net.sf.jasperreports.engine.util.JRStyledText;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zuni.reporting.request.JsonResponse;

public class JRJsonExporter extends JRAbstractExporter {

	private static Logger logger = LoggerFactory.getLogger(JRJsonExporter.class);

	protected Writer writer;
	protected ExporterNature nature;
	StringBuffer title = new StringBuffer("");
	JsonResponse jsonResponse = new JsonResponse();
	StringBuffer output = new StringBuffer();

	@Override
	public void exportReport() throws JRException {
		setOffset();
		setInput();
		if (!isModeBatch) {
			try {
				setPageRange();
			} catch (JRException jre) {
				startPageIndex = 0;
				endPageIndex = 0;
			}
		}
		StringBuffer sb = (StringBuffer) parameters.get(JRExporterParameter.OUTPUT_STRING_BUFFER);
		if (sb != null) {
			try {
				writer = new StringWriter();
				exportReportToWriter();
				sb.append(writer.toString());
			} catch (Exception e) {
				logger.error("Error writing to StringBuffer writer : " + jasperPrint.getName() + e);
				throw new JRException("Error writing to StringBuffer writer : " + jasperPrint.getName(), e);
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						logger.error("Error closing StringBuffer writer : " + jasperPrint.getName() + e);
					}
				}
			}
		} else {
			writer = (Writer) parameters.get(JRExporterParameter.OUTPUT_WRITER);
			if (writer != null) {
				try {
					exportReportToWriter();
				} catch (IOException e) {
					logger.error("Error writing to writer : " + jasperPrint.getName() + e);
					throw new JRException("Error writing to writer : " + jasperPrint.getName(), e);
				}
			} else {
				OutputStream os = (OutputStream) parameters.get(JRExporterParameter.OUTPUT_STREAM);
				if (os != null) {
					try {
						writer = new OutputStreamWriter(os);
						exportReportToWriter();
					} catch (IOException e) {
						logger.error("Error writing to OutputStream writer : " + jasperPrint.getName() + e);
						throw new JRException("Error writing to OutputStream writer : " + jasperPrint.getName(), e);
					}
				}
			}
		}
	}

	protected void exportReportToWriter() throws JRException, IOException {
		for (int reportIndex = 0; reportIndex < jasperPrintList.size(); reportIndex++) {
			setJasperPrint(jasperPrintList.get(reportIndex));

			List<JRPrintPage> pages = jasperPrint.getPages();
			if (pages != null && pages.size() > 0) {
				if (isModeBatch) {
					startPageIndex = 0;
					endPageIndex = pages.size() - 1;
				}

				for (int i = startPageIndex; i <= endPageIndex; i++) {
					if (Thread.interrupted()) {
						logger.error("Current thread interrupted.");
						throw new JRException("Current thread interrupted.");
					}

					JRPrintPage page = pages.get(i);
					exportPage(page);
				}
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("pages", jasperPrint.getPages().size());
		if (jsonResponse.getValue() != null) {
			jsonObject.put("aaData", jsonResponse.getValue().toString());
		} else {
			jsonObject.put("aaData", "");
		}
		jsonObject.put("headers", jsonResponse.getHeaders());
		jsonObject.put("headerCount", jsonResponse.getHeaderCount());
		jsonObject.put("iTotalRecords", jsonResponse.getViewResultsCount());
		jsonObject.put("iTotalDisplayRecords", jsonResponse.getTotalResultsCount());
		writer.write(jsonObject.toString());
		writer.flush();
	}

	protected ExporterNature getExporterNature() {
		if (nature == null) {
			nature = new JRCsvExporterNature(filter);
		}
		return nature;
	}

	protected void exportPage(JRPrintPage page) {
		nature = getExporterNature();
		JRGridLayout layout = new JRGridLayout(nature, page.getElements(), jasperPrint.getPageWidth(), jasperPrint.getPageHeight(), globalOffsetX, globalOffsetY, null);
		JRExporterGridCell[][] grid = layout.getGrid();
		// CutsInfo xCuts = layout.getXCuts();
		CutsInfo yCuts = layout.getYCuts();
		JRPrintElement element = null;
		String text = null;
		ArrayList<String> headers = new ArrayList<String>();
		HashMap<String, Integer> keys = new HashMap<String, Integer>();
		int headerId = 0;
		for (int y = 0; y < grid.length; y++) {
			if (yCuts.isCutNotEmpty(y)) {
				for (int x = 0; x < grid[y].length; x++) {
					if (grid[y][x].getWrapper() != null) {
						element = grid[y][x].getWrapper().getElement();
						if (element instanceof JRPrintText) {
							JRStyledText styledText = getStyledText((JRPrintText) element);
							if (styledText == null) {
								text = "";
							} else {
								text = styledText.getText();
								String origin = ((JRTemplatePrintText) element).getTemplate().getOrigin()!=null?((JRTemplatePrintText) element).getTemplate().getOrigin().getBandTypeValue().toString():"";
								String key = ((JRTemplatePrintText) element).getTemplate().getKey();
								if (origin.equals(BandTypeEnum.COLUMN_HEADER.toString())) {
									keys.put(key, headerId++);
									headers.add(text.trim());
								}
							}
						}
					}
				}
			}
		}
		jsonResponse.setHeaders(headers);
		jsonResponse.setHeaderCount(headers.size());
		output.append("[");
		int detailCount = 0;
		for (int y = 0; y < grid.length; y++) {
			if (yCuts.isCutNotEmpty(y)) {
				for (int x = 0; x < grid[y].length; x++) {
					if (grid[y][x].getWrapper() != null) {
						element = grid[y][x].getWrapper().getElement();
						if (element instanceof JRPrintText) {
							String origin = ((JRTemplatePrintText) element).getTemplate().getOrigin()!=null?((JRTemplatePrintText) element).getTemplate().getOrigin().getBandTypeValue().toString():"";
							String key = ((JRTemplatePrintText) element).getTemplate().getKey();
							JRStyledText styledText = getStyledText((JRPrintText) element);
							/*int xx = element.getX();
							int yy = element.getY();*/
							text = styledText.getText();
							if (origin.equals(BandTypeEnum.TITLE.toString())) {
								title.append(text);
							}
							if (origin.equals(BandTypeEnum.DETAIL.toString())) {
								detailCount++;
								if (keys.get(key).equals(0)) {
									output.append("{");
								}
								if (text.equals("null")) {
									text = "";
								}
								output.append("\"" + headers.get(keys.get(key)) + "\":\"" + text + "\"");
								if (keys.get(key).equals(headers.size() - 1)) {
									output.append("},");
								} else {
									output.append(",");
								}
							}
						}
					}
				}
			}
		}
		if (detailCount == 0) {
			output = new StringBuffer();
		} else {
			output.deleteCharAt(output.lastIndexOf(","));
			output.append("]");
		}
		jsonResponse.setViewResultsCount(jsonResponse.getHeaderCount() > 0 ? detailCount / jsonResponse.getHeaderCount() : 0);
		jsonResponse.setTotalResultsCount(parameters.get(JSONExporterParameter.TOTAL_RECORDS) != null ? (Long) parameters.get(JSONExporterParameter.TOTAL_RECORDS)
				: jsonResponse.getViewResultsCount());
		jsonResponse.setValue(output);
	}

	@Override
	protected JRStyledText getStyledText(JRPrintText textElement) {
		return textElement.getFullStyledText(JRStyledTextAttributeSelector.NONE);
	}

	@Override
	protected String getExporterKey() throws JRException {
		return null;
	}
}
