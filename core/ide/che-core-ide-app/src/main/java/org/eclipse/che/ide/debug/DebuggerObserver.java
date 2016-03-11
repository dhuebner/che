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
package org.eclipse.che.ide.debug;

import org.eclipse.che.api.promises.client.Promise;

import java.util.List;

/**
 * @author Anatoliy Bazko
 */
public interface DebuggerObserver {

    /**
     * Event happens when debugger client connected to the server.
     */
    void onDebuggerAttached(DebuggerDescriptor debuggerDescriptor, Promise<Void> connect);

    /**
     * Event happens when debugger client disconnected from the server.
     */
    void onDebuggerDisconnected();

    /**
     * Event happens when breakpoint added.
     */
    void onBreakpointAdded(Breakpoint breakpoint);

    /**
     * Event happens when breakpoint activated.
     */
    void onBreakpointActivated(String filePath, int lineNumber);

    /**
     * Event happens when breakpoint deleted.
     */
    void onBreakpointDeleted(Breakpoint breakpoint);

    /**
     * Event happens when all breakpoint deleted.
     */
    void onAllBreakpointDeleted();

    /**
     * Event happens on step in.
     */
    void onStepIn();

    /**
     * Event happens on step out.
     */
    void onStepOut();

    /**
     * Event happens on step out.
     */
    void onStepOver();

    /**
     * Event happens when debugger resumed.
     */
    void onResume();

    /**
     * Event happens when debugger stopped at breakpoint.
     */
    void onBreakpointStopped(String className, int lineNumber);

    /**
     * Event happens when value changed.
     */
    void onValueChanged(List<String> path, String newValue);
}
