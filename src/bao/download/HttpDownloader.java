package bao.download;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ProgressDialog;
import android.os.Environment;
import android.view.View;
import android.widget.ProgressBar;
import bao.utils.FileUtils;


public class HttpDownloader {

	/**
	 * 直接从网络获取文本数据
	 * @param urlStr
	 * @return
	 */
	public static String download(String urlStr) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			buffer = new BufferedReader(new InputStreamReader(urlConn
					.getInputStream()));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	/**
	 * 从网络获取文件下载到本地
	 * @param urlStr URL地址
	 * @param path 存储路径，不包含SDCardRoot
	 * @param fileName 文件名称
	 * @return 返回-1表示失败  1表示已经存在  0表示成功
	 */
	public static int downFile(String urlStr, String path, String fileName) {
		InputStream inputStream = null;
		try {
			FileUtils fileUtils = new FileUtils();
			if (fileUtils.isFileExist(fileName,path)) {
				return 1;
			} else {
				inputStream = getInputStreamFromUrl(urlStr);
				System.out.println("本地文件不存在，要开始下载，将下载回来的文件存到--->"+path+fileName);
				File resultFile = fileUtils.write2SDFromInput(path,fileName, inputStream);
				if (resultFile == null) {
					return -1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * 从URL获得InputStream
	 * 
	 * @param urlStr
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static InputStream getInputStreamFromUrl(String urlStr)
			throws MalformedURLException, IOException {
		URL url = new URL(urlStr);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		InputStream inputStream = urlConn.getInputStream();
		return inputStream;
	}
	/**
	 * 从服务器获取文件  与downloadFile类似，设置ProgressDialog进度
	 * @param URLpath 完整下载路径
	 * @param progressDialog
	 * @param completePath 完整存储路径
	 * @return
	 * @throws Exception
	 */
	public static File getFileFromServer(String URLpath, String completePath, ProgressDialog progressDialog )throws Exception{
	    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){   
	        URL url = new URL(URLpath);   
	        HttpURLConnection conn =  (HttpURLConnection) url.openConnection();   
	        conn.setConnectTimeout(5000);   
	        progressDialog.setMax(conn.getContentLength());   
	        InputStream is = conn.getInputStream();   
	        File file = new File(completePath);
	        FileOutputStream fos = new FileOutputStream(file);   
	        BufferedInputStream bis = new BufferedInputStream(is);   
	        byte[] buffer = new byte[1024];   
	        int len ;   
	        int total=0;   
	        while((len =bis.read(buffer))!=-1){   
	            fos.write(buffer, 0, len);   
	            total+= len;   
	            progressDialog.setProgress(total);   
	        }   
	        fos.close();   
	        bis.close();   
	        is.close();   
	        return file;   
	    }   
	    else{   
	        return null;   
	    }   
	}
	
	/**
	 * 从服务器获取文件  与downloadFile类似，设置progressBar进度
	 * @param URLpath 完整下载路径
	 * @param pb
	 * @param completePath 完整存储路径
	 * @return
	 * @throws Exception
	 */
	public static File getFileFromServer(String URLpath, String completePath,ProgressBar progressBar) throws Exception{
		progressBar.setVisibility(View.VISIBLE);
	    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){   
	        URL url = new URL(URLpath);   
	        HttpURLConnection conn =  (HttpURLConnection) url.openConnection();   
	        conn.setConnectTimeout(5000);   
	        progressBar.setMax(conn.getContentLength());   
	        InputStream is = conn.getInputStream();   
	        File file = new File(completePath);   
	        FileOutputStream fos = new FileOutputStream(file);   
	        BufferedInputStream bis = new BufferedInputStream(is);   
	        byte[] buffer = new byte[1024];   
	        int len ;   
	        int total=0;   
	        while((len =bis.read(buffer))!=-1){   
	            fos.write(buffer, 0, len);   
	            total+= len;   
	            progressBar.setProgress(total);   
	        }   
	        fos.close();   
	        bis.close();   
	        is.close();
	        progressBar.setVisibility(View.INVISIBLE);
	        return file;
	    }   
	    else{   
	        return null;   
	    }   
	}
}
