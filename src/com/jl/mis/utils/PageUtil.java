package com.jl.mis.utils;

public class PageUtil {
    /**
     * 帮助页面跳转
     * @param pageTools 页面信息
     * @param skipPage 跳转标识
     *                  -1 首页   -2 末页
     *                   1 上一页  2 下一页
     * @param defined 自定义跳转页面
     * @return 处理后的信息
     */
    public static PageTools helpPageSkip(PageTools pageTools, Integer skipPage, Integer defined){
        if(null != skipPage || null != defined){
            if(null != skipPage){
                if(skipPage > 0){
                    if(skipPage == 1){
                        pageTools.setCurrentPage(pageTools.getCurrentPage() - 1);
                    }else if(skipPage == 2){
                        pageTools.setCurrentPage(pageTools.getCurrentPage() + 1);
                    }
                }else if(skipPage < 0){
                    if(skipPage == -1){
                        pageTools.setCurrentPage(1);
                    }else if(skipPage == -2){
                        pageTools.setCurrentPage(pageTools.getPageCount());
                    }
                }
            }
            if(defined != null){
                if(defined > 0){
                    pageTools.setCurrentPage(defined);
                }
            }
            /*
                SQL语句 分页查询 LIMIT 偏移量,显示数量
                偏移量 = (当前页 - 1) * 显示数量
             */
            pageTools.setiDisplayStart((pageTools.getCurrentPage() - 1) * pageTools.getPageDisplayLength());
        }
        return pageTools;
    }
}
