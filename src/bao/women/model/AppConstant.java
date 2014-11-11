package bao.women.model;

import java.io.File;

import android.os.Environment;

public interface AppConstant {
	
	public class DATABASETABLENAME{
		public static final String HYYM = "t_hyym";
		public static final String YSJT = "t_ysjt";
		public static final String FLZS = "t_flzs";
		public static final String XFJZ = "t_xfjz";
		public static final String ZSDL = "t_zsdl";
		public static final String FAVORITES = "t_favorites";
		public static final String TESTTABLE = "t_test";
	}
	
	public class URL{
		public static final String VERSIONURLSTR="http://110.64.90.156:8080/gaokaobibei/version.txt";
		public static final String UPDATEAPKURLSTR="http://110.64.90.156:8080/gaokaobibei/allforwomen.apk";
		public static final String BASE_URL = "http://110.64.90.156:8080/allforwomen/";
	}
	
	public class DATABASESTRING{
		public static final String[] ALLCOLUMNS= new String[]{"id","fromAndTime","titleString","titleImageName","contentHTMLName"};
	}
	
	
	public class PATH{
		public static final String SDCardRoot = Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ File.separator;
		public static final String PATH_WITH_SEPARATE = "allforwomen/resources/";
		public static final String PATH_WITHOUT_SEPARATE = "allforwomen/resources";
		public static final String COMPLETE_PATH = SDCardRoot+PATH_WITH_SEPARATE;
	}
	
	public enum PARENTSID{HYYM_ID ,YSJT_ID , FLZS_ID, XFJZ_ID, ZSDL_ID }

}
