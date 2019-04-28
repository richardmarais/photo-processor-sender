package com.assement.waldo.photoprocessorjava;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.assement.waldo.PhotoProcessorSenderApplication;
import com.assement.waldo.dto.PhotoDto;
import com.assement.waldo.service.PhotoProcessorService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PhotoProcessorSenderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"sender"})
public class PhotoProcessorJavaApplicationTests {
	
	@Autowired
	private PhotoProcessorService photoProcessorService;
	
	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void testRetrievePhotos() throws Exception {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/photos/pending"),
				HttpMethod.GET, entity, String.class);
		String actual = response.getBody();
		assertThat(actual, containsString("https://s3.amazonaws.com/waldo-thumbs-dev/large/71840919-e422-552d-8c8d-9b2b360ce98c.jpg"));
		assertThat(actual, containsString("https://s3.amazonaws.com/waldo-thumbs-dev/large/72800f95-c406-5475-85ac-b8943877b15f.jpg"));
		assertThat(actual, containsString("https://s3.amazonaws.com/waldo-thumbs-dev/large/366ad885-aafd-48a4-8ff5-c38a1bbc84c8.jpg"));
		assertThat(actual, containsString("https://s3.amazonaws.com/waldo-thumbs-dev/large/b3cbaef4-ff6d-523e-beea-704629c42ca2.jpg"));
		assertThat(actual, containsString("https://s3.amazonaws.com/waldo-thumbs-dev/large/49dd12b2-f019-59c5-bf17-b1b3bb208eba.jpg"));
		assertThat(actual, containsString("https://s3.amazonaws.com/waldo-thumbs-dev/large/c9391205-1892-5139-abe5-b5df3ced8d61.jpg"));
	}
	
	@Test
	public void testProcessPhotos() throws Exception {
		List<UUID> uuids = getAllUuids();
		HttpEntity<List<UUID>> entity = new HttpEntity<List<UUID>>(uuids, headers);
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/photos/process"),
				HttpMethod.POST, entity, String.class);
		System.out.println(response);
		assertEquals(200, response.getStatusCodeValue());
		
		Thread.sleep(10000);
		
		List<PhotoDto> photoDtos = photoProcessorService.getPhotos();
		System.out.println("photoDtos = "+photoDtos);
		assertEquals(uuids.size(), photoDtos.size());
		for (PhotoDto dto:photoDtos) {
			System.out.println(dto.getUuid()+"  "+dto.getStatus());
		}
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
	
	private List<UUID> getAllUuids() {
		List<UUID> uuidds = new ArrayList<>();
		List<PhotoDto> photos = photoProcessorService.getPhotos();
		for (PhotoDto dto:photos) {
			uuidds.add(dto.getUuid());
		}
		return uuidds;
	}
}
