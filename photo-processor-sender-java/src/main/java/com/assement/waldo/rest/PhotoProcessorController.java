package com.assement.waldo.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.assement.waldo.dto.PhotoDto;
import com.assement.waldo.service.PhotoProcessorService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/photos")
public class PhotoProcessorController {

	@Autowired 
	private PhotoProcessorService photoProcessorService;

	/**
	 * Return photo records in JSON format
	 * 
	 * @return List<PhotoDto>
	 */
	@GetMapping("/pending")
	@ResponseStatus(HttpStatus.OK)
	public List<PhotoDto> getPhotos() {
		return photoProcessorService.getPhotos();
	}
	
	/**
	 * Accepting one or more photo UUIDs as JSON input.
	 * 
	 * @param postId
	 * @param newCommentDto
	 * @return
	 */
	@PostMapping("/process")
	@ResponseStatus(HttpStatus.OK)
	public String processPhotos(@RequestBody List<UUID> uuidds) {
		System.out.println(new Gson().toJson(uuidds));
		return photoProcessorService.processPhotos(uuidds);
	}
}
