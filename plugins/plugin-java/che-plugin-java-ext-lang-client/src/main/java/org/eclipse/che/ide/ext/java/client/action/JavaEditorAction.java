/*******************************************************************************
 * Copyright (c) 2012-2016 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.ide.ext.java.client.action;

import com.google.api.client.util.Collections2;
import com.google.common.collect.Lists;

import org.eclipse.che.ide.MimeType;
import org.eclipse.che.ide.api.action.ActionEvent;
import org.eclipse.che.ide.api.action.ProjectAction;
import org.eclipse.che.ide.api.editor.EditorAgent;
import org.eclipse.che.ide.api.editor.EditorInput;
import org.eclipse.che.ide.api.filetypes.FileTypeRegistry;
import org.eclipse.che.ide.api.project.tree.VirtualFile;
import org.eclipse.che.ide.util.loging.Log;
import org.vectomatic.dom.svg.ui.SVGResource;

import java.util.Collections;
import java.util.List;

/**
 * Base action for Java editor related action.
 *
 * @author Evgen Vidolob
 */
public abstract class JavaEditorAction extends ProjectAction {

    private final FileTypeRegistry fileTypeRegistry;
    protected     EditorAgent      editorAgent;

    public JavaEditorAction(String text,
                            String description,
                            SVGResource svgIcon,
                            EditorAgent editorAgent,
                            FileTypeRegistry fileTypeRegistry) {
        super(text, description, svgIcon);
        this.editorAgent = editorAgent;
        this.fileTypeRegistry = fileTypeRegistry;
    }

    public JavaEditorAction(String text, String description, EditorAgent editorAgent, FileTypeRegistry fileTypeRegistry) {
        this(text, description, null, editorAgent, fileTypeRegistry);
    }

    @Override
    protected void updateProjectAction(ActionEvent e) {
        if (editorAgent.getActiveEditor() != null) {
            EditorInput input = editorAgent.getActiveEditor().getEditorInput();
            VirtualFile file = input.getFile();
            final List<String> mimeTypes = fileTypeRegistry.getFileTypeByFile(file).getMimeTypes();
            if (mimeTypes == null || mimeTypes.isEmpty()) {
                return;
            }
            String mediaType = mimeTypes.get(0);
            if (mediaType != null && (mediaType.equals(MimeType.TEXT_X_JAVA) ||
                                      mediaType.equals(MimeType.TEXT_X_JAVA_SOURCE) ||
                                      mediaType.equals(MimeType.APPLICATION_JAVA_CLASS))) {
                e.getPresentation().setEnabledAndVisible(true);
                return;
            }
        }
        e.getPresentation().setEnabledAndVisible(false);
    }
}
