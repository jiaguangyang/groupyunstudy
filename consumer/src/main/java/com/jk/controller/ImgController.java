package com.jk.controller;

import com.jk.utils.AliyunOSSUtil;
import com.jk.utils.OSSClientUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

@Controller
@RequestMapping("img")
public class ImgController {

    @RequestMapping("imgUpload")
    @ResponseBody
    public HashMap<String,Object> imgUpload(MultipartFile companyLogo){

        String filename = companyLogo.getOriginalFilename();
        System.out.println(filename);
        HashMap<String, Object> map = new HashMap<>();
        try {

            if (companyLogo != null) {
                if (!"".equals(filename.trim())) {
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(companyLogo.getBytes());
                    os.close();
                    companyLogo.transferTo(newFile);
                    // 上传到OSS
                    String uploadUrl = AliyunOSSUtil.upLoad(newFile);
                    System.out.println(uploadUrl);

                    map.put("imgId",uploadUrl);
                    map.put("msg","上传成功");
                    return  map;
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        map.put("msg","上传失败");
        map.put("imgId","");
        return  map;
    }


    /**
     * OSS阿里云上传图片
     */
    @RequestMapping("updaloadImg")
    @ResponseBody
    public String uploadImg(MultipartFile imgg)throws IOException {
        if (imgg == null || imgg.getSize() <= 0) {
            throw new IOException("file不能为空");
        }
        OSSClientUtil ossClient=new OSSClientUtil();
        String name = ossClient.uploadImg2Oss(imgg);
        String imgUrl = ossClient.getImgUrl(name);
        String[] split = imgUrl.split("\\?");
        //System.out.println(split[0]);
        return split[0];
    }


}
