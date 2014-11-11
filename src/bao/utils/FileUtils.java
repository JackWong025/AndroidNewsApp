package bao.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.os.Environment;

public class FileUtils {
	private String SDCardRoot;

	public FileUtils() {
		SDCardRoot = Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ File.separator;
	}

	/**
	 * 在SD卡内创建文件
	 * @param dir 不包含SDCardRoot和后面的分隔符\的路径
	 * @throws IOException
	 */
	public File createFileInSDCard(String fileName, String dir)
			throws IOException {
		File file = new File(SDCardRoot + dir + File.separator + fileName);
		System.out.println("创建以下文件---->" + SDCardRoot + dir + File.separator + fileName);
		if(file.createNewFile()){
			System.out.println("创建成功---->" + SDCardRoot + dir + File.separator + fileName);
		}else{
			System.out.println("创建失败---->" + SDCardRoot + dir + File.separator + fileName);
		}

		return file;
	}

	/**
	 * 创建路径
	 * 
	 * @param dir 不带SDCardRoot和后面的分隔符\的路径
	 */
	public File creatSDDir(String dir) {
		File dirFile = new File(SDCardRoot + dir + File.separator);
		System.out.println("创建路径结果---->"+dirFile.mkdirs()+"---->"+dirFile.getPath());
		return dirFile;
	}

	/**
	 * 判断文件是否存在
	 * @param path 不带SDCardRoot和后面的分隔符\的路径
	 */
	public boolean isFileExist(String fileName, String path) {
		File file = new File(SDCardRoot + path + File.separator + fileName);
		if(file.exists()) System.out.println("文件存在--->"+path+ File.separator +fileName);
		return file.exists();
	}
	
	/**
	 * 判断文件是否存在
	 * @param path 全路径包括文件名
	 */
	public boolean isFileExist(String path) {
		File file = new File(path);
		if(file.exists()) System.out.println("文件存在--->"+path);
		return file.exists();
	}
	

	/**
	 * 将InputStream写入SD卡
	 */
	public File write2SDFromInput(String path, String fileName,
			InputStream input) {

		File file = null;
		OutputStream output = null;
		try {
			creatSDDir(path);
			file = createFileInSDCard(fileName, path);
			output = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			int temp;
			while ((temp = input.read(buffer)) != -1) {
				output.write(buffer, 0, temp);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	/**
	 * 从文件获得字符串
	 * @param filePath 文件的全路径信息
	 * @return
	 */
	
	@SuppressWarnings("resource")
	public static String getStringFromFile(String filePath)
	{
		try {
			StringBuffer sBuffer = new StringBuffer();
			FileInputStream fInputStream = new FileInputStream(filePath);
			InputStreamReader inputStreamReader = new InputStreamReader(fInputStream, "UTF-8");
			BufferedReader in = new BufferedReader(inputStreamReader);
			if(!new File(filePath).exists())
			{
				System.out.println("文件不存在 ！无法读取--->"+filePath);
				return null;
			}
			while (in.ready()) {
				sBuffer.append(in.readLine() + "\n");
			}
			in.close();
			System.out.println("文件读取成功--->"+filePath);
			return sBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}