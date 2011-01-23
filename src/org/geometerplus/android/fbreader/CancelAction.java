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

package org.geometerplus.android.fbreader;

import android.view.*;

import org.geometerplus.zlibrary.ui.android.R;

import org.geometerplus.fbreader.fbreader.FBAction;
import org.geometerplus.fbreader.fbreader.FBReaderApp;

class CancelAction extends FBAction implements View.OnCreateContextMenuListener, FBReader.ContextMenuHandler {
	private final FBReader myBaseActivity;

	CancelAction(FBReader baseActivity, FBReaderApp fbreader) {
		super(fbreader);
		myBaseActivity = baseActivity;
	}

	private static final int ITEM_PREVIOUS_BOOK = 0;
	private static final int ITEM_GO_BACK_1 = 1;
	private static final int ITEM_GO_BACK_2 = 2;
	private static final int ITEM_GO_BACK_3 = 3;
	private static final int ITEM_CLOSE_FBREADER = 4;
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		menu.add(0, ITEM_PREVIOUS_BOOK, 0, "Open previous book");
		menu.add(0, ITEM_GO_BACK_1, 0, "Жили у бабуси...");
		menu.add(0, ITEM_GO_BACK_2, 0, "Жили у бабуси...");
		menu.add(0, ITEM_GO_BACK_3, 0, "Жили у бабуси...");
		menu.add(0, ITEM_CLOSE_FBREADER, 0, "Close FBReader");
	}

	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case ITEM_PREVIOUS_BOOK:
				break;
			case ITEM_CLOSE_FBREADER:
				Reader.closeWindow();
				break;
		}
		myBaseActivity.setContextMenuHandler(null);
		return true;
	}

	@Override
	public void run() {
		if (Reader.getCurrentView() != Reader.BookTextView) {
			Reader.showBookTextView();
		} else {
			final View view = myBaseActivity.findViewById(R.id.root_view);
			view.setOnCreateContextMenuListener(this);
			myBaseActivity.setContextMenuHandler(this);
			myBaseActivity.openContextMenu(view);
		}
	}
}
