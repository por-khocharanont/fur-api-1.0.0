package th.cu.thesis.fur.api.repository.model;

import static th.cu.thesis.fur.api.util.CommonUtil.*;

public class AppFile {

	private String fileName;
	private String filePath;
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public AppFile(String fileName, String filePath) {
		super();
		this.fileName = fileName;
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return getGson().toJson(this);
	}

	
}
