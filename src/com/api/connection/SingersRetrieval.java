package com.api.connection;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import com.model.Singer;

public class SingersRetrieval {
	private List<Singer> singers;

	public SingersRetrieval() {
		singers = new ArrayList<Singer>();
	}

	@SuppressLint("NewApi")
	public boolean GetMainAPI(String response) {
		try {
			JSONArray popularSingers = new JSONArray(response);
			for (int i = 0; i < popularSingers.length(); i++) {
				Singer singer = new Singer();
				JSONObject singerObject = popularSingers.getJSONObject(i);
				singer.setId(Long.valueOf(singerObject.getString("id")));
				singer.setName(singerObject.getString("name"));
				String letter = singerObject.getString("letter");
				if (!letter.isEmpty())
					singer.setLetter(singerObject.getString("letter").charAt(0));
				singers.add(singer);
			}
			return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public List<Singer> getSingers() {
		return singers;
	}

	public void setSingers(List<Singer> singers) {
		this.singers = singers;
	}

}
