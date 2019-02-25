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
public class Content {

	private String text;
	
	@JsonInclude(Include.NON_EMPTY)
	private List<Content> list;
	
	@JsonInclude(Include.NON_EMPTY)
	private List<Content> subContent;
	
	@JsonIgnore
	private Content parent;
	
	@JsonIgnore
	private TextPosition textPosition;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Content> getList() {
		return list;
	}

	public void setList(List<Content> list) {
		this.list = list;
	}

	public List<Content> getSubContent() {
		return subContent;
	}

	public void setSubContent(List<Content> subContent) {
		this.subContent = subContent;
	}


	public Content getParent() {
		return parent;
	}

	public void setParent(Content parent) {
		this.parent = parent;
	}

	public TextPosition getTextPosition() {
		return textPosition;
	}

	public void setTextPosition(TextPosition textPosition) {
		this.textPosition = textPosition;
	}

	
	
	
}
