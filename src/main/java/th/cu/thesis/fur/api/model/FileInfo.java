package th.cu.thesis.fur.api.model;

public class FileInfo {
	private String fileName;
	private String filePath;

	public FileInfo() {
	}

	public FileInfo(String fileName, String filePath) {
		this.fileName = fileName;
		this.filePath = filePath;
	}

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FileInfo [fileName=");
		builder.append(fileName);
		builder.append(", filePath=");
		builder.append(filePath);
		builder.append("]");
		return builder.toString();
	}

}
