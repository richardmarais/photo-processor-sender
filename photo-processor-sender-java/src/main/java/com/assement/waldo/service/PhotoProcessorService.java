package com.assement.waldo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.assement.waldo.dto.PhotoDto;
import com.assement.waldo.messages.RabbitMQSender;
import com.assement.waldo.repository.PhotoRepository;
import com.assement.waldo.util.ObjectMapperUtils;

@Component
public class PhotoProcessorService {
	
	@Autowired
	private PhotoRepository photoRepository;

	@Autowired
	RabbitMQSender rabbitMQSender;
	
	/**
	 * Gets the list of photos from the datasource
	 * @return List<PhotoDto>
	 */
	public List<PhotoDto> getPhotos() {
		return ObjectMapperUtils.mapAll(photoRepository.findAll(), PhotoDto.class);
	}

	/**
	 * process the list of photos - send them to the MQ
	 * @param uuids
	 * @return the number of photos sent to the queue
	 */
	public String processPhotos(List<UUID> uuids) {
		for (UUID uuid:uuids) {
			rabbitMQSender.send(uuid);
		}
		return "Number processed: "+uuids.size();
	}

}
