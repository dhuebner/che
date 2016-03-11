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

import org.eclipse.che.commons.annotation.Nullable;

import java.util.List;

/**
 * Breakpoint manager.
 *
 * @author Anatoliy Bazko
 */
public interface BreakpointManager {

    /**
     * Toggle / untoggle breakpoint.
     */
    void changeBreakpointState(int lineNumber);

    /**
     * @return current breakpoint
     */
    @Nullable
    Breakpoint getCurrentBreakpoint();

    /**
     * @return all breakpoints
     */
    List<Breakpoint> getBreakpointList();
}
