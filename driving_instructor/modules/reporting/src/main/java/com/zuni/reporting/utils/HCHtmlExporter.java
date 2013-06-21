package com.zuni.reporting.utils;

import java.awt.Color;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.engine.JRPrintHyperlink;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.export.JRExporterGridCell;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.fill.JRTemplatePrintElement;
import net.sf.jasperreports.engine.fonts.FontFamily;
import net.sf.jasperreports.engine.fonts.FontInfo;
import net.sf.jasperreports.engine.type.RunDirectionEnum;
import net.sf.jasperreports.engine.util.JRColorUtil;
import net.sf.jasperreports.engine.util.JRFontUtil;
import net.sf.jasperreports.engine.util.JRStringUtil;
import net.sf.jasperreports.engine.util.JRStyledText;
import net.sf.jasperreports.engine.util.JRTextAttribute;

public class HCHtmlExporter extends JRHtmlExporter {

	/**
	 *
	 */
	@Override
	protected void exportStyledText(JRStyledText styledText, Locale locale) throws IOException {
		exportStyledText(styledText, null, locale);
	}

	/**
	 *
	 */
	protected void exportStyledText(JRStyledText styledText, String tooltip, Locale locale, String origin) throws IOException {
		String text = styledText.getText();

		int runLimit = 0;

		AttributedCharacterIterator iterator = styledText.getAttributedString().getIterator();

		boolean first = true;
		boolean startedSpan = false;
		while (runLimit < styledText.length() && (runLimit = iterator.getRunLimit()) <= styledText.length()) {
			//if there are several text runs, write the tooltip into a parent <span>
			if (first && runLimit < styledText.length() && tooltip != null) {
				startedSpan = true;
				writer.write("<span title=\"");
				writer.write(JRStringUtil.xmlEncode(tooltip));
				writer.write("\">");
				//reset the tooltip so that inner <span>s to not use it
				tooltip = null;
			}
			first = false;

			exportStyledTextRun(iterator.getAttributes(), text.substring(iterator.getIndex(), runLimit), tooltip, locale, origin);

			iterator.setIndex(runLimit);
		}

		if (startedSpan) {
			writer.write("</span>");
		}
	}

	/**
	 *
	 */
	@Override
	protected void exportStyledTextRun(Map<Attribute, Object> attributes, String text, Locale locale) throws IOException {
		exportStyledTextRun(attributes, text, null, locale);
	}

	/**
	 *
	 */
	@SuppressWarnings("deprecation")
	protected void exportStyledTextRun(Map<Attribute, Object> attributes, String text, String tooltip, Locale locale, String origin) throws IOException {
		String fontFamilyAttr = (String) attributes.get(TextAttribute.FAMILY);
		String fontFamily = fontFamilyAttr;
		if (fontMap != null && fontMap.containsKey(fontFamilyAttr)) {
			fontFamily = fontMap.get(fontFamilyAttr);
		} else {
			FontInfo fontInfo = JRFontUtil.getFontInfo(fontFamilyAttr, locale);
			if (fontInfo != null) {
				//fontName found in font extensions
				FontFamily family = fontInfo.getFontFamily();
				String exportFont = family.getExportFont(getExporterKey());
				if (exportFont != null) {
					fontFamily = exportFont;
				}
			}
		}

		boolean localHyperlink = false;
		JRPrintHyperlink hyperlink = (JRPrintHyperlink) attributes.get(JRTextAttribute.HYPERLINK);
		if (!hyperlinkStarted && hyperlink != null) {
			startHyperlink(hyperlink);
			localHyperlink = true;
		}

		writer.write("<span style=\"font-family: ");
		writer.write(fontFamily);
		writer.write("; ");

		Color forecolor = (Color) attributes.get(TextAttribute.FOREGROUND);
		if (!hyperlinkStarted || !Color.black.equals(forecolor)) {
			writer.write("color: #");
			writer.write(JRColorUtil.getColorHexa(forecolor));
			writer.write("; ");
		}

		Color runBackcolor = (Color) attributes.get(TextAttribute.BACKGROUND);
		if (runBackcolor != null) {
			writer.write("background-color: #");
			writer.write(JRColorUtil.getColorHexa(runBackcolor));
			writer.write("; ");
		}

		writer.write("font-size: ");
		writer.write(toSizeUnit(((Float) attributes.get(TextAttribute.SIZE)).intValue()));
		writer.write(";");

		/*
		if (!horizontalAlignment.equals(CSS_TEXT_ALIGN_LEFT))
		{
			writer.write(" text-align: ");
			writer.write(horizontalAlignment);
			writer.write(";");
		}
		*/

		if (TextAttribute.WEIGHT_BOLD.equals(attributes.get(TextAttribute.WEIGHT))) {
			writer.write(" font-weight: bold;");
		}
		if (TextAttribute.POSTURE_OBLIQUE.equals(attributes.get(TextAttribute.POSTURE))) {
			writer.write(" font-style: italic;");
		}
		if (TextAttribute.UNDERLINE_ON.equals(attributes.get(TextAttribute.UNDERLINE))) {
			writer.write(" text-decoration: underline;");
		}
		if (TextAttribute.STRIKETHROUGH_ON.equals(attributes.get(TextAttribute.STRIKETHROUGH))) {
			writer.write(" text-decoration: line-through;");
		}

		if (TextAttribute.SUPERSCRIPT_SUPER.equals(attributes.get(TextAttribute.SUPERSCRIPT))) {
			writer.write(" vertical-align: super;");
		} else if (TextAttribute.SUPERSCRIPT_SUB.equals(attributes.get(TextAttribute.SUPERSCRIPT))) {
			writer.write(" vertical-align: sub;");
		}
		writer.write("\"");
		writer.write(" origin=\"" + origin + "\"");

		if (tooltip != null) {
			writer.write(" title=\"");
			writer.write(JRStringUtil.xmlEncode(tooltip));
			writer.write("\"");
		}

		writer.write(">");

		writer.write(JRStringUtil.htmlEncode(text));

		writer.write("</span>");

		if (localHyperlink) {
			endHyperlink();
		}
	}

	/**
	 *
	 */
	@Override
	protected void exportText(JRPrintText text, JRExporterGridCell gridCell) throws IOException {
		JRStyledText styledText = getStyledText(text);

		int textLength = 0;

		if (styledText != null) {
			textLength = styledText.length();
		}

		writeCellStart(gridCell);//FIXME why dealing with cell style if no text to print (textLength == 0)?

		if (text.getRunDirectionValue() == RunDirectionEnum.RTL) {
			writer.write(" dir=\"rtl\"");
		}

		StringBuffer styleBuffer = new StringBuffer();

		String verticalAlignment = HTML_VERTICAL_ALIGN_TOP;

		switch (text.getVerticalAlignmentValue()) {
			case BOTTOM: {
				verticalAlignment = HTML_VERTICAL_ALIGN_BOTTOM;
				break;
			}
			case MIDDLE: {
				verticalAlignment = HTML_VERTICAL_ALIGN_MIDDLE;
				break;
			}
			case TOP:
			default: {
				verticalAlignment = HTML_VERTICAL_ALIGN_TOP;
			}
		}

		if (!verticalAlignment.equals(HTML_VERTICAL_ALIGN_TOP)) {
			styleBuffer.append(" vertical-align: ");
			styleBuffer.append(verticalAlignment);
			styleBuffer.append(";");
		}

		appendBackcolorStyle(gridCell, styleBuffer);
		appendBorderStyle(gridCell.getBox(), styleBuffer);
		appendPaddingStyle(text.getLineBox(), styleBuffer);

		String horizontalAlignment = CSS_TEXT_ALIGN_LEFT;

		if (textLength > 0) {
			switch (text.getHorizontalAlignmentValue()) {
				case RIGHT: {
					horizontalAlignment = CSS_TEXT_ALIGN_RIGHT;
					break;
				}
				case CENTER: {
					horizontalAlignment = CSS_TEXT_ALIGN_CENTER;
					break;
				}
				case JUSTIFIED: {
					horizontalAlignment = CSS_TEXT_ALIGN_JUSTIFY;
					break;
				}
				case LEFT:
				default: {
					horizontalAlignment = CSS_TEXT_ALIGN_LEFT;
				}
			}

			if (text.getRunDirectionValue() == RunDirectionEnum.LTR && !horizontalAlignment.equals(CSS_TEXT_ALIGN_LEFT) || text.getRunDirectionValue() == RunDirectionEnum.RTL
					&& !horizontalAlignment.equals(CSS_TEXT_ALIGN_RIGHT)) {
				styleBuffer.append("text-align: ");
				styleBuffer.append(horizontalAlignment);
				styleBuffer.append(";");
			}
		}

		if (isWrapBreakWord) {
			styleBuffer.append("width: " + toSizeUnit(gridCell.getWidth()) + "; ");
			styleBuffer.append("word-wrap: break-word; ");
		}

		if (text.getLineBreakOffsets() != null) {
			//if we have line breaks saved in the text, set nowrap so that
			//the text only wraps at the explicit positions
			styleBuffer.append("white-space: nowrap; ");
		}

		if (styleBuffer.length() > 0) {
			writer.write(" style=\"");
			writer.write(styleBuffer.toString());
			writer.write("\"");
		}

		writer.write(">");
		writer.write("<p style=\"overflow: hidden; ");

		switch (text.getParagraph().getLineSpacing()) {
			case SINGLE:
			default: {
				writer.write("line-height: 1.0; ");
				break;
			}
			case ONE_AND_HALF: {
				writer.write("line-height: 1.5; ");
				break;
			}
			case DOUBLE: {
				writer.write("line-height: 2.0; ");
				break;
			}
			case PROPORTIONAL: {
				writer.write("line-height: " + (int) (100 * text.getParagraph().getLineSpacingSize().floatValue()) + "%; ");
				break;
			}
			case AT_LEAST:
			case FIXED: {
				writer.write("line-height: " + text.getParagraph().getLineSpacingSize().floatValue() + "px; ");
				break;
			}
		}

		writer.write("text-indent: " + text.getParagraph().getFirstLineIndent().intValue() + "px; ");
		//		writer.write("margin-left: " + text.getParagraph().getLeftIndent().intValue() + "px; ");
		//		writer.write("margin-right: " + text.getParagraph().getRightIndent().intValue() + "px; ");
		//		writer.write("margin-top: " + text.getParagraph().getSpacingBefore().intValue() + "px; ");
		//		writer.write("margin-bottom: " + text.getParagraph().getSpacingAfter().intValue() + "px; ");
		writer.write("\">");

		if (text.getAnchorName() != null) {
			writer.write("<a name=\"");
			writer.write(text.getAnchorName());
			writer.write("\"/>");
		}

		startHyperlink(text);

		if (textLength > 0) {
			//only use text tooltip when no hyperlink present
			String textTooltip = hyperlinkStarted ? null : text.getHyperlinkTooltip();
			exportStyledText(styledText, textTooltip, getTextLocale(text), ((JRTemplatePrintElement) text).getTemplate().getOrigin().getBandTypeValue().toString());
		} else {
			writer.write(emptyCellStringProvider.getStringForEmptyTD(imagesURI));
		}

		endHyperlink();

		writer.write("</p>");

		writeCellEnd(gridCell);
	}

}
