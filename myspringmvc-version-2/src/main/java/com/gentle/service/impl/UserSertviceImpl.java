package com.gentle.service.impl;

import com.gentle.annotation.MyService;
import com.gentle.service.UserService;

/**
 * @author Gentle
 * @date 2019/04/07 : 16:14
 */
@MyService
public class UserSertviceImpl implements UserService {
    @Override
    public String aa() {
        return "gentle";
    }
}
