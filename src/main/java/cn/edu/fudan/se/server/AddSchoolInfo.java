package cn.edu.fudan.se.server;/**
 * Created by snow on 15-6-19.
 */


import cn.edu.fudan.se.Parameter;
import cn.edu.fudan.se.messager.Invoker;
import cn.edu.fudan.se.messager.PrintToHtml;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddSchoolInfo extends ActionSupport implements ServletResponseAware,ServletRequestAware {
    private static final long serialVersionUID = 1L;
    private HttpServletResponse response;
    private HttpServletRequest request;
    //定义处理用户请求的execute方法
    public String execute() {
        String ret = "1232123";
        Invoker invoker = null;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line = in.readLine();
            while (line!=null){
                JSONObject jsob = new JSONObject(line);
                invoker = new Invoker(4);
                invoker.setUp(jsob, response,
                        Parameter.RESPONSE_TAG_SCHOOL,
                        Parameter.REQUEST_TAG_SCHOOL,
                        Parameter.DEFAULT);

                Thread.sleep(1000);
                for (int i = 0;i<5;i++){
                    if (!invoker.getPth().isPrint())
                        Thread.sleep(1000);
                }
                invoker.stop();
//                while (!invoker.getPth().isPrint()){//未打印:
//
//                }
                line = in.readLine();
            }
        } catch (JSONException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
//        if (invoker!=null)
//            invoker.stop();
        return null;
    }

    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.request = httpServletRequest;
    }
    @Override
    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.response = httpServletResponse;
    }

}