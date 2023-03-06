package in.sparklogic.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadUtils {

	public static String[] saveUploadedFiles(MultipartFile file, String uniqueId, String userId, String physicalPath, String saperator) throws IOException {
		String relativePath = saperator + userId + saperator;
		String baseDirectoryPath = physicalPath + relativePath;
		File directory = new File(baseDirectoryPath);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		if (file.isEmpty()) {
			return null; // next pls
		}
		String fileName = file.getOriginalFilename();
		String physicalFileName = new Date().getTime() + "-" + String.format("%.0f", Math.ceil(Math.random() * 100))
				+ "-" + fileName;
		byte[] bytes = file.getBytes();
		Path path = Paths.get(baseDirectoryPath + physicalFileName);
		Files.write(path, bytes);
		return new String[]{relativePath, physicalFileName, fileName, file.getContentType()};
	}
	
	
	public static String[] saveUploadedFiles(MultipartFile file, String physicalPath, String saperator) throws IOException {
		String relativePath = saperator;
		String baseDirectoryPath = physicalPath + relativePath;
		File directory = new File(baseDirectoryPath);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		if (file.isEmpty()) {
			return null; // next pls
		}
		String fileName = file.getOriginalFilename();
		String physicalFileName = new Date().getTime() + "-" + String.format("%.0f", Math.ceil(Math.random() * 100))
				+ "-" + fileName;
		byte[] bytes = file.getBytes();
		Path path = Paths.get(baseDirectoryPath + physicalFileName);
		Files.write(path, bytes);
		return new String[]{relativePath, physicalFileName, fileName, file.getContentType()};
	}
	
	
	public static String[] saveUploadedFileToLocation(MultipartFile file, String physicalPath) throws IOException {
		String originalFileName = "";
		String fileName = "";
		if (!Objects.isNull(file)) {
			originalFileName = file.getOriginalFilename();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String randomStr = String.valueOf(timestamp);
		    fileName = String.format("%s_%s.%s",
					com.google.common.io.Files.getNameWithoutExtension(originalFileName), randomStr,
					com.google.common.io.Files.getFileExtension(originalFileName));

			String filePath = String.format("%s%s%s", physicalPath, File.separator, fileName);

			if (!Files.exists(Paths.get(physicalPath))) {
				try {
					(new File(physicalPath)).mkdirs();
				} catch (Exception e) {
				}
			}
			file.transferTo(new File(filePath));
		}
		return new String[]{ originalFileName, fileName, file.getContentType()};
	}
	
}
