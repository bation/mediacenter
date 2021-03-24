package com.localmedia.mediacenter.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BizController {
    @GetMapping("/getMediaList")
    @ResponseBody
    public Object getFileList(@RequestParam(value = "path") String dir, @RequestParam(value = "type", defaultValue = ".mp4") String type) {
        List list = new ArrayList();
        File file = new File(dir);
        if (file.exists()) {
            getFiles(file, list, type);
        }
        return list;
    }

    List getFiles(File file, List list, String type) {
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                getFiles(f, list, type);
            } else {
                String ext = f.getName();
                if (ext.toLowerCase().endsWith(type)) {
                    Map<String, String> m = new HashMap();
                    m.put("dir", f.getParent());
                    m.put("filename", f.getName());
                    m.put("path", f.getAbsolutePath());
                    list.add(m);
                }

            }
        }
        return list;
    }

    @GetMapping("/getDirList")
    @ResponseBody
    public Object getDirList(@RequestParam(value = "path", defaultValue = "G:\\BaiduNetdiskDownload") String dir) {
        List list = new ArrayList();
        File file = new File(dir);
        if (file.exists()) {
            list = getDirs(file,list);
        }
        return list;
    }

    List getDirs(File file, List list) {
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                Map<String, String> m = new HashMap();
                m.put("dir", f.getParent());
                m.put("filename", f.getName());
                m.put("path", f.getAbsolutePath());
                list.add(m);
                getDirs(f, list);
            }
        }
        return list;
    }

}
