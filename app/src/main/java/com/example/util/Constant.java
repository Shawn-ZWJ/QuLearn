package com.example.util;

import com.viaviapp.hdvideo.R;

import java.io.Serializable;

public class Constant implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	//server url
	public static final String SERVER_URL="http://47.94.20.69/";

	public static final String IMAGE_PATH_URL =SERVER_URL+"images/";

	public static final String LATEST_URL = SERVER_URL+"api.php?latest";

	public static final String CATEGORY_URL = SERVER_URL+"api.php?cat_list";

	public static final String CATEGORY_ITEM_URL = SERVER_URL+"api.php?cat_id=";

	public static final String CATEGORY_ITEM_SINGLE_URL = SERVER_URL+"api.php?video_id=";

	public static final String HOME_URL = SERVER_URL+"api.php?home";

	public static final String MOST_URL = SERVER_URL+"api.php?most_viewed";

	public static final String ABOUT_US_URL = SERVER_URL+"api.php";

	public static final String YOUTUBE_IMAGE_FRONT="http://img.youtube.com/vi/";
	public static final String YOUTUBE_IMAGE_BACK="/hqdefault.jpg";

	public static final String LATEST_ARRAY_NAME="HD_VIDEO";
	public static final String LATEST_ID="id";
	public static final String LATEST_CATID="cat_id";
	public static final String LATEST_CAT_NAME="category_name";
	public static final String LATEST_VIDEO_URL="video_url";
	public static final String LATEST_VIDEO_ID="video_id";
	public static final String LATEST_VIDEO_DURATION="video_duration";
	public static final String LATEST_VIDEO_NAME="video_title";
	public static final String LATEST_VIDEO_DESCRIPTION="video_description";
	public static final String LATEST_IMAGE_URL="video_thumbnail_b";
	public static final String LATEST_IMAGEB_URL="video_thumbnail_b";
	public static final String LATEST_TYPE="video_type";
	public static final String LATEST_VIEW="total_views";
	public static final String LATEST_NUM_LIKE="num_like";

	public static final String CATEGORY_ARRAY_NAME="HD_VIDEO";
	public static final String CATEGORY_NAME="category_name";
	public static final String CATEGORY_CID="cid";
	public static final String CATEGORY_IMAGE="category_image_thumb";

	//for title display in CategoryItemF
	public static String CATEGORY_TITLE;
	public static int CATEGORY_ID;
	public static  String LATEST_IDD;

	public static final String CATEGORY_ITEM_ARRAY_NAME="HD_VIDEO";


	public static final String APP_Array="HD_VIDEO";
	public static final String APP_NAME="app_name";
	public static final String APP_IMAGE="app_logo";
	public static final String APP_VERSION="app_version";
	public static final String APP_AUTHOR="app_author";
	public static final String APP_CONTACT="app_contact";
	public static final String APP_EMAIL="app_email";
	public static final String APP_WEBSITE="app_website";
	public static final String APP_DESC="app_description";
	public static final String APP_PRIVACY="app_privacy_policy";
	public static final String APP_DEVELOP="app_developed_by";

}
