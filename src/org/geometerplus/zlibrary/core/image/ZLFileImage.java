/*
 * Copyright (C) 2007-2011 Geometer Plus <contact@geometerplus.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */

package org.geometerplus.zlibrary.core.image;

import java.io.*;

import org.geometerplus.zlibrary.core.filesystem.ZLFile;

public class ZLFileImage extends ZLSingleImage {
	private final ZLFile myFile;
	private final int myOffset;
	private final int myLength;
	
	public ZLFileImage(String mimeType, ZLFile file, int offset, int length) {
		super(mimeType);
		myFile = file;
		myOffset = offset;
		myLength = length;
	}

	public ZLFileImage(String mimeType, ZLFile file) {
		this(mimeType, file, 0, (int)file.size());
	}

	public String getURI() {
		return "imagefile://" + myFile.getPath() + "\000" + myOffset + "\000" + myLength;
	}

	@Override
	public byte[] byteData() {
		InputStream stream = null;
		try {
			stream = myFile.getInputStream();
			int toSkip = myOffset - (int)stream.skip(myOffset);
			while (--toSkip >= 0) {
				stream.read();
			}

			byte[] buffer = new byte[myLength];
			int len = 0;
			while (len < myLength) {
				final int part = stream.read(buffer);
				if (part <= 0) {
					return new byte[0];
				}
				len += part;
			}
			return buffer;
		} catch (IOException e) {
			return new byte[0];
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
