package com.example.fileeditor;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

class SaveThread extends Thread {
	private final String text;
	private final File fileToEdit;
	Context ctxt;


	SaveThread(String text, File fileToEdit, Context context) {
		this.text = text;
		ctxt = context.getApplicationContext();
		this.fileToEdit = fileToEdit;
	}

	@Override
	public void run() {
		try {
			fileToEdit.getParentFile().mkdirs();
			FileOutputStream fos = new FileOutputStream(fileToEdit);
			Writer w = new BufferedWriter(new OutputStreamWriter(fos));
			try {
				w.write(text);
				w.flush();
				fos.getFD().sync();
			} finally {
				w.close();
				String[] paths = {fileToEdit.getAbsolutePath()};
				MediaScannerConnection.scanFile(ctxt, paths, null, null);

			}
		} catch (IOException e) {
			Log.e(getClass().getSimpleName(), "Exception writing file", e);
		}
	}
}

