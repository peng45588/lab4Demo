package cn.edu.fudan.se.messager;

import org.apache.struts2.dispatcher.ServletRedirectResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by snow on 15-6-4.
 */
public class PrintToHtml extends ServletRedirectResult {

    boolean judge = false;

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    private HttpServletResponse response;
    public boolean isPrint(){
        if (judge==false)
            return false;
        else {
            judge = false;
            return true;
        }
    }
    public PrintToHtml(){

    }
    public PrintToHtml(HttpServletResponse response){
        this.response = response;
    }
    public void Print(String ret){

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;chatset=utf-8");
        PrintWriter pw=null;
        try {
            pw = response.getWriter();
            pw.write(ret);
        } catch (IOException e) {

        }
        //response.setCharacterEncoding("utf-8");
        pw.flush();
        pw.close();
        judge = true;
    }
}
