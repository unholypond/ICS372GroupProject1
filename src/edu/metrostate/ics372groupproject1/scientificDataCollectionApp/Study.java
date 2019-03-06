package edu.metrostate.ics372groupproject1.scientificDataCollectionApp;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Study {
	
	@SerializedName("study_name")
	@Expose
	private String studyName;
	
	@SerializedName("study_id")
	@Expose
	private String studyId;
	
	private ArrayList<Site> sitesInStudy;
	
	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public void setAllToCollect() {
		for(Site s : sitesInStudy) {
			s.setRecording(true);
		}
	}
	
	
}
