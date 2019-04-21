package com.tradingPlatform.controller;

import com.tradingPlatform.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@RequestMapping("/upload")
@RestController
public class UploadController {

    @Autowired
    private HttpServletRequest request;

    /**
     * 接收图片文件保存
     *
     * @param up_img_WU_FILE_0 图片文件
     * @return 操作结果
     */
    @PostMapping
    public ResultInfo upload(MultipartFile up_img_WU_FILE_0) {
        ResultInfo result = ResultInfo.failure("上传图片失败");

        try {
            //上传文件
            // 增加文件上传逻辑=================start
            if (up_img_WU_FILE_0 != null && up_img_WU_FILE_0.getOriginalFilename() != null && !"".equals(up_img_WU_FILE_0.getOriginalFilename())) {
                // 1.获取到上传的文件名称
                String oriName = up_img_WU_FILE_0.getOriginalFilename();
                // http://127.0.0.1:8081/pic/springmvc.jpg
                String extName = oriName.substring(oriName.lastIndexOf("."));
                // 2.重新命名文件的名称
                String newName = System.currentTimeMillis() + "";
                newName = newName + extName;
                // 3.直接上传
                String contextPath = request.getSession().getServletContext().getRealPath("/");
                System.out.println("UploadController.upload:  图片路径  " + contextPath);
                File file = new File("E:\\Java\\IdeaProjects\\CampusTradingPlatform-Huzelin\\CampusTradingPlatform\\CampusTradingPlatform-part05-controller\\src\\main\\webapp\\images\\" + newName);
                up_img_WU_FILE_0.transferTo(file);
                //4.文件名称
                System.out.println(newName);
                result = ResultInfo.success("上传成功", "images/" + newName);
                // 增加文件上传逻辑=================end
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
