package org.microservice.handlers;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.microservice.chi.HistoryRequester;
import org.microservice.model.Answer;
import org.microservice.model.History;
import org.microservice.model.Item;
import org.microservice.model.Request;
import org.microservice.utils.Common;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MainServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String reqStr = IOUtils.toString(req.getInputStream(), "UTF-8");
        if(StringUtils.isBlank(reqStr))
        {
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(Common.getPrettyGson().toJson(new Answer("BEEEE", null)));
            return;
        }
        Request r = Common.getPrettyGson().fromJson(reqStr, Request.class);
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
       // List<History> items = Arrays.asList(new Item("First", r.getName()));
        resp.getWriter().println(Common.getPrettyGson().toJson(new Answer("OK", null)));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        int clientId = Integer.parseInt(request.getParameterValues("id")[0]);
        HistoryRequester historyRequester = new HistoryRequester();
        List<History> histories = historyRequester.getHistory(clientId);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        Answer answer = new Answer("OK", histories);
        response.getWriter().println(Common.getPrettyGson().toJson(answer));
    }

}

