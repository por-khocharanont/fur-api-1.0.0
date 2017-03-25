package th.cu.thesis.fur.api.rest.model;

import java.util.List;

import th.cu.thesis.fur.api.model.Custodian;
import th.cu.thesis.fur.api.model.CustodianTeam;

public class CustodianTableInfo {
	private String custodianType;
	private String custodianApproveType;
	private String custodianMinimum;
	private List<Custodian> custodianList;
	private List<CustodianTeam> custodianTeamList;
	private List<Custodian> custodianBackOfficeList;
	private List<CustodianTeam> custodianBackOfficeTeamList;
	private List<Custodian> custodianACCList;
	private List<CustodianTeam> custodianACCTeamList;
	private List<Custodian> custodianBranchList;
	private List<CustodianTeam> custodianBranchTeamList;
	private List<Custodian> custodianOutletList;
	private List<CustodianTeam> custodianOutletTeamList;

	public String getCustodianType() {
		return custodianType;
	}

	public void setCustodianType(String custodianType) {
		this.custodianType = custodianType;
	}

	public String getCustodianApproveType() {
		return custodianApproveType;
	}

	public void setCustodianApproveType(String custodianApproveType) {
		this.custodianApproveType = custodianApproveType;
	}

	public String getCustodianMinimum() {
		return custodianMinimum;
	}

	public void setCustodianMinimum(String custodianMinimum) {
		this.custodianMinimum = custodianMinimum;
	}

	public List<Custodian> getCustodianList() {
		return custodianList;
	}

	public void setCustodianList(List<Custodian> custodianList) {
		this.custodianList = custodianList;
	}

	public List<CustodianTeam> getCustodianTeamList() {
		return custodianTeamList;
	}

	public void setCustodianTeamList(List<CustodianTeam> custodianTeamList) {
		this.custodianTeamList = custodianTeamList;
	}

	public List<Custodian> getCustodianBackOfficeList() {
		return custodianBackOfficeList;
	}

	public void setCustodianBackOfficeList(List<Custodian> custodianBackOfficeList) {
		this.custodianBackOfficeList = custodianBackOfficeList;
	}

	public List<CustodianTeam> getCustodianBackOfficeTeamList() {
		return custodianBackOfficeTeamList;
	}

	public void setCustodianBackOfficeTeamList(List<CustodianTeam> custodianBackOfficeTeamList) {
		this.custodianBackOfficeTeamList = custodianBackOfficeTeamList;
	}

	public List<Custodian> getCustodianACCList() {
		return custodianACCList;
	}

	public void setCustodianACCList(List<Custodian> custodianACCList) {
		this.custodianACCList = custodianACCList;
	}

	public List<CustodianTeam> getCustodianACCTeamList() {
		return custodianACCTeamList;
	}

	public void setCustodianACCTeamList(List<CustodianTeam> custodianACCTeamList) {
		this.custodianACCTeamList = custodianACCTeamList;
	}

	public List<Custodian> getCustodianBranchList() {
		return custodianBranchList;
	}

	public void setCustodianBranchList(List<Custodian> custodianBranchList) {
		this.custodianBranchList = custodianBranchList;
	}

	public List<CustodianTeam> getCustodianBranchTeamList() {
		return custodianBranchTeamList;
	}

	public void setCustodianBranchTeamList(List<CustodianTeam> custodianBranchTeamList) {
		this.custodianBranchTeamList = custodianBranchTeamList;
	}

	public List<Custodian> getCustodianOutletList() {
		return custodianOutletList;
	}

	public void setCustodianOutletList(List<Custodian> custodianOutletList) {
		this.custodianOutletList = custodianOutletList;
	}

	public List<CustodianTeam> getCustodianOutletTeamList() {
		return custodianOutletTeamList;
	}

	public void setCustodianOutletTeamList(List<CustodianTeam> custodianOutletTeamList) {
		this.custodianOutletTeamList = custodianOutletTeamList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustodianTableInfo [custodianType=");
		builder.append(custodianType);
		builder.append(", custodianApproveType=");
		builder.append(custodianApproveType);
		builder.append(", custodianMinimum=");
		builder.append(custodianMinimum);
		builder.append(", custodianList=");
		builder.append(custodianList);
		builder.append(", custodianTeamList=");
		builder.append(custodianTeamList);
		builder.append(", custodianBackOfficeList=");
		builder.append(custodianBackOfficeList);
		builder.append(", custodianBackOfficeTeamList=");
		builder.append(custodianBackOfficeTeamList);
		builder.append(", custodianACCList=");
		builder.append(custodianACCList);
		builder.append(", custodianACCTeamList=");
		builder.append(custodianACCTeamList);
		builder.append(", custodianBranchList=");
		builder.append(custodianBranchList);
		builder.append(", custodianBranchTeamList=");
		builder.append(custodianBranchTeamList);
		builder.append(", custodianOutletList=");
		builder.append(custodianOutletList);
		builder.append(", custodianOutletTeamList=");
		builder.append(custodianOutletTeamList);
		builder.append("]");
		return builder.toString();
	}

}
