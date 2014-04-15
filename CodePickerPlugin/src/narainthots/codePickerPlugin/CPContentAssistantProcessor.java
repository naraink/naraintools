package narainthots.codePickerPlugin;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.lang.StringEscapeUtils;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

public class CPContentAssistantProcessor implements IContentAssistProcessor {

	/**
	 * provides suggestions given the context
	 */
	
	CodePickerHelper cph = null;

	
	public ICompletionProposal[] computeCompletionProposals(ITextViewer arg0,
			int arg1) {
		String searchString = getPrefix(arg0, arg1);
		System.out.println("Search String to searched for: " + searchString);
		ArrayList suggestions = null;
		cph = new CodePickerHelper();
		
		try {
			suggestions = cph.getSuggestions(searchString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return buildResult(suggestions, arg1, searchString.length());

	}
	
	
	public IContextInformation[] computeContextInformation(ITextViewer arg0,
			int arg1) {
		
		return null;

	}

	/**
	 * If the last character is a space it returns an empty String If not it
	 * returns the last substring up to a space
	 */
	public String getPrefix(ITextViewer source, int currentPosition) {
		IDocument doc = source.getDocument();
		try {
			if (doc.getChar(currentPosition - 1) == ' ') {
				return ""; //$NON-NLS-1$
			} else {
				int pos = currentPosition - 1;
				char readChar;
				do {
					readChar = doc.getChar(pos);
					pos--;
				} while (pos >= 0 && readChar != '/' && readChar != '@'
						&& readChar != '.' && readChar != '\n');
				
				//Check for 3 slashes for invoke function. Other empty string is returned
				if (readChar=='/'){
							System.out.println(doc.get(pos + 2, currentPosition - 1 - pos));
							return doc.get(pos + 2, currentPosition - 1 - pos);
							
				}
				
				if (readChar == ' ' || readChar == '\n' || readChar == '.')
					pos++;
				return "";

			}
		} catch (BadLocationException e) {
			return ""; //$NON-NLS-1$
		}

	}

	//TODO check once tab = \t 
	public char[] getCompletionProposalAutoActivationCharacters() {
		return new char[] { '\t'};
	}

	private ICompletionProposal[] buildResult(ArrayList suggestions,
			int currentCursor, int replaceLength) {
		if (suggestions == null){
			System.out.println(
					"Cannot produce a suggestion. List is null");
		return null;
		}
		

		ICompletionProposal[] retProposals = new ICompletionProposal[suggestions
				.size()];
		Iterator<String> it = suggestions.iterator();
		ArrayList<String> titles = cph.getTitles();
		ArrayList<String> links = cph.getLinks();
		Iterator<String> itTiles = titles.iterator();
		Iterator<String> itLinks = links.iterator();

		int c = 0;
		while (it.hasNext()) {
			String cp = it.next();
			String title = itTiles.next();
			String link = itLinks.next();
			IContextInformation contextInfo = new ContextInformation(null,title);

			ICompletionProposal completion = new CompletionProposal(cp,
					currentCursor - replaceLength-1, replaceLength+1, currentCursor
							- replaceLength + cp.length(),null,title,contextInfo,cp+"                            "+link);
			
			retProposals[c] = completion;
			c++;
		}

		return retProposals;
	}
	




	
	public char[] getContextInformationAutoActivationCharacters() {
		// TODO Auto-generated method stub
		return new char[] { '#' };
	}



	
	public IContextInformationValidator getContextInformationValidator() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}
}