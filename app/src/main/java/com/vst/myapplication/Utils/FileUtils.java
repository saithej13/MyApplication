package com.vst.myapplication.Utils;

import android.content.Context;
import android.os.PowerManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUtils {
    public interface DownloadListner
    {
        public void onProgrss(int count);
        public void onComplete();
        public void onError();
    }
    public static synchronized String downloadSQLITE(String sUrl, String directory, Context context, String sqliteName, DownloadListner downloadListener)
    {
        if(!sUrl.contains(".zip"))
            sUrl  = sUrl.replace("sqlite","zip");
        System.gc();

        if ( sUrl == null || sUrl.length() <= 0)
            return null;

        String sqliteFilePath = sqliteName+".zip";

        File file =  new File(directory, sqliteFilePath);

        if(file.exists())
            file.delete();
        //		File masterFiles =  new File(directory);
        //		if(masterFiles.isDirectory() && masterFiles.exists())
        //		{
        //
        //			LogUtils.defaultLog("downloadSQLITE", "file directory");
        //			for(File f : masterFiles.listFiles())
        //			{
        //				LogUtils.defaultLog("downloadSQLITE", "filename ="+f.getName());
        //				f.delete();
        //			}
        //		}
        //
        String localFilePath = directory+sqliteFilePath;

        try
        {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
            sUrl = sUrl.replace(" ", "%20");

            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;

            try
            {

                URL url = new URL(sUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return null;
                }


                int fileLength = connection.getContentLength();

                input = connection.getInputStream();
                output = new FileOutputStream(localFilePath);

                byte data[] = new byte[1024];
                long total = 0;
                int count;
                LogUtils.defaultLog("downloadSQLITE", "filelength="+fileLength+"");
                while ((count = input.read(data)) != -1)
                {
                    if(NetworkUtils.isNetworkAvailable(context))
                    {
                        LogUtils.defaultLog("downloadSQLITE", "count-start ="+count);
                        total += count;
                        if (fileLength > 0)
                            downloadListener.onProgrss((int) (total * 100 / fileLength));
                        LogUtils.defaultLog("downloadSQLITE", "count-end ="+total);
                        output.write(data, 0, count);
                    }
                    else
                    {
                        try
                        {
                            if (output != null)
                                output.close();
                            if (input != null)
                                input.close();
                        }
                        catch (IOException ignored)
                        {}

                        if (connection != null)
                            connection.disconnect();
                        return null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            finally
            {
                try
                {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                }
                catch (IOException ignored)
                {}

                if (connection != null)
                    connection.disconnect();
            }

            ZipUtils.upZipFile(file, directory);

            count = 0;
            downloadListener.onComplete();
            return localFilePath;
        }

        catch (Exception e)
        {
            file.delete();

            if(count < 3)
            {
                count++;
                e.printStackTrace();
                return downloadSQLITE(sUrl,directory, context,  sqliteName,downloadListener);
            }
            else
            {
                count = 0;
                downloadListener.onError();
                return null;
            }
        }
    }
    static int count = 0;
}
