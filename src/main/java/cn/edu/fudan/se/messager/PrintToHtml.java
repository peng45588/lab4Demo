package cn.edu.fudan.se.messager;

import org.apache.struts2.dispatcher.ServletRedirectResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by snow on 15-6-4.
 */
public class PrintToHtml extends ServletRedirectResult {

    static boolean judge = false;
    public static boolean isPrint(){
        if (judge==false)
            return false;
        else {
            judge = false;
            return true;
        }
    }
    public static String PrintToHtml(HttpServletResponse response,String ret){

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
        return null;
    }
}
