package com.example.lab4.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.IOException;
import java.io.OutputStream;

public class ResettableServletOutputStream extends ServletOutputStream {



    public OutputStream outputStream;
    private final ResettableStreamHttpServletResponse wrappedResponse;
    private final ServletOutputStream servletOutputStream = new ServletOutputStream(){
        WriteListener writeListener;

        @Override
        public void setWriteListener(WriteListener writeListener) {
            this.writeListener = writeListener;
        }

        public boolean isReady(){
            return true;
        }
        @Override
        public void write(int w) throws IOException{
            outputStream.write(w);
            wrappedResponse.rawData.add(Integer.valueOf(w).byteValue());
        }
    };

    public ResettableServletOutputStream(ResettableStreamHttpServletResponse wrappedResponse) throws IOException {
        this.outputStream = wrappedResponse.response.getOutputStream();
        this.wrappedResponse = wrappedResponse;
    }

    @Override
    public void close() throws IOException {
        outputStream.close();
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        servletOutputStream.setWriteListener( writeListener );
    }
    @Override
    public boolean isReady(){
        return servletOutputStream.isReady();
    }

    @Override
    public void write(int w) throws IOException {
        servletOutputStream.write(w);
    }
}
