package com.warp.android;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;

public class WarpStorage {

    private static WarpSessionKey mSessionKey;

    private static WarpStorage warpStorage;

    public static void init() {
        WarpStorage.setStorage(new WarpStorage());
    }

    public static void setStorage(WarpStorage storage) {
        if (warpStorage == null) {warpStorage = storage;}

    }

    public static WarpStorage getStorage() {
        return warpStorage;
    }

    public static WarpSessionKey getSessionKey() { return mSessionKey; }

    public static class Storage extends WarpStorage {

        private static Context context;

        public static void Init(Context _context) {
            if (_context == null) {
                throw new NullPointerException("WarpStorage context must not be null");
            }
            context = _context;
        }

        public static File setFileDirectory(File file) {
            if (!file.exists()) {
                if (!file.mkdir()) {
                    return file;
                }
            }
            return file;
        }

        public static File getFileDirectory() {
            return context.getDir("", Context.MODE_PRIVATE);
        }

        public static void WriteFile(WarpSessionKey warpSessionKey) {
            DeleteFile();
            try {
                FileOutputStream fileOutputStream = context.openFileOutput(warpSessionKey.getSessionToken(), Context.MODE_PRIVATE);
                OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutputStream);

                String content = String.format("%s|%s|%s", warpSessionKey.getUserId(),
                        warpSessionKey.getUserName(),
                        warpSessionKey.getPasswordEnc());

                outputWriter.write(content);
                outputWriter.close();

                mSessionKey = warpSessionKey;

            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

        public static WarpSessionKey ReadFile(String sessionToken) {
            try {
                FileInputStream fileInputStream = context.openFileInput(sessionToken);
                InputStreamReader streamReader = new InputStreamReader(fileInputStream);

                char[] inputBuffer= new char[100];
                String content = "";
                int charRead;

                while ((charRead = streamReader.read(inputBuffer)) > 0) {
                    String readString = String.copyValueOf(inputBuffer, 0, charRead);
                    content += readString;
                }
                streamReader.close();

                String[] delimitedValues = content.split("\\|");

                return WarpSessionKey.add(Integer.parseInt(delimitedValues[0]),
                        sessionToken,
                        delimitedValues[1],
                        delimitedValues[2]);

            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

        public static void DeleteFile() {
            File[] files = context.getFilesDir().listFiles();
            if (files != null) {
                sortLastModified(files);
                for (File file : files) {
                    String fileName = file.getName();
                    if (fileName.length() == 10) {
                        file.delete();
                    }
                }
            }
        }

        public static WarpSessionKey GetFile() {
            File[] files = context.getFilesDir().listFiles();
            if (files.length != 0) {
                sortLastModified(files);

                for (File file : files) {
//                    System.out.println("GetFile(): " + file.getName() + ", " + file.lastModified());
                    String fileName = file.getName();
                    if (fileName.length() == 10) {
                        return ReadFile(fileName);
                    }
                }
            }
            return WarpSessionKey.add(0, "", "", "");
        }

        private static void sortLastModified(File[] files) {
            Arrays.sort(files, new Comparator<File>() {
                @Override
                public int compare(File file, File t1) {
                    if ((file).lastModified() > (t1).lastModified()) { return -1; }
                    else if ((file).lastModified() < (t1).lastModified()) { return +1; }
                    else { return 0; }
                }
            });
        }
    }

}
