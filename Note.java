package com.example.table;

import java.util.List;

import org.apache.pdfbox.text.TextPosition;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Joby Wilson Mathews
 *
 */
@JsonInclude(Include.NON_NULL)
public class Note {

	private String heading;
	
	@JsonInclude(Include.NON_EMPTY)
	private List<Content> content;
	
	@JsonIgnore
	private TextPosition textPosition;
	
	@JsonIgnore
	private Object parent;

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public List<Content> getContent() {
		return content;
	}

	public void setContent(List<Content> content) {
		this.content = content;
	}

	public Object getParent() {
		return parent;
	}

	public void setParent(Object parent) {
		this.parent = parent;
	}

	public TextPosition getTextPosition() {
		return textPosition;
	}

	public void setTextPosition(TextPosition textPosition) {
		this.textPosition = textPosition;
	}

	
	
	
}
