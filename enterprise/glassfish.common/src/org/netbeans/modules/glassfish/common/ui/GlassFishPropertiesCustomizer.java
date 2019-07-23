/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.glassfish.common.ui;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import org.netbeans.modules.glassfish.common.GlassFishLogger;
import org.netbeans.modules.glassfish.common.GlassfishInstance;
import org.netbeans.modules.glassfish.spi.CustomizerCookie;
import org.openide.util.Lookup;

/**
 * GlassFish server properties customizer.
 * <p/>
 * @author Tomas Kraus
 */
public class GlassFishPropertiesCustomizer extends JTabbedPane {

    ////////////////////////////////////////////////////////////////////////////
    // Inner classes                                                          //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * GlassFish server properties customizer events handler.
     */
    private static class CustomizerListener implements AncestorListener {

        /** GlassFish server instance being customized. */
        private final GlassfishInstance instance;

        /**
         * Creates an instance of GlassFish server properties customizer
         * events handler.
         * <p/>
         * @param instance GlassFish server instance being customized.
         */
        CustomizerListener(final GlassfishInstance instance) {
            this.instance = instance;
        }

        /**
         * Called when the source or one of its ancestors is made visible.
         * <p/>
         * Currently do nothing.
         * <p/>
         * @param event An event reported to listener.
         */
        @Override
        public void ancestorAdded(final AncestorEvent event) {}

        /**
         * Called when the source or one of its ancestors is made invisible.
         * <p/>
         * Persist updated properties.
         * <p/>
         * @param event An event reported to listener.
         */
        @Override
        public void ancestorRemoved(final AncestorEvent event) {
            LOGGER.log(Level.INFO, "Storing {0} atributes",
                    instance.getDisplayName());
            try {
                // #254197
                // this is weird, but prevents reintroducing the instance that
                // has just been removed from panel (and thus the customizer as
                // well)
                if(instance.getInstanceProvider().getInstance(instance.getUrl()) != null) {
                    GlassfishInstance.writeInstanceToFile(instance);
                }
            } catch(IOException ex) {
                LOGGER.log(Level.INFO,
                        "Could not store GlassFish server attributes", ex);
            }
        }

        /**
         * Called when either the source or one of its ancestors is moved.
         * <p/>
         * Currently do nothing.
         * <p/>
         * @param event An event reported to listener.
         */
        @Override
        public void ancestorMoved(final AncestorEvent event) {}
        
    }

    ////////////////////////////////////////////////////////////////////////////
    // Class attributes                                                       //
    ////////////////////////////////////////////////////////////////////////////

    /** Local logger. */
    private static final Logger LOGGER
            = GlassFishLogger.get(GlassFishPropertiesCustomizer.class);

    ////////////////////////////////////////////////////////////////////////////
    // Instance attributes                                                    //
    ////////////////////////////////////////////////////////////////////////////

    /** Customizer events listener. */
    private final CustomizerListener customizerListener;

    ////////////////////////////////////////////////////////////////////////////
    // Constructors                                                           //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Creates an instance of GlassFish server properties customizer.
     * <p/>
     * @param instance GlassFish server instance being customized.
     * @param lookup   GlassFish server instance local lookup.
     */
    public GlassFishPropertiesCustomizer(
            GlassfishInstance instance, Lookup lookup) {
        customizerListener = new CustomizerListener(instance);
        addAncestorListener(customizerListener);
        JPanel commonCustomizer = instance.isRemote()
                ? new InstanceRemotePanel(instance)
                : new InstanceLocalPanel(instance);
        JPanel vmCustomizer = new VmCustomizer(instance);

        Collection<JPanel> pages = new LinkedList<>();
        Collection<? extends CustomizerCookie> lookupAll
                = lookup.lookupAll(CustomizerCookie.class);
        for(CustomizerCookie cookie : lookupAll) {
            pages.addAll(cookie.getCustomizerPages());
        }
        pages.add(vmCustomizer);
        add(commonCustomizer);
        for (JPanel page : pages) {
            add(page);
        }
    }

}
