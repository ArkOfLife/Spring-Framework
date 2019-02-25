package com.example.table;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NotesExtractor extends PDFTextStripper {

	public NotesExtractor() throws IOException {
		super();
	}

	public static void main(String[] args) throws InvalidPasswordException, IOException {
		PDDocument document = PDDocument.load(new File("D:\\JSON\\Test.pdf"));
		PDPage pdPage = document.getPage(10);

		PDFTextStripper pdfTextStripper = new NotesExtractor();
		pdfTextStripper.setSortByPosition(true);
		pdfTextStripper.setStartPage(10);
		pdfTextStripper.setEndPage(14);

		Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());

		pdfTextStripper.writeText(document, dummy);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(noteList));

	}

	@Override
	protected void writeParagraphStart() throws IOException {
		paragraphStart = true;
	}

	public void addContent(String text, Content parentContent) {
		Content content = new Content();
		content.setText(text);
		ArrayList<Content> list = new ArrayList<>();
		ArrayList<Content> subContent = new ArrayList<>();
		content.setTextPosition(currentTextPosition);
		content.setList(list);
		content.setParent(parentContent);
		content.setSubContent(subContent);
		if(parentContent!=null) {
			if(parentContent.getText().startsWith("<b>")&&parentContent.getText().endsWith("</b>")) {
				if (text.startsWith("• ") || text.startsWith("o ")) {
					Content sub=null;
					if(parentContent.getSubContent().isEmpty()) {
						sub=new Content();
						ArrayList<Content> sublist = new ArrayList<>();
						sublist.add(content);
						sub.setList(sublist);
						parentContent.getSubContent().add(sub);
					}else{
						sub=parentContent.getSubContent().get(parentContent.getSubContent().size()-1);
						sub.getList().add(content);
					}
					
				}else {
					parentContent.getSubContent().add(content);
				}
				
			}else {
			parentContent.getList().add(content);
		}
		}else {
			currentNote.getContent().add(content);
		}
		previousObject = content;
	}

	public Content findParent(Content content) {
		if(content!=null) {
			if (currentTextPosition.getFont().getName().contains("Bold")
					&& content.getTextPosition().getFont().getName().contains("Bold")) {
				return content.getParent();

			} 
			if (currentTextPosition.getFont().getName().contains("Bold")
					&& content.getTextPosition().getFont().getName().contains("Bold")) {
				return content.getParent();

			} else if (currentTextPosition.getFont().getName().contains("Bold")
					&& !content.getTextPosition().getFont().getName().contains("Bold")) {
				return findParent(content.getParent());
			} else if(!currentTextPosition.getFont().getName().contains("Bold")
					&& !content.getTextPosition().getFont().getName().contains("Bold")) {
				return content.getParent();
			}
		}
		return content;
	}

	@Override
	protected void writeParagraphEnd() throws IOException {

		System.out.println(contentText);
		contentText = contentText.trim();
		currentSubHeading = currentSubHeading.trim();
		String fontName = currentTextPosition.getFont().getName();
		String allignment = Allignment.getAllignment(currentTextPosition.getX());
		if(!contentText.isEmpty()) {
			if (previousObject != null) {
				if (previousObject.getClass() == Note.class) {
					Note previousNote = (Note) previousObject;
					if (contentText != null || !contentText.isEmpty()) {

						if (fontName.equals(previousNote.getTextPosition().getFont().getName())
								&& fontName.contains("Bold")) {
							previousNote.setHeading(previousNote.getHeading() + " " + contentText);
							contentText = "";
						} else  {
							addContent(contentText, null);
						}
					}
				} else if (previousObject.getClass() == Content.class) {
					Content previousContent = (Content) previousObject;
					addContent(contentText, findParent(previousContent));

				}
			} else{

				Note note = new Note();
				note.setHeading(contentText);
				ArrayList<Content> contentList = new ArrayList<>();
				note.setContent(contentList);
				note.setTextPosition(currentTextPosition);
				previousObject = note;
				currentNote = note;
				noteList.add(note);
				contentText = "";

			}
		}

		contentText = "";
		paragraphStart = true;
		super.writeParagraphEnd();
	}

	@Override
	protected void setListItemPatterns(List<Pattern> patterns) {
		super.setListItemPatterns(patterns);
	}

	Note currentNote;
	static boolean paragraphStart = true;
	static String currentSubHeading = "";
	static String contentText = "";
	boolean bulletPoint;
	static ArrayList<Note> noteList = new ArrayList<>();
	Object previousObject;
	TextPosition currentTextPosition;

	@Override
	protected void writeString(String text, List<TextPosition> textPositionsList) throws IOException {
		if (!text.trim().isEmpty()) {
			String heading = "";
			String boltText = "";
			String subHeadingText = "";
			currentTextPosition = textPositionsList.get(0);
			String allignment = Allignment.getAllignment(textPositionsList.get(0).getX());
			if (text.startsWith("• ") || text.startsWith("o ")) {
				bulletPoint = true;
			} else {
				bulletPoint = false;
			}
			for (TextPosition textPos : textPositionsList) {
				if(textPos.getY()<750) {
					String letter = textPos.getUnicode();
					boolean containBold = textPos.getFont().getName().contains("Bold");

					if (containBold && paragraphStart && allignment.equals("CENTER")) {
						heading = heading + letter;
					} else if (containBold && paragraphStart) {
						subHeadingText = subHeadingText + letter;
					} else if (containBold) {
						paragraphStart = false;
						boltText = boltText + letter;
					} else {
						paragraphStart = false;
						contentText = contentText + letter;
					}
				}
				
			}

			if (!subHeadingText.equals("")) {
				contentText = "<b>"+subHeadingText+"</b>";

			}

			if (!heading.equals("")) {
				contentText = heading;
				heading = "";
			}
		}

	}
}
