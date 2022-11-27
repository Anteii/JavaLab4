package com.example.lab4.filter;

import javax.servlet.http.HttpServletResponseWrapper;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;

public class ResettableStreamHttpServletResponse extends HttpServletResponseWrapper {


    public List<Byte> rawData = new ArrayList<>();
    public HttpServletResponse response;
    private final ResettableServletOutputStream servletStream;

    public ResettableStreamHttpServletResponse(HttpServletResponse response) throws IOException {
        super(response);
        this.response = response;
        this.servletStream = new ResettableServletOutputStream(this);
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return servletStream;
    }

    public PrintWriter getWriter() throws IOException {
        String encoding = getCharacterEncoding();
        if ( encoding != null ) {
            return new PrintWriter(new OutputStreamWriter(servletStream, encoding));
        } else {
            return new PrintWriter(new OutputStreamWriter(servletStream));
        }
    }
}