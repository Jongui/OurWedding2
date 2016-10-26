package joaogd53.com.br.imageloader;

import android.content.Context;

import java.io.File;

/**
 * Created by root on 20/08/16.
 */
public class FileCache {
    private File cacheDir;

    public FileCache(Context context) {
// Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            cacheDir = new File(
                    android.os.Environment.getExternalStorageDirectory(),
                    "OurWeddingCache");
        else
            cacheDir = context.getCacheDir();
        if (!cacheDir.exists()){
            boolean b = cacheDir.mkdirs();
            if (!b){
                b = cacheDir.mkdirs();
            }
        }
        if (!cacheDir.exists()){
            cacheDir.mkdirs();
        }

    }

    public File getFile(String url) {
        String filename = String.valueOf(url.hashCode());
// String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        return f;

    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        for (File f : files)
            f.delete();
    }

}
