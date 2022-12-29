/**
 *     Copyright (C) 2019-2020 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.parser.tosca;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipUtil {
	private static final Logger LOG = LoggerFactory.getLogger(ZipUtil.class);

	private ZipUtil() {
		// Nothing.
	}

	public static void makeToscaZip(final String dest, final Entry... toscaFile) throws IOException {
		try (FileOutputStream fos = new FileOutputStream(dest)) {
			try (ZipOutputStream zipOut = new ZipOutputStream(fos)) {
				for (final Entry srcFile : toscaFile) {
					try (InputStream is = ZipUtil.class.getClassLoader().getResourceAsStream(srcFile.classPath)) {
						LOG.info("Onpening {} = {} ", srcFile.classPath, is);
						final ZipEntry zipEntry = new ZipEntry(srcFile.zipName);
						zipOut.putNextEntry(zipEntry);

						final byte[] bytes = new byte[1024];
						int length;
						while ((length = is.read(bytes)) >= 0) {
							zipOut.write(bytes, 0, length);
						}
					}
				}
			}
		}
	}

	public static class Entry {
		public static Entry of(final String path, final String zip) {
			final Entry e = new Entry();
			e.classPath = path;
			e.zipName = zip;
			return e;
		}

		public String classPath;
		public String zipName;
	}
}
