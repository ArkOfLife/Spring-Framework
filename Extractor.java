package com.example.table;

import org.apache.pdfbox.text.TextPosition;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Joby Wilson Mathews
 *
 */
public class Extractor {

	@JsonIgnore
	private TextPosition textPosition;
	
	@JsonIgnore
	private Extractor parent;

	public TextPosition getTextPosition() {
		return textPosition;
	}

	public void setTextPosition(TextPosition textPosition) {
		this.textPosition = textPosition;
	}

	public Extractor getParent() {
		return parent;
	}

	public void setParent(Extractor parent) {
		this.parent = parent;
	}
	
	
}
