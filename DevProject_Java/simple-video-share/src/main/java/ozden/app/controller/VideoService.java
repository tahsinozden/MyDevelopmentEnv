package ozden.app.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoService {
	@Value("${multipart.location}")
	String savePath;
	
	@RequestMapping(value="/randomvideo")
	public VideoResponse getRandomVideo(){
		File folder = new File(savePath);
		Random rn = new Random();
		File[] allFiles = folder.listFiles();
		ArrayList<File> supportedFiles = new ArrayList<>();
		for(File file : allFiles){
			if (file.isFile() && file.getName().endsWith("mp4")){
				supportedFiles.add(file);
			}
		}
		String name = supportedFiles.get(rn.nextInt(supportedFiles.size())).getName();
		return  new VideoResponse(name, "/allvideos/" + name );
//		return "/allvideos/" + supportedFiles.get(rn.nextInt(supportedFiles.size())).getName();
	}
	
	class VideoResponse {
		public String name;
		public String url;
		public VideoResponse(String name, String url) {
			super();
			this.name = name;
			this.url = url;
		}
		
	}
}

