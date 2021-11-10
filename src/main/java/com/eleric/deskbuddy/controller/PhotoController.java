package com.eleric.deskbuddy.controller;

import com.eleric.deskbuddy.pojo.Photo;
import com.eleric.deskbuddy.pojo.User;
import com.eleric.deskbuddy.service.PhotoService;
import com.eleric.deskbuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PhotoController {
	private PhotoService photoService;
	private UserService userService;

	@Autowired
	public PhotoController(PhotoService photoService, UserService userService) {
		this.photoService = photoService;
		this.userService = userService;
	}

	@RequestMapping(value = "/photos/{username}")
	public List<Photo> listPhotos(@PathVariable String username) {
		User user = userService.retrieveUser(username);
		List<Photo> photos = photoService.listPhotos(user);
		return photos;
	}

	@RequestMapping(value = "/photos/{username}/image/{photoName}", method = RequestMethod.GET,
			produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public ResponseEntity<byte[]> retrievePhotoImage(
			HttpServletResponse response, @PathVariable String username, @PathVariable String photoName)
			throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(CacheControl.noCache().getHeaderValue());

		User user = userService.retrieveUser(username);
		byte[] image = photoService.retrievePhotoImage(user, photoName);
		return new ResponseEntity<>(image, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/photos/{username}/image/{photoName}", method = RequestMethod.POST,
			produces = MediaType.IMAGE_JPEG_VALUE)
	public void savePhotoImage(
			@RequestParam("file") MultipartFile file, @PathVariable String username, @PathVariable String photoName)
			throws IOException {
		User user = userService.retrieveUser(username);
		photoService.addPhoto(user, photoName, file.getBytes());
	}
}
