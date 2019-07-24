package com.pavelkisliuk.fth.command;

import com.pavelkisliuk.fth.model.FthData;
import com.pavelkisliuk.fth.model.FthString;

import javax.servlet.http.HttpServletRequest;

public class CreatorString implements FthDataByRequestFactory {
	@Override
	public FthString create(HttpServletRequest request) {
		return new FthString(request.getParameter("str"));
	}
}