package com.gentle.resources;

/**
 * @author Gentle
 * @date 2019/06/16 : 19:38
 */
public class XmlLoader implements Loader {

    private static final String SUFFIX ="xml";

    @Override
    public void load(String filePath) {

        if (filePath==null|| "".equals(filePath)){
            throw new NullPointerException("文件路径不能为空");
        }
       String tempFilePath= filePath.substring(filePath.lastIndexOf(".")+1);
        if (!tempFilePath.equals(SUFFIX)){
            throw new IllegalArgumentException("文件不正确");
        }

    }

    public void parseXml(){





    }







}
