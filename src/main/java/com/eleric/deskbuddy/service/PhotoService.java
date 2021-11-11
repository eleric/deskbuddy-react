package com.eleric.deskbuddy.service;

import com.eleric.deskbuddy.pojo.Photo;
import com.eleric.deskbuddy.pojo.User;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;


@Service
public class PhotoService {
	private String root;
	private int standardHeight;
	private static Logger logger = LoggerFactory.getLogger(PhotoService.class);

	public PhotoService(@Value("${photo.root}") String root, @Value("${photo.standard_height}") int standardHeight) {
		this.root = root;
		this.standardHeight = standardHeight;
	}

	private String getDirectoryString(User user) {
		String path = String.format("%s%s%s", root, FileSystems.getDefault().getSeparator(), user.getUsername());
		return path;
	}

	private String getFileString(User user, String photoName) {
		String path = String.format("%s%s%s", getDirectoryString(user),
				FileSystems.getDefault().getSeparator(), photoName);
		return path;
	}

	private void createUserDirectoryIfNotExist(User user)
	{
		String path = getDirectoryString(user);
		File photoDir = new File(path);
		if (!photoDir.exists())
		{
			photoDir.mkdir();
		}
	}

	public List<Photo> listPhotos(User user) {
		createUserDirectoryIfNotExist(user);
		String path = String.format("%s%s%s", root, FileSystems.getDefault().getSeparator(), user.getUsername());
		File photoDir = new File(path);
		List<Photo> photos = new ArrayList<>();
		File[] files = photoDir.listFiles();
		for (File f : files) {
			Photo photo = new Photo(f.getName(), String.format("/%s/%s/%s/%s", "photos", user.getUsername(), "image", f.getName()));
			photos.add(photo);
		}
		return photos;
	}

	public byte[] retrievePhotoImage(User user, String photoName) throws IOException {
		String path = getFileString(user, photoName);
		File photoFile = new File(path);
		BufferedImage buffImg = ImageIO.read(photoFile );
		BufferedImage newBufImg = resizeImage(buffImg);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(newBufImg, "jpeg", os);

		return os.toByteArray();
	}

	private BufferedImage resizeImage(BufferedImage originalImage) {
		int oldHeight = originalImage.getHeight();
		int oldWidth = originalImage.getWidth();

		float ratio = ((float) standardHeight)/((float)oldHeight);
		logger.info("Ratio - " + ratio);
		int newWidth = Math.round(ratio * oldWidth);
		logger.info("New Width - " + newWidth);
		int newHeight = standardHeight;
		logger.info("New Height - " + newHeight);

		Image resultingImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
		BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
		return outputImage;
	}

	public void addPhoto(User user, String photoName, byte[] fileBtyes) throws IOException {
		createUserDirectoryIfNotExist(user);
		String path = getFileString(user, photoName);
		File file = new File(path);
		try (FileOutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(fileBtyes);
		}
	}

	public boolean deletePhoto(User user, String photoName) throws NoSuchFileException {
		String path = getFileString(user, photoName);
		File file = new File(path);
		if (file.isFile()) {
			boolean deleted = file.delete();
			return deleted;
		}
		else {
			throw new NoSuchFileException(String.format("File not found: %s", photoName));
		}
	}
}
