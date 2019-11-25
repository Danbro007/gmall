package com.danbro.gmall.common.utils.handler;

import com.alibaba.fastjson.JSON;

import com.danbro.gmall.common.utils.exceptions.CustomizeErrorCode;
import com.danbro.gmall.common.utils.exceptions.CustomizeException;
import com.danbro.gmall.common.utils.exceptions.ResultDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Danrbo
 * @date 2019/11/19 15:47
 * description
 **/
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(CustomizeException.class)
    public ModelAndView handleException(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Throwable e,
                                        Model model) {

        String contentType = "application/json";
        if (contentType.equals(request.getContentType())) {
            ResultDto resultDto;
            if (e instanceof CustomizeException) {
                resultDto = ResultDto.errorOf((CustomizeException) e);
            } else {
                resultDto = ResultDto.errorOf(CustomizeErrorCode.SERVER_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDto));
                writer.close();
            } catch (IOException e1) {
            }
            return null;
        } else {
            if (e instanceof CustomizeException) {
                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute("message", "服务器出错,请重新试试");
            }
            return new ModelAndView("error");
        }
    }

}

