package com.gwg.orm.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

@Slf4j
public final class Assert {
	//用户ID必须大于4的数字
	public static final String USER_ID_PATTERN = "\\d{4,}";


	public static void isMatchUserIdFormat(String userId) {
		Pattern pattern = Pattern.compile(USER_ID_PATTERN);
		if (userId == null || !pattern.matcher(userId).matches())
			throw new IllegalArgumentException("[Assertion failed] - userId格式不正确！");
	}




}