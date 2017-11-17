package com.pf.mock.controller;

import com.pf.mock.data.BaseResult;

public class BaseController {
    protected <T>BaseResult createResult(int code, T res) {
        BaseResult<T> result = new BaseResult<T>();
        result.setCode(code);
        result.setRes(res);

        return result;
    }
}
