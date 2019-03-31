package com.jl.mis.controller;

import com.jl.mis.dto.HabitLanguageReationDto;
import com.jl.mis.dto.TreeJsonDTO;
import com.jl.mis.model.entity.HabitLanguageRelationEntity;
import com.jl.mis.model.entity.HabitLanguageRelationType;
import com.jl.mis.model.entity.HabitLanguageTypeEntity;
import com.jl.mis.service.HabitLanguageRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 常用语表
 *
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/6/6 14:17
 */
@RestController
public class HabitLanguageRelationController {
    @Autowired
    private HabitLanguageRelationService habitLanguageRelationService;

    /**
     * 查询当前用户下的所有常用语 用作树形菜单
     * @param session session作用域
     * @return
     */

    @RequestMapping("/selectHabitLanguageRelationTree")
    public List<TreeJsonDTO> selectHabitLanguageRelationTree(HttpSession session){
        int providerId= (int) session.getAttribute("userId");
        List<HabitLanguageReationDto> habitLanguageReationDtos= habitLanguageRelationService.selectAll(providerId);
        return habitLanguageRelationService.ConvertTreeJsonDto(habitLanguageReationDtos);
    }
    /**
     * 查询用户下的所有常用语类型
     * @param session session作用域
     * @return
     */
    @RequestMapping("/selectHabitLanguageType")
    public List<HabitLanguageTypeEntity> selectHabitLanguageType(HttpSession session){
        int providerId= (int) session.getAttribute("userId");
        List<HabitLanguageTypeEntity> habitLanguageTypeEntities= habitLanguageRelationService.selectLanguageType(providerId);
        return habitLanguageTypeEntities;
    }

    /**
     * 查询当前登陆用户下的所有常用语
     * @param session
     * @return
     */
    @RequestMapping("/selectHabitLanguageRelation")
    public List<HabitLanguageRelationEntity> habitLanguageReationDtos(HttpSession session){
        int providerId= (int) session.getAttribute("userId");
        List<HabitLanguageRelationEntity> habitLanguageReationDtos= habitLanguageRelationService.selectHabitLanguageRelation(providerId);
        return habitLanguageReationDtos;
    }

    /**
     * 根据 typeid 查询常用语
     * @param session
     * @return
     */
    @RequestMapping("/selectHabitLanguageToType")
    public List<HabitLanguageRelationEntity> habitLanguageReationToType(HttpSession session, HttpServletRequest request){
        int providerId= (int) session.getAttribute("userId");
        Integer typeId=Integer.parseInt(request.getParameter("id"));
        List<HabitLanguageRelationEntity> habitLanguageRelationEntities= habitLanguageRelationService.selectHabitLanguageRelationToType(typeId);
        return habitLanguageRelationEntities;
    }
    /**
     * 删除常用语
     * @param session
     * @return
     */
    @RequestMapping("/delHabitLanguage")
    public List<HabitLanguageRelationEntity> delhabitLanguageReationToType(HttpSession session, HttpServletRequest request){
        int providerId= (int) session.getAttribute("userId");
        Integer relationId=Integer.parseInt(request.getParameter("id"));
        List<HabitLanguageRelationEntity> habitLanguageRelationEntities= habitLanguageRelationService.delHabitLanguage(relationId,providerId);
        return habitLanguageRelationEntities;
    }

    /**
     * 删除常用语分类
     * @param session
     * @param request
     * @return
     */
    @RequestMapping("/delHabitLanguageType")
    public List<HabitLanguageTypeEntity> delhabitLanguageReationType(HttpSession session, HttpServletRequest request){
        int providerId= (int) session.getAttribute("userId");
        Integer relationId=Integer.parseInt(request.getParameter("id"));
        List<HabitLanguageTypeEntity> habitLanguageTypeEntities= habitLanguageRelationService.delHabitLanguageType(providerId,relationId);
        return habitLanguageTypeEntities;
    }

    /**
     * 添加常用语
     * @param session
     * @param request
     * @return
     */
    @RequestMapping("/insertHabitLanguage")
    public List<HabitLanguageRelationEntity> insertHabitLanguageRelation(HttpSession session,HttpServletRequest request){

        int providerId= (int) session.getAttribute("userId");
        //获得常用语类型id
       Integer typeId= Integer.parseInt(request.getParameter("typeId"));
        //获得添加的常用语
        String habitLanguageText= request.getParameter("habitLanguageText");
        HabitLanguageRelationEntity habitLanguageRelationEntity=new HabitLanguageRelationEntity();
        habitLanguageRelationEntity.setHabitLanguageTypeId(typeId);
        habitLanguageRelationEntity.setHabitlanguageContent(habitLanguageText);
        List<HabitLanguageRelationEntity> habitLanguageRelationEntities= habitLanguageRelationService.insertHabitLanguage(providerId,habitLanguageRelationEntity);
        return habitLanguageRelationEntities;
    }

    /**
     * 添加常用语分类
     * @param session
     * @param request
     * @return
     */
    @RequestMapping("/insertHabitLanguageType")
    public List<HabitLanguageTypeEntity> insertHabitLanguageType(HttpSession session, HttpServletRequest request){

        session.setAttribute("userId", 1);
        int providerId= (int) session.getAttribute("userId");
        HabitLanguageTypeEntity habitLanguageTypeEntity=new HabitLanguageTypeEntity();
        String HabitLanguageTypeName=request.getParameter("HabitLanguageTypeName");
        habitLanguageTypeEntity.setHabitLanguageTypeName(HabitLanguageTypeName);
        habitLanguageTypeEntity.setProviderId(providerId);
      return   habitLanguageRelationService.insertHabitLanguageType(habitLanguageTypeEntity);
    }
}
