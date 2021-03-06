/*******************************************************************************
 * Copyright (c) 2005, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package narainthots.codePickerPlugin;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jdt.ui.text.java.IJavaCompletionProposalComputer;


public final class CodePickerProposalComputer implements
		IJavaCompletionProposalComputer {
	/** The wrapped processor. */
	private final CPContentAssistantProcessor cpContentAssistantProcessor = new CPContentAssistantProcessor();

	/**
	 * Default ctor to make it instantiatable via the extension mechanism.
	 */
	public CodePickerProposalComputer() {
	}

	/*
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposalComputer#
	 * computeCompletionProposals
	 * (org.eclipse.jface.text.contentassist.TextContentAssistInvocationContext,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public List computeCompletionProposals(
			ContentAssistInvocationContext context, IProgressMonitor monitor) {
		return Arrays.asList(cpContentAssistantProcessor
				.computeCompletionProposals(context.getViewer(),
						context.getInvocationOffset()));
	}

	/*
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposalComputer#
	 * computeContextInformation
	 * (org.eclipse.jface.text.contentassist.TextContentAssistInvocationContext,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public List computeContextInformation(
			ContentAssistInvocationContext context, IProgressMonitor monitor) {
		return Arrays.asList(cpContentAssistantProcessor
				.computeContextInformation(context.getViewer(),
						context.getInvocationOffset()));
	}

	/*
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposalComputer#
	 * getErrorMessage()
	 */
	public String getErrorMessage() {
		return cpContentAssistantProcessor.getErrorMessage();
	}

	/*
	 * @see
	 * org.eclipse.jdt.ui.text.java.IJavaCompletionProposalComputer#sessionStarted
	 * ()
	 */
	public void sessionStarted() {
	}

	/*
	 * @see
	 * org.eclipse.jdt.ui.text.java.IJavaCompletionProposalComputer#sessionEnded
	 * ()
	 */
	public void sessionEnded() {
	}
}
