package com.eleric.deskbuddy.controller;

import com.eleric.deskbuddy.pojo.Photo;
import com.eleric.deskbuddy.pojo.User;
import com.eleric.deskbuddy.service.PhotoService;
import com.eleric.deskbuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
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
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity  savePhotoImage(
			@RequestParam MultipartFile file, @PathVariable String username, @PathVariable String photoName)
			throws IOException, IllegalStateException {
		try {
			User user = userService.retrieveUser(username);
			photoService.addPhoto(user, photoName, file.getBytes());
			return ResponseEntity.ok().build();
		} catch (Exception e)
		{
			return ResponseEntity.badRequest().build();
		}
	}

	@RequestMapping(value = "/photos/{username}/image/{photoName}", method = RequestMethod.DELETE)
	public ResponseEntity  deletePhotoImage(
			@PathVariable String username, @PathVariable String photoName) {
		try {
			User user = userService.retrieveUser(username);
			boolean deleted = photoService.deletePhoto(user, photoName);
			if (deleted) {
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.status(500).build();
			}
		} catch (NoSuchFileException e)
		{
			return ResponseEntity.notFound().build();
		}
	}
}
