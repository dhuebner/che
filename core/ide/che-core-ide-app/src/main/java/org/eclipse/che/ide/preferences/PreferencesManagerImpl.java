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
package org.eclipse.che.ide.preferences;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.che.api.promises.client.Function;
import org.eclipse.che.api.promises.client.FunctionException;
import org.eclipse.che.api.promises.client.Promise;
import org.eclipse.che.api.promises.client.js.Promises;
import org.eclipse.che.api.user.gwt.client.UserProfileServiceClient;
import org.eclipse.che.commons.annotation.Nullable;
import org.eclipse.che.ide.api.preferences.PreferencesManager;

import java.util.HashMap;
import java.util.Map;

/**
 * The implementation of {@link PreferencesManager} for managing user preference.
 *
 * @author <a href="mailto:aplotnikov@codenvy.com">Andrey Plotnikov</a>
 */
@Singleton
public class PreferencesManagerImpl implements PreferencesManager {
    private final UserProfileServiceClient      userProfileService;
    private final Map<String, String>           changedPreferences;

    private Map<String, String> persistedPreferences;

    /**
     * Create preferences manager
     *
     * @param userProfileService
     *         user preference service client
     */
    @Inject
    protected PreferencesManagerImpl(UserProfileServiceClient userProfileService) {
        this.persistedPreferences = new HashMap<>();
        this.changedPreferences = new HashMap<>();
        this.userProfileService = userProfileService;
    }

    /** {@inheritDoc} */
    @Override
    @Nullable
    public String getValue(String preference) {
        if (changedPreferences.containsKey(preference)) {
            return changedPreferences.get(preference);
        }
        return persistedPreferences.get(preference);
    }

    /** {@inheritDoc} */
    @Override
    public void setValue(String preference, String value) {
        changedPreferences.put(preference, value);
    }

    /** {@inheritDoc} */
    @Override
    public Promise<Void> flushPreferences() {
        if (changedPreferences.isEmpty()) {
            return Promises.resolve(null);
        }

        return userProfileService.updatePreferences(changedPreferences).thenPromise(new Function<Map<String,String>, Promise<Void>>() {
            @Override
            public Promise<Void> apply(Map<String,String> result) throws FunctionException {
                persistedPreferences.putAll(changedPreferences);
                changedPreferences.clear();
                return Promises.resolve(null);
            }
        });
    }

    /** {@inheritDoc} */
    @Override
    public Promise<Map<String, String>> loadPreferences() {
        return userProfileService.getPreferences().then(new Function<Map<String, String>, Map<String, String>>() {
            @Override
            public Map<String, String> apply(Map<String, String> preferences) throws FunctionException {
                persistedPreferences = preferences;
                return preferences;
            }
        });
    }
}
