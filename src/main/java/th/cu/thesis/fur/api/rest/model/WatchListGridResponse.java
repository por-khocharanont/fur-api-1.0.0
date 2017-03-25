package th.cu.thesis.fur.api.rest.model;

import java.io.Serializable;
import java.util.List;

import th.cu.thesis.fur.api.repository.model.WatchList;

public class WatchListGridResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int total;
	private int page;
	private int records;
	private List<WatchList> rows;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRecords() {
		return records;
	}
	public void setRecords(int records) {
		this.records = records;
	}
	public List<WatchList> getRows() {
		return rows;
	}
	public void setRows(List<WatchList> rows) {
		this.rows = rows;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WatchListGridResponse [total=");
		builder.append(total);
		builder.append(", page=");
		builder.append(page);
		builder.append(", records=");
		builder.append(records);
		builder.append(", rows=");
		builder.append(rows);
		builder.append("]");
		return builder.toString();
	}
	
	

}
