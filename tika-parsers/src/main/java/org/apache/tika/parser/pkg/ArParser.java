/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tika.parser.pkg;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.compress.archivers.ar.ArArchiveInputStream;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.CloseShieldInputStream;
import org.apache.tika.metadata.Metadata;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * Ar archive parser.
 */
public class ArParser extends PackageParser {

    /**
     * Parses the given stream as an ar archive.
     */
    public void parse(
            InputStream stream, ContentHandler handler, Metadata metadata)
            throws IOException, TikaException, SAXException {
        // At the end we want to close the ar stream to release any associated
        // resources, but the underlying document stream should not be closed
        ArArchiveInputStream ar =
            new ArArchiveInputStream(new CloseShieldInputStream(stream));
        try {
            parseArchive(ar, handler, metadata);
        } finally {
            ar.close();
        }
    }

}