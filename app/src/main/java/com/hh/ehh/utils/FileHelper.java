package com.hh.ehh.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Marc on 24/02/2015.
 */
public class FileHelper {
    private File filesFolder;
    private static final String folderName = "EHH";
    private static final String fileType = ".png";
    public String DEFAULT_STORAGE;

    public FileHelper() {
        initFilesFolder();
    }

    private void initFilesFolder() {
        filesFolder = new File(Environment.getExternalStorageDirectory()+"/"+folderName);
        if (!filesFolder.exists()) {
            filesFolder.mkdir();
        }
        DEFAULT_STORAGE = filesFolder.getAbsolutePath();
    }

    public File checkExsistFile(String filePath, Bitmap bitmap){
        File file = createFileIfNotExist(filePath);
        storeFile(file, bitmap);
        return file;

    }

    private void storeFile(File file, Bitmap bitmap) {
        new StoreFileTask().execute(file,bitmap);
    }

    private File createFileIfNotExist(String filePath) {
        String fileName = filePath.substring(filePath.lastIndexOf('/')+1)+ fileType;
        return new File(filesFolder,fileName);
    }

    private class StoreFileTask extends AsyncTask<Object, Void,Void> {
        @Override
        protected Void doInBackground(Object... params) {
            File file = (File) params[0];
            Bitmap bitmap = (Bitmap) params[1];

            FileOutputStream fos;
            try {
                fos = new FileOutputStream(file,false);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                byte[] byteArray = bytes.toByteArray();
                fos.write(byteArray);
                fos.flush();
                fos.close();
                bytes.flush();
                byteArray.clone();
            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }

}
