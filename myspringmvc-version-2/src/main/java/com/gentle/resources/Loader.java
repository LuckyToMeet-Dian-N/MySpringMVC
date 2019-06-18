package com.gentle.resources;

import java.io.InputStream;

/**
 * @author Gentle
 * @date 2019/06/16 : 16:50
 */
public interface Loader {
    /**
     * 载入文件
     * @param location 文件名
     */
    void loader(String location);

    /**
     * 载入文件
     * @param inputStream 文件流
     */
    void loader(InputStream inputStream);


}
