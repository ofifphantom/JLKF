package com.jl.mis.service.impl;

import com.jl.mis.utils.CommonMethod;
import com.jl.mis.utils.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class OutPutExcelUtil {
    /**
     * 导出 Excel
     * @param dataList 输出内容
     * @param skip 标识
     *             1    售前
     *             2    售后
     * @param response 输出流
     */
    static boolean outPutExcel(List<String[]> dataList, Integer skip, HttpServletResponse response,HttpServletRequest request){
        boolean result = false;
        String[] dataTitle = null;
        String fileName = null;
        if(skip == 1){
            dataTitle = Constants.PRE_CONSULT_EXCEL_TITLE;
            fileName = Constants.PRE_CONSULT_EXCEL_FILE_NAME;
        }else if(skip == 2){
            dataTitle = Constants.AFTER_CONSULT_EXCEL_TITLE;
            fileName = Constants.AFTER_CONSULT_EXCEL_FILE_NAME;
        }
        try {
            result = CommonMethod.exportData(request,response,fileName,dataTitle,dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
